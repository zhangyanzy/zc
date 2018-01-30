package cn.zhaocaiapp.zc_app_android.views.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;

/**
 * @author 林子
 * @filename SearchActivity.java
 * @data 2018-01-24 10:15
 */
public class SearchResulfActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.search_refresh)
    RefreshLayout search_refresh;
    @BindView(R.id.search_recycler)
    RecyclerView search_recycler;

    private String name = ""; //活动名称
    private int pageNumber = 1;//分页
    private List<ActivityResp> activityRespList = new ArrayList<>();//活动列表

    private ActivityAdapter activityAdapter;

    @Override
    public int getContentViewResId() {
        return R.layout.search_resulf;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Bundle bd = this.getIntent().getExtras();
        name = bd != null ? bd.getString("name", "") : "";
        EBLog.i("tag", "接受到搜索名称：" + name);

        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {

        /**
         * 获取推荐活动
         */
        //获取商家活动列表
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("pageSize", String.valueOf(Constants.CONFIG.PAGE_SIZE));
        params.put("currentResult", String.valueOf((pageNumber - 1) * Constants.CONFIG.PAGE_SIZE));
        EBLog.i("tag", params.toString());
        HttpUtil.get(Constants.URL.GET_ACTIVITY_FIND, params).subscribe(new BaseResponseObserver<List<ActivityResp>>() {
            @Override
            public void success(List<ActivityResp> result) {
                if (pageNumber == 1) {
                    activityRespList = result;
                    //恢复没有更多数据的原始状态
                    search_refresh.resetNoMoreData();
                } else {
                    activityRespList.addAll(result);
                }
                if (result.size() < Constants.CONFIG.PAGE_SIZE) {
                    //完成加载并标记没有更多数据
                    search_refresh.finishLoadmoreWithNoMoreData();
                }

                activityAdapter.updata(activityRespList);
                EBLog.i("tag", result.toString());
                search_refresh.finishRefresh();
            }

            @Override
            public void error(Response<List<ActivityResp>> response) {

            }

        });
    }

    private void initView() {
        search_recycler.setLayoutManager(new LinearLayoutManager(this));

        activityAdapter = new ActivityAdapter(this, activityRespList);
        search_recycler.setAdapter(activityAdapter);

        search_refresh.setOnRefreshListener(this);
        search_refresh.setOnLoadmoreListener(this);

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNumber = 1;
        EBLog.i("tag", "refresh --pageNumber：" + pageNumber);
        initData();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        pageNumber = pageNumber + 1;
        EBLog.i("tag", "loadmore -- pageNumber：" + pageNumber);
        initData();

    }

}
