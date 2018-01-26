package cn.zhaocaiapp.zc_app_android.util;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.Gravity;

import cn.zhaocaiapp.zc_app_android.capabilities.dialog.widget.NormalDialog;

/**
 * 弹出框
 *
 * Created by jinxunmediapty.ltd on 2018/1/3.
 */

public class DialogUtil {

    /**
     * 一个按钮的弹窗
     *
     * @param title   弹窗标题
     * @param content 弹窗提示内容
     */
    public static NormalDialog showDialogOneBut(Context context, @Nullable String title, String content) {
        NormalDialog dialog = new NormalDialog(context);
        dialog.btnNum(1)
                .title(title)
                .titleLineColor(Color.parseColor("#333333"))
                .titleTextSize(18)
                .isTitleShow(false)
                .content(content)
                .contentGravity(Gravity.CENTER)
                .contentTextColor(Color.parseColor("#333333"))
                .contentTextSize(16)
                .btnText("是")
                .btnTextColor(Color.parseColor("#3B70E3"))
                .btnTextSize(18)
                .show();
        return dialog;
    }

    /**
     * 两个按钮的弹窗
     *
     * @param title   弹窗标题
     * @param content 弹窗提示内容
     */
    public static NormalDialog showDialogTwoBut(Context context, @Nullable String title, String content) {
        NormalDialog dialog = new NormalDialog(context);
        dialog.style(NormalDialog.STYLE_TWO)
                .title(title)
                .titleLineColor(Color.parseColor("#333333"))
                .titleTextSize(18)
                .isTitleShow(false)
                .content(content)
                .contentTextColor(Color.parseColor("#333333"))
                .contentTextSize(16)
                .btnText("是")
                .btnTextColor(Color.parseColor("#3B70E3"))
                .btnTextSize(18)
                .show();

        return dialog;
    }
}
