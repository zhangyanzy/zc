package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.adapter.my.MyFollowBusinerAdapter;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;

/**
 * Created by Administrator on 2018/1/24.
 */

public class MyFollowBusinerFragment extends BaseFragment {
    @BindView(R.id.list)
    RecyclerView list;


    private MyFollowBusinerAdapter adapter;

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_list, container, false);
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
}
