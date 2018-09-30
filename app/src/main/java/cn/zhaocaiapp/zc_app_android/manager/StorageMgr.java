package cn.zhaocaiapp.zc_app_android.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin on 2018/9/20.
 * 缓存管理类
 */

public class StorageMgr {

    private static SharedPreferences storage;
    public static String LEVEL_USER = "user";// 用户级别（必需登录后使用）
    public static String LEVEL_GLOBAL = "global";// 全局级别

    public static void init(Context context) {
        storage = context.getSharedPreferences("yuezhi", Context.MODE_PRIVATE);
    }

    // 设置缓存信息
    private static void setStorage(String key, String value) {
        SharedPreferences.Editor editor = storage.edit();
        editor.putString(key, value);
        editor.apply(); // 先提交内存，再异步提交硬盘
        // editor.commit(); //同步提交内存及硬盘
    }
}
