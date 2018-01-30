package cn.zhaocaiapp.zc_app_android.views.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.adapter.common.ActivityAdapter;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

/**
 * Created by Administrator on 2018/1/29.
 */

public class UnpassActivityFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refresh_layout;
    @BindView(R.id.list)
    RecyclerView list;

    private int pageSize = 10;
    private int currentResult;
    private int status = 3;

    private ActivityAdapter adapter;
    private List<ActivityResp> activitys = new ArrayList<>();

    private static final String TAG = "未通过活动";

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.layout_refresh_list, container, false);
    }

    @Override
    public void init() {
        refresh_layout.setOnRefreshListener(this);
        refresh_layout.setOnLoadmoreListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);

        adapter = new ActivityAdapter(getActivity(), activitys);
        list.setAdapter(adapter);
    }

    @Override
    public void loadData() {
        Map<String, String> params = new HashMap<>();
        params.put("pageSize", pageSize + "");
        params.put("currentResult", currentResult + "");
        params.put("status", status + "");

        HttpUtil.get(Constants.URL.GET_ALL_ACTIVITY, params).subscribe(new BaseResponseObserver<List<ActivityResp>>() {

            @Override
            public void success(List<ActivityResp> activityResps) {
                EBLog.i(TAG, activityResps.toString());
                activitys.addAll(activityResps);
                adapter.updata(activitys);

                refresh_layout.finishLoadmore();
                refresh_layout.finishRefresh();
            }

            @Override
            public void error(Response<List<ActivityResp>> response) {
                EBLog.e(TAG, response.getCode()+"");
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
        activitys.clear();
        loadData();
    }
}
