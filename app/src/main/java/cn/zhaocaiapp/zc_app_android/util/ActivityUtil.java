package cn.zhaocaiapp.zc_app_android.util;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by ASUS on 2017/10/31.
 * <p>
 * 创建堆栈，存取activity实例
 */

public class ActivityUtil {
    public static Activity context;
    private static Stack<Activity> activityStack;
    private static ActivityUtil activityManager;

    private ActivityUtil() {
    }

    /**
     * 单一实例
     */
    public static ActivityUtil getActivityManager() {
        if (activityManager == null) {
            activityManager = new ActivityUtil();
        }
        activityStack = new Stack<>();
        return activityManager;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity != null && activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束除指定activity之外的所有Activity
     */
    public void finishAllActivity(Class<?> cls) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i) && cls != activityStack.get(i).getClass()) {
                activityStack.get(i).finish();
                activityStack.remove(i);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

}

