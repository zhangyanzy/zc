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

import butterknife.BindView;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.adapter.my.MyFollowBusinerAdapter;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;

/**
 * Created by Administrator on 2018/1/24.
 */

public class MyFollowBusinerFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refresh_layout;
    @BindView(R.id.list)
    RecyclerView list;


    private MyFollowBusinerAdapter adapter;

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_refresh_list, container, false);
    }

    @Override
    public void init() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(manager);
        adapter = new MyFollowBusinerAdapter(getActivity());
        list.setAdapter(adapter);
        adapter.setOnItemCliclkListener(listener);

    }

    @Override
    public void loadData() {

    }

    private MyFollowBusinerAdapter.OnItemCliclkListener listener = new MyFollowBusinerAdapter.OnItemCliclkListener() {
        @Override
        public void onItemCliclk(int position) {

        }
    };

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {

    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {

    }
}
