package cn.zhaocaiapp.zc_app_android.views.commerclal;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseCompatActivity;
import cn.zhaocaiapp.zc_app_android.databinding.ActivityCasuccessBinding;
import cn.zhaocaiapp.zc_app_android.widget.NavigationTopBar;

public class CASuccessActivity extends BaseCompatActivity implements NavigationTopBar.NavigationTopBarClickListener{

    private ActivityCasuccessBinding binding;
    private NavigationTopBar mNavigationTopBar;

    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_casuccess);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        mNavigationTopBar = findViewById(R.id.success_top);
        mNavigationTopBar.setLeftImageResource(R.mipmap.finish_icon);
        mNavigationTopBar.setCenterTitleText("商家认证");
        mNavigationTopBar.setCenterTitleTextColor(R.color.colorBlack);
        mNavigationTopBar.setNavigationTopBarClickListener(this);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

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
}
