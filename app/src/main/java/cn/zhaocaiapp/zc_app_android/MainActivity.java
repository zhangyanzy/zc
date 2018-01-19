package cn.zhaocaiapp.zc_app_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cn.zhaocaiapp.zc_app_android.base.BaseFragmentActivity;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.util.AppUtil;
import cn.zhaocaiapp.zc_app_android.views.home.HomeFragment;
import cn.zhaocaiapp.zc_app_android.views.member.MemberFragment;
import cn.zhaocaiapp.zc_app_android.views.my.MyFragment;

/**
 * Created by ASUS on 2017/10/30.
 */

public class MainActivity extends BaseFragmentActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.layout_container)
    FrameLayout container;
    @BindView(R.id.layout_group_button)
    RadioGroup groupBotton;

    private int currentPosition;
    private final String[] tags = {"task", "partner", "personal", "Test"};
    private int currentIndex = -1;
    private Map<Integer, Fragment> fragmentMap = new HashMap<>();

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        currentPosition = getIntent().getIntExtra("position", -1);

        initView();
        //判断定位服务
//        isLocation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_activity);
        currentPosition = getIntent().getIntExtra("position", -1);

        initView();
        //判断定位服务
        isLocation();
    }

    private void initView() {
        groupBotton.setOnCheckedChangeListener(this);
        setCheckButton();
    }

    private void setCheckButton() {
        switch (currentPosition) {
            case -1:
            case 0:
                groupBotton.check(R.id.group_button_task);
                break;
            case 1:
                groupBotton.check(R.id.group_button_partner);
                break;
            case 2:
                groupBotton.check(R.id.group_button_personal);
                break;
            case 3:
                groupBotton.check(R.id.group_button_test);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        int index = 0;
        switch (checkedId) {
            case R.id.group_button_task:
                index = 0;
                break;
            case R.id.group_button_partner:
                index = 1;
                break;
            case R.id.group_button_personal:
                index = 2;
                break;
            case R.id.group_button_test:
                index = 3;
                break;
        }
        selectFragment(index);
    }

    //根据点击位置，设置当前显示的fragment
    private void selectFragment(int index) {
        if (index == currentIndex) {
            return;
        }
        currentIndex = index;
        String tag = tags[index];
        FragmentManager manager = getSupportFragmentManager();
        for (int i = 0; i < tags.length; i++) {
            if (i != index && manager.findFragmentByTag(tags[i]) != null) {
                //if the other fragment is visible, hide it.
                manager.beginTransaction().hide(manager.findFragmentByTag(tags[i])).commit();
            }
        }
        if (manager.findFragmentByTag(tag) != null) {
            //if the fragment exists, show it.
            manager.beginTransaction().show(manager.findFragmentByTag(tag)).commit();
        } else {
            //if the fragment does not exist, add it to fragment manager.
            manager.beginTransaction().add(R.id.layout_container, getFragment(index), tag).commit();
        }
    }

    /**
     * 返回各模块对象
     */
    private Fragment getFragment(int index) {
        Fragment fragment = fragmentMap.get(index);
        if (fragment == null) {
            switch (index) {
                case 0:  //精选活动
                    fragment = new HomeFragment();
                    break;
                case 1:  //商家活动
                    fragment = new MemberFragment();
                    break;
                case 2:  //我的
                    fragment = new MyFragment();
                    break;
                case 3:  //测试入口
                    fragment = new TestFragment();
                    break;
            }
            fragmentMap.put(index, fragment);
        }
        return fragment;
    }

    /**
     * 判断是否开启系统定位服务
     */
    private void isLocation() {
        Boolean isLocation = AppUtil.isLocation(this);
        EBLog.i("tag", "定位服务是否开启，" + isLocation.toString());

    }

    /**
     * 判断系统定位是否打开
     *//*
    private void initGPS() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // 判断GPS模块是否开启，如果没有则开启
        if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            Toast.makeText(MainActivity.this, "请打开GPS", Toast.LENGTH_SHORT).show();
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("请打开GPS连接");
            dialog.setMessage("为方便司机更容易接到您，请先打开GPS");
            dialog.setPositiveButton("设置", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // 转到手机设置界面，用户设置GPS
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    Toast.makeText(MainActivity.this, "打开后直接点击返回键即可，若不打开返回下次将再次出现", Toast.LENGTH_SHORT).show();
                    startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
                }
            });
            dialog.setNeutralButton("取消", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            dialog.show();
        } else {
            searchRouteResult(startPoint, endPoint);//路径规划
            // 弹出Toast
//          Toast.makeText(TrainDetailsActivity.this, "GPS is ready",Toast.LENGTH_LONG).show();
//          // 弹出对话框
//          new AlertDialog.Builder(this).setMessage("GPS is ready").setPositiveButton("OK", null).show();
        }
    }

    *//**
     * 判断网络连接是否已开
     * true 已打开  false 未打开
     *//*
    public static boolean isConn(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
            searchNetwork(context);//弹出提示对话框
        }
        return false;
    }

    *//**
     * 判断网络是否连接成功，连接成功不做任何操作
     * 未连接则弹出对话框提示用户设置网络连接
     *//*
    public static void searchNetwork(final Context context) {
        //提示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("网络设置提示").setMessage("网络连接不可用,是否进行设置?").setPositiveButton("设置", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                //判断手机系统的版本  即API大于10 就是3.0或以上版本
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                } else {
                    intent = new Intent();
                    ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
                context.startActivity(intent);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }*/

}
