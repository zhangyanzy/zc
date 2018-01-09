package cn.zhaocaiapp.zc_app_android.views.login;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.KeyBoardUtils;
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
    private String identift_code;
    private boolean isPassUsable;

    @Override
    public int getContentViewResId() {
        return R.layout.layout_register_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tv_top_title.setText(getString(R.string.button_register));
        iv_top_menu.setVisibility(View.GONE);

        monitorEditChange(edit_pass_word);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KeyBoardUtils.closeKeybord(tv_register,this);
        return super.onTouchEvent(event);
    }

    @OnClick({R.id.iv_top_back, R.id.tv_register, R.id.send_identify_code})
    public void onClick(View view) {
        phone = edit_phone_number.getText().toString();
        pass = edit_pass_word.getText().toString();
        identift_code = edit_identify_code.getText().toString();

        switch (view.getId()) {
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.send_identify_code:
                if (GeneralUtils.isNullOrZeroLenght(phone)) {
                    ToastUtil.makeText(RegisterActivity.this, getString(R.string.phone_not_empty));
                } else {
                    waitTimer(send_identify_code);
                    requestIdentifyCode();
                }
                break;
            case R.id.tv_register:
                if (judgPhoneAndPass(phone, pass)){
                    if (GeneralUtils.isNullOrZeroLenght(identift_code))
                        ToastUtil.makeText(RegisterActivity.this, getString(R.string.input_identify_code));
                    else doRegister();
                }
                break;
        }
    }

    private void requestIdentifyCode() {

    }

    private void doRegister(){}


}
