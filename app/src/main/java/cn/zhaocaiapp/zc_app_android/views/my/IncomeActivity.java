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

/**
 * Created by Administrator on 2018/1/15.
 */

public class IncomeActivity extends BaseActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
    @BindView(R.id.tv_top_title)
    TextView tv_top_title;
    @BindView(R.id.tab_title)
    TabLayout tab_title;
    @BindView(R.id.pager)
    ViewPager pager;

    private String[]tabTitles = new String[]{"收入明细", "提现"};
    private Map<Integer, Fragment> fragments = new HashMap<>();


    @Override
    public int getContentViewResId() {
        return R.layout.layout_tab_fragment;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        iv_top_menu.setVisibility(View.GONE);

        pager.setAdapter(new IncomePagerAdapter(getSupportFragmentManager()));
        tab_title.setupWithViewPager(pager);
        pager.setCurrentItem(0);
    }

    @OnClick({R.id.iv_top_back})
    public void onClick(View view){
        finish();
    }

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

    private Fragment getFragment(int position){
        Fragment fragment = fragments.get(position);
        if (fragment == null){
            switch (position){
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
