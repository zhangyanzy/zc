package cn.zhaocaiapp.zc_app_android.util;


import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.CommonResp;
import cn.zhaocaiapp.zc_app_android.bean.response.login.ObtainCodeResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;

/**
 * Created by zhangyan on 2018/7/31.
 * 验证手机号
 */

public class checkPhoneNumberDialog {

    private TextView identify_code;
    private final String TAG = "手机号码验证";

    private String phoneNumber;

    private Dialog mDialog;

    //验证手机号
    public void checkPhoneNumber(final Context context) {
        mDialog = new Dialog(context, R.style.zc_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.my_withdraw_money, null);
        TextView mSubmit = view.findViewById(R.id.tv_register);
        EditText mPhone = view.findViewById(R.id.edit_phone_number);
        EditText mIdentify = view.findViewById(R.id.edit_identify_code);
        TextView mSendIdentify = view.findViewById(R.id.send_identify_code);
        mDialog.setContentView(view);
        mDialog.show();
        /**
         * 提交
         */
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = mPhone.getText().toString();
                String s = mIdentify.getText().toString();
                if (judgePhone(context, phoneNumber)) {
                    if (GeneralUtils.isNullOrZeroLenght(s)) {
                        ToastUtil.makeText(context, "请输入验证码");
                    } else {
                        verifyPhone(context, phoneNumber, s);
                    }
                }
            }
        });
        /**
         * 发送邀请码
         */
        mSendIdentify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = mPhone.getText().toString();
                if (judgePhone(context, phoneNumber)) {
                    waitTimer(mSendIdentify);
                    requestIdentifyCode(context);
                }

            }
        });
    }

    private void requestIdentifyCode(Context context) {

        Map<String, String> params = new HashMap<>();
        params.put("phone", phoneNumber);
        HttpUtil.post(Constants.URL.GET_IDENTIFY_CODE, params).subscribe(new BaseResponseObserver<ObtainCodeResp>() {

            @Override
            public void success(ObtainCodeResp result) {
                EBLog.i(TAG, result.toString());
                ToastUtil.makeText(context, "验证码已经发送");
            }

            @Override
            public void error(Response<ObtainCodeResp> response) {
                ToastUtil.makeText(context, response.getDesc());
                EBLog.i(TAG, response.getCode() + "");
            }
        });

    }

    //校验密码
    private void verifyPhone(Context context, String phone, String code) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("code", code);
        HttpUtil.post(Constants.URL.VEIRFY_CODE, params).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                EBLog.i(TAG, commonResp.getDesc());
                EventBus.getDefault().post(commonResp);
                mDialog.dismiss();
            }

            @Override
            public void error(Response<CommonResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(context, response.getDesc());
            }
        });
    }

    private boolean judgePhone(Context context, String phone) {
        if (GeneralUtils.isNullOrZeroLenght(phone)) {
            ToastUtil.makeText(context, "手机号不能为空");
            return false;
        }
        if (!GeneralUtils.isTel(phone)) {
            ToastUtil.makeText(context, "手机号格式不正确");
            return false;
        }
        return true;
    }

    /**
     * 开启计时器
     */
    private void waitTimer(TextView identify_code) {
        this.identify_code = identify_code;
        identify_code.setBackgroundResource(R.drawable.button_shape_gray_bg);
        identify_code.setEnabled(false);
        timer.start();
    }

    private CountDownTimer timer = new CountDownTimer(61000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            long delayTime = millisUntilFinished / 1000;
            identify_code.setText(String.format(delayTime + "秒"));

        }

        @Override
        public void onFinish() {
            identify_code.setBackgroundResource(R.drawable.button_shape_orange_bg);
            identify_code.setText(R.string.get_identify_code);
            identify_code.setEnabled(true);
            cancel();
        }
    };


}
