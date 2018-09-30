package cn.zhaocaiapp.zc_app_android.views.commerclal;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseCompatActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.Entity;
import cn.zhaocaiapp.zc_app_android.bean.response.my.IsVia;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.databinding.ActivityMerchantHomeBinding;
import cn.zhaocaiapp.zc_app_android.util.BottomNavigationViewHelper;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtils;
import cn.zhaocaiapp.zc_app_android.views.commerclal.fragment.MerchantActivityFragment;
import cn.zhaocaiapp.zc_app_android.views.commerclal.fragment.MerchantMyInfoFragment;
import cn.zhaocaiapp.zc_app_android.widget.RedPackageDialog;


/**
 * 商家端主界面
 */

public class MerchantHomeActivity extends BaseCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private ActivityMerchantHomeBinding binding;

    private MerchantActivityFragment mActivityFragment;
    private MerchantMyInfoFragment mMyInfoFragment;

    private ArrayMap<String, Fragment> mMainFragments;

    public static final String MERCHANT_ACTIVITY_FRAGMENT_TAG = "merchant_activity";
    public static final String MERCHANT_MY_INFO_FRAGMENT_TAG = "merchant_my_info";

    private IsVia mMerchantInfo;


    @Override

    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_merchant_home);
        binding.setPresenter(new Presenter());
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mMainFragments = new ArrayMap<>();
        BottomNavigationViewHelper.disableShiftMode(binding.bottomNavigationView);
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            startFragment(MERCHANT_ACTIVITY_FRAGMENT_TAG);
        }

        mMerchantInfo = new IsVia();
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            mMerchantInfo = (IsVia) bundle.getSerializable("mIsVia");
        }

        if (mMerchantInfo != null) {
            if (mMerchantInfo.getAuditStatus() == 2) {
                showRedPackage();
            }
        }
    }


    private void showRedPackage() {
        RedPackageDialog dialog = new RedPackageDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.setListener(new RedPackageDialog.onCloseListener() {
            @Override
            public void redPackageClose() {
                recordRedPackage();
                dialog.dismiss();
            }
        });
    }

    //记录已领取红包
    private void recordRedPackage() {
        HttpUtil.put(Constants.URL.RECODE_RED_PACKAGE).subscribe(new BaseResponseObserver<String>() {
            @Override
            public void success(String s) {
                ToastUtils.showShortToast(MerchantHomeActivity.this, "领取成功");
            }

            @Override
            public void error(Response<String> response) {
                ToastUtils.showShortToast(MerchantHomeActivity.this, "领取失败");
            }
        });
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {


    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

    }


    private void startFragment(@NonNull String fragmentTag) {
        if (mMainFragments.get(fragmentTag) == null || !mMainFragments.containsKey(fragmentTag)) {
            createFragment(fragmentTag);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, mMainFragments.get(fragmentTag)).commit();
    }

    private void createFragment(String fragmentTag) {
        switch (fragmentTag) {
            case MERCHANT_ACTIVITY_FRAGMENT_TAG:
                mActivityFragment = new MerchantActivityFragment();
                mMainFragments.put(MERCHANT_ACTIVITY_FRAGMENT_TAG, mActivityFragment);
                break;
            case MERCHANT_MY_INFO_FRAGMENT_TAG:
                mMyInfoFragment = new MerchantMyInfoFragment();
                mMainFragments.put(MERCHANT_MY_INFO_FRAGMENT_TAG, mMyInfoFragment);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.my_activity:
                startFragment(MERCHANT_ACTIVITY_FRAGMENT_TAG);
                return true;
            case R.id.my_info:
                startFragment(MERCHANT_MY_INFO_FRAGMENT_TAG);
                return true;
        }
        return false;
    }

    public class Presenter {
        public void onClick(View view) {

        }
    }
}
