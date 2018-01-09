package cn.zhaocaiapp.zc_app_android.views.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
public class HomeActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_main, container, false);
        return view;
    }

    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.vp_view)
    ViewPager mViewPager;
    private LayoutInflater mInflater;
    private List<String> mTitleList = new ArrayList<String>();//页卡标题集合
    private View view1, view2, view3;//页卡视图
    private List<View> mViewList = new ArrayList<>();//页卡视图集合

    private List<String> activityList;

    {
        activityList = new ArrayList<>();
        activityList.add("1");
        activityList.add("2");
        activityList.add("3");
        activityList.add("4");
        activityList.add("5");
    }

    /*@Override
    public int getContentViewResId() {
        return R.layout.home_main;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        mInflater = LayoutInflater.from(this);

        view1 = mInflater.inflate(R.layout.activity_list, null);
        view2 = mInflater.inflate(R.layout.activity_list, null);
        view3 = mInflater.inflate(R.layout.activity_list, null);

        initView1();
        initView2();
        initView3();

        //添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        //添加页卡标题
        mTitleList.add("最新活动");
        mTitleList.add("线上活动");
        mTitleList.add("线下活动");
        //添加tab选项卡，默认第一个选中
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)), true);
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));

        MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        //给ViewPager设置适配器
        mViewPager.setAdapter(mAdapter);

        //将TabLayout和ViewPager关联起来
        mTabLayout.setupWithViewPager(mViewPager);
        //给Tabs设置适配器
        mTabLayout.setTabsFromPagerAdapter(mAdapter);
    }*/

    private void initView1() {
        ListView listView = (ListView) view1.findViewById(R.id.active_list1);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return activityList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = null;

                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.activity_activity_scene_detail, null);

                //TextView textView = (TextView) view.findViewById(R.id.textView);
                //textView.setText(activityList.get(position));

                return view;
            }
        });
    }

    private void initView2() {
        ListView listView = (ListView) view2.findViewById(R.id.active_list1);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return activityList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = null;

                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.activity_activity_scene_detail, null);

                //TextView textView = (TextView) view.findViewById(R.id.textView);
                //textView.setText(activityList.get(position));
//                TextView textView = (TextView) view.findViewById(R.id.textView);
//                textView.setText(activityList.get(position));

                return view;
            }
        });
    }

    private void initView3() {
        ListView listView = (ListView) view3.findViewById(R.id.active_list1);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return activityList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = null;

                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.activity_activity_scene_detail, null);

                //TextView textView = (TextView) view.findViewById(R.id.textView);
                //textView.setText(activityList.get(position));
//                TextView textView = (TextView) view.findViewById(R.id.textView);
//                textView.setText(activityList.get(position));

                return view;
            }
        });
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


