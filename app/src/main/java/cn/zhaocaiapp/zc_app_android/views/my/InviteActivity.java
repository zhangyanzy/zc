package cn.zhaocaiapp.zc_app_android.views.my;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.umeng.socialize.UMShareAPI;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.ShareUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.widget.NavigationTopBar;

/**
 * Created by Administrator on 2018/1/11.
 */

public class    InviteActivity extends BaseActivity implements NavigationTopBar.NavigationTopBarClickListener {
    //    @BindView(R.id.iv_top_back)
//    ImageView iv_top_back;
//    @BindView(R.id.tv_top_title)
//    TextView tv_top_title;
//    @BindView(R.id.iv_top_menu)
//    ImageView iv_top_menu;
    @BindView(R.id.web)
    WebView web;

    private NavigationTopBar mNavigationTopBar;
    private UMShareAPI umShareAPI;
    private String inviteCode; //邀请码

    @Override
    public int getContentViewResId() {
        return R.layout.layout_invite_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        umShareAPI = ZcApplication.getUMShareAPI();
        inviteCode = getIntent().getStringExtra("code");

//        iv_top_menu.setImageResource(R.mipmap.share);
//        tv_top_title.setText(R.string.invite_friend);
        mNavigationTopBar = findViewById(R.id.invite_friends_top);
        mNavigationTopBar.setLeftImageResource(R.mipmap.finish_icon);
        mNavigationTopBar.setCenterTitleText(R.string.invite_friend);
        mNavigationTopBar.setCenterTitleTextColor(R.color.colorBlack);
        mNavigationTopBar.setRightImageResource(R.mipmap.share_activity_icon);
        mNavigationTopBar.setNavigationTopBarClickListener(this);

        WebSettings webSettings = web.getSettings();
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);   //不使用缓存
        webSettings.setAllowUniversalAccessFromFileURLs(true); //跨域
        webSettings.setJavaScriptEnabled(true);    //js支持
        //绑定js方法
        web.addJavascriptInterface(new JavaScriptInterfaces(), "native");
        web.requestFocusFromTouch();
        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        web.loadUrl("file:///android_asset/h5-assets/index.html");
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void leftImageClick() {
        finish();
    }

    @Override
    public void rightImageClick() {
        String shareTitle = getString(R.string.app_name);
        String shareDesc = getString(R.string.share_desc);
        String inviteUrl = String.format(Constants.URL.SHARE_REGISTER, inviteCode);
        ShareUtil.init(this)
                .setUrl(inviteUrl)
                .setSourceId(R.mipmap.icon_launcher)
                .setTitle(shareTitle)
                .setDesc(shareDesc);
        ShareUtil.openShare();
    }

    @Override
    public void alignRightLeftImageClick() {

    }

    //预留给js调用的回调
    class JavaScriptInterfaces {

        @JavascriptInterface
        public String getPage() {
            Map<String, String> params = new HashMap<>();
            params.put("type", "5");
            params.put("code", inviteCode);
            return new Gson().toJson(params);
        }

        @JavascriptInterface
        public void inviteUser() { //邀请好友
//            onClick(iv_top_menu);
            String shareTitle = getString(R.string.app_name);
            String shareDesc = getString(R.string.share_desc);
            String inviteUrl = String.format(Constants.URL.SHARE_REGISTER, inviteCode);
            ShareUtil.init(InviteActivity.this)
                    .setUrl(inviteUrl)
                    .setSourceId(R.mipmap.icon_launcher)
                    .setTitle(shareTitle)
                    .setDesc(shareDesc);
            ShareUtil.openShare();

        }

        @JavascriptInterface
        public void copy() { //复制邀请码
            //获取剪贴板管理器
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            //创建普通字符型ClipData对象
            ClipData mClipData = ClipData.newPlainText("Label", inviteCode);//‘Label’是任意文字标签
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            ToastUtil.makeText(InviteActivity.this, "已复制邀请码");
        }
    }
//
//    @OnClick({R.id.iv_top_back, R.id.iv_top_menu})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.iv_top_back:
//                finish();
//                break;
//            case R.id.iv_top_menu:
//                String shareTitle = getString(R.string.app_name);
//                String shareDesc = getString(R.string.share_desc);
//                String inviteUrl = String.format(Constants.URL.SHARE_REGISTER, inviteCode);
//                ShareUtil.init(this)
//                        .setUrl(inviteUrl)
//                        .setSourceId(R.mipmap.icon_launcher)
//                        .setTitle(shareTitle)
//                        .setDesc(shareDesc);
//                ShareUtil.openShare();
//                break;
//        }
//    }

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
