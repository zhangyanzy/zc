package cn.zhaocaiapp.zc_app_android.constant;

/**
 * 全局常量
 */
public interface Constants {

    interface CONFIG {
        /**
         * 返回请求成功码
         */
        String SUCESS_CODE_END = "0";

        /**
         * 返回请求失败码
         */
        String FAILED_CODE_END = "-1";

        /**
         * 魔鬼数字
         */
        int NUM0 = 0;

        int NUM1 = 1;

        int NUM2 = 2;

        int NUM3 = 3;

        int NUM4 = 4;

        /**
         * 请求失败展示信息
         */
        String ERROR_MESSAGE = "请求失败，请稍后再试";

        /**
         * 请求超时时间
         */
        int DEFAULT_TIMEOUT = 5;
    }

    interface URL {
        /**
         * 服务器地址
         */
        String SERVER = "http://appapi.zhaocaiapp.local/"; //必须以／结尾否则初始化会报错
//        String SERVER = "http://192.168.1.10:8082/"; //必须以／结尾否则初始化会报错

        /**
         * 用户登陆
         */
        String USER_LOGIN = "user/login";
    }

    interface SPREF {
        /**
         * 是否显示引导页
         */
        String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    }

}
