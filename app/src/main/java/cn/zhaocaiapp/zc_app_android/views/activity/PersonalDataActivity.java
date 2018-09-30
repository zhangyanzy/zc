package cn.zhaocaiapp.zc_app_android.views.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jph.takephoto.model.TResult;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;


import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseCompatActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.MessageEvent;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.request.NewPersonalDataEntity;
import cn.zhaocaiapp.zc_app_android.bean.response.common.CommonResp;
import cn.zhaocaiapp.zc_app_android.bean.response.login.ObtainCodeResp;
import cn.zhaocaiapp.zc_app_android.bean.response.my.PersonalDataEntity;
import cn.zhaocaiapp.zc_app_android.bean.response.my.UserDetailResp;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.animation.slide.SlideRightEnter;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.widget.NormalInputDialog;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.widget.TrembleBasesOsDialog;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.capabilities.takephoto.PhotoHelper;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.databinding.ActivityPersonalDataBinding;
import cn.zhaocaiapp.zc_app_android.util.ActivityUtil;
import cn.zhaocaiapp.zc_app_android.util.FileUtil;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.PhotoPickerUtil;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtils;
import cn.zhaocaiapp.zc_app_android.views.commerclal.PayActivity;
import cn.zhaocaiapp.zc_app_android.views.login.LoginActivity;
import cn.zhaocaiapp.zc_app_android.views.my.AddLabelActivity;
import cn.zhaocaiapp.zc_app_android.views.my.UserInfoActivity;
import cn.zhaocaiapp.zc_app_android.widget.CircleImageView;
import cn.zhaocaiapp.zc_app_android.widget.NavigationTopBar;

public class PersonalDataActivity extends BaseCompatActivity implements NavigationTopBar.NavigationTopBarClickListener {

    private ActivityPersonalDataBinding binding;
    private NavigationTopBar mNavigationTopBar;

    private PersonalDataEntity mEntity;
    private TrembleBasesOsDialog trembleBasesOsDialog;
    private PhotoHelper photoHelper;
    private String imgUrl;//用户头像地址
    private String mNikeName;//用户昵称

    private NormalInputDialog inputDialog1;
    private NormalInputDialog inputDialog2;

    private UserDetailResp.ActivityInfoBean activityInfoBean;
    private UserDetailResp.BaseInfoBean baseInfoBean;
    private UserDetailResp.RealInfoBean realInfoBean;

    private static final String TAG = "个人资料";
    private CircleImageView mPhoto;
    private static final int MAX_LENGTH = 20;

    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personal_data);
        binding.setPresenter(new Presenter());
        ActivityUtil.addActivity(this);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        trembleBasesOsDialog = new TrembleBasesOsDialog(this);
        trembleBasesOsDialog.setOnDialogClickListener(trembleListener);
        photoHelper = PhotoHelper.of(binding.personalDataRoot, true);
        mPhoto = findViewById(R.id.change_photo);

        //初始化输入框弹窗
        inputDialog1 = new NormalInputDialog(this);
        inputDialog1.setTitle("验证原手机");
        inputDialog1.setOnDialogClickListener(inputListener1);
        binding.editUserNickname.setSelection(binding.editUserNickname.getText().length());
    }


    private NormalInputDialog.OnDialogClickListener inputListener1 = new NormalInputDialog.OnDialogClickListener() {
        @Override
        public void onDialogClick(View view, @Nullable String str1, String str2) {
            String phone = str1;
            String code = str2;
            if (view.getId() == R.id.tv_get_idntify_code) {
                if (GeneralUtils.isNullOrZeroLenght(phone)) {
                    ToastUtil.makeText(PersonalDataActivity.this, getString(R.string.input_phone_number));
                } else {
                    requestCheckIdentifyCode(phone, view);
                }
            }
            if (view.getId() == R.id.tv_next) {
                if (GeneralUtils.isNullOrZeroLenght(phone))
                    ToastUtil.makeText(PersonalDataActivity.this, getString(R.string.input_phone_number));
                else if (GeneralUtils.isNullOrZeroLenght(code))
                    ToastUtil.makeText(PersonalDataActivity.this, getString(R.string.input_identify_code));
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
                    ToastUtil.makeText(PersonalDataActivity.this, getString(R.string.input_phone_number));
                } else {
//                    waitTimer((TextView) view);
                    modifyCheckIdentifyCode(phone, view);

                }
            }
            if (view.getId() == R.id.tv_next) {
                if (GeneralUtils.isNullOrZeroLenght(phone))
                    ToastUtil.makeText(PersonalDataActivity.this, getString(R.string.input_phone_number));
                else if (GeneralUtils.isNullOrZeroLenght(code))
                    ToastUtil.makeText(PersonalDataActivity.this, getString(R.string.input_identify_code));
                else doRevisePhone(phone, code);
            }
        }
    };

    //提交新手机号
    private void doRevisePhone(String phone, String code) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("code", code);

        HttpUtil.post(Constants.URL.MODIFY_PHONR, params).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                EBLog.i(TAG, commonResp.getDesc());
                binding.phoneNum.setText(phone);
                inputDialog2.dismiss();
            }

            @Override
            public void error(Response<CommonResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(PersonalDataActivity.this, response.getDesc());
            }
        });
    }


    //修改手机号码页面请求验证码
    private void modifyCheckIdentifyCode(String phone, View view) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        HttpUtil.post(Constants.URL.MODIFY_PHONE_OBTAINCODE, params).subscribe(new BaseResponseObserver<ObtainCodeResp>() {

            @Override
            public void success(ObtainCodeResp result) {
                EBLog.i(TAG, result.toString());
                ToastUtil.makeText(PersonalDataActivity.this, result.getDesc());
                waitTimer((TextView) view);
            }

            @Override
            public void error(Response<ObtainCodeResp> response) {
                ToastUtil.makeText(PersonalDataActivity.this, response.getDesc());
                EBLog.i(TAG, response.getCode() + "");
            }
        });
    }


    //校验原手机号码页面请求验证码
    private void requestCheckIdentifyCode(String phone, View view) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        HttpUtil.post(Constants.URL.CHECK_PHONE_OBTAINCODE, params).subscribe(new BaseResponseObserver<ObtainCodeResp>() {

            @Override
            public void success(ObtainCodeResp result) {
                EBLog.i(TAG, result.toString());
                ToastUtil.makeText(PersonalDataActivity.this, result.getDesc());
                waitTimer((TextView) view);
            }

            @Override
            public void error(Response<ObtainCodeResp> response) {
                ToastUtil.makeText(PersonalDataActivity.this, response.getDesc());
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
                inputDialog2 = new NormalInputDialog(PersonalDataActivity.this);
                inputDialog2.setTitle("绑定新手机");
                inputDialog2.setButton("确认绑定");
                inputDialog2.setOnDialogClickListener(inputListener2);
                inputDialog2.showAnim(new SlideRightEnter());
                inputDialog2.show();
            }

            @Override
            public void error(Response<CommonResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(PersonalDataActivity.this, response.getDesc());
            }
        });
    }

    private TrembleBasesOsDialog.OnDialogClickListener trembleListener = new TrembleBasesOsDialog.OnDialogClickListener() {
        @Override
        public void onDialogClick(int resId) {
            if (resId == R.id.tv_exit) {
                doLoginOut();
            } else {
                trembleBasesOsDialog.dismiss();
            }


        }
    };

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        mNavigationTopBar = findViewById(R.id.personal_data_bar);
        mNavigationTopBar.setLeftImageResource(R.mipmap.finish_icon);
        mNavigationTopBar.setCenterTitleText("个人资料");
        mNavigationTopBar.setCenterTitleTextColor(R.color.colorBlack);
        mNavigationTopBar.setNavigationTopBarClickListener(this);
        mNavigationTopBar.setRightImageResource(R.mipmap.change_sure);
    }

    private void getUserInfo() {
        HttpUtil.get(Constants.URL.PERSONAL_DATA).subscribe(new BaseResponseObserver<PersonalDataEntity>() {

            @Override
            public void success(PersonalDataEntity entities) {
                if (entities != null) {
                    mEntity = entities;
                    showUserInfo();
                }
            }

            @Override
            public void error(Response<PersonalDataEntity> response) {
                ToastUtils.showShortToast(PersonalDataActivity.this, response.getDesc());
            }
        });
    }

    //头像上传
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
                    PictureLoadUtil.loadPicture(PersonalDataActivity.this, imgUrl, mPhoto);
                    SpUtils.init(Constants.SPREF.FILE_USER_NAME).put(Constants.SPREF.USER_PHOTO, imgUrl);
                } else
                    ToastUtil.makeText(PersonalDataActivity.this, getString(R.string.avater_upload_failure));
            }

            @Override
            public void error(Response<String> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(PersonalDataActivity.this, response.getDesc());
            }
        });
    }

    private void showUserInfo() {
        if (mEntity != null) {
            if (Objects.equals(mEntity.getAvatar(), "")) {
                mPhoto.setImageResource(R.mipmap.user_default);
            } else {
                Glide.with(this).load(mEntity.getAvatar()).into(mPhoto);
            }
            binding.editUserNickname.setText(mEntity.getNickname());
            binding.phoneNum.setText(mEntity.getPhone());

            if (mEntity.getHomeAddressDetail().equals("")) {
                binding.homeAddressInfo.setText("待完善");
            } else {
                binding.homeAddressInfo.setText("已完善");
            }
            if (mEntity.getCompanyAddressDetail().equals("")) {
                binding.companyAddressInfo.setText("待完善");
            } else {
                binding.companyAddressInfo.setText("已完善");
            }
            if (mEntity.getRealInfoAuditStatus().equals("0")) {
                binding.realNameInfo.setText("未认证");
            } else if (mEntity.getRealInfoAuditStatus().equals("1")) {
                binding.realNameInfo.setText("待审核");
            } else if (mEntity.getRealInfoAuditStatus().equals("2")) {
                binding.realNameInfo.setText("已认证");
            } else if (mEntity.getRealInfoAuditStatus().equals("3")) {
                binding.realNameInfo.setText("未通过");
            }
        }
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        getUserInfo();
    }

    @Override
    public void leftImageClick() {
        finish();
    }

    @Override
    public void rightImageClick() {
        if (isNotEmpty()) {
            postUserInfo();
        }
    }

    @Override
    public void alignRightLeftImageClick() {

    }

    private void deleteAlias() {
        PushAgent pushAgent = PushAgent.getInstance(this);
        pushAgent.deleteAlias((String) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.ALIAS, ""), "alias_user", new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean b, String s) {
                EBLog.i(TAG, s);
            }
        });
    }

    private void postUserInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("avatar", imgUrl);
        params.put("nickname", mNikeName);
        HttpUtil.put(Constants.URL.PUT_PERSONAL_DATA, params).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                ToastUtil.makeText(PersonalDataActivity.this, commonResp.getDesc());
            }

            @Override
            public void error(Response<CommonResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(PersonalDataActivity.this, response.getDesc());
            }
        });
    }

    //退出登录
    private void doLoginOut() {
        Map<String, String> params = new HashMap<>();
        params.put("token", (String) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.TOKEN, ""));
        HttpUtil.post(Constants.URL.USER_LOGIN_OUT, params).subscribe(new BaseResponseObserver<CommonResp>() {
            @Override
            public void success(CommonResp result) {
                EBLog.i(TAG, result.toString());
                deleteAlias();
                SpUtils.init(Constants.SPREF.FILE_USER_NAME).clear();
                Bundle bundle = new Bundle();
                bundle.putBoolean("signOut", true);
                openActivity(LoginActivity.class, bundle);
            }

            @Override
            public void error(Response<CommonResp> response) {
                ToastUtil.makeText(PersonalDataActivity.this, response.getDesc());
                EBLog.i(TAG, response.getCode() + "");
            }
        });
    }

    private boolean isNotEmpty() {
        mNikeName = binding.editUserNickname.getText().toString();
        if (GeneralUtils.isNullOrZeroLenght(mNikeName)) {
            ToastUtils.showShortToast(this, "用户名不能为空");
            return false;
        }
        if (binding.editUserNickname.length() > 20) {
            ToastUtils.showShortToast(this, "用户名做多支持10个汉字");
            return false;
        }
        return true;
    }

    private void postPhoto(View view, Context context) {
        manageKeyBord(view, this);
        //弹出获取照片选择框
        PhotoPickerUtil.init(context);
        PhotoPickerUtil.setContent("选择照片", new String[]{"拍照", "从相册选择"}, null);
        PhotoPickerUtil.show(photoListener);
    }

    private PhotoPickerUtil.OnItemClickListener photoListener = new PhotoPickerUtil.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            photoHelper.onClick(position, getTakePhoto());
        }
    };

    public class Presenter {
        public void onClick(View view) {
            Intent intent = null;
            switch (view.getId()) {
                case R.id.change_photo:
                    postPhoto(binding.logoutBtn, PersonalDataActivity.this);
                    break;
                case R.id.nikeName_root:
                    break;
                case R.id.change_phone:
                    inputDialog1.show();
                    break;
                case R.id.home_address_root:
                    intent = new Intent(PersonalDataActivity.this, HomeAddressActivity.class);
                    if (mEntity.getHomeProvinceName() != null && !mEntity.getHomeProvinceName().equals("")) {
                        String hpn = mEntity.getHomeProvinceName();
                        intent.putExtra("hpn", hpn);
                    }
                    if (mEntity.getHomeCityName() != null && !mEntity.getHomeCityName().equals("")) {
                        String hcn = mEntity.getHomeCityName();
                        intent.putExtra("hcn", hcn);
                    }
                    if (mEntity.getHomeAreaName() != null && !mEntity.getHomeAreaName().equals("")) {
                        String han = mEntity.getHomeAreaName();
                        intent.putExtra("han", han);
                    }
                    if (mEntity.getHomeAddressDetail() != null && !mEntity.getHomeAddressDetail().equals("")) {
                        String detailAddress = mEntity.getHomeAddressDetail();
                        intent.putExtra("detailAddress", detailAddress);
                    }
                    startActivity(intent);
                    break;
                case R.id.company_address_root:
                    intent = new Intent(PersonalDataActivity.this, CompanyAddressActivity.class);
                    if (!GeneralUtils.isNullOrZeroLenght(mEntity.getCompanyCityName())) {
                        String ccn = mEntity.getCompanyCityName();
                        intent.putExtra("ccn", ccn);
                    }
                    if (!GeneralUtils.isNullOrZeroLenght(mEntity.getCompanyProvinceName())) {
                        String cpn = mEntity.getCompanyProvinceName();
                        intent.putExtra("cpn", cpn);
                    }
                    if (!GeneralUtils.isNullOrZeroLenght(mEntity.getCompanyAreaName())) {
                        String can = mEntity.getCompanyAreaName();
                        intent.putExtra("can", can);
                    }
                    if (!GeneralUtils.isNullOrZeroLenght(mEntity.getCompanyAddressDetail())) {
                        String companyDetail = mEntity.getCompanyAddressDetail();
                        intent.putExtra("companyDetail", companyDetail);
                    }
                    startActivity(intent);
                    break;
                case R.id.real_name_root:
                    intent = new Intent(PersonalDataActivity.this, RealNameActivity.class);
                    startActivity(intent);
                    break;
                case R.id.individual_label_root:
                    openActivity(LabelActivity.class);
                    break;
                case R.id.logout_btn:
                    trembleBasesOsDialog.show();
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String imageUrl = result.getImage().getCompressPath();
        File file = new File(imageUrl);
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
    protected void onResume() {
        super.onResume();
        getUserInfo();
    }
}

