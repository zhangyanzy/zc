package cn.zhaocaiapp.zc_app_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.response.login.LoginResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.views.home.HomeActivity;
import cn.zhaocaiapp.zc_app_android.views.login.LoginActivity;
import cn.zhaocaiapp.zc_app_android.views.member.MemberActivity;
import cn.zhaocaiapp.zc_app_android.views.my.MyActivity;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    //声明ViewPager
    private ViewPager mViewPager;
    //适配器
    private FragmentPagerAdapter mAdapter;
    //装载Fragment的集合
    private List<Fragment> mFragments;

    //三个Tab对应的布局
    private LinearLayout mTabHome;
    private LinearLayout mTabMember;
    private LinearLayout mTabMy;

    //三个Tab对应的ImageButton
    private ImageButton mImgHome;
    private ImageButton mImgMember;
    private ImageButton mImgMy;

    private Button button;
    private FloatingActionButton button1;
    private HttpUtil httpUtil;

    //@BindView(R.id.toolbar)
    //protected Toolbar header;   //header

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initViews();//初始化控件
        initEvents();//初始化事件
        initDatas();//初始化数据
    }

    private void initDatas() {
        mFragments = new ArrayList<>();
        //将三个Fragment加入集合中
        mFragments.add(new HomeActivity());
        mFragments.add(new MemberActivity());
        mFragments.add(new MyActivity());

        //初始化适配器
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {//从集合中获取对应位置的Fragment
                return mFragments.get(position);
            }

            @Override
            public int getCount() {//获取集合中Fragment的总数
                return mFragments.size();
            }

        };
        //不要忘记设置ViewPager的适配器
        mViewPager.setAdapter(mAdapter);
        //设置ViewPager的切换监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            //页面滚动事件
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //页面选中事件
            @Override
            public void onPageSelected(int position) {
                //设置position对应的集合中的Fragment
                mViewPager.setCurrentItem(position);
                resetImgs();
                selectTab(position);
            }

            @Override
            //页面滚动状态改变事件
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initEvents() {
        //设置四个Tab的点击事件
        mTabHome.setOnClickListener(this);
        mTabMember.setOnClickListener(this);
        mTabMy.setOnClickListener(this);

    }

    //初始化控件
    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

        mTabHome = (LinearLayout) findViewById(R.id.id_tab_home);
        mTabMember = (LinearLayout) findViewById(R.id.id_tab_member);
        mTabMy = (LinearLayout) findViewById(R.id.id_tab_my);

        mImgHome = (ImageButton) findViewById(R.id.id_tab_home_img);
        mImgMember = (ImageButton) findViewById(R.id.id_tab_member_img);
        mImgMy = (ImageButton) findViewById(R.id.id_tab_my_img);

    }

    @Override
    public void onClick(View v) {
        //先将四个ImageButton置为灰色
        resetImgs();

        //根据点击的Tab切换不同的页面及设置对应的ImageButton为绿色
        switch (v.getId()) {
            case R.id.id_tab_home:
                selectTab(0);
                break;
            case R.id.id_tab_member:
                selectTab(1);
                break;
            case R.id.id_tab_my:
                selectTab(2);
                break;
        }
    }

    private void selectTab(int i) {
        //根据点击的Tab设置对应的ImageButton为绿色
        switch (i) {
            case 0:
                mImgHome.setImageResource(R.mipmap.tab_weixin_pressed);
                break;
            case 1:
                mImgMember.setImageResource(R.mipmap.tab_find_frd_pressed);
                break;
            case 2:
                mImgMy.setImageResource(R.mipmap.tab_address_pressed);
                break;
        }
        //设置当前点击的Tab所对应的页面
        mViewPager.setCurrentItem(i);
    }

    //将四个ImageButton设置为灰色
    private void resetImgs() {
        mImgHome.setImageResource(R.mipmap.tab_weixin_normal);
        mImgMember.setImageResource(R.mipmap.tab_find_frd_normal);
        mImgMy.setImageResource(R.mipmap.tab_address_normal);
    }

    /*@Override
    public void init(Bundle savedInstanceState) {

        //设置header转成actionBar
        //setSupportActionBar(header);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        *//*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*//*

        button = (Button) findViewById(R.id.testBtn);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Map<String, String> mMap = new HashMap<String, String>();
                mMap.put("account", "15044441111");
                mMap.put("password", "123456");
                EBLog.i("tag", mMap.toString());
                EBLog.i("tag", Constants.URL.USER_LOGIN);
                EBLog.i("tag", String.format("user/11100/12", "10001", "22"));
                *//*HttpUtil.post(URLUtil.USER_LOGIN, new HashMap()).subscribe(new BaseResponseObserver<LoginResp>() {
                    @Override
                    public void success(LoginResp result) {
                        EBLog.i("tag", result.getToken());
                    }
                });*//*
                HttpUtil.post(Constants.URL.USER_LOGIN).subscribe(new BaseResponseObserver<LoginResp>() {
                    @Override
                    public void success(LoginResp result) {
                        EBLog.i("tag", result.getToken());
                    }
                });
                *//*HttpUtil.get("/message", new HashMap()).subscribe(new BaseResponseObserver<List<Message>>() {
                    @Override
                    public void success(List<Message> result) {
                        EBLog.i("tag", result.get(0).getMsg());
                        //System.out.print(result.getMsg());
                    }
                });*//*
                Toast.makeText(MainActivity.this, "测试接口使用，误删", Toast.LENGTH_SHORT).show();

            }
        });


        *//*button1 = (FloatingActionButton) findViewById(R.id.goHome);
        button1.setOnClickListener((view) -> {
            Intent intent = new Intent("");
            intent.setClass(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            *//**//*Toast.makeText(MainActivity.this, "点击这个跳转到哪里去呢", Toast.LENGTH_SHORT).show();*//**//*
        });*//*
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_main;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
