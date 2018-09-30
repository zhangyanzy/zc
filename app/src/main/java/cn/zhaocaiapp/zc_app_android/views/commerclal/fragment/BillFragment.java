package cn.zhaocaiapp.zc_app_android.views.commerclal.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.adapter.holder.merchant.BillAdapter;
import cn.zhaocaiapp.zc_app_android.adapter.holder.merchant.PayBillAdapter;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.merchant.PayBillEntity;
import cn.zhaocaiapp.zc_app_android.bean.response.merchant.UserComplete;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;

/**
 * Created by admin on 2018/9/13.
 */

public class BillFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener {

    @BindView(R.id.new_home_detail_refresh)
    RefreshLayout member_detail_refresh;
    @BindView(R.id.bill_rv)
    RecyclerView mRv;

    private BillAdapter mAdapter;

    private Integer currentResult = 10;//每页条目
    private Integer pageSize;//页数
    private int pageNumber = 1;//分页

    private View mView;
    private ArrayList<UserComplete> lists;

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = View.inflate(getContext(), R.layout.fragment_bill, null);
        return mView;
    }

    @Override
    public void init() {
        lists = new ArrayList<>();
        mAdapter = new BillAdapter();
        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRv.setAdapter(mAdapter);

        member_detail_refresh.setOnRefreshListener(this);
        member_detail_refresh.setOnLoadmoreListener(this);
        member_detail_refresh.setEnableLoadmoreWhenContentNotFull(false);
        member_detail_refresh.autoRefresh();
    }

    private void getComplete() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageSize", String.valueOf(Constants.CONFIG.PAGE_SIZE));
        params.put("currentResult", String.valueOf((pageNumber - 1) * Constants.CONFIG.PAGE_SIZE));
        HttpUtil.get(Constants.URL.USER_COMPLETE, params).subscribe(new BaseResponseObserver<ArrayList<UserComplete>>() {
            @Override
            public void success(ArrayList<UserComplete> userCompletes) {
                if (userCompletes.size() > 0) {
                    lists = userCompletes;
                    if (pageNumber == 1) {
                        lists = userCompletes;
                        //恢复没有更多数据的原始状态
                        member_detail_refresh.resetNoMoreData();
                    } else {
                        lists.addAll(userCompletes);
                    }
                    mAdapter.setList(userCompletes);

                    if (userCompletes.size() < Constants.CONFIG.PAGE_SIZE) {
                        //完成加载并标记没有更多数据
                        member_detail_refresh.finishLoadmoreWithNoMoreData();
                    }
                    if (member_detail_refresh.isRefreshing())
                        member_detail_refresh.finishRefresh();
                    else if (member_detail_refresh.isLoading())
                        member_detail_refresh.finishLoadmore();
                }

            }

            @Override
            public void error(Response<ArrayList<UserComplete>> response) {

            }
        });
    }

    @Override
    public void loadData() {
        member_detail_refresh.autoRefresh();

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        pageNumber = pageNumber + 1;
        getComplete();

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNumber = 1;
        getComplete();
    }

    @Override
    public void onResume() {
        super.onResume();
        member_detail_refresh.autoRefresh();
    }
}
