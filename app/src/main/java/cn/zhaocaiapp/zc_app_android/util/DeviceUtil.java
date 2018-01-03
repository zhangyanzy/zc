package cn.zhaocaiapp.zc_app_android.util;

/**
 * 获取设备相关信息
 *
 * Created by jinxunmediapty.ltd on 2018/1/3.
 */

public class DeviceUtil {

    /**
     * 获取设备型号
     *
     * @return
     */
    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }
}
