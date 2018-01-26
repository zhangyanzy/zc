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

    private View rootView;

    private OptionsPickerView optionsPickerView;
    private TimePickerView timePickerView;
    private List<String> genders = new ArrayList<>();
    private Calendar startDate;
    private Calendar endDate;
    private UserDetailResp.RealInfoBean realInfoBean;
    private PhotoHelper photoHelper;

    private String name;
    private String idCard;
    private int sex;
    private String birthday;
    private String idCardPath;

    private static final String TAG = "用户实名信息";
    private static final int REQUEST_CODE_PICK_IMAGE_FRONT = 101;
    private static final int REQUEST_CODE_CAMERA = 102;

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

        //初始化照片选择器设置
        photoHelper = PhotoHelper.of(rootView, true);

        //监听点击空白处，隐藏软键盘
        rootView.setOnTouchListener(onTouchListener);
    }

    private void showUserInfo() {
        if (GeneralUtils.isNotNullOrZeroLenght(realInfoBean.getName()))
            edit_user_name.setText(realInfoBean.getName());
        if (GeneralUtils.isNotNullOrZeroLenght(realInfoBean.getIdCard()))
            edit_id_number.setText(realInfoBean.getIdCard());

        int sex = realInfoBean.getSex();
        if (sex == 0) tv_user_gender.setText("男");
        else tv_user_gender.setText("女");

    }

    //性别选择器初始化设置
    private void setGenderPicker() {
        genders.add("女");
        genders.add("男");
        optionsPickerView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tv_user_gender.setText(genders.get(options1));
            }
        }).setTitleText("性别选择")
                .setSelectOptions(0)
                .build();
        optionsPickerView.setPicker(genders);
    }

    //日期选择起初始化设置、
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
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("idCard", idCard);
        params.put("sex", sex+"");
        params.put("birthday", birthday);
        params.put("idCardPath", idCardPath);

        HttpUtil.put(Constants.URL.REVISE_REAL_INFO, params).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
               ToastUtil.makeText(getActivity(), commonResp.getDesc());
            }

            @Override
            public void error(Response<CommonResp> response) {
                EBLog.e(TAG, response.getCode()+"");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    @Override
    public void loadData() {
    }

    //接收EventBus发送的消息，并处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent<UserDetailResp.RealInfoBean> event) {
        if (event.getMessage() instanceof UserDetailResp.RealInfoBean) {
            realInfoBean = event.getMessage();
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
                    ToastUtil.makeText(getActivity(), "未获取百度身份证识别LICENCE授权");
                } else {
                    //弹出获取照片选择框
                    PhotoPickerUtil.init(getActivity());
                    PhotoPickerUtil.setContent("选择照片", new String[]{"拍照", "从相册选择"}, null);
                    PhotoPickerUtil.show(listener);
                }
                break;
            case R.id.tv_submit:
                name = edit_user_name.getText().toString();
                idCard = edit_id_number.getText().toString();
                birthday = tv_birth_day.getText().toString();
                if (tv_user_gender.getText().toString().equals("男"))
                    sex = 0;
                else sex = 1;
                if (verify()){
                    reviseRealInfo();
                }
                break;
        }
    }

    private boolean verify(){
        if (GeneralUtils.isNullOrZeroLenght(name)) {
            ToastUtil.makeText(getActivity(), "用户姓名不能为空");
            return false;
        }
        if (GeneralUtils.isNullOrZeroLenght(birthday)){
            ToastUtil.makeText(getActivity(), "用户出生年月不能为空");
            return false;
        }
        if (GeneralUtils.isNullOrZeroLenght(idCard)){
            ToastUtil.makeText(getActivity(), "用户身份证号不能为空");
            return false;
        }
        return true;
    }

    private PhotoPickerUtil.OnItemClickListener listener = new PhotoPickerUtil.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            if (position == 0) {
                Intent intent = new Intent(getActivity(), CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getActivity().getApplication()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            } else {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE_FRONT);
            }
        }
    };

    private String getBirthTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    //识别身份证信息
    private void recIDCard(String filePath) {
        idCardPath = filePath;
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
                if (result != null) {
                    edit_user_name.setText(result.getName().toString());
                    tv_user_gender.setText(result.getGender().toString());
                    tv_birth_day.setText(GeneralUtils.splitTodate(result.getBirthday().toString()));
                    edit_id_number.setText(result.getIdNumber().toString());
                    PictureLoadUtil.loadPicture(getActivity(), new File(filePath), iv_scan_icture);
                }
            }

            @Override
            public void onError(OCRError error) {
                EBLog.e(TAG, error.getMessage());
            }
        });
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_IMAGE_FRONT && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String filePath = getRealPathFromURI(uri);
            recIDCard(filePath);
        }

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                String filePath = FileUtil.getSaveFile(getActivity().getApplicationContext()).getAbsolutePath();
                if (GeneralUtils.isNotNullOrZeroLenght(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        recIDCard(filePath);
                    }
                }
            } else {
                ToastUtil.makeText(getActivity(), "身份证未识别，请重新选择照片");
            }
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OCR.getInstance().release();
    }
}
