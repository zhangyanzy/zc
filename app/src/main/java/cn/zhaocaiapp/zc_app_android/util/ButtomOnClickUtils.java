package cn.zhaocaiapp.zc_app_android.util;

/**
 * Created by admin on 2018/9/7.
 * 防止多次点击插入数据
 */

public class ButtomOnClickUtils {

    //间隔时间
    private static final int MIN_CLICK_DELAY_TIME = 6000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();//当前时间
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }
}
