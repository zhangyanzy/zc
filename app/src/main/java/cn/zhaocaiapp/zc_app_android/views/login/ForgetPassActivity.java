package cn.zhaocaiapp.zc_app_android.views.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
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
                if (GeneralUtils.isNullOrZeroLenght(phone)) {
                    ToastUtil.makeText(ForgetPassActivity.this, getString(R.string.phone_not_empty));
                } else {
                    waitTimer();
                    getIdentifyCode(phone);
                }
                break;
            case R.id.tv_reset_pass:
                if (judgPhoneAndPass(phone, pass)){
                    if (GeneralUtils.isNullOrZeroLenght(identift_code))
                        ToastUtil.makeText(ForgetPassActivity.this, getString(R.string.input_identify_code));
                    else doResetPass(identift_code, pass);
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
    private void getIdentifyCode(String phone) {

    }

    //请求重置密码
    private void doResetPass(String identift_code, String pass) {

    }

    private void waitTimer() {
        get_idntify_code.setBackgroundResource(R.drawable.shape_gray_bg);
        get_idntify_code.setEnabled(false);
        timer.start();
    }

    private CountDownTimer timer = new CountDownTimer(61000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            long delayTime = millisUntilFinished / 1000;
            get_idntify_code.setText(String.format(getString(R.string.delay_time), delayTime));
        }

        @Override
        public void onFinish() {
            get_idntify_code.setBackgroundResource(R.drawable.shape_orange_bg);
            get_idntify_code.setText(getString(R.string.get_identify_code));
            get_idntify_code.setEnabled(true);
            cancel();
        }
    };
}
