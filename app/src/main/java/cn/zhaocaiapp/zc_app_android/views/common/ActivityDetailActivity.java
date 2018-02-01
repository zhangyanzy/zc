package cn.zhaocaiapp.zc_app_android.views.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.jph.takephoto.model.TResult;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BasePhotoActivity;
import cn.zhaocaiapp.zc_app_android.capabilities.json.GsonHelper;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.capabilities.takephoto.PhotoHelper;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.LocationUtil;

public class ActivityDetailActivity extends BasePhotoActivity {
    @BindView(R.id.activity_detail_webView)
    WebView activity_detail_webView;

    private View rootView;
    private PhotoHelper photoHelper;

    @Override
    public int getContentViewResId() {
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_detail, null);
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

        //初始化takephoto
        photoHelper = PhotoHelper.of(rootView, true);

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
            params.put("token", (String) SpUtils.get(Constants.SPREF.TOKEN, ""));
            //授权码
            params.put("code", "");
            //经度
            params.put("longitude", String.valueOf(LocationUtil.getGps().getLongitude()));
            //经度
            params.put("latitude", String.valueOf(LocationUtil.getGps().getLatitude()));

            EBLog.i("tag", GsonHelper.toJson(params));

            return GsonHelper.toJson(params);
        }

        @JavascriptInterface
        public void takePhoto(){
            photoHelper.onClick(0, getTakePhoto());
        }

    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String imgUrl = result.getImage().getCompressPath();

        activity_detail_webView.evaluateJavascript("javascript:callJs(" + "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517399801249&di=4a680b11296f67259748efe4ca2108e1&imgtype=0&src=http%3A%2F%2Fcms-bucket.nosdn.127.net%2Fcatchpic%2F5%2F54%2F54a8cd32c0aae2254f9cdaeb70ae8697.png%3FimageView%26thumbnail%3D550x0" + ")", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                EBLog.i("H5回调", value);
            }
        });

    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }
}
