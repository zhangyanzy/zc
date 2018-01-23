package cn.zhaocaiapp.zc_app_android.views.home;

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

/**
 * @author 林子
 * @filename NewFragment.java
 * @data 2018-01-22 20:02
 */
public class HistoryFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.home_history_refresh)
    RefreshLayout home_history_refresh;
    @BindView(R.id.home_history_recycler)
    RecyclerView home_history_recycler;

    private int listType = 4;//最新活动 1最新活动 2线下活动 3线上活动 4历史活动
    private int pageNumber = 1;//分页
    private int sortRule = 2;//降序 1升序 2降序
    private int sortType = 1;//时间 1时间 2金额 3距离
    private String longitude = "";//经度
    private String latitude = "";//纬度

    private List<ActivityResp> activityRespList = new ArrayList<>();//活动列表

    private ActivityAdapter activityAdapter;

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_history, container, false);
    }

    @Override
    public void init() {
        home_history_recycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        activityAdapter = new ActivityAdapter(this.getActivity(), activityRespList);
        home_history_recycler.setAdapter(activityAdapter);

        home_history_refresh.setOnRefreshListener(this);
        home_history_refresh.setOnLoadmoreListener(this);
    }

    @Override
    public void loadData() {
        Map<String, String> params = new HashMap<>();
        params.put("listType", String.valueOf(listType));
        params.put("pageSize", String.valueOf(Constants.CONFIG.PAGE_SIZE));
        params.put("currentResult", String.valueOf((pageNumber - 1) * Constants.CONFIG.PAGE_SIZE));
        params.put("sortRule", String.valueOf(sortRule));
        params.put("sortType", String.valueOf(sortType));
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        EBLog.i("tag", params.toString());

        HttpUtil.get(Constants.URL.GET_ACTIVITY_LIST, params).subscribe(new BaseResponseObserver<List<ActivityResp>>() {
            @Override
            public void success(List<ActivityResp> result) {
                if (pageNumber == 1) {
                    activityRespList = result;
                    //恢复没有更多数据的原始状态
                    home_history_refresh.resetNoMoreData();
                } else {
                    activityRespList.addAll(result);
                }
                if (result.size() < Constants.CONFIG.PAGE_SIZE) {
                    //完成加载并标记没有更多数据
                    home_history_refresh.finishLoadmoreWithNoMoreData();
                }

                activityAdapter.updata(activityRespList);
                EBLog.i("tag", result.toString());
                home_history_refresh.finishRefresh();
            }

            @Override
            public void error(Response<List<ActivityResp>> response) {

            }

        });
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNumber = 1;
        loadData();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        pageNumber = pageNumber + 1;
        loadData();

    }
}
