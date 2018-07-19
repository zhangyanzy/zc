package cn.zhaocaiapp.zc_app_android.views.login;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
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
import cn.zhaocaiapp.zc_app_android.views.common.UserAgreementActivity;
import cn.zhaocaiapp.zc_app_android.views.my.AddLabelActivity;

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
    @BindView(R.id.check_agreement)
    CheckBox check_agreement;
    @BindView(R.id.tv_agreement)
    TextView tv_agreement;
    @BindView(R.id.confirm_pass_word)
    EditText confirm_pass_word;

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

    @OnClick({R.id.iv_top_back, R.id.tv_register, R.id.send_identify_code, R.id.tv_agreement})
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
            case R.id.tv_agreement:
                openActivity(UserAgreementActivity.class);
                break;
            case R.id.tv_register:
                String repass = confirm_pass_word.getText().toString();
                pass = edit_pass_word.getText().toString();
                identifyCcode = edit_identify_code.getText().toString();
                inviteCode = edit_invite_code.getText().toString();
                if (judgePhone(phone) && judgePass(pass)) {
                    if (GeneralUtils.isNullOrZeroLenght(identifyCcode))
                        ToastUtil.makeText(RegisterActivity.this, getString(R.string.input_identify_code));
                    else if (!check_agreement.isChecked())
                        ToastUtil.makeText(RegisterActivity.this, getString(R.string.check_agreement));
                    else if (!pass.equals(repass))
                        ToastUtil.makeText(RegisterActivity.this, getString(R.string.compile_pass));
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
                ToastUtil.makeText(RegisterActivity.this, getString(R.string.identifycode_send));
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
        if (GeneralUtils.isNotNullOrZeroLenght(inviteCode))
            params.put("inviteCode", inviteCode);

        HttpUtil.post(Constants.URL.REGISTER, params).subscribe(new BaseResponseObserver<SignupResp>() {
            @Override
            public void success(SignupResp result) {
                EBLog.i(TAG, result.toString());
                ToastUtil.makeText(RegisterActivity.this, result.getDesc());

                setAlias(result);
                saveUserData(result);
                notifyWake();

                Bundle bundle = new Bundle();
                bundle.putBoolean("isFirstAdd", true); //是否首次添加标签
                openActivity(AddLabelActivity.class, bundle);
                RegisterActivity.this.finish();
            }

            @Override
            public void error(Response response) {
                ToastUtil.makeText(RegisterActivity.this, response.getDesc());
                EBLog.i(TAG, response.getCode() + "");
            }
        });
    }

    private void setAlias(SignupResp result) {
        PushAgent pushAgent = PushAgent.getInstance(this);
        pushAgent.addAlias(result.getAlias(), "alias_user", new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean b, String s) {
                EBLog.i(TAG, s);
            }
        });
    }

    //保存用户数据
    private void saveUserData(SignupResp result) {
        SpUtils.init(Constants.SPREF.FILE_USER_NAME).put(Constants.SPREF.TOKEN, result.getToken());
        SpUtils.init(Constants.SPREF.FILE_USER_NAME).put(Constants.SPREF.IS_LOGIN, true);
        SpUtils.init(Constants.SPREF.FILE_USER_NAME).put(Constants.SPREF.LOGIN_MODE, 0);
        SpUtils.init(Constants.SPREF.FILE_USER_NAME).put(Constants.SPREF.USER_PHONE, result.getPhone());
        SpUtils.init(Constants.SPREF.FILE_USER_NAME).put(Constants.SPREF.USER_ID, result.getKid());
        SpUtils.init(Constants.SPREF.FILE_USER_NAME).put(Constants.SPREF.ALIAS, result.getAlias());
    }

    //通知服务器，应用已唤醒
    private void notifyWake(){
        Map<String, Integer>map = new HashMap<>();
        map.put("type", 1);
        HttpUtil.get(Constants.URL.APP_WAKE, map).subscribe(new BaseResponseObserver<String>() {

            @Override
            public void success(String s) {
                EBLog.i(TAG, s);
            }

            @Override
            public void error(Response<String> response) {
                EBLog.i(TAG, response.getCode()+"");
                ToastUtil.makeText(getApplicationContext(), response.getDesc());
            }
        });
    }

}
