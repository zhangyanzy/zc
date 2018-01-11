package cn.zhaocaiapp.zc_app_android.views.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.MainActivity;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.response.login.LoginResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.KeyBoardUtils;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.widget.CircleImageView;


/**
 * @author 林子
 * @filename LoginActivity.java
 * @data 2018-01-05 17:52
 */
public class LoginActivity extends BaseActivity{
    @BindView(R.id.iv_app_logo)
    ImageView iv_log;
    @BindView(R.id.tv_skip_login)
    TextView tv_skip_login;
    @BindView(R.id.edit_phone_number)
    EditText edit_phone_number;
    @BindView(R.id.edit_pass_word)
    EditText edit_pass_word;
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.tv_forget_pass)
    TextView tv_forget_pass;
    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.login_wechat)
    CircleImageView login_wechat;
    @BindView(R.id.login_qq)
    CircleImageView login_qq;
    @BindView(R.id.login_sina)
    CircleImageView login_sina;

    private String phone;
    private LoginResp loginResp;

    @Override
    public int getContentViewResId() {
        return R.layout.layout_login_main;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        GeneralUtils.addUnderLineToText(tv_forget_pass);
        GeneralUtils.addUnderLineToText(tv_register);
    }

    @OnClick({R.id.tv_skip_login, R.id.tv_register, R.id.tv_forget_pass, R.id.tv_login,
            R.id.login_wechat, R.id.login_qq, R.id.login_sina})
    public void onClick(View view) {
        phone = edit_phone_number.getText().toString();
        String pass = edit_pass_word.getText().toString();
        switch (view.getId()) {
            case R.id.tv_skip_login:
                openActivity(MainActivity.class);
                finish();
                break;
            case R.id.tv_register:
                openActivity(RegisterActivity.class);
                break;
            case R.id.tv_forget_pass:
                openActivity(ForgetPassActivity.class);
                break;
            case R.id.tv_login:
                //手机号和密码通过正则验证
                if (judgPhoneAndPass(phone, pass)) {
                    doLogin(phone, pass);
                }
                break;
            case R.id.login_wechat:
                getAuth(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.login_qq:
                getAuth(SHARE_MEDIA.QQ);
                break;
            case R.id.login_sina:
                getAuth(SHARE_MEDIA.SINA);
                break;
        }
    }

    //发送登录请求
    private void doLogin(String phone, String pass) {
        Map<String, String> params = new HashMap<>();
        params.put("type", "0");
        params.put("account", "18888888888");
        params.put("password", "123456");
        //params.put("uid", "");

        HttpUtil.post(Constants.URL.USER_LOGIN, params).subscribe(new BaseResponseObserver<LoginResp>() {

            @Override
            public void success(LoginResp result) {
                loginResp = result;
                saveUserData();
                EBLog.i(LoginActivity.this.getClass().getName(), result.toString());
            }
        });
    }

    //保存用户数据
    private void saveUserData(){
        SpUtils.put(Constants.SPREF.TOKEN, "");
    }

    //获取三方授权
    private void getAuth(SHARE_MEDIA platform) {
        UMShareAPI.get(this).doOauthVerify(this, platform, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                ToastUtil.makeText(LoginActivity.this, "授权成功");
                Log.i("UMENG", map.toString());
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                ToastUtil.makeText(LoginActivity.this, "授权失败" + "---错误码---" + i);
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KeyBoardUtils.closeKeybord(iv_log, this);
        return super.onTouchEvent(event);
    }

    private void openActivity(Class<?> mClass) {
        Intent intent = new Intent(this, mClass);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }


}
