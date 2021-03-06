package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.widget.NavigationTopBar;

/**
 * Created by Administrator on 2018/1/15.
 */

public class IncomeActivity extends BaseActivity implements NavigationTopBar.NavigationTopBarClickListener {

    @BindView(R.id.tab_title)
    TabLayout tab_title;
    @BindView(R.id.pager)
    ViewPager pager;

    private NavigationTopBar mNavigationTopBar;
    private String[] tabTitles = new String[]{"收入明细", "提现"};
    private Map<Integer, Fragment> fragments = new HashMap<>();


    @Override
    public int getContentViewResId() {
        return R.layout.layout_tab_fragment;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        mNavigationTopBar = findViewById(R.id.my_follow_top);
        mNavigationTopBar.setLeftImageResource(R.mipmap.finish_icon);
        mNavigationTopBar.setCenterTitleTextColor(R.color.colorBlack);
        mNavigationTopBar.setCenterTitleText("交易明细");
        mNavigationTopBar.setNavigationTopBarClickListener(this);
        pager.setAdapter(new IncomePagerAdapter(getSupportFragmentManager()));
        tab_title.setupWithViewPager(pager);
        pager.setCurrentItem(0);
    }

    @Override
    public void leftImageClick() {
        finish();
    }

    @Override
    public void rightImageClick() {

    }

    @Override
    public void alignRightLeftImageClick() {

    }

//    @OnClick({R.id.iv_top_back, R.id.iv_top_menu})
//    public void onClick(View view){
//        switch (view.getId()){
//            case R.id.iv_top_back:
//                finish();
//                break;
//            case R.id.iv_top_menu:
//                openActivity(IncomeShareActivity.class);
//                break;
//        }
//    }

    private class IncomePagerAdapter extends FragmentPagerAdapter {
        public IncomePagerAdapter(FragmentManager fm) {
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

    private Fragment getFragment(int position) {
        Fragment fragment = fragments.get(position);
        if (fragment == null) {
            switch (position) {
                case 0: // 收入明细
                    fragment = new IncomeFragment();
                    break;
                case 1: // 提现明细
                    fragment = new WithdrawFragment();
                    break;
            }
            fragments.put(position, fragment);
        }
        return fragment;
    }
}
