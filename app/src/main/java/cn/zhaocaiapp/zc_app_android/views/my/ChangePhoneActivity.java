package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.CommonResp;
import cn.zhaocaiapp.zc_app_android.bean.response.login.ObtainCodeResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.KeyBoardUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.views.login.ForgetPassActivity;

/**
 * Created by Administrator on 2018/1/25.
 */

public class ChangePhoneActivity extends BaseActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.tv_top_title)
    TextView tv_top_title;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
    @BindView(R.id.edit_phone_number)
    EditText edit_phone_number;
    @BindView(R.id.edit_identify_code)
    EditText edit_identify_code;
    @BindView(R.id.tv_get_idntify_code)
    TextView tv_get_idntify_code;
    @BindView(R.id.tv_reset_phone)
    TextView tv_reset_phone;

    private static final String TAG = "更换手机号";

    private String phone;
    private String code;

    @Override
    public int getContentViewResId() {
        return R.layout.layout_change_phone;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tv_top_title.setText("更换手机号");
    }

    private void doRevisePass() {
        Map<String, String>params = new HashMap<>();
        params.put("phone", phone);
        params.put("code", code);

        HttpUtil.post(Constants.URL.UPDATE_PHONE, params).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                EBLog.i(TAG, commonResp.getDesc());
                EventBus.getDefault().post(phone);
                finish();
            }

            @Override
            public void error(Response<CommonResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(ChangePhoneActivity.this, response.getDesc());
            }
        });
    }

    //获取验证码
    private void requestIdentifyCode() {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        HttpUtil.post(Constants.URL.GET_IDENTIFY_CODE, params).subscribe(new BaseResponseObserver<ObtainCodeResp>() {

            @Override
            public void success(ObtainCodeResp result) {
                EBLog.i(TAG, result.toString());
                ToastUtil.makeText(ChangePhoneActivity.this, result.getDesc());
            }

            @Override
            public void error(Response<ObtainCodeResp> response) {
                ToastUtil.makeText(ChangePhoneActivity.this, response.getDesc());
                EBLog.i(TAG, response.getCode() + "");
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (KeyBoardUtils.isKeyBordVisiable(this))
            KeyBoardUtils.closeKeybord(tv_reset_phone, this);
        return super.onTouchEvent(event);
    }

    @OnClick({R.id.iv_top_back, R.id.tv_get_idntify_code, R.id.tv_reset_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.tv_get_idntify_code:
                phone = edit_phone_number.getText().toString();
                if(judgePhone(phone))
                    requestIdentifyCode();
                break;
            case R.id.tv_reset_phone:
                code = edit_identify_code.getText().toString();
                if (GeneralUtils.isNullOrZeroLenght(code))
                    ToastUtil.makeText(this, getString(R.string.input_identify_code));
                else doRevisePass();
                break;
        }
    }

}
