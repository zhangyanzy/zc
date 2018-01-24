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
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;

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

    private String[] tabTitles = new String[]{"最新活动", "线上活动", "线下活动", "历史活动"};
    private Map<Integer, Fragment> fragments = new HashMap<>();


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

            }
        });
        home_view.setCurrentItem(0);
    }


    @Override
    public void loadData() {

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
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_title_search:
                openActivity(SearchActivity.class);
                break;
        }
    }

}


