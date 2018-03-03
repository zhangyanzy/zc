package cn.zhaocaiapp.zc_app_android.views.login;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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
import cn.zhaocaiapp.zc_app_android.bean.response.login.SignupResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.KeyBoardUtils;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.views.common.UserAgreementActivity;

/**
 * Created by Administrator on 2018/1/16.
 */

public class RegistPhoneActivity extends BaseActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.tv_top_title)
    TextView tv_top_titlel;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
    @BindView(R.id.input_pass)
    EditText input_pass;
    @BindView(R.id.input_invite_code)
    EditText input_invite_code;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.check_agreement)
    CheckBox check_agreement;
    @BindView(R.id.tv_agreement)
    TextView tv_agreement;

    private String pass;
    private String inviteCode;
    private int type;
    private String phone;
    private String uid;
    private String avatar;
    private int sex;

    private static final String TAG = "绑定手机号";

    @Override
    public int getContentViewResId() {
        return R.layout.layout_register_phone;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        iv_top_menu.setVisibility(View.GONE);
        tv_top_titlel.setText(getString(R.string.input_pass_word));

        phone = getIntent().getStringExtra("phone");
        type = getIntent().getIntExtra(Constants.SPREF.LOGIN_MODE, -1);
        uid = getIntent().getStringExtra("uid");
        avatar = getIntent().getStringExtra("avatar");
        sex = getIntent().getIntExtra("gender", 0);
    }

    @OnClick({R.id.iv_top_back, R.id.tv_submit, R.id.tv_agreement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.tv_submit:
                pass = input_pass.getText().toString();
                inviteCode = input_invite_code.getText().toString();
                if (judgePass(pass)) {
                    if (!check_agreement.isChecked())
                        ToastUtil.makeText(RegistPhoneActivity.this, getString(R.string.check_agreement));
                    else doRegister();
                }
                break;
            case R.id.tv_agreement:
                openActivity(UserAgreementActivity.class);
                break;
        }
    }

    //请求绑定账号
    private void doRegister() {
        Map<String, String> params = new HashMap<>();
        params.put("type", type + "");
        params.put("phone", phone);
        params.put("password", pass);
        params.put("inviteCode", inviteCode);
        params.put("uid", uid);
        params.put("avatar", avatar);
        params.put("sex", sex + "");

        HttpUtil.post(Constants.URL.REGISTER, params).subscribe(new BaseResponseObserver<SignupResp>() {
            @Override
            public void success(SignupResp result) {
                EBLog.i(TAG, result.toString());
                ToastUtil.makeText(RegistPhoneActivity.this, result.getDesc());

                saveUserData(result);
                Bundle bundle = new Bundle();
                bundle.putInt("position", 0);
                openActivity(MainActivity.class, bundle);
                RegistPhoneActivity.this.finish();
            }

            @Override
            public void error(Response response) {
                ToastUtil.makeText(RegistPhoneActivity.this, response.getDesc());
                EBLog.i(TAG, response.getCode() + "");
            }
        });
    }

    //保存用户数据
    private void saveUserData(SignupResp result) {
        SpUtils.put(Constants.SPREF.TOKEN, result.getToken());
        SpUtils.put(Constants.SPREF.IS_LOGIN, true);
        SpUtils.put(Constants.SPREF.LOGIN_MODE, type);
        SpUtils.put(Constants.SPREF.USER_PHOTO, result.getAvatar());
        SpUtils.put(Constants.SPREF.NICK_NAME, result.getNickname());
        SpUtils.put(Constants.SPREF.USER_PHONE, result.getPhone());
        SpUtils.put(Constants.SPREF.USER_ID, result.getKid());
        SpUtils.put(Constants.SPREF.ALIAS, result.getAlias());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KeyBoardUtils.closeKeybord(tv_submit, this);
        return super.onTouchEvent(event);
    }
}
