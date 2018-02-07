package cn.zhaocaiapp.zc_app_android.views.login;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.MainActivity;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.login.ObtainCodeResp;
import cn.zhaocaiapp.zc_app_android.bean.response.login.SignupResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.KeyBoardUtils;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

/**
 * Created by ASUS on 2017/11/6.
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.layout_top_title)
    RelativeLayout layout_top_title;
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.tv_top_title)
    TextView tv_top_title;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.edit_phone_number)
    EditText edit_phone_number;
    @BindView(R.id.edit_pass_word)
    EditText edit_pass_word;
    @BindView(R.id.edit_identify_code)
    EditText edit_identify_code;
    @BindView(R.id.send_identify_code)
    TextView send_identify_code;
    @BindView(R.id.edit_invite_code)
    EditText edit_invite_code;

    private String phone;
    private String pass;
    private String identifyCcode;
    private String inviteCode = "";
    private int type = 0;
    private static final String TAG = "注册";


    @Override
    public int getContentViewResId() {
        return R.layout.layout_register_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tv_top_title.setText("注册");
        iv_top_menu.setVisibility(View.GONE);

        monitorEditChange(edit_pass_word);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KeyBoardUtils.closeKeybord(tv_register, this);
        return super.onTouchEvent(event);
    }

    @OnClick({R.id.iv_top_back, R.id.tv_register, R.id.send_identify_code})
    public void onClick(View view) {
        phone = edit_phone_number.getText().toString();
        switch (view.getId()) {
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.send_identify_code:
                if (judgePhone(phone)) {
                    waitTimer(send_identify_code);
                    requestIdentifyCode();
                }
                break;
            case R.id.tv_register:
                pass = edit_pass_word.getText().toString();
                identifyCcode = edit_identify_code.getText().toString();
                inviteCode = edit_invite_code.getText().toString();
                if (judgePhone(phone) && judgePass(pass)) {
                    if (GeneralUtils.isNullOrZeroLenght(identifyCcode))
                        ToastUtil.makeText(RegisterActivity.this, getString(R.string.input_identify_code));
                    else doRegister();
                }
                break;
        }
    }

    //获取验证码
    private void requestIdentifyCode() {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        HttpUtil.post(Constants.URL.GET_IDENTIFY_CODE, params).subscribe(new BaseResponseObserver<ObtainCodeResp>() {

            @Override
            public void success(ObtainCodeResp result) {
                EBLog.i(TAG, result.toString());
                ToastUtil.makeText(RegisterActivity.this, result.getDesc());
            }

            @Override
            public void error(Response<ObtainCodeResp> response) {
                ToastUtil.makeText(RegisterActivity.this, response.getDesc());
                EBLog.i(TAG, response.getCode() + "");
            }
        });

    }

    //请求注册账号
    private void doRegister() {
        Map<String, String> params = new HashMap<>();
        params.put("type", type + "");
        params.put("phone", phone);
        params.put("password", pass);
        params.put("code", identifyCcode);
        params.put("inviteCode", inviteCode);

        HttpUtil.post(Constants.URL.REGISTER, params).subscribe(new BaseResponseObserver<SignupResp>() {
            @Override
            public void success(SignupResp result) {
                EBLog.i(TAG, result.toString());
                ToastUtil.makeText(RegisterActivity.this, result.getDesc());

                saveUserData(result);
                Bundle bundle = new Bundle();
                bundle.putInt("position", 0);
                openActivity(MainActivity.class, bundle);
                RegisterActivity.this.finish();
            }

            @Override
            public void error(Response response) {
                ToastUtil.makeText(RegisterActivity.this, response.getDesc());
                EBLog.i(TAG, response.getCode() + "");
            }
        });
    }

    //保存用户数据
    private void saveUserData(SignupResp result) {
        SpUtils.put(Constants.SPREF.TOKEN, result.getToken());
        SpUtils.put(Constants.SPREF.IS_LOGIN, true);
        SpUtils.put(Constants.SPREF.LOGIN_MODE, 0);
        SpUtils.put(Constants.SPREF.USER_PHONE, result.getPhone());
        SpUtils.put(Constants.SPREF.USER_ID, result.getKid());
        SpUtils.put(Constants.SPREF.ALIAS, result.getAlias());
    }

}
