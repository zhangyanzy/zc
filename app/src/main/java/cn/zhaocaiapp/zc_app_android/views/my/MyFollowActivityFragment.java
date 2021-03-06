package cn.zhaocaiapp.zc_app_android.views.my;

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
import cn.zhaocaiapp.zc_app_android.adapter.home.NewHomeAdapter;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ShareUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

/**
 * Created by Administrator on 2018/1/24.
 */

public class MyFollowActivityFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener{
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refresh_layout;
    @BindView(R.id.list)
    RecyclerView list;

    private int pageSize = 10;
    private int currentResult = 0;

    private NewHomeAdapter adapter;
    private ArrayList<ActivityResp> activitys = new ArrayList<>();

    private static final String TAG = "我关注的活动";

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
        adapter = new NewHomeAdapter();
        list.setAdapter(adapter);

//        adapter = new ActivityAdapter(getActivity(), activitys);
//        list.setAdapter(adapter);
//        adapter.setOnItemCliclkListener(listener);
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
    private void initNetData(){
        Map<String, String> params = new HashMap<>();
        params.put("pageSize", pageSize + "");
        params.put("currentResult", currentResult + "");

        HttpUtil.get(Constants.URL.GET_FOLLOW_ACTIVITY, params).subscribe(new BaseResponseObserver<ArrayList<ActivityResp>>() {

            @Override
            public void success(ArrayList<ActivityResp> activityResps) {
                EBLog.i(TAG, activityResps.toString());
                if (currentResult == 0) activitys = activityResps;
                else activitys.addAll(activityResps);
                adapter.setList(activitys);

                if (activityResps.size() < pageSize) {
                    //完成加载并标记没有更多数据
                    refresh_layout.finishLoadmoreWithNoMoreData();
                }
                if (refresh_layout.isRefreshing())
                    refresh_layout.finishRefresh();
                else if (refresh_layout.isLoading())
                    refresh_layout.finishLoadmore();
            }

            @Override
            public void error(Response<ArrayList<ActivityResp>> response) {
                EBLog.e(TAG, response.getCode()+"");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    private ActivityAdapter.OnItemCliclkListener listener = new ActivityAdapter.OnItemCliclkListener() {
        @Override
        public void onItemCliclk(int position) {
            String webUrl = "";
            if (activitys.get(position).getActivityForm() == 3)
                webUrl = String.format(Constants.URL.SHARE_INFORMATION_ACTIVITY_URL, activitys.get(position).getKid(), 3);
            else
                webUrl = String.format(Constants.URL.SHARE_ACTIVITY_URL, activitys.get(position).getKid());
            String shareTitle = activitys.get(position).getName();
            String desc = getString(R.string.share_desc);
            ShareUtil.init(getActivity())
                    .setUrl(webUrl)
                    .setSourceId(R.mipmap.icon_launcher)
                    .setTitle(shareTitle)
                    .setDesc(desc);
            ShareUtil.openShare();
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
