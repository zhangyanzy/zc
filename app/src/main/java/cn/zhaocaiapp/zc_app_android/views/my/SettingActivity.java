package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.listener.OnBtnClickL;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.widget.NormalDialog;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.AppUtil;
import cn.zhaocaiapp.zc_app_android.util.DialogUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;

/**
 * Created by Administrator on 2018/1/11.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.tv_top_title)
    TextView tv_top_titlel;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.tv_clear_cache)
    TextView tv_clear_cache;
    @BindView(R.id.tv_about_us)
    TextView tv_about_us;
    @BindView(R.id.layout_clear_cache)
    RelativeLayout layout_clear_cache;
    @BindView(R.id.layout_setting_activity_all)
    RadioButton layout_setting_activity_all;
    @BindView(R.id.layout_setting_activity_current)
    RadioButton layout_setting_activity_current;
    @BindView(R.id.layout_setting_activity_group)
    RadioGroup layout_setting_activity_group;

    @Override
    public int getContentViewResId() {
        return R.layout.layout_setting_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tv_top_titlel.setText("设置");
        iv_top_menu.setVisibility(View.GONE);
        tv_version.setText("V" + AppUtil.getAppVersionName(this));
        try {
            tv_clear_cache.setText(AppUtil.getCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 设置首页线下活动范围
         */
        int t = (int) SpUtils.get(Constants.SPREF.ACTIVITY_RANGE, 0);// 0代表全国 1代表当前城市
        if (t == 0) {
            layout_setting_activity_all.setChecked(true);
        } else {
            layout_setting_activity_current.setChecked(true);
        }
        layout_setting_activity_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.layout_setting_activity_all:
                        SpUtils.put(Constants.SPREF.ACTIVITY_RANGE, 0);
                        EBLog.e("tag", "全国");
                        break;
                    case R.id.layout_setting_activity_current:
                        SpUtils.put(Constants.SPREF.ACTIVITY_RANGE, 1);
                        EBLog.e("tag", "当前城市");
                        break;
                }
            }
        });

    }

    @OnClick({R.id.iv_top_back, R.id.iv_top_menu, R.id.tv_about_us, R.id.layout_clear_cache})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.iv_top_menu:

                break;
            case R.id.layout_clear_cache: //清除缓存
                String content = getString(R.string.confirm_clean_cache);
                NormalDialog dialog = DialogUtil.showDialogTwoBut(SettingActivity.this, null, content, "取消", "确认");
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
                            AppUtil.cleanInternalCache(SettingActivity.this);
                            tv_clear_cache.setText(AppUtil.getCacheSize(SettingActivity.this));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.tv_about_us: //关于我们
                openActivity(AboutUsActivity.class);
                break;
        }
    }


}
