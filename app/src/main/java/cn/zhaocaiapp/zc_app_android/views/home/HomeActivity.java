package cn.zhaocaiapp.zc_app_android.views.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;

/**
 * @author 林子
 * @filename HomeActivity.java
 * @data 2018-01-05 17:52
 */
public class HomeActivity extends BaseActivity{

    @BindView(R.id.tabs) TabLayout mTabLayout;
    @BindView(R.id.vp_view) ViewPager mViewPager;
    private LayoutInflater mInflater;
    private List<String> mTitleList = new ArrayList<String>();//页卡标题集合
    private View view1, view2, view3, view4, view5, view6, view7, view8;//页卡视图
    private List<View> mViewList = new ArrayList<>();//页卡视图集合

    @Override
    public int getContentViewResId() {
        return R.layout.home_main;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        mInflater = LayoutInflater.from(this);

        view1 = mInflater.inflate(R.layout.activity_list, null);
        view2 = mInflater.inflate(R.layout.activity_list, null);
        view3 = mInflater.inflate(R.layout.activity_list, null);
        view4 = mInflater.inflate(R.layout.activity_list, null);
        view5 = mInflater.inflate(R.layout.activity_list, null);
        view6 = mInflater.inflate(R.layout.activity_list, null);
        view7 = mInflater.inflate(R.layout.activity_list, null);
        view8 = mInflater.inflate(R.layout.activity_list, null);
        //添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        mViewList.add(view4);
        mViewList.add(view5);
        mViewList.add(view6);
        mViewList.add(view7);
        mViewList.add(view8);
        //添加页卡标题
        mTitleList.add("头条");
        mTitleList.add("热点");
        mTitleList.add("本地");
        mTitleList.add("财经");
        mTitleList.add("科技");
        mTitleList.add("教育");
        mTitleList.add("体育");
        mTitleList.add("笑话");
        //添加tab选项卡，默认第一个选中
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)), true);
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(3)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(4)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(5)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(6)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(7)));

        MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        //给ViewPager设置适配器
        mViewPager.setAdapter(mAdapter);

        //将TabLayout和ViewPager关联起来
        mTabLayout.setupWithViewPager(mViewPager);
        //给Tabs设置适配器
        mTabLayout.setTabsFromPagerAdapter(mAdapter);
    }

    //ViewPager适配器
    class MyPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        public MyPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();//页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//官方推荐写法
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));//删除页卡
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);//页卡标题
        }

    }
}


