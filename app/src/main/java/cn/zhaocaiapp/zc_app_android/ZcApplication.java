package cn.zhaocaiapp.zc_app_android;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinxunmediapty.ltd on 2018/1/3.
 */

public class ZcApplication extends Application {

    //app 实例
    public static ZcApplication application;

    //本地activity栈
    public static List<Activity> activitys = new ArrayList<Activity>();

    //当前activity名称
    public static String currentActivityName = "";

    /**
     * 添加
     *
     * @param activity
     */
    public void addActivity(Activity activity)
    {
        activitys.add(activity);
    }

    /**
     * 删除
     *
     * @param activity
     */
    public void deleteActivity(Activity activity)
    {
        if (activity != null)
        {
            activitys.remove(activity);
            activity.finish();
            activity = null;
        }
    }
}