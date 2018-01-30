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
import cn.zhaocaiapp.zc_app_android.adapter.my.MyMessageAdapter;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.my.MessageResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

/**
 * Created by Administrator on 2018/1/25.
 */

public class ActivityMessageFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refresh_layout;
    @BindView(R.id.list)
    RecyclerView list;

    private static final String TAG = "活动提醒";

    private int type = 0;//消息类型
    private int currentResult = 0;
    private int pageSize = 10;
    private MyMessageAdapter adapter;

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_refresh_list, container, false);
    }

    @Override
    public void init() {
        refresh_layout.setOnRefreshListener(this);
        refresh_layout.setOnLoadmoreListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        adapter = new MyMessageAdapter(getActivity(), new ArrayList<MessageResp>());
        list.setAdapter(adapter);
        adapter.setOnItemCliclkListener(listener);
    }

    @Override
    public void loadData() {
        Map<String, String> params = new HashMap<>();
        params.put("type", type + "");
        params.put("currentResult", currentResult + "");
        params.put("pageSize", pageSize + "");

        HttpUtil.get(String.format(Constants.URL.MESSAGE_LIST, type),params).subscribe(new BaseResponseObserver<List<MessageResp>>() {

            @Override
            public void success(List<MessageResp> messageResps) {
                EBLog.i(TAG, messageResps.toString());
                adapter.refresh(messageResps);
            }

            @Override
            public void error(Response<List<MessageResp>> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    private MyMessageAdapter.OnItemCliclkListener listener = new MyMessageAdapter.OnItemCliclkListener() {
        @Override
        public void onItemCliclk(int position) {
//            msgId = messages.get(position).getMessageId();
//            updateMessageStatus();
        }
    };

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        currentResult += 10;
        loadData();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

    }
}