package cn.zhaocaiapp.zc_app_android.util;

import android.widget.Toast;

/**
 * 提示
 *
 * Created by jinxunmediapty.ltd on 2018/1/3.
 */

public class ToastUtil {

    private static Toast toast;

    /**
     * 销毁
     */
    public static void destory() {
        toast = null;
    }
}
