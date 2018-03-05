package cn.zhaocaiapp.zc_app_android.views.login;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.MainActivity;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.base.BaseFragmentActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.login.LoginResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.ActivityUtil;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.KeyBoardUtils;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.views.common.ActivityDetailActivity;
import cn.zhaocaiapp.zc_app_android.widget.CircleImageView;


/**
 * @author 林子
 * @filename LoginActivity.java
 * @data 2018-01-05 17:52
 */
public class LoginActivity extends BaseFragmentActivity {
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
    private int type = Constants.SPREF.TYPE_PHONE; //用户登录方式
    private String uid = "";

    private UMShareAPI umShareAPI;
    private String avatar;
    private int sex;

    private Activity lastActivity;//当前activity的上一个activity

    private static final String TAG = "登录";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_main);
        if (getIntent().getBooleanExtra("signOut", false))
            ActivityUtil.finishAllActivity();
        if (ActivityUtil.getActivityStackSize() != 0)
            lastActivity = ActivityUtil.currentActivity();

        ActivityUtil.addActivity(this);

        umShareAPI = ZcApplication.getUMShareAPI();
    }

    @OnClick({R.id.tv_skip_login, R.id.tv_register, R.id.tv_forget_pass, R.id.tv_login,
            R.id.login_wechat, R.id.login_qq, R.id.login_sina})
    public void onClick(View view) {
        phone = edit_phone_number.getText().toString();
        pass = edit_pass_word.getText().toString();
        switch (view.getId()) {
            case R.id.tv_skip_login: //跳过登陆
                if (lastActivity != null) {
                    ActivityUtil.finishActivity(LoginActivity.this);
                } else openActivity(MainActivity.class);
                break;
            case R.id.tv_register: //注册
                openActivity(RegisterActivity.class);
                break;
            case R.id.tv_forget_pass: //忘记密码
                openActivity(ForgetPassActivity.class);
                break;
            case R.id.tv_login: //登陆
                type = Constants.SPREF.TYPE_PHONE;
                if (judgePhone(phone) && judgePass(pass))
                    doLogin();
                break;
            case R.id.login_wechat: //微信登陆
                type = Constants.SPREF.TYPE_WECHAT;
                isPlatformExist(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.login_qq: //qq登陆
                type = Constants.SPREF.TYPE_QQ;
                isPlatformExist(SHARE_MEDIA.QQ);
                break;
            case R.id.login_sina: //微博登陆
                type = Constants.SPREF.TYPE_SINA;
                isPlatformExist(SHARE_MEDIA.SINA);
                break;
        }
    }

    //发送登录请求
    private void doLogin() {
        Map<String, String> params = new HashMap<>();
        if (type == Constants.SPREF.TYPE_PHONE) {
            params.put("account", phone);
            params.put("password", pass);
        } else {
            params.put("uid", uid);
        }
        params.put("type", type + "");

        HttpUtil.post(Constants.URL.USER_LOGIN, params).subscribe(new BaseResponseObserver<LoginResp>() {

            @Override
            public void success(LoginResp result) {
                EBLog.i(TAG, result.toString());

                setAlias(result);
                saveUserData(result);

                if (lastActivity != null && lastActivity instanceof ActivityDetailActivity) {
                    openActivity(ActivityDetailActivity.class, getIntent().getExtras());
                } else {
                    int position = getIntent().getIntExtra("currentPosition", 0);
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", position);
                    openActivity(MainActivity.class, bundle);
                }
                finish();
            }

            @Override
            public void error(Response response) {
                EBLog.i(TAG, response.getCode() + "");
                if (type != 0 && response.getCode() == 5000) { //此三方账号未绑定
                    turnToCheckPhone();
                } else if (response.getCode() == 5005) { // 此账号已被封禁
                    openActivity(ClosureActivity.class);
                } else {
                    ToastUtil.makeText(LoginActivity.this, response.getDesc());
                }
            }
        });
    }

    private void setAlias(LoginResp result) {
        PushAgent pushAgent = PushAgent.getInstance(this);
        pushAgent.addAlias(result.getAlias(), "alias", new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean b, String s) {
                EBLog.i(TAG, s);
            }
        });
    }

    //保存用户数据
    private void saveUserData(LoginResp loginResp) {
        SpUtils.put(Constants.SPREF.TOKEN, loginResp.getToken());
        SpUtils.put(Constants.SPREF.IS_LOGIN, true);
        SpUtils.put(Constants.SPREF.LOGIN_MODE, loginResp.getType());
        SpUtils.put(Constants.SPREF.USER_PHOTO, loginResp.getAvatar());
        SpUtils.put(Constants.SPREF.NICK_NAME, loginResp.getNickname());
        SpUtils.put(Constants.SPREF.USER_PHONE, loginResp.getPhone());
        SpUtils.put(Constants.SPREF.USER_ID, loginResp.getKid());
        SpUtils.put(Constants.SPREF.ALIAS, loginResp.getAlias());
    }

    //检测是否安装三方应用
    private void isPlatformExist(SHARE_MEDIA platform) {
        if (!umShareAPI.isInstall(this, platform))
            ToastUtil.makeText(this, "请先安装应用");
        else doOauth(platform);
    }

    //获取三方授权
    private void doOauth(SHARE_MEDIA platform) {
        umShareAPI.getPlatformInfo(this, platform, new UMAuthListener() {
            /**
             * @desc 授权开始的回调
             * @param share_media 授权平台
             */
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                EBLog.i("友盟---", "授权开始的回调");
            }

            /**
             * @desc 授权成功的回调
             * @param share_media 授权平台
             * @param map 用户资料返回
             */
            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                EBLog.i("友盟---", map.toString());

                getUserInfo(map);
                doLogin();
            }

            /**
             * @desc 授权失败的回调
             * @param share_media 授权平台
             * @param throwable 错误原因
             */
            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                EBLog.e(TAG, throwable.getMessage());
            }

            /**
             * @desc 授权取消的回调
             * @param share_media 授权平台
             */
            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                EBLog.e(TAG, "授权取消的回调");
            }
        });
    }

    //获取三方账号信息
    private void getUserInfo(Map<String, String> map) {
        if (map.get("gender").equals("男") || map.get("gender").equals("1"))
            sex = 0;
        else sex = 1;
        uid = map.get("uid");
        avatar = map.get("iconurl");
    }

    //跳转绑定手机页面
    private void turnToCheckPhone() {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.SPREF.LOGIN_MODE, type);
        bundle.putInt("gender", sex);
        bundle.putString("uid", uid);
        bundle.putString("avatar", avatar);
        openActivity(CheckPhoneActivity.class, bundle);

    }

    //验证手机号是否符合规则
    protected boolean judgePhone(String phone) {
        if (GeneralUtils.isNullOrZeroLenght(phone)) {
            ToastUtil.makeText(this, getString(R.string.phone_not_empty));
            return false;
        }
        if (!GeneralUtils.isTel(phone)) {
            ToastUtil.makeText(this, getString(R.string.isNot_phone));
            return false;
        }
        return true;
    }

    //验证密码是否符合规则
    protected boolean judgePass(String pass) {
        if (GeneralUtils.isNullOrZeroLenght(pass)) {
            ToastUtil.makeText(this, getString(R.string.pass_not_empty));
            return false;
        }
        if (!GeneralUtils.IsPassword(pass)) {
            ToastUtil.makeText(this, getString(R.string.pass_length));
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        umShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        umShareAPI.onSaveInstanceState(outState);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KeyBoardUtils.closeKeybord(iv_log, this);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        umShareAPI.release();
    }

}
