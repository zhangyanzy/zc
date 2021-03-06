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
        int DEFAULT_TIMEOUT = 10;

        /**
         * 分页pageSize
         */
        int PAGE_SIZE = 10;

        /**
         * 用户定位城市名称
         */
        String AREA_NAME = "上海市";
        /**
         * 用户定位城市Code
         */
        String AREA_CODE = "310115";
    }

    interface URL {
        /**
         * 服务器地址
         * <p>
         * 必须以／结尾否则初始化会报错
         */
//        String SERVER = "http://appapi.zhaocaiapp.local/"; //开发环境
//        String SERVER = "https://appapi-pre.zhaocaiapp.cn/"; //预生产环境
        String SERVER = "https://appapi.zhaocaiapp.cn/"; //生产环境
//        String SERVER = "http://192.168.1.125:9090/"; //本地测试环境

        /**
         * H5页面地址
         */
//        String H5_URL = "http://m.zhaocaiapp.local"; //开发环境
//        String H5_URL = "https://m-pre.zhaocaiapp.cn"; //预生产环境
        String H5_URL = "https://m.zhaocaiapp.cn"; //生产环境

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
         * 获取用户详细信息1
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
        String UPDATE_MESSAGE_STATUS = "userinfo/update/usermessage/%s";
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
         * 我的活动列表
         */
        String GET_ALL_ACTIVITY = "my/activity/list";
        /**
         * 我关注的活动列表
         */
        String GET_FOLLOW_ACTIVITY = "activity/follow";
        /**
         * 我关注的商家列表
         */
        String GET_FOLLOW_BUSINER = "userinfo/member/follow";
        /**
         * 获取学历及职业列表
         */
        String GET_RELATION_LIST = "common/user/info";
        /**
         * 添加个人标签
         */
        String ADD_LABEL = "userinfo/addlabels";
        /**
         * 获取标签
         */
        String GET_LABELS = "/label";
        /**
         * 绑定账户
         */
        String BIND_ACCOUNT = "userinfo/add/account";
        /**
         * 解除账户绑定
         */
        String REMOVE_ACCOUNT_BIND = "userinfo/abolish/account";
        /**
         * 提现
         */
        String DO_WITHDRAW = "userinfo/witdraw";
        /**
         * 关于我们
         */
        String ABOUT_US = "about_us/%s";
        /**
         * 取消活动报名
         */
        String CANCEL_ACTIVITY = "my/activity/%s/cancel";
        /**
         * 领取活动奖励
         */
        String REWARD_AVTIVITY = "my/activity/money/%s";
        /**
         * 支付宝授权
         */
        String ALIPAY_OTHUR = "userinfo/orderinfo";
        /**
         * 校验密码
         */
        String VERIFY_PASS = "userinfo/ckeckpassword";
        /**
         * 分享活动
         */
        String SHARE_ACTIVITY_URL = "/activity/detail?id=%s";
        /**
         * 分享资讯活动
         */
        String SHARE_INFORMATION_ACTIVITY_URL = "/newInfomation?id=%s&type=%s";
        /**
         * 分享邀请好友
         */
        String INVITE_URL = "/invite/user?code=%s";
        /**
         * 收支明细
         */
        String INCOME_LIST = "userinfo/billinfo/%s";
        /**
         * 收入分享
         */
        String INCOME_SHARE = "/my/income?income=%s";
        /**
         * 提现校验密码
         */
        String WITHDRAW_VERIFU_PASS = "userinfo/check/witdraw/password";
        /**
         * 分享注册
         */
        String SHARE_REGISTER = "/register/goRegister.html?code=%s";


        /**
         * 获取城市列表
         */
        String GET_AREA = "area";

        /**
         * 首页获取个人信息
         */
        String GET_ACTIVITY_USER = "my/activity/user";
        /**
         * 首页获取个人信息
         */
        String GET_USERINFO_FRISTPAGE = "userinfo/fristpage";

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
         * 商家关注与取消关注
         */
        String POST_MEMBER_FOLLOW = "member/%s/follow";

        /**
         * 商家列表
         */
        String GET_MEMBER_QUERY = "member/query";

        /**
         * 商家搜索联想
         */
        String GET_MEMBER_ASSOCIATE = "member/associate";

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

        /**
         * 是否开启分享功能
         */
        String IS_SHOW_SHARE = "app/config/ios?appVersion=%s";
        /**
         * 反馈意见
         */
        String FEEDBACK = "userinfo/insertSuggestion";
        /**
         * 通知后台，应用已唤醒
         */
        String APP_WAKE = "userinfo/insertUserActive?type=s%";
        /**
         * 校验手机号和验证码
         */
        String VEIRFY_CODE = "/userinfo/checkOriginCode";

        /**
         * 绑定新手机号
         */
        String MODIFY_PHONR = "/userinfo/updateMobile";

        /**
         * 校验原手机号码页面请求验证码
         */
        String CHECK_PHONE_OBTAINCODE = "/register/checkPhoneObtaincode";

        /**
         * 修改手机号码页面请求验证码
         */
        String MODIFY_PHONE_OBTAINCODE = "/register/modifyPhoneObtaincode";
        /**
         * 首页banner
         */
        String BANNER = "/activity/foreverList";

        /**
         * 模糊查询
         */
        String BLURRED_SEARCH = "/activity/associateActivity";

        /**
         * 阅值个人资料详情
         */
        String PERSONAL_DATA = "/userinfo/userInfoDetail";

        /**
         * 阅值个人资料上传
         */
        String PUT_PERSONAL_DATA = "/userinfo/update/userBaseInfo";

        /**
         * 阅值实名认证
         */
        String REAL_NANE_DATA = "/userinfo/update/userRealInfo";

        /**
         * 阅值首页活动列表
         */
        String NEW_HOME_ACTIVITY_LIST = "/activity/listForYueZhi";

        /**
         * 商户端阅值商户信息提交审核
         */
        String MERCHANT_INFORMATION_SUBMITTED = "/platform/insertMember";

        /**
         * 商户端阅值判断商户是否审核通过
         */
        String CHECK_IS_MERCHANT = "/platform/judgeStatus";

        /**
         * 商户端阅值商家基本信息
         */
        String GET_MEMBER_INFO = "/platform/getMemberInfo";

        /**
         * 商户端阅值商户弹窗
         */
        String RECODE_RED_PACKAGE = "/platform/updateMemberFirst";

        /**
         * 商户端阅值商家充值
         */
        String PAY_ORDER = "/pay/generateOrder";

        /**
         * 阅值个人实名资料详情页
         */
        String REAL_NAME = "/userinfo/userRealInfoDetail";

        /**
         * 商户端阅值商家发布的活动列表
         */
        String MEMBER_ACTIVITY = "/platform/getMemberActivity";

        /**
         * 视频上传
         */
        String VIDEO_UPLOAD = "/common/upload/video";

        /**
         * 商户端阅值获取充值订单详情
         */
        String PAY_BILL_LIST = "/platform/getPlatformMemberBill";

        /**
         * 阅值商户端发布活动
         */
        String ADD_ACTIVITY = "/platform/addActivity";

        /**
         * 阅值商户端获取商户发布活动状态
         */
        String GET_ACTIVITY_STATUS = "/platform/getActivityStatus";

        /**
         * 阅值商户端获取完成用户账单
         */
        String USER_COMPLETE = "/platform/getPlatformCompleteUserBill";

    }

    interface SPREF {
        /**
         * 保存在手机里面的文件名 用户有关
         */
        String FILE_USER_NAME = "share_data";
        /**
         * 保存在手机里面的文件名 应用有关
         */
        String FILE_APP_NAME = "share_app";
        /**
         * 是否开启分享功能
         */
        String IS_SHOW_SHARE = "isShowShare";

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
        String TOKEN = "token";                     //用户登录返回的唯一识别码
        String DEVICE_TOKEN = "deviceToken";        //友盟推送的设备识别号
        String ALIAS = "alias";                     // 友盟推送的用户别名
        String INVITE_CODE = "inviteCode";          // 邀請碼
        String IS_CERTIFICATION = "realInfoAuditStatus"; //是否通过实名认证


        //        int TYPE_PHONE = 0;    //手机登录
        int TYPE_WECHAT = 1;   //微信登录
        int TYPE_QQ = 2;       //qq登陆
        int TYPE_SINA = 3;     //新浪微博登录
        int TYPE_ALI = 4;      //支付宝账户
        int TYPE_BANK = 5;     //银行卡账户
        int TYPE_PHONE = 6;    //手机登录

        String SERVICE_PHONE = "servicePhone";      //客服电话
        String SEARCH_HISTORY = "searchHistory"; //搜索历史
        String AREA_NAME = "areaName"; //用户定位城市名称
        String AREA_CODE = "areaCode"; //用户定位城市Code
        String MESSAGE_PUSH = "messagePush"; //消息推送时间
        String ACTIVITY_RANGE = "activityRange"; //首页获取线下活动范围
        String SHOW_NEWER_ACTIVITY = "isShowNewerActivity";   //是否弹窗提示新手任务

    }

    interface ASSETS {
        /**
         * 地区基础数据
         */
        String AREA = "android-assets/area.json";
    }
}
