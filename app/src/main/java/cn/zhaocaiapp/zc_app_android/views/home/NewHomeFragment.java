package cn.zhaocaiapp.zc_app_android.views.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.adapter.home.NewHomeAdapter;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.MessageEvent;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.bean.response.common.BannerEntity;
import cn.zhaocaiapp.zc_app_android.bean.response.common.UserResp;
import cn.zhaocaiapp.zc_app_android.bean.response.home.Gps;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.listener.OnBtnClickL;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.widget.NormalDialog;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.BannerImageLoader;
import cn.zhaocaiapp.zc_app_android.util.DialogUtil;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.LocationUtil;
import cn.zhaocaiapp.zc_app_android.util.NetworkUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.views.common.ActivityDetailActivity;
import cn.zhaocaiapp.zc_app_android.views.common.InformationDetailActivity;
import cn.zhaocaiapp.zc_app_android.widget.recycler.PullCallback;

import static android.app.Activity.RESULT_OK;
import static com.umeng.socialize.utils.ContextUtil.getPackageName;

/**
 * Created by admin on 2018/8/24.
 */

public class NewHomeFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener {

    private static String TAG = "NewHomeFragment";

    private Banner mBanner;
    private RecyclerView mRv;
    private View view;
    private List<BannerEntity> mBannerList;
    private int pageNumber = 1;//分页
    private int sortRule = 2;//降序 1升序 2降序(发布时间降序)
    private int sortType = 0;//默认 0默认 1时间 2金额 3距离
    private String longitude = "";//经度
    private String latitude = "";//纬度
    private NewHomeAdapter mAdapter;
    private boolean isLoading = false;
    private UserResp userResp;
    private boolean isFirst = true; //是否首次加载用户信息

    private static final String API_KEY = "25c0dceebb14cc8e48feb63bbda831a8";
    private static final String APP_KEY = "7c10df58102b007d320b5a63cda7f74f";
    private ProgressDialog dialog;
    private File mFile;

    private String areaName;//用户定位城市名称
    private ArrayList<ActivityResp> mActivityRespList;


    private Integer currentResult = 10;//每页条目
    private Integer pageSize;//页数


    @BindView(R.id.new_home_title_area_text)
    TextView home_title_area_text;
    @BindView(R.id.home_title_search)
    ImageView home_title_search;
    @BindView(R.id.new_home_detail_refresh)
    RefreshLayout member_detail_refresh;
    @BindView(R.id.new_home_location_root)
    RelativeLayout mLocationRoot;
    @BindView(R.id.root)
    RelativeLayout mRoot;
    @BindView(R.id.no_net_work_available)
    LinearLayout no_net_work_available;

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getContext(), R.layout.fragment_home_user, null);
        //注册EventBus消息订阅者
        EventBus.getDefault().register(this);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!isFirst) {

        }
        isFirst = false;
    }

    @Override
    public void init() {
        mBanner = view.findViewById(R.id.banner);
        mRv = view.findViewById(R.id.recycler_view_new_home);

        LinearLayoutManager manager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(manager);
        mAdapter = new NewHomeAdapter();
        mRv.setAdapter(mAdapter);
        member_detail_refresh.setOnRefreshListener(this);
        member_detail_refresh.setOnLoadmoreListener(this);
        member_detail_refresh.setEnableLoadmoreWhenContentNotFull(false);
        member_detail_refresh.autoRefresh();
    }

    private void initListeners() {

    }

    @Override
    public void loadData() {
        getBannerPhoto();
        initNetData();
        initListeners();

        //注册蒲公英版本更新监听
        PgyUpdateManager.register(getActivity(), updateListener);

//        //加载首页头部用户数据
//        getUserInfo();
    }

    //获取banner图片
    private void getBannerPhoto() {
        HttpUtil.get(Constants.URL.BANNER).subscribe(new BaseResponseObserver<List<BannerEntity>>() {

            @Override
            public void success(List<BannerEntity> bannerEntities) {
                if (bannerEntities != null) {
                    mBannerList = new ArrayList<>();
                    mBannerList.clear();
                    mBannerList = bannerEntities;
                    setPhotoToBanner();
                }
            }

            @Override
            public void error(Response<List<BannerEntity>> response) {

            }
        });

        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = null;
                if (mBannerList.get(position).getActivity_form() == 3 || mBannerList.get(position).getActivity_form() == 4) {
                    intent = new Intent(getContext(), InformationDetailActivity.class);
                } else {
                    intent = new Intent(getContext(), ActivityDetailActivity.class);
                }
                intent.putExtra("id", mBannerList.get(position).getKid());
                intent.putExtra("title", mBannerList.get(position).getName());
                intent.putExtra("isNeedQRCode", mBannerList.get(position).getIf_check());
                intent.putExtra("activityForm", mBannerList.get(position).getActivity_form());
                intent.putExtra("userType", "0");//表示用户端
                startActivity(intent);
            }
        });
    }

    private void setPhotoToBanner() {
        mBanner.setImages(mBannerList)
                .setImageLoader(new BannerImageLoader())
                .setDelayTime(3000)
                .setIndicatorGravity(BannerConfig.CENTER)
                .start();
    }

    /**
     * 解决因懒加载和进入时的自动刷新导致的重复加载
     * <p>
     * 加载网络数据
     */

    private void initNetData() {
        Map<String, String> params = new HashMap<>();
        params.put("pageSize", String.valueOf(Constants.CONFIG.PAGE_SIZE));
        params.put("currentResult", String.valueOf((pageNumber - 1) * Constants.CONFIG.PAGE_SIZE));
        HttpUtil.get(Constants.URL.NEW_HOME_ACTIVITY_LIST, params).subscribe(new BaseResponseObserver<ArrayList<ActivityResp>>() {
            @Override
            public void success(ArrayList<ActivityResp> activityResps) {
                if (activityResps.size() > 0) {
                    if (pageNumber == 1) {
                        mActivityRespList = activityResps;
                        //恢复没有更多数据的原始状态
                        member_detail_refresh.resetNoMoreData();
                    } else {
                        mActivityRespList.addAll(activityResps);
                    }
//                    if (mActivityRespList.size() > 0) {
//                        list_null.setVisibility(View.GONE);
//                    } else {
//                        list_null.setVisibility(View.VISIBLE);
//                    }
                    mAdapter.setList(activityResps);

                    if (activityResps.size() < Constants.CONFIG.PAGE_SIZE) {
                        //完成加载并标记没有更多数据
                        member_detail_refresh.finishLoadmoreWithNoMoreData();
                    }
                    if (member_detail_refresh.isRefreshing())
                        member_detail_refresh.finishRefresh();
                    else if (member_detail_refresh.isLoading())
                        member_detail_refresh.finishLoadmore();
                }
            }

            @Override
            public void error(Response<ArrayList<ActivityResp>> response) {

            }
        });
    }

    @Override
    public void onStart() {
        mBanner.startAutoPlay();
        if (!NetworkUtil.isNetworkAvailable(getActivity())) {
            mRoot.setVisibility(View.GONE);
            no_net_work_available.setVisibility(View.VISIBLE);
        } else {
            mRoot.setVisibility(View.VISIBLE);
            no_net_work_available.setVisibility(View.GONE);
        }
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
    }

    //是否开启定位通知
    private void openLocation() {
        areaName = (String) SpUtils.init(Constants.SPREF.FILE_APP_NAME).get(Constants.SPREF.AREA_NAME, Constants.CONFIG.AREA_NAME);
        //显示当前用户定位城市
        home_title_area_text.setText(areaName);
        EBLog.i(TAG, "当前定位城市-" + areaName);
        Gps gps = LocationUtil.getGps();
        if (gps.getOpen() && !areaName.equals(gps.getCity())) {
            String content = String.format(getString(R.string.change_position), gps.getCity());
            NormalDialog normalDialog = DialogUtil.showDialogTwoBut(getActivity(), "切换城市", content, "关闭", "切换");
            normalDialog.setCanceledOnTouchOutside(false);
            normalDialog.setCancelable(false);
            normalDialog.setOnBtnClickL(new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    EBLog.i(TAG, "您点击了关闭");
                    normalDialog.dismiss();

                    //提示新手任务
                    notifyNewTask();
                }
            }, new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    EBLog.i(TAG, "您点击了切换");
                    home_title_area_text.setText(gps.getCity());
                    SpUtils.init(Constants.SPREF.FILE_APP_NAME).put(Constants.SPREF.AREA_NAME, gps.getCity());
                    SpUtils.init(Constants.SPREF.FILE_APP_NAME).put(Constants.SPREF.AREA_CODE, gps.getAdCode());
                    EventBus.getDefault().post(new MessageEvent<String>("home_tab_0"));
                    normalDialog.dismiss();

                    //提示新手任务
                    notifyNewTask();
                }
            });
        } else {
            //提示新手任务
            notifyNewTask();
        }
    }

    /**
     * 新手任务提示
     */
    private void notifyNewTask() {
        //判断登录
        if ((boolean) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.IS_LOGIN, false)
                && (boolean) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.SHOW_NEWER_ACTIVITY, false)) {
            //获取新手任务
            HttpUtil.get(String.format(Constants.URL.GET_USERINFO_FRISTPAGE)).subscribe(new BaseResponseObserver<UserResp>() {
                @Override
                public void success(UserResp result) {
                    userResp = result;

                    //是否弹窗提示新手任务
                    if ((boolean) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.SHOW_NEWER_ACTIVITY, true)) {
                        //判断用户是否做新手任务
                        if (GeneralUtils.isNotNull(userResp.getIsFinishActivity()) && userResp.getIsFinishActivity() == 0) {
                            String content = getString(R.string.new_task_reward);
                            NormalDialog normalDialog = DialogUtil.showDialogTwoBut(getActivity(), "新手奖励", content, "忽略", "任务详情");
                            //点击空白处,弹窗是否消失
                            normalDialog.setCanceledOnTouchOutside(false);
                            //点击后退键，弹窗是否消失
                            normalDialog.setCancelable(false);
                            normalDialog.setOnBtnClickL(new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    EBLog.i(TAG, "您点击了忽略");
                                    normalDialog.cancel();
                                }
                            }, new OnBtnClickL() {
                                @Override
                                public void onBtnClick() {
                                    EBLog.i(TAG, "您点击了任务详情");
                                    Bundle bd = new Bundle();
                                    bd.putString("newbieAmount", GeneralUtils.getBigDecimalToTwo(userResp.getNewbieAmount()));
                                    openActivity(newbieTaskActivity.class, bd);
                                    normalDialog.dismiss();
                                }
                            });
                            SpUtils.init(Constants.SPREF.FILE_USER_NAME).put(Constants.SPREF.SHOW_NEWER_ACTIVITY, false);
                        }
                    }
                    EBLog.i(TAG, result.toString());
                }

                @Override
                public void error(Response<UserResp> response) {
                    EBLog.i(TAG, response.getCode() + "");
                    ToastUtil.makeText(getActivity(), response.getDesc());
                }
            });
        }
    }

    //蒲公英版本更新监听
    private UpdateManagerListener updateListener = new UpdateManagerListener() {
        @Override
        public void onNoUpdateAvailable() { //不更新
            openNotification();
            openLocation();
        }

        @Override
        public void onUpdateAvailable(String result) { //更新
            // 将新版本信息封装到AppBean中
            AlertDialog dialog = null;
            final AppBean appBean = getAppBeanFromString(result);
            if (PgyUpdateManager.isForced()) { //强制更新
                dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("更新")
                        .setMessage(appBean.getReleaseNote())
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            startDownloadTask(getActivity(), appBean.getDownloadURL());
                                            SpUtils.init(Constants.SPREF.FILE_USER_NAME).clear();
                                        } else {
                                            getNewAppUri();
                                        }
                                    }
                                })
                        .show();
            } else { //非强制更新
                dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("更新")
                        .setMessage(appBean.getReleaseNote())
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startDownloadTask(getActivity(), appBean.getDownloadURL());
                                        SpUtils.init(Constants.SPREF.FILE_USER_NAME).clear();

                                    }
                                })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                openNotification();
                                openLocation();
                                dialog.dismiss();
                            }
                        }).show();
            }
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        }
    };

    private void getNewAppUri() {
        String downloadUrl = String.format("https://www.pgyer.com/apiv2/app/install?_api_key=%s&appKey=%s", API_KEY, APP_KEY);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("正在下载中...请稍后");
        dialog.setIndeterminate(true);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(true);
        dialog.setMax(100);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        new DownloadAPK(dialog).execute(downloadUrl);
    }


    @SuppressLint("StaticFieldLeak")
    private class DownloadAPK extends AsyncTask<String, Integer, String> {

        ProgressDialog mProgressDialog;


        DownloadAPK(ProgressDialog mProgressDialog) {
            this.mProgressDialog = mProgressDialog;
        }

        //根据url获取下载apk;
        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection conn;
            BufferedInputStream bis = null;
            FileOutputStream fos = null;

            try {
                url = new URL(strings[0]);
                Log.i(TAG, "doInBackground: " + url);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                int status = conn.getResponseCode();
                Log.i(TAG, "status: " + status);
                if (status == conn.HTTP_MOVED_TEMP || status == conn.HTTP_MOVED_PERM || status == conn.HTTP_SEE_OTHER) {
                    String newUrl = conn.getHeaderField("Location");
                    url = new URL(newUrl);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    int newStatus = conn.getResponseCode();
                    Log.i(TAG, "newStatus: " + newStatus);
                }
                int fileLength = conn.getContentLength();
                bis = new BufferedInputStream(conn.getInputStream());
                String fileName = Environment.getExternalStorageDirectory().getPath() + "/zhaocai/zcapp.apk";
                mFile = new File(fileName);
                //判断文件是否存在
                if (!mFile.exists()) {
                    if (!mFile.getParentFile().exists()) {
                        mFile.getParentFile().mkdirs();
                    }
                    mFile.createNewFile();
                }
                fos = new FileOutputStream(mFile);
                byte data[] = new byte[4 * 1024];
                long total = 0;
                int count;
                while ((count = bis.read(data)) != -1) {
                    total += count;
                    publishProgress((int) (total * 100 / fileLength));
                    fos.write(data, 0, count);
                    fos.flush();
                }
                fos.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (bis != null) {
                        bis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }


        //改变ProgressDialog的进度
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mProgressDialog.setProgress(values[0]);
        }


        //到这里说明下载完成，判断文件是否存在，如果存在，执行安装apk的操作
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mProgressDialog.dismiss();
            Log.i(TAG, "onPostExecute: " + mFile);

            boolean haveInstaallPermission;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                haveInstaallPermission = getContext().getPackageManager().canRequestPackageInstalls();
                if (!haveInstaallPermission) {
                    //先判断是否安装未知来源权限
                    AlertDialog dialog = new AlertDialog.Builder(getActivity())
                            .setTitle("安装权限")
                            .setMessage("需要打开允许来自此来源，请去设置中开启此权限")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        startInstallPermissionSettingActivity();
                                    }
                                }
                            }).show();
                    return;
                }
                installApk(mFile);
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10086 && resultCode == RESULT_OK) {
            installApk(mFile);
        }
    }

    private void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(getActivity(), "cn.zhaocaiapp.zc_app_android.fileProvider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        getActivity().startActivity(intent);
    }


    //是否开启消息通知
    private void openNotification() {
        NotificationManagerCompat manager = NotificationManagerCompat.from(this.getActivity());
        boolean isOpened = manager.areNotificationsEnabled();
        EBLog.i(TAG, "消息推送通知开关：" + isOpened);
        if (!SpUtils.init(Constants.SPREF.FILE_USER_NAME).contains(Constants.SPREF.MESSAGE_PUSH)) {
            SpUtils.init(Constants.SPREF.FILE_USER_NAME).put(Constants.SPREF.MESSAGE_PUSH, new Date().getTime());
        }
        if (!isOpened) {
            Date messagePush = new Date((Long) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.MESSAGE_PUSH, new Date().getTime()));
            Calendar cal = Calendar.getInstance();
            cal.setTime(messagePush);
            cal.add(Calendar.DAY_OF_YEAR, 15);

            if (new Date().after(cal.getTime())) {
                SpUtils.init(Constants.SPREF.FILE_USER_NAME).put(Constants.SPREF.MESSAGE_PUSH, new Date().getTime());
                String content = getString(R.string.notice_switch);
                NormalDialog normalDialog = DialogUtil.showDialogTwoBut(getActivity(), null, content, "取消", "设置");
                normalDialog.isTitleShow(false);
                normalDialog.setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        EBLog.i(TAG, "您点击了取消");
                        normalDialog.cancel();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        EBLog.i(TAG, "您点击了设置");
                        // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", NewHomeFragment.this.getActivity().getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        normalDialog.dismiss();
                    }
                });
            }
        }
    }

    @OnClick({R.id.new_home_title_area_text, R.id.home_title_search, R.id.new_home_location_root})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_home_location_root:
                openActivity(LocationActivity.class);
                break;
            case R.id.home_title_search:
                openActivity(SearchActivity.class);
                break;
            default:
                break;
        }
    }

    //接收EventBus发送的消息，并处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent<String> event) {
        if (event.getMessage() instanceof String) {
            if (event.getMessage().equals("home_location")) {
                EventBus.getDefault().post(new MessageEvent<String>("home_tab_0"));
                home_title_area_text.setText((String) SpUtils.init(Constants.SPREF.FILE_APP_NAME).get(Constants.SPREF.AREA_NAME, Constants.CONFIG.AREA_NAME));
                EBLog.i(TAG, "接收到定位切换消息");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isFirst = true;
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNumber = 1;
        initNetData();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        pageNumber = pageNumber + 1;
        initNetData();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        Uri packageURI = Uri.parse("package:" + getPackageName());
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, 10086);
    }


}
