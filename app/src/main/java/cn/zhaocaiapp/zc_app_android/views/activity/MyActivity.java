package cn.zhaocaiapp.zc_app_android.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;

/**
 * Created by Administrator on 2018/1/29.
 */

public class MyActivity extends BaseActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.tv_top_title)
    TextView tv_top_titlel;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
    @BindView(R.id.tab_title)
    TabLayout tabTitle;
    @BindView(R.id.pager)
    ViewPager pager;

    private String[] tabTitles = {"全部活动", "待交付", "待审核", "待领钱", "未通过"};
    private List<Fragment>fragments = new ArrayList<>();
    private int curPosition;

    private UMShareAPI umShareAPI;

    @Override
    public int getContentViewResId() {
        return R.layout.layout_tab_fragment;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        umShareAPI = ZcApplication.getUMShareAPI();

        iv_top_menu.setVisibility(View.GONE);
        tv_top_titlel.setText("我的活动");

        curPosition = getIntent().getIntExtra("position", 0);

        fragments.add(new AllActivityFragment());
        fragments.add(new DeliverActivityFragment());
        fragments.add(new VerifyActivityFragment());
        fragments.add(new RewardActivityFragment());
        fragments.add(new UnpassActivityFragment());

        pager.setOffscreenPageLimit(tabTitles.length);
        pager.setAdapter(new MyActivityPagerAdapter(getSupportFragmentManager()));
        tabTitle.setupWithViewPager(pager);
        pager.setCurrentItem(curPosition);

    }


    private class MyActivityPagerAdapter extends FragmentPagerAdapter {
        public MyActivityPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
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

    @OnClick(R.id.iv_top_back)
    public void onClick(View view){
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        umShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        umShareAPI.release();
    }
}
