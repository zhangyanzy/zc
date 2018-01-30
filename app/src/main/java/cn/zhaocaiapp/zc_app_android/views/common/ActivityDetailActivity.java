package cn.zhaocaiapp.zc_app_android.views.common;

import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.capabilities.json.GsonHelper;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;

public class ActivityDetailActivity extends BaseActivity {

    @BindView(R.id.activity_detail_webView)
    WebView activity_detail_webView;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        activity_detail_webView.loadUrl("file:///android_asset/h5-assets/index.html");
        //webView.loadUrl("http://192.168.1.189:8080");
        WebSettings settings = activity_detail_webView.getSettings();
        settings.setJavaScriptEnabled(true);    //js支持
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);   //不使用缓存
        settings.setAllowUniversalAccessFromFileURLs(true); //跨域
        settings.setMediaPlaybackRequiresUserGesture(false);    //手机支持视频播放

        //绑定js方法
        activity_detail_webView.addJavascriptInterface(new JavaScriptInterfaces(), "native");
        activity_detail_webView.requestFocusFromTouch();
        activity_detail_webView.setWebChromeClient(new WebChromeClient());      //解决alert不弹出问题
        activity_detail_webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

    }

    //预留给js调用的回调
    class JavaScriptInterfaces {

        /**
         * 获取访问的页面
         *
         * @return
         */
        @JavascriptInterface
        public String getPage() {
            Map<String, String> params = new HashMap<>();
            //活动详情id
            params.put("id", "1");
            //token
            params.put("token", "98f8df6220da997283eace4bab823a9b");
            //授权码
            params.put("code", "");
            //经度
            params.put("longitude", "");
            //经度
            params.put("latitude", "");

            EBLog.i("tag", GsonHelper.toJson(params));
            return GsonHelper.toJson(params);
        }
    }
}
