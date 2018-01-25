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
import cn.zhaocaiapp.zc_app_android.util.LocationUtil;
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
    private final String[] tags = {"task", "partner", "personal"};
    private int currentIndex = -1;
    private Map<Integer, Fragment> fragmentMap = new HashMap<>();

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        currentPosition = getIntent().getIntExtra("position", -1);

        initView();
        //判断定位服务
        isLocation();
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
}
