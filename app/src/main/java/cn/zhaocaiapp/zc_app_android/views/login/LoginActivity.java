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
import cn.zhaocaiapp.zc_app_android.bean.Response;
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
public class LoginActivity extends BaseActivity {
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
    private String pass;
    private LoginResp loginResp;
    private int type = 0;
    private String uid = "";

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
        pass = edit_pass_word.getText().toString();
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
                //验证手机号和密码
                if (judgePhone(phone) && judgePass(pass))
                    doLogin(phone, pass);
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
        if (type == 0){
            params.put("account", "18888888888");
            params.put("password", "123456");
        }else {
            params.put("uid", uid);
        }
        params.put("type", type + "");

        HttpUtil.post(Constants.URL.USER_LOGIN, params).subscribe(new BaseResponseObserver<LoginResp>() {

            @Override
            public void success(LoginResp result) {
                loginResp = result;
                saveUserData();
                openActivity(MainActivity.class);

                EBLog.i("HTTP", result.toString());
            }

            @Override
            public void error(Response<LoginResp> response) {
                EBLog.i("HTTP", response.getCode().toString());
            }
        });
    }

    //保存用户数据
    private void saveUserData() {
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
                uid = map.get("uid");
                turnToBindPhone(share_media);
                doLogin(phone, pass);
                Log.i("UMENG", map.get("uid"));
                ToastUtil.makeText(LoginActivity.this, "授权成功");
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

    //跳转绑定手机页面
    private void turnToBindPhone(SHARE_MEDIA share_media) {
        Bundle bundle = new Bundle();
        if (share_media == SHARE_MEDIA.WEIXIN) {
            type = 1;
            bundle.putInt(Constants.SPREF.LOGIN_MODE, Constants.SPREF.TYPE_WECHAT);
        } else if (share_media == SHARE_MEDIA.QQ) {
            type = 2;
            bundle.putInt(Constants.SPREF.LOGIN_MODE, Constants.SPREF.TYPE_QQ);
        } else if (share_media == SHARE_MEDIA.SINA) {
            type = 3;
            bundle.putInt(Constants.SPREF.LOGIN_MODE, Constants.SPREF.TYPE_SINA);
        }
//        openActivity(BindPhoneActivity.class, bundle);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }


}
