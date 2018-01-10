package cn.zhaocaiapp.zc_app_android.views.activity;

import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import butterknife.BindView;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;

public class ActivityActivityDetail extends BaseActivity {

    @BindView(R.id.h5web) WebView webView;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_activity_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        webView.loadUrl("file:///android_asset/h5-assets/demo.html");

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);    //js支持
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);   //不使用缓存
        settings.setAllowUniversalAccessFromFileURLs(true); //跨域

        //绑定js方法
        webView.addJavascriptInterface(new JavaScriptInterfaces(),"native");
        webView.setWebViewClient(new WebViewClient(){
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

        @JavascriptInterface
        public void test1(String a) {
            Toast.makeText(ActivityActivityDetail.this, "js调用成功,传值：" + a, Toast.LENGTH_SHORT).show();
        }
    }
}
