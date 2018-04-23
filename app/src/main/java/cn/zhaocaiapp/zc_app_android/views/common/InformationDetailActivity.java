package cn.zhaocaiapp.zc_app_android.views.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.widget.SampleControlPlayer;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Administrator on 2018/4/23.
 */

public class InformationDetailActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.iv_top_back)
    ImageView iv_back;
    @BindView(R.id.tv_top_title)
    TextView tv_title;
    @BindView(R.id.iv_top_menu)
    ImageView iv_menu;
    @BindView(R.id.activity_detail_webView)
    WebView activity_detail_webView;
    @BindView(R.id.vp_player)
    SampleControlPlayer vp_player;

    private View rootView;

    @Override
    public int getContentViewResId() {
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_detail, null);
        return R.layout.activity_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
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

    }

    public class JavaScriptInterfaces {

        @JavascriptInterface
        public void getH5Page() {

        }
    }
}
