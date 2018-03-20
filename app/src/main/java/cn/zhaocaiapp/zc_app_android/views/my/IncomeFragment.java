package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.util.AsyncExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.adapter.my.MyIncomeAdapter;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.my.IncomeResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

/**
 * Created by Administrator on 2018/1/15.
 */

public class IncomeFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refresh_layout;
    @BindView(R.id.list)
    RecyclerView list;

    private int type = 0;//收入 1-提现
    private int currentResult = 0;
    private int pageSize = 10;

    private List<IncomeResp> incomes = new ArrayList<>();
    private MyIncomeAdapter adapter;

    private static final String TAG = "收入明细";

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_refresh_list, container, false);
    }

    @Override
    public void init() {
        refresh_layout.setOnRefreshListener(this);
        refresh_layout.setOnLoadmoreListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        adapter = new MyIncomeAdapter(getActivity(), incomes, type);
        list.setAdapter(adapter);
    }

    @Override
    public void loadData() {
        Map<String, Integer> params = new HashMap<>();
        params.put("pageSize", pageSize);
        params.put("currentResult", currentResult);
        HttpUtil.get(String.format(Constants.URL.INCOME_LIST, type), params).subscribe(new BaseResponseObserver<List<IncomeResp>>() {

            @Override
            public void success(List<IncomeResp> incomeResps) {
                EBLog.i(TAG, incomeResps.toString());
                if (currentResult == 0) incomes = incomeResps;
                else incomes.addAll(incomeResps);
                adapter.refresh(incomes);

                if (incomeResps.size() < pageSize) {
                    //完成加载并标记没有更多数据
                    refresh_layout.finishLoadmoreWithNoMoreData();
                    refresh_layout.setEnableFooterFollowWhenLoadFinished(true);
                }
                if (refresh_layout.isRefreshing())
                    refresh_layout.finishRefresh();
                else if (refresh_layout.isLoading())
                    refresh_layout.finishLoadmore();
            }

            @Override
            public void error(Response<List<IncomeResp>> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        currentResult += 10;
        loadData();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        currentResult = 0;
        loadData();
    }
}
