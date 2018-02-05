package cn.zhaocaiapp.zc_app_android.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jph.takephoto.app.TakePhotoActivity;
import com.umeng.message.PushAgent;

import butterknife.ButterKnife;
import cn.zhaocaiapp.zc_app_android.widget.LoadingDialog;

/**
 * Created by Administrator on 2018/1/31.
 */

public abstract class BasePhotoActivity extends TakePhotoActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResId());
        ButterKnife.bind(this);

        //初始化工程
        init(savedInstanceState);

        //初始化友盟推送
        PushAgent.getInstance(this).onAppStart();
    }

    /**
     * 绑定根布局
     */
    public abstract int getContentViewResId();

    /**
     *初始化工程
     */
    public abstract void init(Bundle savedInstanceState);

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

}
