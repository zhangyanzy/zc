package cn.zhaocaiapp.zc_app_android.widget;

import java.util.Date;

import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;

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
        boolean isAntiShake = false;
        long currentTime = new Date().getTime();
        EBLog.i("防抖点击", "currentTime---"+currentTime + "/// lastClickTime---" + lastClickTime);
        if (currentTime - lastClickTime >= MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            isAntiShake = false;
        } else {
            isAntiShake = true;
        }
        return isAntiShake;
    }
}