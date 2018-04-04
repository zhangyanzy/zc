package cn.zhaocaiapp.zc_app_android.util;

import android.content.Context;
import android.widget.Toast;

import cn.zhaocaiapp.zc_app_android.constant.Constants;

/**
 * 提示
 * <p>
 * Created by jinxunmediapty.ltd on 2018/1/3.
 */

public class ToastUtil {

    private static Toast toast;

    /**
     * 显示toast提示
     *
     * @param context
     * @param msg
     */
    public static void makeText(Context context, String msg) {

        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 显示toast提示
     *
     * @param context
     * @param msg
     */
    public static void makeTextLong(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 显示失败信息
     *
     * @param context
     */
    public static void makeTextError(Context context) {
        makeText(context, Constants.CONFIG.ERROR_MESSAGE);
    }

    /**
     * 显示失败信息
     *
     * @param context
     */
    public static void makeTextErrorLong(Context context) {
        makeTextLong(context, Constants.CONFIG.ERROR_MESSAGE);
    }

    /**
     * 销毁
     */
    public static void destory() {
        toast = null;
    }
}
