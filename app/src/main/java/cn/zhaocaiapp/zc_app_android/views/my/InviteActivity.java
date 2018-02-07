package cn.zhaocaiapp.zc_app_android.views.my;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.autonavi.rtbt.IFrameForRTBT;
import com.umeng.socialize.UMShareAPI;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.ShareUtil;

/**
 * Created by Administrator on 2018/1/11.
 */

public class InviteActivity extends BaseActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.tv_top_title)
    TextView tv_top_titlel;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
    @BindView(R.id.web)
    WebView web;

    private String inviteUrl = "/#/invite/user?code=%s";
    private String shareTitle = "一个可以赚钱的APP";
    private String shareDesc = "你看广告，我发钱";

    private UMShareAPI umShareAPI;
    private String inviteCode;
    private String webUrl;

    @Override
    public int getContentViewResId() {
        return R.layout.layout_invite_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        iv_top_menu.setImageResource(R.mipmap.share);
        tv_top_titlel.setText("邀请好友");

        umShareAPI = ZcApplication.getUMShareAPI();
        inviteCode = getIntent().getStringExtra("code");

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

        webUrl = String.format(inviteUrl, inviteCode);
        web.loadUrl(Constants.URL.H5_URL + webUrl);
        EBLog.i("TAG", Constants.URL.H5_URL + webUrl);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @OnClick({R.id.iv_top_back, R.id.iv_top_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.iv_top_menu:
                ShareUtil.init(this)
                        .setUrl(webUrl)
                        .setSourceId(R.mipmap.logo)
                        .setTitle(shareTitle)
                        .setDesc(shareDesc);
                ShareUtil.openShare();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        umShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        umShareAPI.release();
    }
}
