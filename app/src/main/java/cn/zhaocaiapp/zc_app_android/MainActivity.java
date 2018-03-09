package cn.zhaocaiapp.zc_app_android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.umeng.commonsdk.stateless.UMSLEnvelopeBuild;
import com.umeng.socialize.UMShareAPI;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cn.zhaocaiapp.zc_app_android.base.BaseFragmentActivity;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.ActivityUtil;
import cn.zhaocaiapp.zc_app_android.util.AppUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.views.home.HomeFragment;
import cn.zhaocaiapp.zc_app_android.views.login.LoginActivity;
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

    private UMShareAPI umShareAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_activity);
        ActivityUtil.addActivity(this);

        umShareAPI = ZcApplication.getUMShareAPI();

        currentPosition = getIntent().getIntExtra("position", -1);

        //注册蒲公英Crash反馈
        PgyCrashManager.register(getApplicationContext());
        //注册蒲公英版本更新
        PgyUpdateManager.setIsForced(false); //设置是否强制更新。true为强制更新；false为不强制更新（默认值）。
        PgyUpdateManager.register(this, updateListener);

        initView();
        //判断定位服务
        isLocation();
    }

    private void initView() {
        groupBotton.setOnCheckedChangeListener(this);
        setCheckButton(currentPosition);
    }

    public void setCheckButton(int position) {
        currentPosition = position;
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
        switch (checkedId) {
            case R.id.group_button_task:
                currentPosition = 0;
                break;
            case R.id.group_button_partner:
                currentPosition = 1;
                break;
            case R.id.group_button_personal:
                if (!(boolean) SpUtils.get(Constants.SPREF.IS_LOGIN, false)) {
                    RadioButton radioButton = null;
                    if (currentPosition == 0)
                        radioButton = findViewById(R.id.group_button_task);
                    if (currentPosition == 1)
                        radioButton = findViewById(R.id.group_button_partner);
                    radioButton.setChecked(true);
                    Bundle bundle = new Bundle();
                    bundle.putInt("currentPosition", 2);
                    openActivity(LoginActivity.class, bundle);
                    return;
                } else currentPosition = 2;
                break;
        }
        selectFragment();
    }

    //根据点击位置，设置当前显示的fragment
    private void selectFragment() {
        if (currentPosition == currentIndex) {
            return;
        }
        currentIndex = currentPosition;
        String tag = tags[currentPosition];
        FragmentManager manager = getSupportFragmentManager();
        for (int i = 0; i < tags.length; i++) {
            if (i != currentPosition && manager.findFragmentByTag(tags[i]) != null) {
                //if the other fragment is visible, hide it.
                manager.beginTransaction().hide(manager.findFragmentByTag(tags[i])).commit();
            }
        }
        if (manager.findFragmentByTag(tag) != null) {
            //if the fragment exists, show it.
            manager.beginTransaction().show(manager.findFragmentByTag(tag)).commit();
        } else {
            //if the fragment does not exist, add it to fragment manager.
            manager.beginTransaction().add(R.id.layout_container, getFragment(currentPosition), tag).commit();
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

    //蒲公英版本更新监听器
    private UpdateManagerListener updateListener = new UpdateManagerListener() {
        @Override
        public void onNoUpdateAvailable() { //不更新

        }

        @Override
        public void onUpdateAvailable(String result) { //更新
            // 将新版本信息封装到AppBean中
            final AppBean appBean = getAppBeanFromString(result);
            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("更新")
                    .setMessage(appBean.getReleaseNote())
                    .setNegativeButton("确定",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startDownloadTask(MainActivity.this, appBean.getDownloadURL());
                                }
                            }).show();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        }
    };

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
