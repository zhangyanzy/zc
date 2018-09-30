package cn.zhaocaiapp.zc_app_android.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.umeng.message.PushAgent;

import java.util.List;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.util.ActivityUtil;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.KeyBoardUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.widget.LoadingDialog;
import cn.zhaocaiapp.zc_app_android.widget.TakePhotoActivity;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by zhangyan on 2018/7/30.
 * activity基础类，所有activity继承   Databinding
 */

public abstract class BaseCompatActivity extends TakePhotoActivity implements EasyPermissions.PermissionCallbacks {

    private TextView identify_code;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
        initViews(savedInstanceState);
        this.initToolbar(savedInstanceState);
        initData();
        initListeners();
        PushAgent.getInstance(this).onAppStart();
        ActivityUtil.addActivity(this);
    }

    /**
     * 管理软键盘的显示
     */
    public void manageKeyBord(View view, Activity mActivity) {
        if (KeyBoardUtils.isKeyBordVisiable(mActivity))
            KeyBoardUtils.closeKeybord(view, mActivity);
    }

    /**
     * 初始化布局
     */
    protected abstract void initComponent();

    /**
     *初始化工程
     */
    public abstract void initViews(Bundle savedInstanceState);

    /**
     * Initialize the toolbar in the layout
     *
     * @param savedInstanceState
     */
    protected abstract void initToolbar(Bundle savedInstanceState);

    /**
     * Initalize the view Of the listener
     */
    protected abstract void initListeners();

    /**
     * Initialize the Activity Data
     */
    protected abstract void initData();



    /**
     * 验证手机号是否符合规则
     */
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

    /**
     * 验证密码是否符合规则
     * */
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



    /**
     * 输入框的输入监听器
     * */
    protected void monitorEditChange(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!GeneralUtils.IsPassword(s.toString())) {
                    editText.setTextColor(Color.RED);
                } else {
                    editText.setTextColor(getResources().getColor(R.color.colorFont6));
                }
            }
        });
    }



    /**
     * 开启浮动加载进度条
     */
    public void startProgressDialog() {
        LoadingDialog.showDialogForLoading(this);
    }

    /**
     * 开启浮动加载进度条Gif
     */
    public void startGifProgressDialog() {
        LoadingDialog.showDialogForLoadingGif(this);
    }

    /**
     * 开启浮动加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
        LoadingDialog.showDialogForLoading(this, msg, true);
    }

    /**
     * 停止浮动加载进度条
     */
    public void stopProgressDialog() {
        LoadingDialog.cancelDialogForLoading();
    }

    /**
     * activity跳转
     * */
    public void openActivity(Class<?> mClass) {
        Intent intent = new Intent(this, mClass);
        startActivity(intent);
    }

    /**
     * activity跳转
     * */
    public void openActivity(Class<?> mClass, Bundle bundle) {
        Intent intent = new Intent(this, mClass);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * activity跳转
     * */
    public void openActivityForResult(Class<?> mClass, int requestCode) {
        Intent intent = new Intent(this, mClass);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 开启计时器
     * */
    protected void waitTimer(TextView identify_code) {
        this.identify_code = identify_code;
        identify_code.setBackgroundResource(R.drawable.button_shape_gray_bg);
        identify_code.setEnabled(false);
        timer.start();
    }

    private CountDownTimer timer = new CountDownTimer(61000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            long delayTime = millisUntilFinished / 1000;
            identify_code.setText(String.format(getString(R.string.delay_time), delayTime));
        }

        @Override
        public void onFinish() {
            identify_code.setBackgroundResource(R.drawable.button_shape_orange_bg);
            identify_code.setText(getString(R.string.get_identify_code));
            identify_code.setEnabled(true);
            cancel();
        }
    };


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
