package cn.zhaocaiapp.zc_app_android.views.commerclal;

import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseCompatActivity;
import cn.zhaocaiapp.zc_app_android.databinding.ActivityBillloggingBinding;
import cn.zhaocaiapp.zc_app_android.views.commerclal.fragment.BillFragment;
import cn.zhaocaiapp.zc_app_android.views.commerclal.fragment.PayBillFragment;
import cn.zhaocaiapp.zc_app_android.widget.NavigationTopBar;
import cn.zhaocaiapp.zc_app_android.widget.ViewPagerAdapter;


/**
 * 商家端充值记录
 */
public class BillLoggingActivity extends BaseCompatActivity implements NavigationTopBar.NavigationTopBarClickListener {


    private ActivityBillloggingBinding binding;
    private NavigationTopBar mNavigationTopBar;
    private ViewPagerAdapter mTabAdapter;

    private BillFragment mBillFragment;
    private PayBillFragment mPayBillFragment;
    private List<Fragment> mFragments;
    private String[] title = new String[]{"账单记录", "充值记录"};


    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_billlogging);
        binding.setPresenter(new Presenter());
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initViewPager();
    }

    private void initViewPager() {
        mBillFragment = new BillFragment();
        mPayBillFragment = new PayBillFragment();
        mFragments = new ArrayList<>();
        mFragments.add(mBillFragment);
        mFragments.add(mPayBillFragment);
        mTabAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragments);
        binding.vpContent.setCurrentItem(0);
        binding.vpContent.setAdapter(mTabAdapter);
        binding.vpContent.setOnPageChangeListener(new myOnPagerChangeListener());
        binding.vpContent.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));

        for (int i = 0; i < title.length; i++) {
            TabLayout.Tab tab = binding.tabLayout.newTab();
            tab.setText(title[i]);
            binding.tabLayout.addTab(tab);
        }
    }

    private class myOnPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        mNavigationTopBar = findViewById(R.id.bill_top);
        mNavigationTopBar.setLeftImageResource(R.mipmap.finish_icon);
        mNavigationTopBar.setCenterTitleText("账单记录");
        mNavigationTopBar.setCenterTitleTextColor(R.color.colorBlack);
        mNavigationTopBar.setNavigationTopBarClickListener(this);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

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

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {

            }
        }
    }
}
