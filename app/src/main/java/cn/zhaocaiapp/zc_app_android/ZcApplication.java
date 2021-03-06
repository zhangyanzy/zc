package cn.zhaocaiapp.zc_app_android;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.update.PgyUpdateManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tendcloud.tenddata.TCAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zhaocaiapp.zc_app_android.base.BaseAndroid;
import cn.zhaocaiapp.zc_app_android.base.BaseConfig;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.home.LocationResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.AreaUtil;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.LocationUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

/**
 * Created by jinxunmediapty.ltd on 2018/1/3.
 */

public class ZcApplication extends MultiDexApplication {

    static {
        //配置三方appkey
        PlatformConfig.setWeixin("wx8401993fc69cc0ce", "18c4c38288be5935ce1a5b4455f125bd");
        PlatformConfig.setQQZone("1106660590", "mh54ewnGH5QCRwPN");
        PlatformConfig.setSinaWeibo("2998825649", "9251f8e40b6ab489d56dbfd18f545297", "https://api.weibo.com/oauth2/default.html");

        //配置SmartRefreshLayout的全局参数
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }
        });
    }

    private static SharedPreferences spUser;
    private static SharedPreferences spApp;
    private static UMShareAPI umShareAPI;
    private static UMShareConfig config;
    private static List<LocationResp> provinces = new ArrayList<>();
    private static String OCRToken;
    private static String umPushToken;

    private static final String TAG = "找财app";

    @Override
    public void onCreate() {
        super.onCreate();

        BaseAndroid.init(new BaseConfig()
                .setAppColor(R.color.colorPrimary)//app主调颜色，用于标题栏等背景颜色
                .setAppLogo(R.mipmap.icon_launcher)//app图标
                .setFailPicture(R.mipmap.icon_launcher)//加载加载失败和加载中显示的图
                .setLoadingView(R.drawable.gif)//设置加载框的gif图
                .setCode(0)//网络请求成功返回的code数字，默认为1
                .setHttpCode("code")//网络请求返回的code字段名称，默认为code
                .setHttpMessage("message")//网络请求返回的message字段名称，默认为message
                .setHttpResult("response"));//网络请求返回的result字段名称，默认为result

        //存储用户相关信息
        spUser = getSharedPreferences(Constants.SPREF.FILE_USER_NAME, Context.MODE_PRIVATE);
        //存储应用相关信息
        spApp = getSharedPreferences(Constants.SPREF.FILE_APP_NAME, Context.MODE_PRIVATE);

        //初始化定位
        LocationUtil.initLocation(this);

        //初始化省市区列表
        getAreasList();

        //初始化友盟组件
        initUM();
        Config.DEBUG = false;

        //初始化OCR单例
        initAccessToken(getApplicationContext());

        //注册蒲公英Crash反馈
        PgyCrashManager.register(getApplicationContext());
        /**
         * 设置是否强制更新。true为强制更新；false为不强制更新（默认值）
         * 发布新版本时，必须设置这个值
         * */
        PgyUpdateManager.setIsForced(true);

        setWakeAppListener();

        initTalkingData();
    }

    //初始化TalkingData数据
    private void initTalkingData(){
        TCAgent.LOG_ON = true;
        TCAgent.init(this);
        TCAgent.setReportUncaughtExceptions(true);
    }

    //开启子线程解析城市数据
    private void getAreasList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                provinces = AreaUtil.initArea(getApplicationContext());
            }
        }).start();
    }

    //初始化白底身份识别授权
    private void initAccessToken(Context context) {
        OCR.getInstance().initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                OCRToken = accessToken.getAccessToken();
//                EBLog.i(TAG, "百度ocr---" + OCRToken);
            }

            @Override
            public void onError(OCRError error) {
            }
        }, context);
    }

    //初始化友盟组件
    private void initUM() {
        umShareAPI = UMShareAPI.get(this);
        config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        config.setSinaAuthType(UMShareConfig.AUTH_TYPE_SSO);
        umShareAPI.setShareConfig(config);

        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                umPushToken = deviceToken;
                SpUtils.init(Constants.SPREF.FILE_USER_NAME).put(Constants.SPREF.DEVICE_TOKEN, umPushToken);
                EBLog.i("youmeng", "友盟token---" + umPushToken);
            }

            @Override
            public void onFailure(String s, String s1) {
            }
        });
    }

    /**
     * 监听应用的活动状态
     */
    private int account = -1;
    private void setWakeAppListener() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                account++;
                boolean isLogin = (boolean) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.IS_LOGIN, false);
                if (account == 0 && isLogin) { //应用被唤醒
                    EBLog.i(TAG, "我被唤醒了");
                    notifyWake();
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                account--;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    //通知服务器，应用已唤醒
    private void notifyWake(){
        Map<String, Integer>map = new HashMap<>();
        map.put("type", 1);
        HttpUtil.get(Constants.URL.APP_WAKE, map).subscribe(new BaseResponseObserver<String>() {

            @Override
            public void success(String s) {
                EBLog.i(TAG, s);
            }

            @Override
            public void error(Response<String> response) {
                EBLog.i(TAG, response.getCode()+"");
                ToastUtil.makeText(getApplicationContext(), response.getDesc());
            }
        });
    }

    /**
     * 退出程序 清理内存
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        OCR.getInstance().release();
    }

    /**
     * 通知垃圾回收
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    //获取全局SharedPreferences对象
    public static SharedPreferences getUserPreferences() {
        return spUser;
    }

    //获取全局SharedPreferences对象
    public static SharedPreferences getAppPreferencesApp() {
        return spApp;
    }

    //获取UMShareAPI初始化对象
    public static UMShareAPI getUMShareAPI() {
        return umShareAPI;
    }

    //获取省市区列表
    public static List<LocationResp> getProvinces() {
        return provinces;
    }

    public static String getLicenceToken() {
        return OCRToken;
    }

    public static String getUmPushToken() {
        return umPushToken;
    }

}