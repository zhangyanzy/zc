package cn.zhaocaiapp.zc_app_android.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;

/**
 * Created by ASUS on 2017/10/31.
 * <p>
 * 创建堆栈，存取activity实例
 */

public class ActivityUtil {
    public static Activity context;
    private static List<Activity> activityStack = new ArrayList<>();

    private static final String TAG = "当前活动的activity数---";

    /**
     * 添加Activity到堆栈
     */
    public static void addActivity(Activity activity) {
        activityStack.add(activity);
        EBLog.i(TAG, activityStack.toString());
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public static Activity currentActivity() {
        EBLog.i(TAG, activityStack.toString());
        Activity activity = activityStack.get(activityStack.size() - 1);
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public static void finishActivity() {
        Activity activity = activityStack.get(activityStack.size() - 1);
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            activityStack.remove(activity);
        }
        EBLog.i(TAG, activityStack.toString());
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity != null && activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
        EBLog.i(TAG, activityStack.toString());
    }

    /**
     * 结束除指定activity之外的所有Activity
     */
    public static void finishAllActivity(Class<?> cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            if (null != activityStack.get(i) && cls != activityStack.get(i).getClass()) {
                activityStack.get(i).finish();
                activityStack.remove(i);
            }
        }
        EBLog.i(TAG, activityStack.toString());
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        for (int i = 0; i < activityStack.size(); i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
        EBLog.i(TAG, activityStack.toString());
    }

}

