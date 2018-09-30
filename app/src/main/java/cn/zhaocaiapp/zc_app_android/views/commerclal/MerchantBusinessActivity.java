package cn.zhaocaiapp.zc_app_android.views.commerclal;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseCompatActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.my.MerchantInfo;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.databinding.ActivityMerchantBusinessBinding;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.widget.NavigationTopBar;

public class MerchantBusinessActivity extends BaseCompatActivity implements NavigationTopBar.NavigationTopBarClickListener {

    private ActivityMerchantBusinessBinding binding;
    private NavigationTopBar mNavigationTopBar;
    private MerchantInfo mMerchantInfo;


    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_merchant_business);
        binding.setPresenter(new Presenter());
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        mNavigationTopBar = findViewById(R.id.merchant_business_top);
        mNavigationTopBar.setLeftImageResource(R.mipmap.finish_icon);
        mNavigationTopBar.setCenterTitleText("企业信息");
        mNavigationTopBar.setCenterTitleTextColor(R.color.colorBlack);
        mNavigationTopBar.setNavigationTopBarClickListener(this);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        getMerchantInfo();
    }

    private void getMerchantInfo() {
        HttpUtil.get(Constants.URL.GET_MEMBER_INFO).subscribe(new BaseResponseObserver<MerchantInfo>() {


            @Override
            public void success(MerchantInfo merchantInfo) {
                if (merchantInfo != null) {
                    mMerchantInfo = merchantInfo;
                    showMerchantInfo();
                }
            }

            @Override
            public void error(Response<MerchantInfo> response) {

            }
        });
    }

    private void showMerchantInfo() {
        if (mMerchantInfo != null) {
            binding.merchantName.setText(mMerchantInfo.getName());
            binding.merchantPhone.setText(mMerchantInfo.getPhone());
            String merchantDetailAddress = mMerchantInfo.getProvinceName() + mMerchantInfo.getCityName()
                    + mMerchantInfo.getAreaName() + mMerchantInfo.getAddressDetail();
            binding.merchantAddress.setText(merchantDetailAddress);
            Glide.with(this).load(mMerchantInfo.getLicense()).into(binding.licenseImage);
            Glide.with(this).load(mMerchantInfo.getLogo()).into(binding.logoImage);
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

        }
    }
}

