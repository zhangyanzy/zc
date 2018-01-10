package cn.zhaocaiapp.zc_app_android;


import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.List;

import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.views.home.HomeFragment;
import cn.zhaocaiapp.zc_app_android.views.login.LoginActivity;
import cn.zhaocaiapp.zc_app_android.views.member.MemberFragment;
import cn.zhaocaiapp.zc_app_android.views.my.MyFragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    //三个Tab对应的布局
    private LinearLayout mTabHome;
    private LinearLayout mTabMember;
    private LinearLayout mTabMy;

    //三个Tab对应的ImageButton
    private ImageButton mImgHome;
    private ImageButton mImgMember;
    private ImageButton mImgMy;

    //三个Tab对应的Fragment
    private HomeFragment homeActivity;
    private MemberFragment memberActivity;
    private MyFragment myActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initViews();//初始化控件
        initEvents();//初始化事件
        setSelect(0);
    }

    private void initEvents() {
        //设置三个Tab的点击事件
        mTabHome.setOnClickListener(this);
        mTabMember.setOnClickListener(this);
        mTabMy.setOnClickListener(this);

    }

    //初始化控件
    private void initViews() {

        //三个Tab的对象
        mTabHome = (LinearLayout) findViewById(R.id.id_tab_home);
        mTabMember = (LinearLayout) findViewById(R.id.id_tab_member);
        mTabMy = (LinearLayout) findViewById(R.id.id_tab_my);

        //三个Tab的按钮
        mImgHome = (ImageButton) findViewById(R.id.id_tab_home_img);
        mImgMember = (ImageButton) findViewById(R.id.id_tab_member_img);
        mImgMy = (ImageButton) findViewById(R.id.id_tab_my_img);

    }

    @Override
    public void onClick(View v) {
        //先将三个ImageButton置为灰色
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
                setSelect(0);
                break;
            case 1:
                mImgMember.setImageResource(R.mipmap.tab_find_frd_pressed);
                setSelect(1);
                break;
            case 2:
                mImgMy.setImageResource(R.mipmap.tab_address_pressed);
                setSelect(2);
                break;
        }
        //设置当前点击的Tab所对应的页面
        //mViewPager.setCurrentItem(i);
    }

    //将三个ImageButton设置为灰色
    private void resetImgs() {
        mImgHome.setImageResource(R.mipmap.tab_weixin_normal);
        mImgMember.setImageResource(R.mipmap.tab_find_frd_normal);
        mImgMy.setImageResource(R.mipmap.tab_address_normal);
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (homeActivity != null) {
            fragmentTransaction.hide(homeActivity);
        }
        if (memberActivity != null) {
            fragmentTransaction.hide(memberActivity);
        }
        if (myActivity != null) {
            fragmentTransaction.hide(myActivity);
        }
    }

    /*
    * 设置某个Fragment
    * */
    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        //重置图片状态
        resetImgs();
        hideFragment(fragmentTransaction);
        switch (i) {
            case 0:
                if (homeActivity == null) {
                    //如果Fragment还没实例化，实例化，并在fragmentTransaction中添加
                    homeActivity = new HomeFragment();
                    fragmentTransaction.add(R.id.id_content, homeActivity);
                } else {
                    //如果已经实例化了，就显示
                    fragmentTransaction.show(homeActivity);

                }
                fragmentTransaction.commit();
                //改变底部图标的状态
                //mWeiXin.setImageResource(R.drawable.tab_weixin_pressed);

                break;
            case 1:
                if (memberActivity == null) {
                    memberActivity = new MemberFragment();
                    fragmentTransaction.add(R.id.id_content, memberActivity);

                } else {
                    fragmentTransaction.show(memberActivity);
                }

                fragmentTransaction.commit();
                //mFriend.setImageResource(R.drawable.tab_find_frd_pressed);


                break;
            case 2:
                if (myActivity == null) {
                    myActivity = new MyFragment();
                    fragmentTransaction.add(R.id.id_content, myActivity);

                } else {
                    fragmentTransaction.show(myActivity);

                }
                fragmentTransaction.commit();
                //mAddress.setImageResource(R.drawable.tab_address_pressed);
                break;
        }

    }


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
