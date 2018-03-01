package cn.zhaocaiapp.zc_app_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.pgyersdk.crash.PgyCrashManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;

import java.util.ArrayList;
import java.util.List;

import cn.zhaocaiapp.zc_app_android.base.BaseAndroid;
import cn.zhaocaiapp.zc_app_android.base.BaseConfig;
import cn.zhaocaiapp.zc_app_android.bean.response.home.LocationResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.AreaUtil;
import cn.zhaocaiapp.zc_app_android.util.LocationUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;

/**
 * Created by jinxunmediapty.ltd on 2018/1/3.
 */

public class ZcApplication extends MultiDexApplication {

    static {
        //配置三方appkey
        PlatformConfig.setWeixin("wx8401993fc69cc0ce", "18c4c38288be5935ce1a5b4455f125bd");
        PlatformConfig.setQQZone("1106660590", "mh54ewnGH5QCRwPN");
        PlatformConfig.setSinaWeibo("2998825649", "9251f8e40b6ab489d56dbfd18f545297", "https://api.weibo.com/oauth2/default.html");


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

    private static SharedPreferences sp;
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
                .setAppLogo(R.mipmap.ic_launcher)//app图标
                .setFailPicture(R.mipmap.ic_launcher)//加载加载失败和加载中显示的图
                .setLoadingView(R.drawable.gif)//设置加载框的gif图
                .setCode(0)//网络请求成功返回的code数字，默认为1
                .setHttpCode("code")//网络请求返回的code字段名称，默认为code
                .setHttpMessage("message")//网络请求返回的message字段名称，默认为message
                .setHttpResult("response"));//网络请求返回的result字段名称，默认为result

        //初始化友盟组件
        initUM();
        Config.DEBUG = true;

        //SharedPreferences存储全局设置
        sp = getSharedPreferences(Constants.SPREF.FILE_NAME, Context.MODE_PRIVATE);

        //初始化定位
        LocationUtil.initLocation(this);

        //获取省列表
        getAreasList();

        //初始化OCR单例
        initAccessToken(getApplicationContext());

        //首次进入，设置新手任务弹窗显示
        SpUtils.put(Constants.SPREF.SHOW_NEWER_ACTIVITY, true);
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

    private void initAccessToken(Context context) {

        OCR.getInstance().initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                OCRToken = accessToken.getAccessToken();
                EBLog.i(TAG, "百度ocr---" + OCRToken);
            }

            @Override
            public void onError(OCRError error) {
                EBLog.e("百度身份证识别licence授权失败", error.getMessage());
            }
        }, context);
    }

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
                SpUtils.put(Constants.SPREF.DEVICE_TOKEN, umPushToken);
                EBLog.i(TAG, "友盟token---" + umPushToken);
            }

            @Override
            public void onFailure(String s, String s1) {

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

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    //获取全局SharedPreferences对象
    public static SharedPreferences getPreferences() {
        return sp;
    }

    //获取UMShareAPI初始化对象
    public static UMShareAPI getUMShareAPI() {
        return umShareAPI;
    }

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