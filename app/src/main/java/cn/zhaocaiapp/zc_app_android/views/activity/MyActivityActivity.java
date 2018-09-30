package cn.zhaocaiapp.zc_app_android.views.activity;

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
import java.util.Map;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.adapter.member.MyActivityAdapter;
import cn.zhaocaiapp.zc_app_android.base.BaseCompatActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.databinding.ActivityMyActivityBinding;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.widget.NavigationTopBar;

public class MyActivityActivity extends BaseCompatActivity implements NavigationTopBar.NavigationTopBarClickListener, OnRefreshListener, OnLoadmoreListener {


    private ActivityMyActivityBinding binding;
    private NavigationTopBar mNavigationTopBar;
    private MyActivityAdapter mAdapter;

    private int pageSize = 10;
    private int currentResult = 0;
    private int status = -1;
    private ArrayList<ActivityResp> activitys = new ArrayList<>();

    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_activity);
        binding.setPresenter(new Presenter());
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.refreshLayout.setOnRefreshListener(this);
        binding.refreshLayout.setOnLoadmoreListener(this);
        binding.refreshLayout.setEnableLoadmoreWhenContentNotFull(false);
        binding.activityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyActivityAdapter();
        binding.activityRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        mNavigationTopBar = findViewById(R.id.my_activity_top);
        mNavigationTopBar.setLeftImageResource(R.mipmap.finish_icon);
        mNavigationTopBar.setCenterTitleText("我的活动");
        mNavigationTopBar.setCenterTitleTextColor(R.color.colorBlack);
        mNavigationTopBar.setNavigationTopBarClickListener(this);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        binding.refreshLayout.autoRefresh();

    }

    private void initNetData() {
        Map<String, String> params = new HashMap<>();
        params.put("pageSize", pageSize + "");
        params.put("currentResult", currentResult + "");
        params.put("status", status + "");

        HttpUtil.get(Constants.URL.GET_ALL_ACTIVITY, params).subscribe(new BaseResponseObserver<ArrayList<ActivityResp>>() {

            @Override
            public void success(ArrayList<ActivityResp> activityResps) {
                if (currentResult == 0) {
                    activitys = activityResps;
                } else {
                    activitys.addAll(activityResps);
                }
                mAdapter.setList(activitys);

                if (activityResps.size() < pageSize) {
                    //完成加载并标记没有更多数据
                    binding.refreshLayout.finishLoadmoreWithNoMoreData();
                }
                if (binding.refreshLayout.isRefreshing())
                    binding.refreshLayout.finishRefresh();
                else if (binding.refreshLayout.isLoading())
                    binding.refreshLayout.finishLoadmore();
            }

            @Override
            public void error(Response<ArrayList<ActivityResp>> response) {
                ToastUtil.makeText(MyActivityActivity.this, response.getDesc());
            }
        });
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
        initNetData();

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        binding.refreshLayout.resetNoMoreData();
        currentResult = 0;
        initNetData();
    }

    public class Presenter {

    }
}
