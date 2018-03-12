package cn.zhaocaiapp.zc_app_android.widget;

import java.util.Calendar;

/**
 * Created by Administrator on 2018/3/12.
 */

public class OneClickUtil {
    private String methodName;
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0l;

    public OneClickUtil(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public boolean check() {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime >= MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            return false;
        } else {
            return true;
        }
    }
}