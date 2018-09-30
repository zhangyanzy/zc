package cn.zhaocaiapp.zc_app_android.views.commerclal;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;

import java.util.HashMap;
import java.util.Map;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseCompatActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.merchant.ALiPayEntity;
import cn.zhaocaiapp.zc_app_android.bean.response.merchant.PayResult;
import cn.zhaocaiapp.zc_app_android.bean.response.my.MerchantInfo;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.databinding.ActivityPayBinding;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtils;
import cn.zhaocaiapp.zc_app_android.widget.NavigationTopBar;

public class PayActivity extends BaseCompatActivity implements NavigationTopBar.NavigationTopBarClickListener {

    private static String TAG = "PayActivity";
    private static final int SDK_PAY_FLAG = 1001;

    private ActivityPayBinding binding;
    private NavigationTopBar mNavigationTopBar;
    private String payResult;


    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pay);
        binding.setPresenter(new Presenter());
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();
                    Log.i(TAG, "resultInfo: " + resultInfo);
                    String resultStatus = payResult.getResultStatus();
                    Log.i(TAG, "resultStatus: "+resultStatus);
                    if (TextUtils.equals(resultStatus, "9000")) {
                        ToastUtils.showShortToast(PayActivity.this, "支付成功");
                    } else if (TextUtils.equals(resultStatus, "8000")) {
                        ToastUtils.showShortToast(PayActivity.this, "支付确认中");
                    } else {
                        ToastUtils.showShortToast(PayActivity.this, "支付失败");
                    }
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void initViews(Bundle savedInstanceState) {
        mNavigationTopBar = findViewById(R.id.main_top_bar);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        mNavigationTopBar.setLeftImageResource(R.mipmap.finish_icon);
        mNavigationTopBar.setCenterTitleText(R.string.my_Pay);
        mNavigationTopBar.setCenterTitleTextColor(R.color.colorBlack);
        mNavigationTopBar.setRightImageResource(R.mipmap.envelope_icon);
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
                    binding.accountBalance.setText(merchantInfo.getAmount() + "");
                }
            }

            @Override
            public void error(Response<MerchantInfo> response) {

            }
        });
    }

    private void getOrder() {
        String amount = binding.payBalance.getText().toString();
        if (!TextUtils.isEmpty(amount)) {
            HashMap<String, String> params = new HashMap<>();
            params.put("money", amount);
            HttpUtil.post(Constants.URL.PAY_ORDER, params).subscribe(new BaseResponseObserver<String>() {

                @Override
                public void success(String entity) {
                    Log.i(TAG, "success: " + entity);
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            PayTask payTask = new PayTask(PayActivity.this);
                            Map<String, String> map = payTask.payV2(entity, true);
                            Message msg = new Message();
                            msg.what = SDK_PAY_FLAG;
                            msg.obj = map;
                            handler.sendMessage(msg);
                        }
                    };
                    Thread thread = new Thread(runnable);
                    thread.start();
                }

                @Override
                public void error(Response<String> response) {
                    ToastUtils.showShortToast(getApplicationContext(), response.getDesc());
                }
            });
        } else {
            ToastUtils.showShortToast(PayActivity.this, "请填写充值金额");
        }


    }

    @Override
    public void leftImageClick() {
        finish();
    }

    @Override
    public void rightImageClick() {
        openActivity(BillLoggingActivity.class);

    }

    @Override
    public void alignRightLeftImageClick() {

    }

    private void pay() {
        getOrder();
    }

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ali_pay_bg:
                    pay();
                    break;
                default:
                    break;
            }
        }
    }

}
