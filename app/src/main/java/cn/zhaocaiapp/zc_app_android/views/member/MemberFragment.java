package cn.zhaocaiapp.zc_app_android.views.member;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

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
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.member.MemberResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;

/**
 * @author 林子
 * @filename MemberActivity.java
 * @data 2018-01-05 17:59
 */
public class MemberFragment extends BaseFragment {

    //@BindView(R.id.member_grid)
    //GridLayout member_grid;
    @BindView(R.id.member_refresh_layout)
    RefreshLayout member_refresh_layout;
    @BindView(R.id.member_recycler_view)
    RecyclerView member_recycler_view;

    private List<MemberResp> memberRespList = new ArrayList<>(); //商家数据
    private MemberAdapter memberAdapter;

    @Nullable
    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_main, container, false);
        return view;
    }

    @Override
    public void init() {
        initData("");
        initView();
        refresh();

    }

    //刷新操作
    private void refresh() {
        member_refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout member_refresh_layout) {
                Log.i("Log", "刷新了");
                member_refresh_layout.finishRefresh(2000);
                initData("");
            }
        });
        member_refresh_layout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout member_refresh_layout) {
                Log.i("Log", "加载了");
                member_refresh_layout.finishLoadmore(2000);
            }
        });
    }

    //初始化视图
    private void initView() {
        member_recycler_view.setLayoutManager(new GridLayoutManager(getActivity(), 5));

        member_recycler_view.addItemDecoration(new MemberDivider(getActivity(), 10, 10));

        memberAdapter = new MemberAdapter(getActivity(), memberRespList);
        member_recycler_view.setAdapter(memberAdapter);
        memberAdapter.setOnItemCliclkListener(listener);

    }

    private MemberAdapter.OnItemCliclkListener listener = new MemberAdapter.OnItemCliclkListener() {
        @Override
        public void onItemCliclk(int position) {
            Bundle bd = new Bundle();
            bd.putLong("memberId", memberRespList.get(position).getId());
            openActivity(MemberDetailActivity.class, bd);
            EBLog.i("tag", "您点击了第" + position + "条");
        }
    };

    //初始化数据
    private void initData(String name) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);

        HttpUtil.get(Constants.URL.GET_MEMBER_QUERY, params).subscribe(new BaseResponseObserver<List<MemberResp>>() {
            @Override
            public void success(List<MemberResp> result) {
                memberRespList = result;
                memberAdapter.updata(memberRespList);
                EBLog.i("tag", result.toString());
            }

            @Override
            public void error(Response<List<MemberResp>> response) {

            }

        });
    }


}
