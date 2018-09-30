package cn.zhaocaiapp.zc_app_android.views.commerclal.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.adapter.merchant.MerchantActivityAdapter;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.bean.response.common.MerchantActivityEntity;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.views.commerclal.UploadActivityActivity;

/**
 * Created by admin on 2018/9/11.
 * 商家端---活动列表
 */

public class MerchantActivityFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.merchant_my_activity)
    RecyclerView mActivityView;
    @BindView(R.id.merchant_my_activity_refresh)
    RefreshLayout mRefresh;
    @BindView(R.id.left_finish)
    ImageView mleft;
    @BindView(R.id.merchant_center_title)
    TextView mCenterTitle;
    @BindView(R.id.up_load_activity)
    TextView mUploadActivity;

    private int pageNumber = 1;//分页
    private View view;
    private MerchantActivityAdapter mAdapter;
    private ArrayList<ActivityResp> entities;


    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getContext(), R.layout.merchant_home, null);
        return view;
    }

    @Override
    public void init() {
        mActivityView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MerchantActivityAdapter();
        mActivityView.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(this);
        mRefresh.setOnLoadmoreListener(this);
        mRefresh.setEnableLoadmoreWhenContentNotFull(false);
        mRefresh.autoRefresh();
    }

    @Override
    public void loadData() {
        getMemberActivity();
    }


    private void getMemberActivity() {
        HashMap<String, String> params = new HashMap<>();
        params.put("pageSize", String.valueOf(Constants.CONFIG.PAGE_SIZE));
        params.put("currentResult", String.valueOf((pageNumber - 1) * Constants.CONFIG.PAGE_SIZE));
        HttpUtil.get(Constants.URL.MEMBER_ACTIVITY, params).subscribe(new BaseResponseObserver<ArrayList<ActivityResp>>() {
            @Override
            public void success(ArrayList<ActivityResp> activityResps) {
                if (activityResps.size() > 0) {
                    if (pageNumber == 1) {
                        entities = activityResps;
                        //恢复没有更多数据的原始状态
                        mRefresh.resetNoMoreData();
                    } else {
                        entities.addAll(activityResps);
                    }
                    mAdapter.setList(activityResps);

                    if (activityResps.size() < Constants.CONFIG.PAGE_SIZE) {
                        //完成加载并标记没有更多数据
                        mRefresh.finishLoadmoreWithNoMoreData();
                    }
                    if (mRefresh.isRefreshing())
                        mRefresh.finishRefresh();
                    else if (mRefresh.isLoading())
                        mRefresh.finishLoadmore();
                }
            }

            @Override
            public void error(Response<ArrayList<ActivityResp>> response) {

            }
        });
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        pageNumber = pageNumber + 1;
        getMemberActivity();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNumber = 1;
        getMemberActivity();
    }

    @OnClick({R.id.up_load_activity, R.id.left_finish})
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.left_finish:
            case R.id.up_load_activity:
                openActivity(UploadActivityActivity.class);
                break;
            default:
                break;
        }
    }
}
