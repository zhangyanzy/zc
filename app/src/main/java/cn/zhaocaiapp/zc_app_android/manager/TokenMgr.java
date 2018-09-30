package cn.zhaocaiapp.zc_app_android.manager;

import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;

/**
 * Created by admin on 2018/9/20.
 */

public class TokenMgr {


    // key
    private static String KEY_TOKEN = "Token";
    private static String token;


    public static void init() {
        token = (String) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.TOKEN, "");
    }
}
