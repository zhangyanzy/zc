package cn.zhaocaiapp.zc_app_android.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import cn.zhaocaiapp.zc_app_android.R;

/**
 * Created by admin on 2018/9/18.
 */

public class PreferenceUtils {

    private static final String FILE_NAME = "settings";

    public static boolean isAppFirstLaunch(Context context) {
        SharedPreferences sp = context.getSharedPreferences(
                FILE_NAME, Activity.MODE_PRIVATE);
        boolean bFirstLaunch = sp.getBoolean(
                "boolean_first_launch_version2", true);
        String versionCode = sp.getString("version_code", "");
        return bFirstLaunch || !versionCode.equals(R.string.account_merge);
    }

}
