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

        /**
         * 分页pageSize
         */
        int PAGE_SIZE = 10;
    }

    interface URL {
        /**
         * 服务器地址
         */
        String SERVER = "http://appapi.zhaocaiapp.local/"; //必须以／结尾否则初始化会报错
//        String SERVER = "http://192.168.1.10:8082/"; //必须以／结尾否则初始化会报错
//        String SERVER = "http://192.168.1.182:8083/"; //必须以／结尾否则初始化会报错

        /**
         * 注册
         */
        String REGISTER = "register/signup";

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
         * 校验验证码
         */
        String VERIFY_CODE = "register/check/code";
        /**
         * 重置密码
         */
        String UPDATE_PASS = "user/updatepassword";
        /**
         * 获取用户中心首页信息
         */
        String GET_BRIEF_USER_INFO = "userinfo/user";
        /**
         * 获取用户详细信息
         */
        String GET_USER_INFO_DETAIL = "userinfo/userdetail";
        /**
         * 获取个人标签
         */
        String GET_USER_LABEL = "userinfo/tag";
        /**
         * 个人中心更换手机号
         */
        String UPDATE_PHONE = "userinfo/updatephone";
        /**
         * 个人中心消息列表
         */
        String MESSAGE_LIST = "userinfo/usermessage/%s";
        /**
         * 修改密码
         */
        String REVISE_PASS = "userinfo/updatepassword";
        /**
         * 更新消息状态
         */
        String UPDATE_MESSAGE_STATUS = "userinfo/usermessage/%s";
        /**
         * 修改活动相关信息
         */
        String REVISE_ACTIVITY_INFO = "userinfo/update/activtiyinfo";
        /**
         * 修改实名信息
         */
        String REVISE_REAL_INFO = "userinfo/update/realinfo";
        /**
         * 修改个人信息
         */
        String REVISE_BASE_INFO = "userinfo/update/baseinfo";
        /**
         * 删除个人标签
         */
        String DELETE_LABEL = "userinfo/deletelabels";
        /**
         * 获取用户账户信息
         */
        String GET_ACCOUNT_INFO = "userinfo/account";
        /**
         *我的活动列表
         */
        String GET_ALL_ACTIVITY = "my/activity/list";
        /**
         * 我关注的活动列表
         * */
        String GET_FOLLOW_ACTIVITY = "my/activity/follow";
        /**
         * 我关注的商家列表
         * */
         String GET_FOLLOW_BUSINER = "userinfo/member/follow";

        /**
         * 获取城市列表
         */
        String GET_AREA = "area";

        /**
         * 首页获取个人信息
         */
        String GET_ACTIVITY_USER = "activity/user";

        /**
         * 活动首页活动列表
         */
        String GET_ACTIVITY_LIST = "activity/list";

        /**
         * 搜索推荐
         */
        String GET_SEARCH_RECOMMEND = "searchrecommend";

        /**
         * 搜索结果
         */
        String GET_ACTIVITY_FIND = "activity/find";

        /**
         * 活动关注与取消关注
         */
        String POST_ACTIVITY_FOLLOW = "activity/%s/follow";

        /**
         * 商家列表
         */
        String GET_MEMBER_QUERY = "member/query";

        /**
         * 商家活动列表
         */
        String GET_ACTIVITY_LIST_MEMBER = "activity/list/member";

        /**
         * 商家详情
         */
        String GET_MEMBER_DETAIL = "member/%s";

        /**
         * 图片上传
         */
        String UPLOAD_IMAGE = "common/upload/image";
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

        int TYPE_PHONE = 0;    //手机登录
        int TYPE_WECHAT = 1;   //微信登录
        int TYPE_QQ = 2;       //qq登陆
        int TYPE_SINA = 3;     //新浪微博登录

        int TYPE_ALI = 4;      //支付宝账户
        int TYPE_BANK = 5;     //银行卡账户

        String SEARCH_HISTORY = "searchHistory"; //搜索历史
    }

    interface ASSETS {
        /**
         * 地区基础数据
         */
        String AREA = "android-assets/area.json";
    }
}
