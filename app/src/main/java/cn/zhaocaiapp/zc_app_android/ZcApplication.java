package cn.zhaocaiapp.zc_app_android;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

import cn.zhaocaiapp.zc_app_android.base.BaseAndroid;
import cn.zhaocaiapp.zc_app_android.base.BaseConfig;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.LocationUtil;

/**
 * Created by jinxunmediapty.ltd on 2018/1/3.
 */

public class ZcApplication extends Application {

    //配置三方appkey
    {
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

        //初始化友盟sdk
        umShareAPI = UMShareAPI.get(this);
        Config.DEBUG = true;

        //SharedPreferences存储全局设置
        sp = getSharedPreferences(Constants.SPREF.FILE_NAME, Context.MODE_PRIVATE);

        //初始化定位
        LocationUtil.initLocation(this);
    }

    /**
     * 退出程序 清理内存
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    //获取全局SharedPreferences对象
    public static SharedPreferences getPreferences() {
        return sp;
    }

    //获取UMShareAPI初始化对象
    public static UMShareAPI getUMShareAPI() {
        return umShareAPI;
    }
}