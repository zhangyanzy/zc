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
    @BindView(R.id.edit_phone_number)
    EditText edit_phone_number;
    @BindView(R.id.edit_identify_code)
    EditText edit_identify_code;
    @BindView(R.id.tv_get_idntify_code)
    TextView tv_get_code;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.tv_title)
    TextView tv_title;

    private String title = "提示";
    private String butText = "下一步";

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

        return view;
    }

    public void setTitle(@Nullable String title) {
        if (GeneralUtils.isNotNullOrZeroLenght(title))
            this.title = title;
    }

    public void setButton(@Nullable String butText){
        if (GeneralUtils.isNotNullOrZeroLenght(butText))
            this.butText = butText;
    }

    @Override
    public void setUiBeforShow() {
        setCanceledOnTouchOutside(false);
//        setCancelable(false);
        tv_title.setText(title);
        tv_next.setText(butText);
        edit_phone_number.setText("");
        edit_identify_code.setText("");
        tv_get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edit_phone_number.getText().toString();
                listener.onDialogClick(v, phone, null);
            }
        });
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edit_phone_number.getText().toString();
                String code = edit_identify_code.getText().toString();
                listener.onDialogClick(v, phone, code);
            }
        });
    }

    public interface OnDialogClickListener {
        void onDialogClick(View view, @Nullable String str1, @Nullable String str2);
    }

    public void setOnDialogClickListener(OnDialogClickListener listener) {
        this.listener = listener;
    }
}
