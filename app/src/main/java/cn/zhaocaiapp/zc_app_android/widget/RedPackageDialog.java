package cn.zhaocaiapp.zc_app_android.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.constant.Constants;

/**
 * Created by admin on 2018/9/13.
 */

public class RedPackageDialog extends Dialog implements View.OnClickListener {

    private ImageView mClose;
    private onCloseListener listener;


    public RedPackageDialog(@NonNull Context context) {
        super(context, R.style.BaseDialog);
    }

    public RedPackageDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected RedPackageDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setListener(onCloseListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_red_package);
        init();
    }

    private void init() {
        mClose = findViewById(R.id.read_package_close);
        mClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.read_package_close:
                if (listener != null) {
                    listener.redPackageClose();
                }
                break;
            default:
                break;
        }
    }

    public interface onCloseListener {
        void redPackageClose();
    }

}
