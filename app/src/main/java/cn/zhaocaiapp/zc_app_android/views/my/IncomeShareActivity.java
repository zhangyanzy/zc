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

import com.umeng.socialize.UMShareAPI;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.my.MyResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ShareUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

/**
 * Created by Administrator on 2018/3/8.
 */

public class IncomeShareActivity extends BaseActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.tv_top_title)
    TextView tv_top_title;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
    @BindView(R.id.web)
    WebView web;

    private String webUrl;
    private String income;
    private UMShareAPI umShareAPI;

    private static final String TAG = "炫耀收入";

    @Override
    public int getContentViewResId() {
        return R.layout.layout_share_income;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        umShareAPI = ZcApplication.getUMShareAPI();

        iv_top_menu.setImageResource(R.mipmap.share);
        tv_top_title.setText("炫耀收入");

        loadData();
    }

    public void loadData() {
        HttpUtil.get(Constants.URL.GET_BRIEF_USER_INFO).subscribe(new BaseResponseObserver<MyResp>() {

            @Override
            public void success(MyResp result) {
                EBLog.i(TAG, result.toString());
                income = result.getGrossIncomeAmount().toString();
                loadWeb();
            }

            @Override
            public void error(Response<MyResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(IncomeShareActivity.this, response.getDesc());
            }
        });
    }

    private void loadWeb(){
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

        webUrl = String.format(Constants.URL.INCOME_SHARE, income);
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
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.iv_top_menu:
                String shareTitle = getString(R.string.app_name);
                String shareDesc = getString(R.string.share_desc);
                ShareUtil.init(this)
                        .setUrl(webUrl)
                        .setSourceId(R.mipmap.icon_launcher)
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
