package cn.zhaocaiapp.zc_app_android.views.commerclal;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.adapter.merchant.MerchantMessageAdapter;
import cn.zhaocaiapp.zc_app_android.base.BaseCompatActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.my.MessageResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.databinding.ActivityMerchantPlatformMessageBinding;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.widget.NavigationTopBar;

public class MerchantPlatformMessageActivity extends BaseCompatActivity implements NavigationTopBar.NavigationTopBarClickListener
        , OnRefreshListener, OnLoadmoreListener {

    private ActivityMerchantPlatformMessageBinding binding;
    private NavigationTopBar mNavigationTopBar;

    private MerchantMessageAdapter mAdapter;
    private ArrayList<MessageResp> messages = new ArrayList<>();
    private int type = 2;//消息类型
    private int currentResult = 0;
    private int pageSize = 10;


    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_merchant_platform_message);
        binding.setPresenter(new Presenter());
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mAdapter = new MerchantMessageAdapter();
        binding.messageRv.setLayoutManager(new LinearLayoutManager(this));
        binding.messageRv.setAdapter(mAdapter);

        binding.refreshLayout.setOnRefreshListener(this);
        binding.refreshLayout.setOnLoadmoreListener(this);
        binding.refreshLayout.setEnableLoadmoreWhenContentNotFull(false);
        binding.refreshLayout.autoRefresh();
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        mNavigationTopBar = findViewById(R.id.merchant_message_top);
        mNavigationTopBar.setLeftImageResource(R.mipmap.finish_icon);
        mNavigationTopBar.setCenterTitleTextColor(R.color.colorBlack);
        mNavigationTopBar.setCenterTitleText("消息通知");
        mNavigationTopBar.setNavigationTopBarClickListener(this);
    }

    private void getMessage() {
        HashMap<String, String> params = new HashMap<>();
        params.put("type", type + "");
        params.put("currentResult", currentResult + "");
        params.put("pageSize", pageSize + "");
        HttpUtil.get(String.format(Constants.URL.MESSAGE_LIST, type), params).subscribe(new BaseResponseObserver<ArrayList<MessageResp>>() {
            @Override
            public void success(ArrayList<MessageResp> messageResps) {
                if (currentResult == 0) messages = messageResps;
                else messages.addAll(messageResps);
                mAdapter.setList(messages);

                if (messageResps.size() < pageSize) {
                    //完成加载并标记没有更多数据
                    binding.refreshLayout.finishLoadmoreWithNoMoreData();
                }
                if (binding.refreshLayout.isRefreshing())
                    binding.refreshLayout.finishRefresh();
                else if (binding.refreshLayout.isLoading())
                    binding.refreshLayout.finishLoadmore();
            }

            @Override
            public void error(Response<ArrayList<MessageResp>> response) {
                ToastUtil.makeText(getApplicationContext(), response.getDesc());
            }
        });
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        binding.refreshLayout.autoRefresh();
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

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        currentResult += 10;
        getMessage();

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        binding.refreshLayout.resetNoMoreData();
        currentResult = 0;
        getMessage();

    }

    public class Presenter {

    }
}
