package cn.zhaocaiapp.zc_app_android.capabilities.dialog.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.animation.attention.Swing;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.base.BasesOsDialog;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.utils.CornerUtils;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.utils.ViewFindUtils;

/**
 * 自定义退出弹框
 * */
public class TrembleBasesOsDialog extends BasesOsDialog<TrembleBasesOsDialog> {
    private TextView tv_cancel;
    private TextView tv_exit;

    private OnDialogClickListener listener;

    public TrembleBasesOsDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new Swing());

        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(context, R.layout.dialog_custom_base, null);
        tv_cancel = ViewFindUtils.find(inflate, R.id.tv_cancel);
        tv_exit = ViewFindUtils.find(inflate, R.id.tv_exit);
        inflate.setBackgroundDrawable(
                CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(5)));

        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDialogClick(v.getId());
            }
        });
        tv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDialogClick(v.getId());
            }
        });
    }

    public interface OnDialogClickListener{
        void onDialogClick(int resId);
    }

    public void setOnDialogClickListener(OnDialogClickListener listener){
        this.listener = listener;
    }
}
