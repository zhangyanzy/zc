package cn.zhaocaiapp.zc_app_android.views.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseCompatActivity;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.listener.OnBtnClickL;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.widget.NormalDialog;
import cn.zhaocaiapp.zc_app_android.databinding.ActivityNewSettingBinding;
import cn.zhaocaiapp.zc_app_android.util.AppUtil;
import cn.zhaocaiapp.zc_app_android.util.DialogUtil;
import cn.zhaocaiapp.zc_app_android.views.my.SettingActivity;
import cn.zhaocaiapp.zc_app_android.widget.NavigationTopBar;

public class NewSettingActivity extends BaseCompatActivity implements NavigationTopBar.NavigationTopBarClickListener {


    private ActivityNewSettingBinding binding;
    private NavigationTopBar mNavigationTopBar;


    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_setting);
        binding.setPresenter(new Presenter());
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        mNavigationTopBar = findViewById(R.id.setting_bar);
        mNavigationTopBar.setLeftImageResource(R.mipmap.finish_icon);
        mNavigationTopBar.setCenterTitleText("系统设置");
        mNavigationTopBar.setCenterTitleTextColor(R.color.colorBlack);
        mNavigationTopBar.setNavigationTopBarClickListener(this);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        binding.versionNumber.setText("V" + AppUtil.getAppVersionName(this));

        try {
            binding.clearCache.setText(AppUtil.getCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                case R.id.clear_cache_root:
                    cleanCache();
                    break;
                case R.id.user_info_root:
                    openActivity(PersonalDataActivity.class);
                    break;
                case R.id.about_us:
                    openActivity(AboutUsActivity.class);
                default:
                    break;
            }
        }
    }

    //清除缓存
    private void cleanCache() {
        String content = getString(R.string.confirm_clean_cache);
        NormalDialog dialog = DialogUtil.showDialogTwoBut(this, null, content, "取消", "确认");
        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                dialog.dismiss();
            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                try {
                    dialog.dismiss();
                    AppUtil.cleanInternalCache(getApplicationContext());
                    binding.clearCache.setText(AppUtil.getCacheSize(getApplicationContext()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
