package cn.zhaocaiapp.zc_app_android.views.my;

import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.adapter.my.MyMessageAdapter;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.my.MessageResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.widget.NavigationTopBar;

/**
 * Created by Administrator on 2018/1/25.
 */

public class MessageActivity extends BaseActivity implements NavigationTopBar.NavigationTopBarClickListener {

    @BindView(R.id.tab_title)
    TabLayout titles;
    @BindView(R.id.pager)
    ViewPager pager;

    private NavigationTopBar mNavigationTopBar;

    private List<Fragment> fragments = new ArrayList<>();
    private String[] tabTitles = {"系统消息", "活动消息"};

    @Override
    public int getContentViewResId() {
        return R.layout.layout_tab_fragment;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        mNavigationTopBar = findViewById(R.id.my_follow_top);
        mNavigationTopBar.setLeftImageResource(R.mipmap.finish_icon);
        mNavigationTopBar.setCenterTitleText("消息中心");
        mNavigationTopBar.setCenterTitleTextColor(R.color.colorBlack);
        mNavigationTopBar.setNavigationTopBarClickListener(this);
        fragments.add(new SystemMessageFragment());
        fragments.add(new ActivityMessageFragment());

        pager.setAdapter(new MessagePagerAdapter(getSupportFragmentManager()));
        titles.setupWithViewPager(pager);
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

    private class MessagePagerAdapter extends FragmentPagerAdapter {
        public MessagePagerAdapter(FragmentManager fm) {
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

}
