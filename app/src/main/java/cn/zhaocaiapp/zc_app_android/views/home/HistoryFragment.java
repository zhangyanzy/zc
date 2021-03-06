package cn.zhaocaiapp.zc_app_android.views.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.adapter.common.ActivityAdapter;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.MessageEvent;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ShareUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

/**
 * @author 林子
 * @filename NewFragment.java
 * @data 2018-01-22 20:02
 */
public class HistoryFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.home_refresh)
    RefreshLayout home_refresh;
    @BindView(R.id.home_recycler)
    RecyclerView home_recycler;
    @BindView(R.id.list_null)
    LinearLayout list_null;

    private int listType = 4;//最新活动 1最新活动 2线下活动 3线上活动 4历史活动
    private int pageNumber = 1;//分页
    private int sortRule = 2;//降序 1升序 2降序
    private int sortType = 0;//默认 0默认 1时间 2金额 3距离
    private String longitude = "";//经度
    private String latitude = "";//纬度

    private List<ActivityResp> activityRespList = new ArrayList<>();//活动列表

    private ActivityAdapter activityAdapter;

    private static final String TAG = "历史活动";

    @Override
    public void onStart() {
        super.onStart();
        //注册EventBus消息订阅者
        EventBus.getDefault().register(this);
    }

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_tab_history, container, false);
    }

    @Override
    public void init() {
        home_recycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        home_refresh.setOnRefreshListener(this);
        home_refresh.setOnLoadmoreListener(this);

        activityAdapter = new ActivityAdapter(this.getActivity(), activityRespList);
        home_recycler.setAdapter(activityAdapter);
        activityAdapter.setOnItemCliclkListener(listener);

    }

    @Override
    public void loadData() {
        /**
         * 会导致首次进入时重复加载数据
         * */
        home_refresh.autoRefresh();//自动刷新
    }

    /**
     * 解决因懒加载和进入时的自动刷新导致的重复加载
     * <p>
     * 加载网络数据
     */
    private void initNetData() {
        Map<String, String> params = new HashMap<>();
        params.put("listType", String.valueOf(listType));
        params.put("pageSize", String.valueOf(Constants.CONFIG.PAGE_SIZE));
        params.put("currentResult", String.valueOf((pageNumber - 1) * Constants.CONFIG.PAGE_SIZE));
        params.put("sortRule", String.valueOf(sortRule));
        params.put("sortType", String.valueOf(sortType));
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        if ((int) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.ACTIVITY_RANGE, 0) == 0) {
            params.put("cityCode", "");
        } else {
            params.put("cityCode", (String) SpUtils.init(Constants.SPREF.FILE_APP_NAME).get(Constants.SPREF.AREA_CODE, Constants.CONFIG.AREA_CODE));
        }
        EBLog.i(TAG, params.toString());

        HttpUtil.get(Constants.URL.GET_ACTIVITY_LIST, params).subscribe(new BaseResponseObserver<List<ActivityResp>>() {
            @Override
            public void success(List<ActivityResp> result) {
                EBLog.i(TAG, result.toString());
                if (pageNumber == 1) {
                    activityRespList = result;
                    //恢复没有更多数据的原始状态
                    home_refresh.resetNoMoreData();
                } else {
                    activityRespList.addAll(result);
                }
                if (activityRespList.size() > 0) {
                    list_null.setVisibility(View.GONE);
                } else {
                    list_null.setVisibility(View.VISIBLE);
                }
                activityAdapter.updata(activityRespList);

                if (result.size() < Constants.CONFIG.PAGE_SIZE) {
                    //完成加载并标记没有更多数据
                    home_refresh.finishLoadmoreWithNoMoreData();
                }
                if (home_refresh.isRefreshing())
                    home_refresh.finishRefresh();
                else if (home_refresh.isLoading())
                    home_refresh.finishLoadmore();
            }

            @Override
            public void error(Response<List<ActivityResp>> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    //接收EventBus发送的消息，并处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        if (event.getMessage() instanceof Map) {
            Map<String, Integer>map = (Map<String, Integer>) event.getMessage();
            if (map.get("tabCurPosition") == 3) {
                EBLog.i(TAG, "接受到更新通知");
                pageNumber = map.get("pageNumber");
                sortType = map.get("sortType");
                sortRule = map.get("sortRule");
                home_recycler.scrollToPosition(0);//回到顶部
                home_refresh.autoRefresh();
            }
        }
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
            ShareUtil.init(getActivity())
                    .setUrl(webUrl)
                    .setSourceId(R.mipmap.icon_launcher)
                    .setTitle(shareTitle)
                    .setDesc(desc);
            ShareUtil.openShare();
        }
    };

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNumber = 1;
        initNetData();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        pageNumber = pageNumber + 1;
        initNetData();

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
