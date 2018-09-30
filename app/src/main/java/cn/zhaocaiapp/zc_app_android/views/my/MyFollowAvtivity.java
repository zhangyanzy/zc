package cn.zhaocaiapp.zc_app_android.views.my;

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

import com.umeng.commonsdk.stateless.UMSLEnvelopeBuild;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.widget.NavigationTopBar;

/**
 * Created by Administrator on 2018/1/11.
 */

public class MyFollowAvtivity extends BaseActivity implements NavigationTopBar.NavigationTopBarClickListener{
//    @BindView(R.id.iv_top_back)
//    ImageView iv_top_back;
//    @BindView(R.id.tv_top_title)
//    TextView tv_top_titlel;
//    @BindView(R.id.iv_top_menu)
//    ImageView iv_top_menu;
    @BindView(R.id.tab_title)
    TabLayout tab_title;
    @BindView(R.id.pager)
    ViewPager pager;

    private String[]tabTitles = {"活动", "商家"};
    private List<Fragment>fragments = new ArrayList<>();

    private UMShareAPI umShareAPI;

    private NavigationTopBar mNavigationTopBar;

    @Override
    public int getContentViewResId() {
        return R.layout.layout_tab_fragment;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        umShareAPI = ZcApplication.getUMShareAPI();
        mNavigationTopBar = findViewById(R.id.my_follow_top);
        mNavigationTopBar.setCenterTitleText("我的关注");
        mNavigationTopBar.setLeftImageResource(R.mipmap.finish_icon);
        mNavigationTopBar.setCenterTitleTextColor(R.color.colorBlack);
        mNavigationTopBar.setNavigationTopBarClickListener(this);


        fragments.add(new MyFollowActivityFragment());
        fragments.add(new MyFollowBusinerFragment());

        pager.setAdapter(new MyFollowPagerAdapter(getSupportFragmentManager()));
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

    private class MyFollowPagerAdapter extends FragmentPagerAdapter {

        public MyFollowPagerAdapter(FragmentManager fm) {
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

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
//
//    @OnClick({R.id.iv_top_back})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.iv_top_back:
//                finish();
//                break;
//        }
//    }

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
