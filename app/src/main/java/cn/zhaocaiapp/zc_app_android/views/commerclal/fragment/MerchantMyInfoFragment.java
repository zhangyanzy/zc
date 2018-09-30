package cn.zhaocaiapp.zc_app_android.views.commerclal.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.MainActivity;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.my.MerchantInfo;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.views.commerclal.MerchantBusinessActivity;
import cn.zhaocaiapp.zc_app_android.views.commerclal.MerchantPlatformMessageActivity;
import cn.zhaocaiapp.zc_app_android.views.commerclal.PayActivity;
import cn.zhaocaiapp.zc_app_android.widget.NavigationTopBar;

/**
 * Created by admin on 2018/9/11.
 */

public class MerchantMyInfoFragment extends BaseFragment {


    @BindView(R.id.merchant_photo)
    ImageView mMerchantPhoto;
    @BindView(R.id.merchant_name)
    TextView mMerchantName;

    private NavigationTopBar mNavigationTopBar;

    private View view;
    private MerchantInfo mMerchantInfo;

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getContext(), R.layout.merchant_my_info, null);
        return view;
    }

    @Override
    public void init() {
        mNavigationTopBar = view.findViewById(R.id.merchant_my_info_top);
        mNavigationTopBar.setCenterTitleTextColor(R.color.colorBlack);
        mNavigationTopBar.setCenterTitleText("商家中心");
    }

    @Override
    public void loadData() {
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
        Glide.with(getActivity()).load(mMerchantInfo.getLogo()).into(mMerchantPhoto);
        mMerchantName.setText(mMerchantInfo.getName());

    }

    @Override
    public void onResume() {
        super.onResume();
        getMerchantInfo();
    }

    @OnClick({R.id.merchant_my_account, R.id.merchant_platform_message, R.id.merchant_business_message, R.id.merchant_phone, R.id.check_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.merchant_my_account:
                openActivity(PayActivity.class);
                break;
            case R.id.merchant_platform_message:
                openActivity(MerchantPlatformMessageActivity.class);
                break;
            case R.id.merchant_business_message:
                openActivity(MerchantBusinessActivity.class);
                break;
            case R.id.merchant_phone:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "021-68788170111"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.check_user:
                openActivity(MainActivity.class);
                break;
            default:
                break;
        }
    }
}
