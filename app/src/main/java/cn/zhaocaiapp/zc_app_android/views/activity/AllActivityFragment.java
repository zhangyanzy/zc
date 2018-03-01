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
import cn.zhaocaiapp.zc_app_android.adapter.my.MyActivityAdapter;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.views.common.ActivityDetailActivity;

/**
 * Created by Administrator on 2018/1/29.
 */

public class AllActivityFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refresh_layout;
    @BindView(R.id.list)
    RecyclerView list;

    private int pageSize = 10;
    private int currentResult = 0;
    private int status = -1;

    private MyActivityAdapter adapter;
    private List<ActivityResp> activitys = new ArrayList<>();

    private static final String TAG = "全部活动";

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

        adapter = new MyActivityAdapter(getActivity(), activitys, MyActivityAdapter.MYACTIVITY_ALL);
        list.setAdapter(adapter);
        adapter.setOnItemClickListener(listener);
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
                adapter.refresh(activitys);

                refresh_layout.finishLoadmore();
                refresh_layout.finishRefresh();
            }

            @Override
            public void error(Response<List<ActivityResp>> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    private void cancelActivity(long kid) {
        HttpUtil.put(String.format(Constants.URL.CANCEL_ACTIVITY, kid)).subscribe(new BaseResponseObserver<String>() {

            @Override
            public void success(String s) {
                ToastUtil.makeText(getActivity(), s);
            }

            @Override
            public void error(Response<String> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    private MyActivityAdapter.OnItemClickListener listener = new MyActivityAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position, int viewId) {
            Bundle bundle = new Bundle();
            long kid = activitys.get(position).getKid();
            String activityTitle = activitys.get(position).getName();
            switch (viewId) {
                case R.id.activity_item_img_i: //跳转活动详情，提交活动，领取活动奖励
                case R.id.activity_item_text_centent:
                case R.id.tv_submit:
                case R.id.tv_reward:
                    bundle.clear();
                    bundle.putLong("id", kid);
                    bundle.putString("title", activityTitle);
                    openActivity(ActivityDetailActivity.class, bundle);
                    break;
                case R.id.tv_cancel: // 取消活动报名
                    cancelActivity(kid);
                    break;
            }
        }
    };

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
