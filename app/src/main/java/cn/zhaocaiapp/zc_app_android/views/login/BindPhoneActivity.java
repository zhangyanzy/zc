package cn.zhaocaiapp.zc_app_android.views.login;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.KeyBoardUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

/**
 * Created by Administrator on 2018/1/11.
 */

public class BindPhoneActivity extends BaseActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.tv_top_title)
    TextView tv_top_titlel;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
    @BindView(R.id.input_phone)
    EditText input_phone;
    @BindView(R.id.edit_identify_code)
    EditText edit_identify_code;
    @BindView(R.id.tv_get_identify_code)
    TextView tv_get_identify_code;
    @BindView(R.id.tv_login_mode)
    TextView tv_login_mode;
    @BindView(R.id.tv_submit)
    TextView tv_submit;

    private int login_mode;
    private String phone;
    private String identify_code;

    @Override
    public int getContentViewResId() {
        return R.layout.layout_bind_phone;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        login_mode = getIntent().getIntExtra(Constants.SPREF.LOGIN_MODE, -1);
        showLoginMode(login_mode);
    }

    private void showLoginMode(int mode) {
        switch (mode) {
            case Constants.SPREF.TYPE_WECHAT:
                tv_login_mode.setText("微信");
                break;
            case Constants.SPREF.TYPE_QQ:
                tv_login_mode.setText("QQ");
                break;
            case Constants.SPREF.TYPE_SINA:
                tv_login_mode.setText("新浪微博");
                break;
            case R.id.tv_submit:

                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KeyBoardUtils.closeKeybord(input_phone, this);
        return super.onTouchEvent(event);
    }

    @OnClick({R.id.iv_top_back, R.id.iv_top_menu, R.id.tv_get_identify_code})
    public void onClick(View view) {
        phone = input_phone.getText().toString();
        identify_code = edit_identify_code.getText().toString();
        switch (view.getId()) {
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.iv_top_menu:

                break;
            case R.id.tv_get_identify_code:
                if (judgePhone(phone))
                    waitTimer(tv_get_identify_code);
                    getIdentifyCode();
                break;
            case R.id.tv_submit:
                if (judgePhone(phone) && !GeneralUtils.isNullOrZeroLenght(identify_code))
                    bindPhone();
        }
    }

    //获取验证码
    private void getIdentifyCode() {
        Map<String, String>params = new HashMap<>();
        params.put("phone", phone);
        HttpUtil.post(Constants.URL.GET_IDENTIFY_CODE, params).subscribe(new BaseResponseObserver<String>() {

            @Override
            public void success(String result) {
                ToastUtil.makeText(BindPhoneActivity.this, "获取验证码成功");
            }

            @Override
            public void error(Response<String> response) {

            }
        });

    }

    //请求绑定手机号
    private void bindPhone() {

    }

}
