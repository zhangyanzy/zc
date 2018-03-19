package cn.zhaocaiapp.zc_app_android.views.member;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import cn.zhaocaiapp.zc_app_android.adapter.common.MemberActivityAdapter;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.bean.response.member.MemberResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ShareUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

/**
 * @author 林子
 * @filename MemberDetailActivity.java
 * @data 2018-01-15 11:36
 */
public class MemberDetailActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.member_detail_refresh)
    RefreshLayout member_detail_refresh;
    @BindView(R.id.member_detail_recycler)
    RecyclerView member_detail_recycler;
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.tv_top_title)
    TextView tv_top_title;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;

    private long memberId;//商家id
    private int pageNumber = 1;//分页
    private MemberResp memberResp = new MemberResp(); //商家详情
    private List<ActivityResp> activityRespList = new ArrayList<>();//活动列表

    private MemberActivityAdapter activityAdapter;
    private UMShareAPI umShareAPI;

    private static final String TAG = "商家详情";

    @Override
    public int getContentViewResId() {
        return R.layout.member_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        umShareAPI = ZcApplication.getUMShareAPI();

        tv_top_title.setText(TAG);
        iv_top_menu.setVisibility(View.GONE);

        Bundle bd = this.getIntent().getExtras();
        memberId = bd.getLong("memberId");
        EBLog.i(TAG, "接受到商家Id：" + memberId);

        member_detail_recycler.setLayoutManager(new LinearLayoutManager(this));

        activityAdapter = new MemberActivityAdapter(this, activityRespList, memberResp);
        member_detail_recycler.setAdapter(activityAdapter);
        activityAdapter.setOnItemClickListener(listener);

        member_detail_refresh.setOnRefreshListener(this);
        member_detail_refresh.setOnLoadmoreListener(this);
        member_detail_refresh.autoRefresh();

    }

    private void initData() {
        //获取商家信息
        HttpUtil.get(String.format(Constants.URL.GET_MEMBER_DETAIL, memberId)).subscribe(new BaseResponseObserver<MemberResp>() {
            @Override
            public void success(MemberResp result) {
                memberResp = result;
                activityAdapter.updataMember(memberResp);
                EBLog.i(TAG, result.toString());
                if (member_detail_refresh.isRefreshing())
                    member_detail_refresh.finishRefresh();
            }

            @Override
            public void error(Response<MemberResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(MemberDetailActivity.this, response.getDesc());
            }
        });

        //获取商家活动列表
        Map<String, String> params = new HashMap<>();
        params.put("memberId", String.valueOf(memberId));
        params.put("pageSize", String.valueOf(Constants.CONFIG.PAGE_SIZE));
        params.put("currentResult", String.valueOf((pageNumber - 1) * Constants.CONFIG.PAGE_SIZE));
        EBLog.i(TAG, params.toString());

        HttpUtil.get(Constants.URL.GET_ACTIVITY_LIST_MEMBER, params).subscribe(new BaseResponseObserver<List<ActivityResp>>() {
            @Override
            public void success(List<ActivityResp> result) {
                EBLog.i(TAG, result.toString());
                if (pageNumber == 1) {
                    activityRespList = result;
                    //恢复没有更多数据的原始状态
                    member_detail_refresh.resetNoMoreData();
                } else {
                    activityRespList.addAll(result);
                }
                activityAdapter.updata(activityRespList);

                if (result.size() < Constants.CONFIG.PAGE_SIZE) {
                    //完成加载并标记没有更多数据
                    member_detail_refresh.finishLoadmoreWithNoMoreData();
                }
                if (member_detail_refresh.isRefreshing())
                    member_detail_refresh.finishRefresh();
                else if (member_detail_refresh.isLoading())
                    member_detail_refresh.finishLoadmore();
            }

            @Override
            public void error(Response<List<ActivityResp>> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(MemberDetailActivity.this, response.getDesc());
            }
        });
    }

    private MemberActivityAdapter.OnItemClickListener listener = new MemberActivityAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            String webUrl = String.format(Constants.URL.SHARE_ACTIVITY_URL, activityRespList.get(position - 1).getKid());
            String shareTitle = activityRespList.get(position - 1).getName();
            String desc = getString(R.string.share_desc);
            ShareUtil.init(MemberDetailActivity.this)
                    .setUrl(webUrl)
                    .setSourceId(R.mipmap.ic_launcher)
                    .setTitle(shareTitle)
                    .setDesc(desc);
            ShareUtil.openShare();
        }
    };

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

    @OnClick({R.id.iv_top_back})
    public void onClicl(View view) {
        finish();
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
