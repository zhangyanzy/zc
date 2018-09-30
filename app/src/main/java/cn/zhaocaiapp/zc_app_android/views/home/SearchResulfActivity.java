package cn.zhaocaiapp.zc_app_android.views.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.adapter.common.ActivityAdapter;
import cn.zhaocaiapp.zc_app_android.adapter.home.NewHomeAdapter;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.ActivityUtil;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ShareUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

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
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.iv_top_layout)
    LinearLayout iv_top_layout;
    @BindView(R.id.iv_top_edit)
    EditText iv_top_edit;
    @BindView(R.id.list_null)
    LinearLayout list_null;

    private String name = ""; //活动名称
    private String activityForm = "";//活动类型 0线下活动 1视频活动 2问卷活动
    private String activityType = "";//活动分类 0单体活动 1串联活动 2协同活动
    private String limit = ""; //金额下限
    private String topLimit = "";//金额上限
    private String cityCode = ""; //城市编码
    private String areaCode = ""; //区县编码

    private int pageNumber = 1;//分页
    private ArrayList<ActivityResp> activityRespList = new ArrayList<>();//活动列表

    private NewHomeAdapter activityAdapter;

    private Activity lastActivity;
    private UMShareAPI umShareAPI;

    private static final String TAG = "搜索结果页";

    @Override
    public int getContentViewResId() {
        return R.layout.search_resulf;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        lastActivity = ActivityUtil.currentActivity();
        ActivityUtil.addActivity(this);

        umShareAPI = ZcApplication.getUMShareAPI();

        Bundle bd = this.getIntent().getExtras();
        name = bd != null ? bd.getString("name", "") : "";
        activityForm = bd != null ? bd.getString("activityForm", "") : "";
        activityType = bd != null ? bd.getString("activityType", "") : "";
        limit = bd != null ? bd.getString("limit", "") : "";
        topLimit = bd != null ? bd.getString("topLimit", "") : "";
        cityCode = bd != null ? bd.getString("cityCode", "") : "";
        areaCode = bd != null ? bd.getString("areaCode", "") : "";

        EBLog.i(TAG, "接受到搜索名称：" + name);

        initView();
    }

    /**
     * 获取推荐活动
     */
    private void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("activityForm", activityForm);
        params.put("activityType", activityType);
        params.put("limit", limit);
        params.put("topLimit", topLimit);
        params.put("cityCode", cityCode);
        params.put("areaCode", areaCode);
        params.put("pageSize", String.valueOf(Constants.CONFIG.PAGE_SIZE));
        params.put("currentResult", String.valueOf((pageNumber - 1) * Constants.CONFIG.PAGE_SIZE));
        EBLog.i(TAG, params.toString());
        HttpUtil.get(Constants.URL.GET_ACTIVITY_FIND, params).subscribe(new BaseResponseObserver<ArrayList<ActivityResp>>() {
            @Override
            public void success(ArrayList<ActivityResp> result) {
                EBLog.i(TAG, result.toString());
                if (pageNumber == 1) {
                    activityRespList = result;
                    //恢复没有更多数据的原始状态
                    search_refresh.resetNoMoreData();
                } else {
                    activityRespList.addAll(result);
                }
                if (activityRespList.size() > 0) {
                    list_null.setVisibility(View.GONE);
                } else {
                    list_null.setVisibility(View.VISIBLE);
                }
                activityAdapter.setList(activityRespList);

                if (result.size() < Constants.CONFIG.PAGE_SIZE) {
                    //完成加载并标记没有更多数据
                    search_refresh.finishLoadmoreWithNoMoreData();
                }
                if (search_refresh.isRefreshing())
                    search_refresh.finishRefresh();
                else if (search_refresh.isLoading())
                    search_refresh.finishLoadmore();
            }

            @Override
            public void error(Response<ArrayList<ActivityResp>> response) {
                EBLog.e(TAG, response.getCode()+"");
                ToastUtil.makeText(SearchResulfActivity.this, response.getDesc());
            }
        });
    }

    private void initView() {
        iv_top_edit.setFocusable(false);
        iv_top_edit.setHint(getString(R.string.search_activity));
        iv_top_edit.setText(name);

        search_recycler.setLayoutManager(new LinearLayoutManager(this));
        search_refresh.setOnRefreshListener(this);
        search_refresh.setOnLoadmoreListener(this);
        search_refresh.setEnableLoadmoreWhenContentNotFull(false);
        activityAdapter = new NewHomeAdapter();
        search_recycler.setAdapter(activityAdapter);
//        activityAdapter = new ActivityAdapter(this, activityRespList);
//        search_recycler.setAdapter(activityAdapter);
//        activityAdapter.setOnItemCliclkListener(listener);
        search_refresh.autoRefresh();
    }

    private ActivityAdapter.OnItemCliclkListener listener = new ActivityAdapter.OnItemCliclkListener() {
        @Override
        public void onItemCliclk(int position) {
            String webUrl = "";
            if (activityRespList.get(position).getActivityForm() == 3)
                webUrl = String.format(Constants.URL.SHARE_INFORMATION_ACTIVITY_URL, activityRespList.get(position).getKid(), 3);
            else
                webUrl = String.format(Constants.URL.SHARE_ACTIVITY_URL, activityRespList.get(position).getKid());
            String shareTitle = activityRespList.get(position).getName();
            String desc = getString(R.string.share_desc);
            ShareUtil.init(SearchResulfActivity.this)
                    .setUrl(webUrl)
                    .setSourceId(R.mipmap.icon_launcher)
                    .setTitle(shareTitle)
                    .setDesc(desc);
            ShareUtil.openShare();
        }
    };

    @OnClick({R.id.iv_top_edit, R.id.iv_top_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_edit:
                ActivityUtil.finishActivity(this);
                break;
            case R.id.iv_top_back:
//                ActivityUtil.finishActivity(lastActivity);
//                ActivityUtil.finishActivity(this);
                finish();
                break;
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNumber = 1;
        EBLog.i(TAG, "refresh --pageNumber：" + pageNumber);
        initData();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        pageNumber = pageNumber + 1;
        EBLog.i(TAG, "loadmore -- pageNumber：" + pageNumber);
        initData();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtil.finishActivity(lastActivity);
        ActivityUtil.finishActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        umShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        umShareAPI.release();
    }
}
