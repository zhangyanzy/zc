package cn.zhaocaiapp.zc_app_android.views.member;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import java.util.List;

import butterknife.BindView;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;

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

    private List<String> mDatas; //商家数据

    @Nullable
    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_main, container, false);
        return view;
    }

    @Override
    public void init() {
        /*int count = 1;
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++) {
                Button btn = new Button(getContext());
                btn.setWidth(40);
                btn.setText(String.valueOf(count));
                count++;
                GridLayout.Spec rowSpec = GridLayout.spec(i);     //设置它的行和列
                GridLayout.Spec columnSpec = GridLayout.spec(j);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                params.setGravity(Gravity.LEFT);
                member_grid.addView(btn, params);
            }*/
        initData();
        refresh();

    }

    //刷新操作
    private void refresh() {
        member_refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout member_refresh_layout) {
                Log.i("Log", "刷新了");
                member_refresh_layout.finishRefresh(2000);
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

    //动态添加数据
    private void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("商家" + (char) i);
        }
    }




}
