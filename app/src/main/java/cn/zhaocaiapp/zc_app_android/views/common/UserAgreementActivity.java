package cn.zhaocaiapp.zc_app_android.views.common;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;

/**
 * Created by Administrator on 2018/2/9.
 */

public class UserAgreementActivity extends BaseActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
    @BindView(R.id.tv_top_title)
    TextView tv_top_title;
    @BindView(R.id.web)
    WebView web;

    @Override
    public int getContentViewResId() {
        return R.layout.layout_webview;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tv_top_title.setText("用户协议");
        iv_top_menu.setVisibility(View.GONE);

        WebSettings webSettings = web.getSettings();
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);   //不使用缓存
        webSettings.setAllowUniversalAccessFromFileURLs(true); //跨域
        webSettings.setJavaScriptEnabled(true);    //js支持
        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        web.loadUrl("file:///android_asset/h5-assets/static/user_agreement.html");
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @OnClick({R.id.iv_top_back})
    public void onClick(View view){
        finish();
    }
}
