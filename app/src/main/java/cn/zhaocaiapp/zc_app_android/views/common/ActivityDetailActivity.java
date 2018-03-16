package cn.zhaocaiapp.zc_app_android.views.common;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.transition.Transition;
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


import com.google.gson.Gson;
import com.jph.takephoto.model.TResult;
import com.umeng.socialize.UMShareAPI;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.base.BasePhotoActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.capabilities.json.GsonHelper;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.capabilities.takephoto.PhotoHelper;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.ActivityUtil;
import cn.zhaocaiapp.zc_app_android.util.DeviceUtil;
import cn.zhaocaiapp.zc_app_android.util.FileUtil;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;
import cn.zhaocaiapp.zc_app_android.util.ShareUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.LocationUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.views.login.LoginActivity;
import cn.zhaocaiapp.zc_app_android.widget.SampleFullPlayer;
import pub.devrel.easypermissions.EasyPermissions;


public class ActivityDetailActivity extends BasePhotoActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.iv_top_back)
    ImageView iv_back;
    @BindView(R.id.tv_top_title)
    TextView tv_title;
    @BindView(R.id.iv_top_menu)
    ImageView iv_menu;
    @BindView(R.id.activity_detail_webView)
    WebView activity_detail_webView;
    @BindView(R.id.vp_player)
    SampleFullPlayer vp_player;

    private View rootView;
    private PhotoHelper photoHelper;
    private String zxResult; //二维码扫描解析结果

    private UMShareAPI shareAPI;

    private static final String TAG = "H5详情页";
    private static final int REQUEST_CODE = 2001;
    public final static String TRANSITION = "TRANSITION";

    private String activityUrl = "/activity/detail?id=%s"; // 分享活動url
    private String inviteUrl = "/activity/detail?id=%s&code=%s"; //邀請好友協同活動

    private long activityId;  // 活动id
    private String inviteCode = "0";  //活動邀請碼
    private String activityTitle; // 活动名称

    private ImageView backBut;//返回键
    private View startBut;//播放键

    private boolean isTransition;
    private Transition transition;

    @Override
    public int getContentViewResId() {
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_detail, null);
        return R.layout.activity_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        ActivityUtil.addActivity(this);
        shareAPI = ZcApplication.getUMShareAPI();

        activityId = getIntent().getLongExtra("id", -1);
        activityTitle = getIntent().getStringExtra("title");

        //从浏览器跳转回活动详情
        Uri uri = getIntent().getData();
        if (uri != null) {
            activityId = Long.valueOf(uri.getQueryParameter("id"));
            inviteCode = uri.getQueryParameter("code");
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

    //初始化播放器控件
    private void initPlayer(){
//        isTransition = getIntent().getBooleanExtra(TRANSITION, false);

        //设置返回键
        backBut = vp_player.getBackButton();
        backBut.setVisibility(View.VISIBLE);
        //设置播放键
        startBut = vp_player.getStartButton();
        startBut.setVisibility(View.VISIBLE);
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
            params.put("id", activityId + "");
            //用戶token
            params.put("token", (String) SpUtils.get(Constants.SPREF.TOKEN, ""));
            params.put("type", "0");
            //手机唯一识别码
            params.put("deviceUUID", DeviceUtil.getDeviceModel());

            //邀請码
            if (!inviteCode.equals("0")) {
                params.put("code", inviteCode);
            }

            //经度
            params.put("longitude", String.valueOf(LocationUtil.getGps().getLongitude()));
            //经度
            params.put("latitude", String.valueOf(LocationUtil.getGps().getLatitude()));

            EBLog.i("tag", GsonHelper.toJson(params));

            return GsonHelper.toJson(params);
        }

        @JavascriptInterface
        public String getUser() {
            Map<String, String> params = new HashMap<>();

            //用戶token
            params.put("token", (String) SpUtils.get(Constants.SPREF.TOKEN, ""));

            EBLog.i("tag", GsonHelper.toJson(params));

            return GsonHelper.toJson(params);
        }

        //掃二維碼及拍照
        @JavascriptInterface
        public void takePhoto(String code) {
            if (code.equals("-1")) { // 不检查二维码
                photoHelper.onClick(0, getTakePhoto());
            } else { // 需要校验二维码
                if (EasyPermissions.hasPermissions(ActivityDetailActivity.this, Manifest.permission.CAMERA)) {
                    Intent intent = new Intent(ActivityDetailActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    EasyPermissions.requestPermissions(ActivityDetailActivity.this, null, REQUEST_CODE, new String[]{Manifest.permission.CAMERA});
                }
            }
        }

        //跳轉登錄
        @JavascriptInterface
        public void goLogin() {
            if (!(boolean) SpUtils.get(Constants.SPREF.IS_LOGIN, false))
                turoToLogin();
        }

        //邀請好友協同任務
        @JavascriptInterface
        public void inviteFriend(String code) {
            String webUrl = String.format(inviteUrl, activityId, code);
            shareActivity(webUrl);
        }
    }

    //分享活动
    private void shareActivity(String webUrl) {
        String shareDesc = getString(R.string.share_desc);
        ShareUtil.init(this)
                .setUrl(webUrl)
                .setSourceId(R.mipmap.ic_launcher)
                .setTitle(activityTitle)
                .setDesc(shareDesc);
        ShareUtil.openShare();
    }

    @OnClick({R.id.iv_top_back, R.id.iv_top_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                goBack();
                break;
            case R.id.iv_top_menu:
                String webUrl = String.format(activityUrl, activityId);
                shareActivity(webUrl);
                break;
        }
    }

    //未登录，跳转至登录页
    private void turoToLogin() {
        Bundle bundle = new Bundle();
        bundle.putLong("id", activityId);
        bundle.putString("title", activityTitle);
        openActivity(LoginActivity.class, bundle);
    }

    //上傳圖片至圖片服務器
    private void uploadImage(Bitmap bitmap) {
        Map<String, String> map = new HashMap<>();
        map.put("postfix", ".jpg");
        map.put("base64Str", PictureLoadUtil.bitmapToBase64(bitmap));

        HttpUtil.post(Constants.URL.UPLOAD_IMAGE, map).subscribe(new BaseResponseObserver<String>() {

            @Override
            public void success(String s) {
                EBLog.i(TAG, s);
                getPicture(s, zxResult);
            }

            @Override
            public void error(Response<String> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(ActivityDetailActivity.this, response.getDesc());
            }
        });
    }

    //H5中點擊返回按鈕
    public void goBack() {
        activity_detail_webView.evaluateJavascript("javascript:goBack()", new ValueCallback<String>() {

            @Override
            public void onReceiveValue(String value) {
                if (value.equals("false")) {
                    ActivityUtil.finishActivity(ActivityDetailActivity.this);
                }
            }
        });
    }

    //交付任務，提交二維碼解析結果及圖片地址
    private void getPicture(String imgUrl, String code) {
        Map<String, String> map = new HashMap<>();
        map.put("pictureUrl", imgUrl);
        map.put("qrCode", code);
        String s = new Gson().toJson(map);
        activity_detail_webView.evaluateJavascript("javascript:getPicture('" + s + "')", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                EBLog.i("H5回调", value);
            }
        });
    }

    //设置图片长宽压缩的比例
    private BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

    //拍照成功返回
    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String imgUrl = result.getImage().getCompressPath();

        //将图片长宽缩小为原来的 1/2，并返回bitmap
        Bitmap bitmap = BitmapFactory.decodeFile(imgUrl, getBitmapOption(2));
        //给图片添加水印
        String curTime = GeneralUtils.getNowDateString();
        Bitmap pic = PictureLoadUtil.drawTextToRightBottom(this, bitmap, curTime, 12, Color.YELLOW, 6, 6);
        uploadImage(pic);
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
        shareAPI.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    ToastUtil.makeText(this, "二维码解析失败");
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    //返回解析结果字符串
                    zxResult = bundle.getString(CodeUtils.RESULT_STRING);
                    photoHelper.onClick(0, getTakePhoto());
                    ToastUtil.makeText(ActivityDetailActivity.this, "解析结果:" + zxResult);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.makeText(ActivityDetailActivity.this, "二维码解析失败");
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == REQUEST_CODE && perms.contains(Manifest.permission.CAMERA)) {
            Intent intent = new Intent(ActivityDetailActivity.this, CaptureActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shareAPI.release();
    }
}
