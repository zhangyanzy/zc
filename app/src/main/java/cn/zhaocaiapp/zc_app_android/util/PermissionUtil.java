package cn.zhaocaiapp.zc_app_android.util;

import android.app.Activity;
import android.support.annotation.NonNull;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Administrator on 2018/1/20.
 */

public class PermissionUtil {
    private static final int REQUEST_CODE = 1010;

    // 检查是否拥有相应权限
    public static void checkPermission(Activity mActivity, String[] perms, @NonNull String rationale) {
        if (!EasyPermissions.hasPermissions(mActivity, perms))
            EasyPermissions.requestPermissions(mActivity, rationale, REQUEST_CODE, perms);
    }

}
