package cn.zhaocaiapp.zc_app_android.views.common;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
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
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.umeng.socialize.UMShareAPI;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

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
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.LocationUtil;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;
import cn.zhaocaiapp.zc_app_android.util.ShareUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.views.login.LoginActivity;
import cn.zhaocaiapp.zc_app_android.widget.OnTransitionListener;
import cn.zhaocaiapp.zc_app_android.widget.SampleControlPlayer;
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
    SampleControlPlayer vp_player;


    private View rootView;
    private PhotoHelper photoHelper;
    private String zxResult; //二维码扫描解析结果

    private UMShareAPI shareAPI;

    private static final String TAG = "H5详情页";
    private static final int REQUEST_CODE = 2001;
    public final static String IMG_TRANSITION = "IMG_TRANSITION";

    private String activityUrl = "/activity/detail?id=%s"; // 分享活動url
    private String inviteUrl = "/activity/detail?id=%s&code=%s"; //邀請好友協同活動

    private long activityId;  // 活动id
    private String inviteCode = "0";  //活動邀請碼
    private String activityTitle; // 活动名称

    private ImageView backBut;//返回键
    private View startBut;//播放键
    private TextView vp_title;//视频标题
    private ImageView fullBut;//全屏键

    private Transition transition;
    private OrientationUtils orientationUtils;
    private Runnable runnable;
    private float curTime;//当前播放时间
    private int isPlayComplete = 0;//是否播放完毕  0-未播放完毕  1-已播放完毕
    private GSYVideoOptionBuilder gsyVideoOption;
    private MediaPlayer mMediaPlayer;


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
        activity_detail_webView.setWebChromeClient(new WebChromeClient());//解决alert不弹出问题
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

        //初始化音频播放器
        mMediaPlayer =MediaPlayer.create(this, R.raw.radio);
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
            params.put("token", (String) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.TOKEN, ""));
            params.put("type", "0");
            //手机唯一识别码
            params.put("deviceUUID", DeviceUtil.getDeviceModel());
            //经度
            params.put("longitude", String.valueOf(LocationUtil.getGps().getLongitude()));
            //经度
            params.put("latitude", String.valueOf(LocationUtil.getGps().getLatitude()));
            //邀請码
            if (!inviteCode.equals("0")) {
                params.put("code", inviteCode);
            }
            EBLog.i(TAG, GsonHelper.toJson(params));

            return GsonHelper.toJson(params);
        }

        @JavascriptInterface
        public String getUser() {
            Map<String, String> params = new HashMap<>();
            //用戶token
            params.put("token", (String) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.TOKEN, ""));
            EBLog.i(TAG, GsonHelper.toJson(params));

            return GsonHelper.toJson(params);
        }

        //掃二維碼及拍照
        @JavascriptInterface
        public void takePhoto(String code) {
            if (code.equals("-1")) { // 不检查二维码
                photoHelper.onClick(0, getTakePhoto());
            } else { // 需要校验二维码x
                if (EasyPermissions.hasPermissions(ActivityDetailActivity.this, Manifest.permission.CAMERA)) {
                    Intent intent = new Intent(ActivityDetailActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                } else {
                    EasyPermissions.requestPermissions(ActivityDetailActivity.this, "应用缺少拍照权限，请获取拍照权限", REQUEST_CODE, new String[]{Manifest.permission.CAMERA});
                }
            }
        }

        //跳轉登錄
        @JavascriptInterface
        public void goLogin() {
            if (!(boolean) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.IS_LOGIN, false))
                turnToLogin();
        }

        //邀請好友協同任務
        @JavascriptInterface
        public void inviteFriend(String code) {
            String webUrl = String.format(inviteUrl, activityId, code);
            shareActivity(webUrl);
        }

        //发送视频资源
        @JavascriptInterface
        public void postUrl(String videoUrl) {
            EBLog.i(TAG, "videoUrl---" + videoUrl);

            //初始化播放器控件
            initPlayer();
            //初始化过渡动画
            initTransition();
            //配置播放器资源
            startPlayer(videoUrl);
        }

        //点击全屏播放
        @JavascriptInterface
        public void enterFull(String url, float time) {
            EBLog.i(TAG, "url---" + url + "\n time---" + time);

            //外部辅助的旋转，帮助全屏
            orientationUtils = new OrientationUtils(ActivityDetailActivity.this, vp_player);
            //打开外部的旋转
            orientationUtils.setEnable(true);
            isPlayComplete = 0;
            showPlayer(url, time);
        }

        @JavascriptInterface
        public void playMedia(){
//            EBLog.i(TAG, "这是一个音频");
            mMediaPlayer.start();
        }
    }

    //分享活动
    private void shareActivity(String webUrl) {
        String shareDesc = getString(R.string.share_desc);
        ShareUtil.init(this)
                .setUrl(webUrl)
                .setSourceId(R.mipmap.icon_launcher)
                .setTitle(activityTitle)
                .setDesc(shareDesc);
        ShareUtil.openShare();
    }

    //初始化播放器控件
    private void initPlayer() {
        //显示视频标题
        vp_title = vp_player.getTitleTextView();
        vp_title.setVisibility(View.VISIBLE);
        //设置返回键
        backBut = vp_player.getBackButton();
        backBut.setVisibility(View.VISIBLE);
        //设置播放键
        startBut = vp_player.getStartButton();
        startBut.setVisibility(View.VISIBLE);
        //设置全屏键
        fullBut = vp_player.getFullscreenButton();
        fullBut.setVisibility(View.GONE);
        //屏蔽滑动快进
        View view = vp_player.findViewById(R.id.progress);
        view.setEnabled(false);

        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //初始化过渡动画
    private void initTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
            ViewCompat.setTransitionName(vp_player, IMG_TRANSITION);
            addTransitionListener();
            startPostponedEnterTransition();
        } else {
            vp_player.startPlayLogic();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean addTransitionListener() {
        transition = getWindow().getSharedElementEnterTransition();
        if (transition != null) {
            transition.addListener(new OnTransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    super.onTransitionEnd(transition);
                    vp_player.startPlayLogic();
                    transition.removeListener(this);
                }
            });
            return true;
        }
        return false;
    }

    //配置播放器资源
    private void startPlayer(String url) {
        //初始化播放器配置
        gsyVideoOption = new GSYVideoOptionBuilder()
                .setIsTouchWiget(false)
                .setIsTouchWigetFull(false)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(true)
                .setCacheWithPlay(false)
                .setUrl(url)
                .setVideoTitle(activityTitle)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        EBLog.i(TAG, "onPrepared---准备视频资源");
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        EBLog.i(TAG, "onEnterFullscreen---进入全屏");
                    }

                    @Override
                    public void onAutoComplete(String url, Object... objects) {
                        super.onAutoComplete(url, objects);
                        EBLog.i(TAG, "播放完毕");
                        isPlayComplete = 1;
                    }

                    @Override
                    public void onClickStartError(String url, Object... objects) {
                        super.onClickStartError(url, objects);
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        EBLog.i(TAG, "onQuitFullscreen---退出全屏");
                    }
                })
                .setGSYVideoProgressListener(new GSYVideoProgressListener() {
                    @Override
                    public void onProgress(int progress, int secProgress, int currentPosition, int duration) {
                        EBLog.i(TAG, "当前播放时间-" + currentPosition);
                        curTime = Float.valueOf(currentPosition / 1000 + "");
                    }
                })
                .setStartAfterPrepared(true);
    }

    //显示全屏播放器
    private void showPlayer(String url, float time) {
        runnable = new Runnable() {
            @Override
            public void run() {
                vp_player.setVisibility(View.VISIBLE);
                orientationUtils.resolveByClick();

                gsyVideoOption.setSeekOnStart((long) time * 1000L).build(vp_player);
                startBut.performClick();
                EBLog.i(TAG, "切换至UI线程播放视频");
            }
        };
        ActivityDetailActivity.this.runOnUiThread(runnable);
    }

    //由全屏返回小屏播放
    private void turnToWebPlayer() {
        Map<String, String> map = new HashMap<>();
        map.put("currentTime", curTime + "");
        map.put("allPlay", isPlayComplete + "");
        String s = new Gson().toJson(map);
        activity_detail_webView.evaluateJavascript("javascript:getVideoInfo('" + s + "')", new ValueCallback<String>() {

            @Override
            public void onReceiveValue(String value) {
                EBLog.i(TAG, "返回小屏播放-" + value);
            }
        });
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
    private void turnToLogin() {
        Bundle bundle = new Bundle();
        bundle.putLong("id", activityId);
        bundle.putString("title", activityTitle);
        openActivityForResult(LoginActivity.class, bundle, REQUEST_CODE);
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

    //登陆成功回调H5,通知刷新
//    private void refresh() {
//        activity_detail_webView.evaluateJavascript("javascript:getHadLogin()", new ValueCallback<String>() {
//            @Override
//            public void onReceiveValue(String value) {
//                EBLog.i(TAG, "刷新---" + value);
//            }
//        });
//    }

    //交付任務，提交二維碼解析結果及圖片地址
    private void getPicture(String imgUrl, String code) {
        Map<String, String> map = new HashMap<>();
        map.put("pictureUrl", imgUrl);
        map.put("qrCode", code);
        String s = new Gson().toJson(map);
        activity_detail_webView.evaluateJavascript("javascript:getPicture('" + s + "')", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                EBLog.i(TAG, "交付任务-" + value);
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
        if (requestCode == REQUEST_CODE) {
            if (resultCode == LoginActivity.RESULT_CODE) {
//                refresh();
                activity_detail_webView.reload();
                return;
            }
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
                    EBLog.i(TAG, "二维码解析结果:" + zxResult);
                    photoHelper.onClick(0, getTakePhoto());
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
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils != null) {
            if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                vp_player.getFullscreenButton().performClick();
                orientationUtils.backToProtVideo();
                vp_player.setVisibility(View.GONE);
                //释放播放器资源
                vp_player.setVideoAllCallBack(null);
                GSYVideoManager.releaseAllVideos();
                orientationUtils = null;
                //通知H5继续播放
                turnToWebPlayer();
                return;
            }
        }
        goBack();
    }

    @Override
    protected void onPause() {
        super.onPause();
        vp_player.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vp_player.onVideoResume();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        shareAPI.release();
        vp_player.release();
        mMediaPlayer.release();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

}
