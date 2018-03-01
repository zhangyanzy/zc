package cn.zhaocaiapp.zc_app_android.capabilities.dialog.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.base.BasesOsDialog;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.utils.CornerUtils;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public class NormalInputDialog extends BasesOsDialog<NormalInputDialog> {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.tv_submit)
    TextView tv_submit;

    private OnDialogClickListener listener;

    /**
     * method execute order:
     * show:constrouctor---show---oncreate---onStart---onAttachToWindow
     * dismiss:dismiss---onDetachedFromWindow---onStop
     *
     * @param context
     */
    public NormalInputDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        //设置弹窗的宽度
        widthScale(0.8f);

        View view = View.inflate(context, R.layout.layout_dialog_input, null);
        ButterKnife.bind(this, view);

        view.setBackgroundDrawable(
                CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(5)));
        setCanceledOnTouchOutside(false);
        setCancelable(false);

        return view;
    }

    public void setTitle(@Nullable String title){
        if (GeneralUtils.isNullOrZeroLenght(title))
            title = content.getResources().getString(R.string.input);
        tv_title.setText(title);
    }

//    public void showDialog(){
//        content.setText("");
//        show();
//    }

    @Override
    public void setUiBeforShow() {
        content.setText("");

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDialogClick(v.getId(), null);
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = content.getText().toString();
                listener.onDialogClick(v.getId(), str);
            }
        });
    }

    public interface OnDialogClickListener{
        void onDialogClick(int resId, @Nullable String content);
    }

    public void setOnDialogClickListener(OnDialogClickListener listener){
        this.listener = listener;
    }
}
