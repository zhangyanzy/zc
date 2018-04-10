package cn.zhaocaiapp.zc_app_android.views.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.MainActivity;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.MessageEvent;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.UserResp;
import cn.zhaocaiapp.zc_app_android.bean.response.home.Gps;
import cn.zhaocaiapp.zc_app_android.bean.response.home.UserInfoResp;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.listener.OnBtnClickL;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.widget.NormalDialog;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.DialogUtil;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.LocationUtil;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.views.login.LoginActivity;
import cn.zhaocaiapp.zc_app_android.widget.AntiShake;

/**
 * @author 林子
 * @filename HomeActivity.java
 * @data 2018-01-05 17:52
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.home_tabs)
    TabLayout home_tabs;
    @BindView(R.id.home_view)
    ViewPager home_view;
    @BindView(R.id.home_title_search)
    ImageView home_title_search;
    @BindView(R.id.home_title_area)
    ImageView home_title_area;
    @BindView(R.id.home_title_user_img)
    ImageView home_title_user_img;
    @BindView(R.id.home_title_user_name)
    TextView home_title_user_name;
    @BindView(R.id.home_title_user_income)
    TextView home_title_user_income;
    @BindView(R.id.home_title_user_balance)
    TextView home_title_user_balance;
    @BindView(R.id.home_title_user_cart)
    LinearLayout home_title_user_cart;
    @BindView(R.id.home_title_area_text)
    TextView home_title_area_text;
    @BindView(R.id.home_title_area_layout)
    LinearLayout home_title_area_layout;

    private String[] tabTitles = new String[]{"最新活动", "线上活动", "线下活动", "历史活动"};
    private Map<Integer, Fragment> fragments = new HashMap<>();
    private UserInfoResp userInfoResp;//用户个人信息
    private String areaName;//用户定位城市名称
    private UserResp userResp;//用户

    private boolean isFirst = true; //是否首次加载用户信息
    private AntiShake antiShake = new AntiShake();

    private static final String TAG = "首页";

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirst)
            getUserInfo();
        isFirst = false;
        EBLog.i(TAG, "---onResume---加载首页用户数据");
    }

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_main, container, false);
    }

    @Override
    public void init() {
        //注册EventBus消息订阅者
        EventBus.getDefault().register(this);

        home_view.setOffscreenPageLimit(tabTitles.length);
        home_view.setAdapter(new HomeFragmentPagerAdapter(this.getActivity().getSupportFragmentManager()));
        home_tabs.setupWithViewPager(home_view);
        for (int i = 0; i < tabTitles.length; i++) {
            home_tabs.getTabAt(i).setCustomView(R.layout.home_tab);
            TextView textView = (TextView) home_tabs.getTabAt(i).getCustomView().findViewById(R.id.activity_tab_text);
            textView.setText(tabTitles[i]);
            if (i == 0) {
                textView.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));
                textView.setBackground(getActivity().getResources().getDrawable(R.drawable.home_tab_on));
            }
        }
        home_tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //选中了tab的逻辑
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView textView = (TextView) tab.getCustomView().findViewById(R.id.activity_tab_text);
                textView.setTextColor(getActivity().getResources().getColor(R.color.colorWhite));
                textView.setBackground(getActivity().getResources().getDrawable(R.drawable.home_tab_on));
            }

            //未选中tab的逻辑
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView textView = (TextView) tab.getCustomView().findViewById(R.id.activity_tab_text);
                textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                textView.setBackground(getActivity().getResources().getDrawable(R.drawable.home_tab_off));
            }

            //再次选中tab的逻辑
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                EBLog.i(TAG, "您再次点击了---" + tab.getText());
                EventBus.getDefault().post(new MessageEvent<String>("home_tab_" + tab.getPosition()));
            }
        });
        home_view.setCurrentItem(0);
    }


    @Override
    public void loadData() {
        //注册蒲公英版本更新监听
        PgyUpdateManager.register(getActivity(), updateListener);

        //加载首页头部用户数据
        getUserInfo();
    }

    //首页获取用户信息
    private void getUserInfo() {
        //判断登录
        if ((boolean) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.IS_LOGIN, false)) {
            //获取用户信息
            HttpUtil.get(String.format(Constants.URL.GET_ACTIVITY_USER)).subscribe(new BaseResponseObserver<UserInfoResp>() {
                @Override
                public void success(UserInfoResp result) {
                    EBLog.i(TAG, "用户信息---" + result.toString());
                    userInfoResp = result;
                    //用户头像
                    if (GeneralUtils.isNotNullOrZeroLenght(userInfoResp.getAvatar())) {
                        PictureLoadUtil.loadPicture(getActivity(), userInfoResp.getAvatar(), home_title_user_img);
                    }
                    //用户昵称
                    home_title_user_name.setText(userInfoResp.getNickname());
                    //用户总收入
                    home_title_user_income.setText(String.valueOf(userInfoResp.getGrossIncomeAmount()));
                    //用户余额
                    home_title_user_balance.setText(String.valueOf(userInfoResp.getAccountBalanceAmount()));
                }

                @Override
                public void error(Response<UserInfoResp> response) {
                    EBLog.i(TAG, response.getCode() + "");
                    ToastUtil.makeText(getActivity(), response.getDesc());
                }
            });
        }
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
                        Uri uri = Uri.fromParts("package", HomeFragment.this.getActivity().getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        normalDialog.dismiss();
                    }
                });
            }
        }
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

    //接收EventBus发送的消息，并处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent<String> event) {
        if (event.getMessage() instanceof String) {
            if (event.getMessage().equals("home_location")) {
                home_view.setCurrentItem(0);
                EventBus.getDefault().post(new MessageEvent<String>("home_tab_0"));
                home_title_area_text.setText((String) SpUtils.init(Constants.SPREF.FILE_APP_NAME).get(Constants.SPREF.AREA_NAME, Constants.CONFIG.AREA_NAME));
                EBLog.i(TAG, "接收到定位切换消息");
            }
        }
    }

    /**
     * 新手任务提示
     */
    private void notifyNewTask() {
        //判断登录
        if ((boolean) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.IS_LOGIN, false)
                && (boolean) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.SHOW_NEWER_ACTIVITY, true)) {
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

    /**
     * tabs控制器
     */
    private class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
        public HomeFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return getFragment(position);
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

    }

    /**
     * Fragment数据
     *
     * @param position
     * @return
     */
    private Fragment getFragment(int position) {
        Fragment fragment = fragments.get(position);
        if (fragment == null) {
            switch (position) {
                case 0: // 最新活动
                    fragment = new NewFragment();
                    break;
                case 1: // 线上活动
                    fragment = new OnLineFragment();
                    break;
                case 2: // 线下活动
                    fragment = new LineFragment();
                    break;
                case 3: // 历史活动
                    fragment = new HistoryFragment();
                    break;
            }
            fragments.put(position, fragment);
        }
        return fragment;
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
                                        startDownloadTask(getActivity(), appBean.getDownloadURL());
                                        SpUtils.init(Constants.SPREF.FILE_USER_NAME).clear();
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

    @OnClick({R.id.home_title_search, R.id.home_title_user_cart, R.id.home_title_area_layout})
    public void onClick(View view) {
        if (antiShake.check(view.getId())) return;
        switch (view.getId()) {
            case R.id.home_title_search: //搜索
                openActivity(SearchActivity.class);
                break;
            case R.id.home_title_area_layout: //切换定位地点
                openActivity(LocationActivity.class);
                break;
            case R.id.home_title_user_cart:
                //已登录
                if ((boolean) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.IS_LOGIN, false)) {
                    ((MainActivity) getActivity()).setCheckButton(2);
                }
                //未登录
                else {
                    openActivity(LoginActivity.class);
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isFirst = true;
    }
}


