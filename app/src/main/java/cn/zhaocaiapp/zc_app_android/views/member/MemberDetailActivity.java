package cn.zhaocaiapp.zc_app_android.views.member;

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
import cn.zhaocaiapp.zc_app_android.adapter.member.MemberDetailAdapter;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;

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

    private long memberId;//商家
    private int pageNumber = 1;//分页
    private int total = 0;//总数
    private List<ActivityResp> activityRespList = new ArrayList<>();//活动列表

    private MemberDetailAdapter memberDetailAdapter;


    @Override
    public int getContentViewResId() {
        return R.layout.member_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Bundle bd = this.getIntent().getExtras();
        memberId = bd.getLong("memberId");
        EBLog.i("tag", "接受到商家Id：" + memberId);

        member_detail_recycler.setLayoutManager(new LinearLayoutManager(this));

        memberDetailAdapter = new MemberDetailAdapter(this, activityRespList);
        member_detail_recycler.setAdapter(memberDetailAdapter);

        member_detail_refresh.setOnRefreshListener(this);
        member_detail_refresh.setOnLoadmoreListener(this);
        initData();
    }

    private void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("memberId", String.valueOf(memberId));
        params.put("pageSize", String.valueOf(Constants.CONFIG.PAGE_SIZE));
        params.put("currentResult", String.valueOf((pageNumber - 1) * Constants.CONFIG.PAGE_SIZE));
        EBLog.i("tag", params.toString());

        HttpUtil.get(Constants.URL.GET_ACTIVITY_LIST_MEMBER, params).subscribe(new BaseResponseObserver<Response<List<ActivityResp>>>() {
            @Override
            public void success(Response<List<ActivityResp>> result) {
                if (pageNumber == 1) {
                    activityRespList = result.getData();
                    //恢复没有更多数据的原始状态
                    member_detail_refresh.resetNoMoreData();
                } else {
                    activityRespList.addAll(result.getData());
                }
                if (pageNumber * Constants.CONFIG.PAGE_SIZE >= result.getTotal()) {
                    //完成加载并标记没有更多数据
                    member_detail_refresh.finishLoadmoreWithNoMoreData();
                }

                memberDetailAdapter.updata(activityRespList);
                EBLog.i("tag", result.toString());
                member_detail_refresh.finishRefresh();
            }

            @Override
            public void error(Response<Response<List<ActivityResp>>> response) {

            }

        });
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
