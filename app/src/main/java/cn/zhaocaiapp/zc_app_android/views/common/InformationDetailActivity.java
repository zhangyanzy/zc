package cn.zhaocaiapp.zc_app_android.views.common;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.socialize.UMShareAPI;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.capabilities.json.GsonHelper;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.ActivityUtil;
import cn.zhaocaiapp.zc_app_android.util.DeviceUtil;
import cn.zhaocaiapp.zc_app_android.util.LocationUtil;
import cn.zhaocaiapp.zc_app_android.util.ShareUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.views.login.LoginActivity;
import cn.zhaocaiapp.zc_app_android.widget.SampleControlPlayer;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Administrator on 2018/4/23.
 */

public class InformationDetailActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.tv_top_title)
    TextView tv_title;
    @BindView(R.id.iv_top_menu)
    ImageView iv_menu;
    @BindView(R.id.activity_detail_webView)
    WebView activity_detail_webView;
    @BindView(R.id.vp_player)
    SampleControlPlayer vp_player;

    private View rootView;

    private long activityId;  // 活动id
    private String activityTitle; // 活动名称
    private UMShareAPI shareAPI;
    private MediaPlayer mMediaPlayer;
    private static final int REQUEST_CODE = 2002;

    private static final String TAG = "资讯详情页";

    @Override
    public int getContentViewResId() {
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_detail, null);
        return R.layout.activity_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        ActivityUtil.addActivity(this);

        activityId = getIntent().getLongExtra("id", -1);
        activityTitle = getIntent().getStringExtra("title");

        //从浏览器跳转回活动详情
        Uri uri = getIntent().getData();
        if (uri != null) {
            activityId = Long.valueOf(uri.getQueryParameter("id"));
            activityTitle = uri.getQueryParameter("name");
        }
        tv_title.setText(activityTitle);
        iv_menu.setImageResource(R.mipmap.share);

        //加载H5活动详情
        activity_detail_webView.loadUrl("file:///android_asset/h5-assets/index.html");
        WebSettings settings = activity_detail_webView.getSettings();
        settings.setJavaScriptEnabled(true);    //js支持
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);   //不使用缓存
        settings.setAllowUniversalAccessFromFileURLs(true); //跨域
        settings.setMediaPlaybackRequiresUserGesture(false);    //手机支持视频播放

        //绑定js方法
        activity_detail_webView.addJavascriptInterface(new InformationDetailActivity.JavaScriptInterfaces(), "native");
        activity_detail_webView.requestFocusFromTouch();
        activity_detail_webView.setWebChromeClient(new WebChromeClient());//解决alert不弹出问题
        activity_detail_webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        shareAPI = ZcApplication.getUMShareAPI();

        //初始化音频播放器
        mMediaPlayer = MediaPlayer.create(this, R.raw.radio);

    }

    public class JavaScriptInterfaces {
        /**
         * 获取访问的页面
         *
         * @return
         */
        @JavascriptInterface
        public String getPage() {
            Map<String, String> params = new HashMap<>();
            //活动详情id
            params.put("id", activityId + "");
            //用戶token
            params.put("token", (String) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.TOKEN, ""));
            params.put("type", "3");
            //手机唯一识别码
            params.put("deviceUUID", DeviceUtil.getDeviceModel());
            //经度
            params.put("longitude", String.valueOf(LocationUtil.getGps().getLongitude()));
            //经度
            params.put("latitude", String.valueOf(LocationUtil.getGps().getLatitude()));

            EBLog.i(TAG, GsonHelper.toJson(params));

            return GsonHelper.toJson(params);
        }

        @JavascriptInterface
        public String getUser() {
            Map<String, String> params = new HashMap<>();
            //用戶token
            params.put("token", (String) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.TOKEN, ""));
            EBLog.i(TAG, GsonHelper.toJson(params));

            return GsonHelper.toJson(params);
        }

        //跳轉登錄
        @JavascriptInterface
        public void goLogin() {
            if (!(boolean) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.IS_LOGIN, false))
                turnToLogin();
        }

        @JavascriptInterface
        public void playMedia() {
//            EBLog.i(TAG, "这是一个音频");
            mMediaPlayer.start();
        }
    }

    //未登录，跳转至登录页
    private void turnToLogin() {
        Bundle bundle = new Bundle();
        bundle.putLong("id", activityId);
        bundle.putString("title", activityTitle);
        openActivity(LoginActivity.class, bundle);
    }

    //分享活动
    private void shareActivity(String webUrl) {
        String shareDesc = getString(R.string.share_desc);
        ShareUtil.init(this)
                .setUrl(webUrl)
                .setSourceId(R.mipmap.icon_launcher)
                .setTitle(activityTitle)
                .setDesc(shareDesc);
        ShareUtil.openShare();
    }

    //登陆成功回调H5,通知刷新
    private void refresh() {
        activity_detail_webView.evaluateJavascript("javascript:getHadLogin()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                EBLog.i(TAG, "刷新---" + value);
            }
        });
    }

    @OnClick({R.id.iv_top_back, R.id.iv_top_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                ActivityUtil.finishActivity(this);
                break;
            case R.id.iv_top_menu:
                String webUrl = String.format(Constants.URL.SHARE_INFORMATION_ACTIVITY_URL, activityId, 3);
                shareActivity(webUrl);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        shareAPI.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == LoginActivity.RESULT_CODE) {
                refresh();
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shareAPI.release();
        mMediaPlayer.release();
    }

}
