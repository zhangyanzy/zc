package cn.zhaocaiapp.zc_app_android.views.my;

import android.graphics.drawable.Drawable;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.adapter.my.MyFollowBusinerAdapter;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.member.MemberResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.views.member.MemberDetailActivity;

/**
 * Created by Administrator on 2018/1/24.
 */

public class MyFollowBusinerFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refresh_layout;
    @BindView(R.id.list)
    RecyclerView list;

    private int pageSize = 10;
    private int currentResult = 0;

    private MyFollowBusinerAdapter adapter;
    private List<MemberResp> members = new ArrayList<>();
    private Map<String, String> params = new HashMap<>();
    private long memberId;// 商家id

    private static final String TAG = "我关注的商家";

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_refresh_list, container, false);
    }

    @Override
    public void init() {
        refresh_layout.setOnRefreshListener(this);
        refresh_layout.setOnLoadmoreListener(this);
        refresh_layout.setEnableLoadmoreWhenContentNotFull(false);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);

        adapter = new MyFollowBusinerAdapter(getActivity(), members);
        list.setAdapter(adapter);
        adapter.setOnItemCliclkListener(listener);
    }

    //加载活动列表
    @Override
    public void loadData() {
        /**
         * 会导致首次进入时重复加载数据
         * */
        refresh_layout.autoRefresh();
    }

    /**
     * 解决因懒加载和进入时的自动刷新导致的重复加载
     * <p>
     * 加载网络数据
     */
    private void initNetData() {
        Map<String, String> params = new HashMap<>();
        params.put("pageSize", pageSize + "");
        params.put("currentResult", currentResult + "");

        HttpUtil.get(Constants.URL.GET_FOLLOW_BUSINER, params).subscribe(new BaseResponseObserver<List<MemberResp>>() {

            @Override
            public void success(List<MemberResp> memberResps) {
                EBLog.i(TAG, memberResps.toString());
                if (currentResult == 0) members = memberResps;
                else members.addAll(memberResps);
                adapter.refresh(members);

                if (memberResps.size() < pageSize) {
                    //完成加载并标记没有更多数据
                    refresh_layout.finishLoadmoreWithNoMoreData();
                }
                if (refresh_layout.isRefreshing())
                    refresh_layout.finishRefresh();
                else if (refresh_layout.isLoading())
                    refresh_layout.finishLoadmore();
            }

            @Override
            public void error(Response<List<MemberResp>> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    //关注商家、取消关注
    private void doFollow(int position, View view) {
        memberId = members.get(position).getKid();
        MemberResp memberResp = members.get(position);

        HttpUtil.post(String.format(Constants.URL.POST_MEMBER_FOLLOW, memberId), params).subscribe(new BaseResponseObserver<String>() {
            @Override
            public void success(String result) {
                EBLog.i("tag", result.toString());
                if (memberResp.getIsFollow() == 1) {
                    memberResp.setIsFollow(0);
                    ((TextView)view).setBackground(getResources().getDrawable(R.drawable.member_follow_off));
                    ((TextView)view).setTextColor(getResources().getColor(R.color.colorWhite));
                    ((TextView)view).setText("关注");
                    Drawable drawable = getResources().getDrawable(R.mipmap.add);
                    drawable.setBounds(0, 0, 35, 35);
                    ((TextView)view).setCompoundDrawables(drawable, null, null, null);
                } else {
                    memberResp.setIsFollow(1);
                    ((TextView)view).setBackground(getResources().getDrawable(R.drawable.member_follow_on));
                    ((TextView)view).setTextColor(getResources().getColor(R.color.colorLine));
                    ((TextView)view).setText("已关注");
                    ((TextView)view).setCompoundDrawables(null, null, null, null);
                }
                adapter.refresh(members);
            }

            @Override
            public void error(Response<String> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    private MyFollowBusinerAdapter.OnItemCliclkListener listener = new MyFollowBusinerAdapter.OnItemCliclkListener() {
        @Override
        public void onItemCliclk(int position, View view) {
            switch (view.getId()) {
                case R.id.tv_followed:
                    params.clear();
                    //取消关注
                    if (members.get(position).getIsFollow() == 1) {
                        params.put("follow", "0");
                    } else { //关注
                        params.put("follow", "1");
                    }
                    doFollow(position, view);
                    break;
                default:
                    Bundle bundle = new Bundle();
                    bundle.putLong("memberId", members.get(position).getKid());
                    openActivity(MemberDetailActivity.class, bundle);
            }
        }
    };

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        currentResult += 10;
        initNetData();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        refresh_layout.resetNoMoreData();
        currentResult = 0;
        initNetData();
    }
}
