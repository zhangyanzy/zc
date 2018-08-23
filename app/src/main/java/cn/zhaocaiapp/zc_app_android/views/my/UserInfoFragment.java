package cn.zhaocaiapp.zc_app_android.views.my;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.jph.takephoto.model.TResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
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
import cn.zhaocaiapp.zc_app_android.bean.response.home.LocationResp;
import cn.zhaocaiapp.zc_app_android.bean.response.login.ObtainCodeResp;
import cn.zhaocaiapp.zc_app_android.bean.response.my.UserDetailResp;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.animation.slide.SlideRightEnter;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.widget.NormalInputDialog;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.capabilities.takephoto.PhotoHelper;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.FileUtil;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.PhotoPickerUtil;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.widget.CircleImageView;

/**
 * Created by Administrator on 2018/1/12.
 */

public class UserInfoFragment extends BaseFragment {
    @BindView(R.id.iv_user_photo)
    CircleImageView iv_user_photo;
    @BindView(R.id.tv_change_photo)
    TextView tv_change_photo;
    @BindView(R.id.tv_revise_phone)
    TextView tv_revise_phone;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.edit_user_address)
    TextView edit_user_address;
    @BindView(R.id.edit_company_address)
    TextView edit_company_address;
    @BindView(R.id.edit_user_nickname)
    EditText edit_user_nickname;
    @BindView(R.id.edit_user_phone)
    TextView edit_user_phone;
    @BindView(R.id.home_address_detail)
    EditText home_address_detail;
    @BindView(R.id.company_address_detail)
    EditText company_address_detail;
    @BindView(R.id.iv_select_ha)
    ImageView iv_select_ha;
    @BindView(R.id.iv_select_ca)
    ImageView iv_select_ca;

    private TextView identify_code;

    private View rootView;
    private PhotoHelper photoHelper;

    private List<LocationResp> provinces;
    private List<List<LocationResp>> citys;
    private List<List<List<LocationResp>>> towns;
    private OptionsPickerView optionsPickerView;

    private UserDetailResp.BaseInfoBean baseInfoBean;
    private String nickName;
    private String homeAddress;
    private String companyAddress;
    private String homeAddressDetail;
    private String companyAddressDetail;
    private long hpCode;//家庭省代码
    private String hpName;//家庭省名称
    private long hcCode;//家庭市代码
    private String hcName;//家庭市名称
    private long htCode;//家庭区代码
    private String htName;//家庭区名称
    private long cpCode;//公司省代码
    private String cpName;//公司省名称
    private long ccCode;//公司市代码
    private String ccName;//公司市名称
    private long ctCode;//公司区代码
    private String ctName;//公司区名称

    private String hAddress;//家庭住址省市区
    private String hDetail;//家庭详细住址
    private String cAddress;//公司地址省市区
    private String cDetail;//公司详细地址
    private String name;//用户昵称
    private String imgUrl;//用户头像地址

    private int addressType; // 0-家庭住址  1-公司地址

    private NormalInputDialog inputDialog1;
    private NormalInputDialog inputDialog2;
    private static final int REQUEST_CODE = 3001;

    private static final String TAG = "个人资料";

    @Override
    public void onStart() {
        super.onStart();
        //注册EventBus消息订阅者
        EventBus.getDefault().register(this);
    }

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_userinfo_fragment, container, false);
        return rootView;
    }

    @Override
    public void init() {
        //初始化城市列表
        citys = new ArrayList<>();
        towns = new ArrayList<>();
        getAreasList();

        //初始化照片选择器设置
        photoHelper = PhotoHelper.of(rootView, true);

        //监听点击空白处，隐藏软键盘
        rootView.setOnTouchListener(onTouchListener);

        //初始化输入框弹窗
        inputDialog1 = new NormalInputDialog(getActivity());
        inputDialog1.setTitle("验证原手机");
        inputDialog1.setOnDialogClickListener(inputListener1);

    }

    //城市选择器初始化和设置
    private void setCityPicker() {
        optionsPickerView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                LocationResp province = provinces.get(options1);
                LocationResp city = citys.get(options1).get(options2);
                LocationResp town = towns.get(options1).get(options2).get(options3);

                if (addressType == 0) {//家庭地址
                    hpCode = province.getAreaCode();
                    hpName = province.getAreaName();
                    hcCode = city.getAreaCode();
                    hcName = city.getAreaName();
                    htCode = town.getAreaCode();
                    htName = town.getAreaName();
                    edit_user_address.setText(hpName + hcName + htName);
                }
                if (addressType == 1) {//公司地址
                    cpCode = province.getAreaCode();
                    cpName = province.getAreaName();
                    ccCode = city.getAreaCode();
                    ccName = city.getAreaName();
                    ctCode = town.getAreaCode();
                    ctName = town.getAreaName();
                    edit_company_address.setText(cpName + ccName + ctName);
                }
            }
        }).setTitleText("城市选择")
                .build();
        if (GeneralUtils.isNotNullOrZeroSize(provinces) && GeneralUtils.isNotNullOrZeroSize(citys) && GeneralUtils.isNotNullOrZeroSize(towns))
            optionsPickerView.setPicker(provinces, citys, towns);
    }

    @Override
    public void loadData() {
    }

    //显示用户信息
    private void showUserInfo() {
        hpCode = baseInfoBean.getHomeProvinceCode();
        hpName = baseInfoBean.getHomeProvinceName();
        hcCode = baseInfoBean.getHomeCityCode();
        hcName = baseInfoBean.getHomeCityName();
        htCode = baseInfoBean.getHomeAreaCode();
        htName = baseInfoBean.getHomeAreaName();
        cpCode = baseInfoBean.getCompanyProvinceCode();
        cpName = baseInfoBean.getCompanyProvinceName();
        ccCode = baseInfoBean.getCompanyCityCode();
        ccName = baseInfoBean.getCompanyCityName();
        ctCode = baseInfoBean.getCompanyAreaCode();
        ctName = baseInfoBean.getCompanyAreaName();

        name = baseInfoBean.getNickname();
        imgUrl = baseInfoBean.getAvatar();
        hAddress = baseInfoBean.getHomeProvinceName() + baseInfoBean.getHomeCityName() + baseInfoBean.getHomeAreaName();
        hDetail = baseInfoBean.getHomeAddressDetail();
        cAddress = baseInfoBean.getCompanyProvinceName() + baseInfoBean.getCompanyCityName() + baseInfoBean.getCompanyAreaName();
        cDetail = baseInfoBean.getCompanyAddressDetail();

        if (GeneralUtils.isNotNullOrZeroLenght(name))
            edit_user_nickname.setText(name);
        if (GeneralUtils.isNotNullOrZeroLenght(hAddress))
            edit_user_address.setText(hAddress);
        if (GeneralUtils.isNotNullOrZeroLenght(hDetail))
            home_address_detail.setText(hDetail);
        if (GeneralUtils.isNotNullOrZeroLenght(cAddress))
            edit_company_address.setText(cAddress);
        if (GeneralUtils.isNotNullOrZeroLenght(cDetail))
            company_address_detail.setText(cDetail);
        if (GeneralUtils.isNotNullOrZeroLenght(imgUrl))
            PictureLoadUtil.loadPicture(getActivity(), imgUrl, iv_user_photo);
        edit_user_phone.setText(baseInfoBean.getPhone());
    }

    //接收EventBus发送的消息，并处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        if (event.getMessage() instanceof UserDetailResp.BaseInfoBean) {
            baseInfoBean = (UserDetailResp.BaseInfoBean) event.getMessage();
            showUserInfo();
            EBLog.i(TAG, baseInfoBean.toString());
        }
    }

    //获取省、市、区三级列表
    private void getAreasList() {
        provinces = ZcApplication.getProvinces();
        for (int i = 0; i < provinces.size(); i++) {
            List<LocationResp> cityList = provinces.get(i).getAreaList();//该省的城市列表（第二级）
            List<List<LocationResp>> province_townList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int j = 0; j < cityList.size(); j++) {
                List<LocationResp> city_townList = cityList.get(j).getAreaList();
                province_townList.add(city_townList);
            }
            citys.add(cityList);
            towns.add(province_townList);
        }
        setCityPicker();
    }

    @OnClick({R.id.iv_user_photo, R.id.tv_change_photo, R.id.tv_revise_phone, R.id.tv_submit,
            R.id.edit_user_address, R.id.edit_company_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_photo: //更换头像
            case R.id.tv_change_photo:
                manageKeyBord(tv_submit, getActivity());
                //弹出获取照片选择框
                PhotoPickerUtil.init(getActivity());
                PhotoPickerUtil.setContent("选择照片", new String[]{"拍照", "从相册选择"}, null);
                PhotoPickerUtil.show(photoListener);
                break;
            case R.id.edit_user_address: //选择家庭地址
            case R.id.iv_select_ha:
                addressType = 0;
                optionsPickerView.show();
                break;
            case R.id.edit_company_address: //选择公司地址
            case R.id.iv_select_ca:
                addressType = 1;
                optionsPickerView.show();
                break;
            case R.id.tv_revise_phone: // 更换手机号
                inputDialog1.show();
                break;
            case R.id.tv_submit:
                if (isNotEmpty() && isCanUpdate()) reviceBaseInfo();
                break;
            default:
                break;
        }
    }

    //判断内容是否为空
    private boolean isNotEmpty() {
        nickName = edit_user_nickname.getText().toString();
        homeAddress = edit_user_address.getText().toString();
        homeAddressDetail = home_address_detail.getText().toString();
        companyAddress = edit_company_address.getText().toString();
        companyAddressDetail = company_address_detail.getText().toString();

//        if (GeneralUtils.isNullOrZeroLenght(imgUrl)) {
//            ToastUtil.makeText(getActivity(), "用户头像不能为空");
//            return false;
//        }
        if (GeneralUtils.isNullOrZeroLenght(nickName)) {
            ToastUtil.makeText(getActivity(), "用户昵称不能为空");
            return false;
        }
        if (GeneralUtils.isNullOrZeroLenght(homeAddress) || GeneralUtils.isNullOrZeroLenght(homeAddressDetail)) {
            ToastUtil.makeText(getActivity(), "家庭地址不能为空");
            return false;
        }
//        if (GeneralUtils.isNullOrZeroLenght(companyAddress) || GeneralUtils.isNullOrZeroLenght(companyAddressDetail)) {
//            ToastUtil.makeText(getActivity(), "公司地址不能为空");
//            return false;
//        }
        return true;

    }

    //校验是否可以上传到服务器
    private boolean isCanUpdate() {
        if (nickName.equals(name) && homeAddress.equals(hAddress) && homeAddressDetail.equals(hDetail)
                && companyAddress.equals(cAddress) && companyAddressDetail.equals(cDetail) && imgUrl.equals(baseInfoBean.getAvatar())) {
            ToastUtil.makeText(getActivity(), getString(R.string.not_revise));
            return false;
        }
        return true;
    }

    private PhotoPickerUtil.OnItemClickListener photoListener = new PhotoPickerUtil.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            photoHelper.onClick(position, getTakePhoto());
        }
    };

    private void reviceBaseInfo() {
        Map<String, Object> params = new HashMap<>();
        params.put("homeProvinceCode", hpCode);
        params.put("homeProvinceName", hpName);
        params.put("homeCityCode", hcCode);
        params.put("homeCityName", hcName);
        params.put("homeAreaCode", htCode);
        params.put("homeAreaName", htName);
        params.put("homeAddressDetail", homeAddressDetail);
        params.put("companyProvinceCode", cpCode);
        params.put("companyProvinceName", cpName);
        params.put("companyCityCode", ccCode);
        params.put("companyCityName", ccName);
        params.put("companyAreaCode", ctCode);
        params.put("companyAreaName", ctName);
        params.put("companyAddressDetail", companyAddressDetail);
        params.put("nickname", nickName);
        params.put("avatar", imgUrl);

        HttpUtil.put(Constants.URL.REVISE_BASE_INFO, params).subscribe(new BaseResponseObserver<CommonResp>() {

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

    private void uploadImage(File file) {
        Map<String, String> map = new HashMap<>();
        map.put("postfix", ".jpg");
        map.put("base64Str", FileUtil.fileToStream(file));

        HttpUtil.post(Constants.URL.UPLOAD_IMAGE, map).subscribe(new BaseResponseObserver<String>() {

            @Override
            public void success(String s) {
                EBLog.i(TAG, s);

                if (GeneralUtils.isNotNullOrZeroLenght(s)) {
                    imgUrl = s;
                    PictureLoadUtil.loadPicture(getActivity(), imgUrl, iv_user_photo);
                    SpUtils.init(Constants.SPREF.FILE_USER_NAME).put(Constants.SPREF.USER_PHOTO, imgUrl);
                } else ToastUtil.makeText(getActivity(), getString(R.string.avater_upload_failure));
            }

            @Override
            public void error(Response<String> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    private NormalInputDialog.OnDialogClickListener inputListener1 = new NormalInputDialog.OnDialogClickListener() {
        @Override
        public void onDialogClick(View view, @Nullable String str1, String str2) {
            String phone = str1;
            String code = str2;
            if (view.getId() == R.id.tv_get_idntify_code) {
                if (GeneralUtils.isNullOrZeroLenght(phone)) {
                    ToastUtil.makeText(getActivity(), getString(R.string.input_phone_number));
                } else {
                    requestCheckIdentifyCode(phone,view);
                }
            }
            if (view.getId() == R.id.tv_next) {
                if (GeneralUtils.isNullOrZeroLenght(phone))
                    ToastUtil.makeText(getActivity(), getString(R.string.input_phone_number));
                else if (GeneralUtils.isNullOrZeroLenght(code))
                    ToastUtil.makeText(getActivity(), getString(R.string.input_identify_code));
                else verifyPhone(phone, code);
            }
        }
    };

    private NormalInputDialog.OnDialogClickListener inputListener2 = new NormalInputDialog.OnDialogClickListener() {
        @Override
        public void onDialogClick(View view, @Nullable String str1, String str2) {
            String phone = str1;
            String code = str2;
            if (view.getId() == R.id.tv_get_idntify_code) {
                if (GeneralUtils.isNullOrZeroLenght(phone)) {
                    ToastUtil.makeText(getActivity(), getString(R.string.input_phone_number));
                } else {
//                    waitTimer((TextView) view);
                    modifyCheckIdentifyCode(phone,view);

                }
            }
            if (view.getId() == R.id.tv_next) {
                if (GeneralUtils.isNullOrZeroLenght(phone))
                    ToastUtil.makeText(getActivity(), getString(R.string.input_phone_number));
                else if (GeneralUtils.isNullOrZeroLenght(code))
                    ToastUtil.makeText(getActivity(), getString(R.string.input_identify_code));
                else doRevisePhone(phone, code);
            }
        }
    };

    //校验原手机号码页面请求验证码
    private void requestCheckIdentifyCode(String phone,View view) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        HttpUtil.post(Constants.URL.CHECK_PHONE_OBTAINCODE, params).subscribe(new BaseResponseObserver<ObtainCodeResp>() {

            @Override
            public void success(ObtainCodeResp result) {
                EBLog.i(TAG, result.toString());
                ToastUtil.makeText(getActivity(), result.getDesc());
                waitTimer((TextView) view);
            }

            @Override
            public void error(Response<ObtainCodeResp> response) {
                ToastUtil.makeText(getActivity(), response.getDesc());
                EBLog.i(TAG, response.getCode() + "");
            }
        });
    }

    //修改手机号码页面请求验证码
    private void modifyCheckIdentifyCode(String phone,View view) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        HttpUtil.post(Constants.URL.MODIFY_PHONE_OBTAINCODE, params).subscribe(new BaseResponseObserver<ObtainCodeResp>() {

            @Override
            public void success(ObtainCodeResp result) {
                EBLog.i(TAG, result.toString());
                ToastUtil.makeText(getActivity(), result.getDesc());
                waitTimer((TextView) view);
            }

            @Override
            public void error(Response<ObtainCodeResp> response) {
                ToastUtil.makeText(getActivity(), response.getDesc());
                EBLog.i(TAG, response.getCode() + "");
            }
        });
    }

    //校验手机号
    private void verifyPhone(String phone, String code) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("code", code);
        HttpUtil.post(Constants.URL.VEIRFY_CODE, params).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                EBLog.i(TAG, commonResp.getDesc());

                inputDialog1.dismiss();
                inputDialog2 = new NormalInputDialog(getActivity());
                inputDialog2.setTitle("绑定新手机");
                inputDialog2.setButton("确认绑定");
                inputDialog2.setOnDialogClickListener(inputListener2);
                inputDialog2.showAnim(new SlideRightEnter());
                inputDialog2.show();
            }

            @Override
            public void error(Response<CommonResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    //提交新手机号
    private void doRevisePhone(String phone, String code) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("code", code);

        HttpUtil.post(Constants.URL.MODIFY_PHONR, params).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                EBLog.i(TAG, commonResp.getDesc());
                edit_user_phone.setText(phone);
                inputDialog2.dismiss();
            }

            @Override
            public void error(Response<CommonResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String imgUrl = result.getImage().getCompressPath();
        File file = new File(imgUrl);
        uploadImage(file);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == ChangePhoneActivity.RESULT_CODE) {
            String phone = data.getStringExtra("phone");
            SpUtils.init(Constants.SPREF.FILE_USER_NAME).put(Constants.SPREF.USER_PHONE, phone);
            edit_user_phone.setText(phone);
        }
    }

    /**
     * 开启计时器
     */
    protected void waitTimer(TextView identify_code) {
        this.identify_code = identify_code;
        identify_code.setBackgroundResource(R.drawable.button_shape_gray_bg);
        identify_code.setEnabled(false);
        timer.start();
    }

    private CountDownTimer timer = new CountDownTimer(61000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            long delayTime = millisUntilFinished / 1000;
            identify_code.setText(String.format(getString(R.string.delay_time), delayTime));
        }

        @Override
        public void onFinish() {
            identify_code.setBackgroundResource(R.drawable.button_shape_orange_bg);
            identify_code.setText(getString(R.string.get_identify_code));
            identify_code.setEnabled(true);
            cancel();
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
