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
        /**
         * 用户注销登录
         */
        String USER_LOGIN_OUT = "user/logout";
        /**
         * 获取验证码
         */
        String GET_IDENTIFY_CODE = "register/obtaincode";
        /**
         * 校验手机号是否存在
         */
        String IS_PHONE_EXIST = "register/check/phone/exist";

        /**
         * 获取城市列表
         */
        String GET_CITY = "";

        /**
         * 商家列表
         */
        String GET_MEMBER_QUERY = "member/query";


    }

    interface SPREF {
        /**
         * 保存在手机里面的文件名
         */
        String FILE_NAME = "share_data";
        /**
         * 是否显示引导页
         */
        String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
        //保存到sharedPreferenced的数据的key
        String IS_FIRST = "isFirstLogin";           //是否首次登陆
        String IS_LOGIN = "isLogin";                //登录状态
        String LOGIN_MODE = "loginMode";            //登录方式
        String USER_ID = "userId";                  //用户id
        String USER_PHONE = "userPhone";            //手机号
        String USER_PHOTO = "headPortrait";         //头像
        String USER_NAME = "userName";              //用户姓名
        String NICK_NAME = "nickName";              //用户昵称
        String GENDER = "gender";                   //性别
        String AGE = "age";                         //年龄
        String TOKEN = "token";

        int TYPE_WECHAT = 1;   //微信登录
        int TYPE_QQ = 2;       //qq登陆
        int TYPE_SINA = 3;     //新浪微博登录
    }

}
