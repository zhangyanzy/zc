package cn.zhaocaiapp.zc_app_android;

import android.app.Activity;
import android.app.Application;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

import cn.zhaocaiapp.zc_app_android.base.BaseAndroid;
import cn.zhaocaiapp.zc_app_android.base.BaseConfig;

/**
 * Created by jinxunmediapty.ltd on 2018/1/3.
 */

public class ZcApplication extends Application {

    { // 配置三方appkey
        PlatformConfig.setWeixin("", "");
        PlatformConfig.setQQZone("1106660590", "mh54ewnGH5QCRwPN");
        PlatformConfig.setSinaWeibo("2998825649", "9251f8e40b6ab489d56dbfd18f545297", "http://sns.whalecloud.com");
    }

    //app 实例
    public static ZcApplication application;

    //本地activity栈
    public static List<Activity> activitys = new ArrayList<Activity>();

    //当前activity名称
    public static String currentActivityName = "";

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
        UMShareAPI.get(this);
    }

    /**
     * 添加
     *
     * @param activity
     */
    public void addActivity(Activity activity)
    {
        activitys.add(activity);
    }

    /**
     * 删除
     *
     * @param activity
     */
    public void deleteActivity(Activity activity)
    {
        if (activity != null)
        {
            activitys.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 退出程序 清理内存
     *
     */
    @Override
    public void onTerminate()
    {
        super.onTerminate();
    }
}