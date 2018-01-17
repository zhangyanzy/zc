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
import cn.zhaocaiapp.zc_app_android.bean.response.login.CheckPhoneResp;
import cn.zhaocaiapp.zc_app_android.bean.response.login.ObtainCodeResp;
import cn.zhaocaiapp.zc_app_android.bean.response.login.VerifyCodeResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.AppUtil;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.KeyBoardUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

/**
 * Created by Administrator on 2018/1/11.
 */

public class CheckPhoneActivity extends BaseActivity {
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

    private int type;
    private String uid;
    private String phone;
    private String identify_code;
    private static final String TAG = "校验手机号";

    @Override
    public int getContentViewResId() {
        return R.layout.layout_bind_phone;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        type = getIntent().getIntExtra(Constants.SPREF.LOGIN_MODE, -1);
        uid = getIntent().getStringExtra("uid");
        showLoginMode(type);
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
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KeyBoardUtils.closeKeybord(input_phone, this);
        return super.onTouchEvent(event);
    }

    @OnClick({R.id.iv_top_back, R.id.iv_top_menu, R.id.tv_get_identify_code, R.id.tv_submit})
    public void onClick(View view) {
        phone = input_phone.getText().toString();
        switch (view.getId()) {
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.iv_top_menu:

                break;
            case R.id.tv_get_identify_code:
                if (judgePhone(phone)) {
                    waitTimer(tv_get_identify_code);
                    getIdentifyCode();
                }
                break;
            case R.id.tv_submit:
                identify_code = edit_identify_code.getText().toString();
                if (judgePhone(phone)) {
                    if (GeneralUtils.isNullOrZeroLenght(identify_code))
                        ToastUtil.makeText(CheckPhoneActivity.this, getString(R.string.input_identify_code));
                    else verifyPhone();
                }
                break;
        }
    }

    //获取验证码
    private void getIdentifyCode() {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        HttpUtil.post(Constants.URL.GET_IDENTIFY_CODE, params).subscribe(new BaseResponseObserver<ObtainCodeResp>() {

            @Override
            public void success(ObtainCodeResp result) {
                EBLog.i(TAG, result.toString());
                ToastUtil.makeText(CheckPhoneActivity.this, result.getDesc());
            }

            @Override
            public void error(Response<ObtainCodeResp> response) {
                ToastUtil.makeText(CheckPhoneActivity.this, response.getDesc());
                EBLog.i(TAG, response.getCode() + "");
            }
        });

    }

    //校验手机号是否存在
    private void verifyPhone() {
        Map<String, String> params = new HashMap<>();
        params.put("type", type + "");
        params.put("phone", phone);
        params.put("code", identify_code);
        params.put("uid", uid);
        HttpUtil.post(Constants.URL.VERIFY_CODE, params).subscribe(new BaseResponseObserver<VerifyCodeResp>() {
            @Override
            public void success(VerifyCodeResp result) {
                EBLog.i(TAG, result.getDesc());

                Bundle bundle = new Bundle();
                bundle.putInt(Constants.SPREF.LOGIN_MODE, type);
                bundle.putString(Constants.SPREF.USER_PHONE, phone);
                bundle.putString("uid", uid);
                openActivity(RegistPhoneActivity.class, bundle);

            }

            @Override
            public void error(Response<VerifyCodeResp> response) {
                ToastUtil.makeText(CheckPhoneActivity.this, response.getDesc());
                EBLog.i(TAG, response.getCode() + "");
            }

        });
    }

}
