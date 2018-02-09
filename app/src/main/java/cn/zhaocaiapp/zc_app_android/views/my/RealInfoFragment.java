package cn.zhaocaiapp.zc_app_android.views.my;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.MessageEvent;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.CommonResp;
import cn.zhaocaiapp.zc_app_android.bean.response.my.UserDetailResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.capabilities.takephoto.PhotoHelper;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.FileUtil;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.PhotoPickerUtil;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

/**
 * Created by Administrator on 2018/1/12.
 */

public class RealInfoFragment extends BaseFragment {
    @BindView(R.id.tv_user_gender)
    TextView tv_user_gender;
    @BindView(R.id.tv_birth_day)
    TextView tv_birth_day;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.iv_scan_icture)
    ImageView iv_scan_icture;
    @BindView(R.id.edit_user_name)
    EditText edit_user_name;
    @BindView(R.id.edit_id_number)
    EditText edit_id_number;
    @BindView(R.id.tv_identify_state)
    TextView tv_identify_state;

    private View rootView;

    private OptionsPickerView optionsPickerView;
    private TimePickerView timePickerView;
    private List<String> genders = new ArrayList<>();
    private Calendar startDate;
    private Calendar endDate;
    private UserDetailResp.RealInfoBean realInfoBean;

    private String realName;
    private String userGender = "男";
    private String birthDay;
    private String idNumber;
    private String cardPath;
    private Map<String, String> params = new HashMap<>();
    private boolean isCanUpdate;

    private String name;
    private String gender;
    private String date;
    private String number;
    private String path;

    private static final String TAG = "用户实名信息";
    private static final int REQUEST_CODE_CAMERA = 101;

    @Override
    public void onStart() {
        super.onStart();
        //注册EventBus消息订阅者
        EventBus.getDefault().register(this);

    }

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_identify_fragment, container, false);
        return rootView;
    }

    @Override
    public void init() {
        setGenderPicker();
        setDatePicker();

        //监听点击空白处，隐藏软键盘
        rootView.setOnTouchListener(onTouchListener);
    }

    private void showUserInfo() {
        switch (realInfoBean.getRealInfoAuditStatus()) {
            case 0:
                tv_identify_state.setText("未认证");
                break;
            case 1:
                tv_identify_state.setText("待审核");
                break;
            case 2:
                tv_identify_state.setText("已认证");
                break;
            case 3:
                tv_identify_state.setText("未通过");
                break;
        }

        name = realInfoBean.getName();
        if (realInfoBean.getSex() == 0)
            gender = "男";
        if (realInfoBean.getSex() == 1)
            gender = "女";
        date = realInfoBean.getBirthdayStr();
        number = realInfoBean.getIdCard();
        path = realInfoBean.getIdCardPath();

        if (GeneralUtils.isNotNullOrZeroLenght(name))
            edit_user_name.setText(name);
        if (GeneralUtils.isNotNullOrZeroLenght(date))
            tv_birth_day.setText(date);
        if (GeneralUtils.isNotNullOrZeroLenght(number))
            edit_id_number.setText(number);
        tv_user_gender.setText(gender);
        if (GeneralUtils.isNotNullOrZeroLenght(path))
            PictureLoadUtil.loadPicture(getActivity(), path, iv_scan_icture);
    }

    //性别选择器初始化设置
    private void setGenderPicker() {
        genders.add("女");
        genders.add("男");
        optionsPickerView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                userGender = genders.get(options1);
                tv_user_gender.setText(userGender);
            }
        }).setTitleText("性别选择")
                .setSelectOptions(0)
                .build();
        optionsPickerView.setPicker(genders);
    }

    //日期选择起初始化设置
    private void setDatePicker() {
        Calendar selectedDate = Calendar.getInstance();
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);
        endDate.set(2100, 11, 31);
        timePickerView = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tv_birth_day.setText(getBirthTime(date));
            }
        }).setTitleText("出生日期选择")
                .setType(new boolean[]{true, true, true, false, false, false})
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setDecorView(null)
                .build();
    }

    private void reviseRealInfo() {
        HttpUtil.put(Constants.URL.REVISE_REAL_INFO, params).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                ToastUtil.makeText(getActivity(), commonResp.getDesc());
            }

            @Override
            public void error(Response<CommonResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    @Override
    public void loadData() {
    }

    //接收EventBus发送的消息，并处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        if (event.getMessage() instanceof UserDetailResp.RealInfoBean) {
            realInfoBean = (UserDetailResp.RealInfoBean) event.getMessage();
            showUserInfo();
            EBLog.i(TAG, realInfoBean.toString());
        }
    }

    @OnClick({R.id.tv_user_gender, R.id.tv_birth_day, R.id.iv_scan_icture, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_user_gender:
                manageKeyBord(tv_submit, getActivity());
                optionsPickerView.show();
                break;
            case R.id.tv_birth_day:
                manageKeyBord(tv_submit, getActivity());
                timePickerView.show();
                break;
            case R.id.iv_scan_icture:
                if (GeneralUtils.isNullOrZeroLenght(ZcApplication.getLicenceToken())) {
                    ToastUtil.makeText(getActivity(), "获取百度身份证识别LICENCE授权失败");
                } else {
                    Intent intent = new Intent(getActivity(), CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getActivity()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                }
                break;
            case R.id.tv_submit:
                verify();
                if (isCanUpdate) reviseRealInfo();
                break;
        }
    }

    private void verify() {
        realName = edit_user_name.getText().toString();
        idNumber = edit_id_number.getText().toString();
        birthDay = tv_birth_day.getText().toString();

        if (GeneralUtils.isNullOrZeroLenght(realName)) {
            ToastUtil.makeText(getActivity(), "用户姓名不能为空");
            isCanUpdate = false;
            return;
        } else if (!realName.equals(name)) {
            params.put("name", realName);
            isCanUpdate = true;
        }
        if (!userGender.equals(gender)) {
            if (userGender.equals("男"))
                params.put("sex", "0");
            if (userGender.equals("女"))
                params.put("sex", "1");
            isCanUpdate = true;
        }
        if (GeneralUtils.isNullOrZeroLenght(birthDay)) {
            ToastUtil.makeText(getActivity(), "用户出生年月不能为空");
            isCanUpdate = false;
            return;
        } else if (!birthDay.equals(date)) {
            params.put("birthdayStr", birthDay);
            isCanUpdate = true;
        }
        if (GeneralUtils.isNullOrZeroLenght(idNumber)) {
            ToastUtil.makeText(getActivity(), "用户身份证号不能为空");
            isCanUpdate = false;
            return;
        } else if (!idNumber.equals(number)) {
            params.put("idCard", idNumber);
            isCanUpdate = true;
        }
        if (GeneralUtils.isNullOrZeroLenght(cardPath)) {
            ToastUtil.makeText(getActivity(), "身份证上传失败");
            isCanUpdate = false;
            return;
        } else if (!cardPath.equals(path)) {
            params.put("idCardPath", cardPath);
            isCanUpdate = true;
        }
    }

    private String getBirthTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    //识别身份证信息
    private void recIDCard(String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(IDCardParams.ID_CARD_SIDE_FRONT);
        // 设置方向检测
        param.setDetectDirection(false);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);

        OCR.getInstance().recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                EBLog.i(TAG, result.toString());
                if (result != null) {
                    if (GeneralUtils.isNotNull(result.getName()))
                        edit_user_name.setText(result.getName().toString());
                    if (GeneralUtils.isNotNull(result.getGender()))
                        tv_user_gender.setText(result.getGender().toString());
                    if (GeneralUtils.isNotNull(result.getBirthday()))
                        tv_birth_day.setText(GeneralUtils.splitTodate(result.getBirthday().toString()));
                    if (GeneralUtils.isNotNull(result.getIdNumber()))
                        edit_id_number.setText(result.getIdNumber().toString());

                    uploadImage(new File(filePath));
                }
            }

            @Override
            public void onError(OCRError error) {
                EBLog.e(TAG, error.getMessage());
            }
        });
    }

    private void uploadImage(File file) {
        Map<String, String> map = new HashMap<>();
        map.put("postfix", ".jpg");
        map.put("base64Str", FileUtil.fileToStream(file));

        HttpUtil.post(Constants.URL.UPLOAD_IMAGE, map).subscribe(new BaseResponseObserver<String>() {

            @Override
            public void success(String s) {
                EBLog.i(TAG, s);
                cardPath = s;
                PictureLoadUtil.loadPicture(getActivity(), cardPath, iv_scan_icture);
            }

            @Override
            public void error(Response<String> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 识别成功回调，身份证识别
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                String filePath = FileUtil.getSaveFile(getActivity()).getAbsolutePath();
                if (!contentType.isEmpty()) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        recIDCard(filePath);
                    }
                }
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
