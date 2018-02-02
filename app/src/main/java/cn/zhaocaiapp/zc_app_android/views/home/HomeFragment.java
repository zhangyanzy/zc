package cn.zhaocaiapp.zc_app_android.views.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.MainActivity;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.MessageEvent;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.home.UserInfoResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.views.login.LoginActivity;

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

    private String[] tabTitles = new String[]{"最新活动", "线上活动", "线下活动", "历史活动"};
    private Map<Integer, Fragment> fragments = new HashMap<>();
    private UserInfoResp userInfoResp;//用户个人信息


    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_main, container, false);
    }

    @Override
    public void init() {
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
                EBLog.i("tag", "您再次点击了");
                EBLog.i("tag", tab.toString());
                EventBus.getDefault().post(new MessageEvent<String>("home_tab_" + tab.getPosition()));
            }
        });
        home_view.setCurrentItem(0);
    }


    @Override
    public void loadData() {

        //判断登录
        if (GeneralUtils.isNotNull((String) SpUtils.get(Constants.SPREF.TOKEN, ""))) {
            //获取商家详情
            HttpUtil.get(String.format(Constants.URL.GET_ACTIVITY_USER)).subscribe(new BaseResponseObserver<UserInfoResp>() {
                @Override
                public void success(UserInfoResp result) {
                    userInfoResp = result;
                    //用户头像
                    PictureLoadUtil.loadPicture(getActivity(), userInfoResp.getAvatar(), home_title_user_img);
                    //用户昵称
                    home_title_user_name.setText(userInfoResp.getNickname());
                    //用户总收入
                    home_title_user_income.setText(String.valueOf(userInfoResp.getGrossIncomeAmount()));
                    //用户余额
                    home_title_user_balance.setText(String.valueOf(userInfoResp.getAccountBalanceAmount()));
                    EBLog.i("tag", result.toString());
                }

                @Override
                public void error(Response<UserInfoResp> response) {
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
                case 0: // 个人资料
                    fragment = new NewFragment();
                    break;
                case 1: // 实名信息
                    fragment = new OnLineFragment();
                    break;
                case 2: // 活动相关信息
                    fragment = new LineFragment();
                    break;
                case 3: // 标签信息
                    fragment = new HistoryFragment();
                    break;
            }
            fragments.put(position, fragment);
        }
        return fragment;
    }

    @OnClick({
            R.id.home_title_search,
            R.id.home_title_area,
            R.id.home_title_user_cart
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_title_search:
                openActivity(SearchActivity.class);
                break;
            case R.id.home_title_area:
                openActivity(LocationActivity.class);
                break;
            case R.id.home_title_user_cart:
                //已登录
                if (GeneralUtils.isNotNull((String) SpUtils.get(Constants.SPREF.TOKEN, ""))) {
                    ((MainActivity) getActivity()).setCheckButton(2);
                }
                //未登录
                else {
                    openActivity(LoginActivity.class);
                }
                break;
        }
    }

}


