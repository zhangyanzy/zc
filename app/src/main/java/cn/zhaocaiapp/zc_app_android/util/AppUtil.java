package cn.zhaocaiapp.zc_app_android.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * App 相关信息
 *
 * Created by jinxunmediapty.ltd on 2018/1/3.
 */

public class AppUtil {

    /**
     * 返回当前程序版本名,失败为null
     */
    public static String getAppVersionName(Context context) {
        String versionName = null;

        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }

        return versionName;
    }
}
