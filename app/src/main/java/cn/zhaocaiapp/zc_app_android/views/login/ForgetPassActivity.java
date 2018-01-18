package cn.zhaocaiapp.zc_app_android.views.login;

import android.os.Bundle;
import android.os.CountDownTimer;
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
 * Created by Administrator on 2018/1/9.
 */

public class ForgetPassActivity extends BaseActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
    @BindView(R.id.tv_top_title)
    TextView tv_top_title;
    @BindView(R.id.tv_get_idntify_code)
    TextView get_idntify_code;
    @BindView(R.id.tv_reset_pass)
    TextView tv_reset_pass;
    @BindView(R.id.edit_phone_number)
    EditText edit_phone_number;
    @BindView(R.id.edit_identify_code)
    EditText edit_identify_code;
    @BindView(R.id.edit_new_pass)
    EditText edit_new_pass;


    private String phone;
    private String pass;
    private String identift_code;
    private boolean isPassUsable;

    @Override
    public int getContentViewResId() {
        return R.layout.layout_forget_pass;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tv_top_title.setText(getString(R.string.reset_pass));
        iv_top_menu.setVisibility(View.GONE);

        monitorEditChange(edit_new_pass);
    }

    @OnClick({R.id.iv_top_back, R.id.tv_get_idntify_code, R.id.tv_reset_pass})
    public void onClick(View view) {
        phone = edit_phone_number.getText().toString();
        pass = edit_new_pass.getText().toString();
        identift_code = edit_identify_code.getText().toString();
        switch (view.getId()) {
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.tv_get_idntify_code:
                if (judgePhone(phone)) {
                    waitTimer(get_idntify_code);
                    getIdentifyCode();
                }
                break;
            case R.id.tv_reset_pass:
                if (judgePhone(phone) && judgePass(pass)){
                    if (GeneralUtils.isNullOrZeroLenght(identift_code))
                        ToastUtil.makeText(ForgetPassActivity.this, getString(R.string.input_identify_code));
                    else doResetPass();
                }
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KeyBoardUtils.closeKeybord(tv_reset_pass, this);
        return super.onTouchEvent(event);
    }

    //获取验证码
    private void getIdentifyCode() {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        HttpUtil.post(Constants.URL.GET_IDENTIFY_CODE, params).subscribe(new BaseResponseObserver<String>() {

            @Override
            public void success(String result) {
                ToastUtil.makeText(ForgetPassActivity.this, "获取验证码成功");
            }

            @Override
            public void error(Response<String> response) {

            }
        });

    }

    //请求重置密码
    private void doResetPass() {




    }

}
