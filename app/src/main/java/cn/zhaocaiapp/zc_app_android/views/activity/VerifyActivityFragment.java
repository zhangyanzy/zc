package cn.zhaocaiapp.zc_app_android.views.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import cn.zhaocaiapp.zc_app_android.adapter.my.MyActivityAdapter;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ShareUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.views.common.ActivityDetailActivity;
import cn.zhaocaiapp.zc_app_android.views.member.MemberDetailActivity;

/**
 * Created by Administrator on 2018/1/29.
 */

public class VerifyActivityFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refresh_layout;
    @BindView(R.id.list)
    RecyclerView list;

    private int pageSize = 10;
    private int currentResult = 0;
    private int status = 1;

    private MyActivityAdapter adapter;
    private List<ActivityResp> activitys = new ArrayList<>();

    private static final String TAG = "待审核活动";

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

        adapter = new MyActivityAdapter(getActivity(), activitys, MyActivityAdapter.MYACTIVITY_VERIFY);
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
                adapter.refresh(activitys);

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

    //关注活动、取消关注
    private void doFollow(int position, View view) {
        ActivityResp activity = activitys.get(position);
        Map<String, Integer> params = new HashMap<>();
        if (activity.getFollow()) { //已关注，点击取消关注
            params.put("follow", 0);
        } else { // 未关注， 点击关注
            params.put("follow", 1);
        }
        HttpUtil.post(String.format(Constants.URL.POST_ACTIVITY_FOLLOW, activitys.get(position).getKid()), params).subscribe(new BaseResponseObserver<String>() {

            @Override
            public void success(String s) {
                if (activity.getFollow()){ //已关注
                    activity.setFollow(false);
                    ((ImageView)view).setImageResource(R.mipmap.collection_off);
                }else { //未关注
                    activity.setFollow(true);
                    ((ImageView)view).setImageResource(R.mipmap.collection_on);
                }
            }

            @Override
            public void error(Response<String> response) {
                EBLog.e(TAG, response.getCode()+"");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    private MyActivityAdapter.OnItemClickListener listener = new MyActivityAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position, View view) {
            Bundle bundle = new Bundle();
            long activityId = activitys.get(position).getKid();

            switch (view.getId()) {
                case R.id.activity_item_img_i: //跳转活动详情
                case R.id.layout_activity_content:
                    String activityTitle = activitys.get(position).getName();

                    bundle.clear();
                    bundle.putLong("id", activityId);
                    bundle.putString("title", activityTitle);
                    openActivity(ActivityDetailActivity.class, bundle);
                    break;
                case R.id.iv_logo: // 跳转商家详情
                case R.id.tv_name:
                    long memberId = activitys.get(position).getMemberId();
                    bundle.clear();
                    bundle.putLong("memberId", memberId);
                    openActivity(MemberDetailActivity.class, bundle);
                    break;
                case R.id.activity_item_text_collection: //活动关注
                    doFollow(position, view);
                    break;
                case R.id.activity_item_text_share: //活动分享
                    String webUrl = String.format(Constants.URL.SHARE_ACTIVITY_URL, activityId);
                    String shareTitle = activitys.get(position).getName();
                    String shareDesc = getString(R.string.share_desc);
                    ShareUtil.init(getActivity())
                            .setUrl(webUrl)
                            .setSourceId(R.mipmap.ic_launcher)
                            .setTitle(shareTitle)
                            .setDesc(shareDesc);
                    ShareUtil.openShare();
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
