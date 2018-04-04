package cn.zhaocaiapp.zc_app_android;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import com.umeng.message.PushAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.PermissionUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.views.login.LoginActivity;
import pub.devrel.easypermissions.EasyPermissions;

public class WelcomeActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.btn_skip)
    Button btnSkip;
    @BindView(R.id.btn_start)
    Button btnStart;

    private MyViewPagerAdapter myViewPagerAdapter;
    private String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};

    private List<ImageView> views = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置activity全屏显示，且状态栏隐藏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //动态申请权限
        PermissionUtil.checkPermission(this, perms, null);

        //存储应用是否首次启动的信息
        SharedPreferences sp = getSharedPreferences("first_start", Context.MODE_PRIVATE);
        boolean isFirstStart = sp.getBoolean("is_first_start", true);
        //不是第一次启动
        if (!isFirstStart) {
            launchHomeScreen();
        } else {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("is_first_start", false).apply();
        }

        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        init();

        //初始化友盟推送
        PushAgent.getInstance(this).onAppStart();
    }

    private void init() {
        //设置状态栏透明
        changeStatusBarColor();

        int[]ids = new int[]{R.mipmap.welcome_slide1, R.mipmap.welcome_slide2, R.mipmap.welcome_slide3};
        for (int i = 0; i < ids.length; i ++ ){
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(ids[i]);
            views.add(imageView);
        }
        viewPager.setAdapter(new MyViewPagerAdapter());
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });
    }

    /**
     * 跳过引导页
     */
    private void launchHomeScreen() {
        if ((boolean) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get((Constants.SPREF.IS_LOGIN), false))
            startActivity(new Intent(this, MainActivity.class));
        else startActivity(new Intent(this, LoginActivity.class));

        finish();
    }

    /**
     * 设置状态栏变透明
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    //viewpager的滑动监听
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            if (position == views.size() - 1){
                btnStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        launchHomeScreen();
                    }
                });
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };

    public class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = views.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }
}
