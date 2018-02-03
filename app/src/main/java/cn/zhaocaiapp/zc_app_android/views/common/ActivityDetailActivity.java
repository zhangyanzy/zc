package cn.zhaocaiapp.zc_app_android.views.common;

import android.content.Intent;
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


import com.jph.takephoto.model.TResult;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BasePhotoActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.capabilities.json.GsonHelper;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.capabilities.takephoto.PhotoHelper;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.ActivityUtil;
import cn.zhaocaiapp.zc_app_android.util.FileUtil;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.LocationUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.views.login.LoginActivity;

import static com.darsh.multipleimageselect.helpers.Constants.REQUEST_CODE;

public class ActivityDetailActivity extends BasePhotoActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_back;
    @BindView(R.id.tv_top_title)
    TextView tv_title;
    @BindView(R.id.iv_top_menu)
    ImageView iv_menu;
    @BindView(R.id.activity_detail_webView)
    WebView activity_detail_webView;

    private View rootView;
    private PhotoHelper photoHelper;
    private String zxResult; //二维码扫描解析结果

    private static final String TAG = "H5详情页";

    @Override
    public int getContentViewResId() {
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_detail, null);
        return R.layout.activity_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        ActivityUtil.getActivityManager().addActivity(this);

        tv_title.setText("活动详情");

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

        //初始化扫描二维码组件
        ZXingLibrary.initDisplayOpinion(this);

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
        public void takePhoto() {
            photoHelper.onClick(0, getTakePhoto());
        }

        @JavascriptInterface
        public void goLogin() {
            if (!(boolean) SpUtils.get(Constants.SPREF.IS_LOGIN, false))
                openActivity(LoginActivity.class);
        }

    }

    @OnClick({R.id.iv_top_back, R.id.iv_top_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                goBack();
                break;
            case R.id.iv_top_menu:

                break;
        }
    }

    private void uploadImage(File file) {
        Map<String, String> map = new HashMap<>();
        map.put("postfix", ".jpg");
        map.put("base64Str", FileUtil.fileToStream(file));

        HttpUtil.post(Constants.URL.UPLOAD_IMAGE, map).subscribe(new BaseResponseObserver<String>() {

            @Override
            public void success(String s) {
                EBLog.i(TAG, s);
                getPicture(s);
            }

            @Override
            public void error(Response<String> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(ActivityDetailActivity.this, response.getDesc());
            }
        });
    }

    public void goBack() {
        activity_detail_webView.evaluateJavascript("javascript:goBack()", new ValueCallback<String>() {

            @Override
            public void onReceiveValue(String value) {
                if (value.equals("false")) {
                    finish();
                }
            }
        });
    }

    private void getPicture(String imgUrl) {
        activity_detail_webView.evaluateJavascript("javascript:getPicture('" + imgUrl + "')", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                EBLog.i("H5回调", value);
            }
        });
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String imgUrl = result.getImage().getCompressPath();

        uploadImage(new File(imgUrl));
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    //返回解析结果字符串
                    zxResult = bundle.getString(CodeUtils.RESULT_STRING);
                    ToastUtil.makeText(this, "解析结果:" + zxResult);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.makeText(this, "解析二维码失败");
                }
            }
        }
    }
}
