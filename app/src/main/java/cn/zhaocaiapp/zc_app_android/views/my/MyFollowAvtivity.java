package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.adapter.my.MyFollowAdapter;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;

/**
 * Created by Administrator on 2018/1/11.
 */

public class MyFollowAvtivity extends BaseActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.tv_top_title)
    TextView tv_top_titlel;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
    @BindView(R.id.tv_slogan)
    TextView tv_slogan;
    @BindView(R.id.task_list)
    RecyclerView task_list;

    @Override
    public int getContentViewResId() {
        return R.layout.layout_follow_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        task_list.setLayoutManager(manager);

        MyFollowAdapter adapter = new MyFollowAdapter(this);
        task_list.setAdapter(adapter);
        adapter.setOnItemCliclkListener(listener);

    }

    private MyFollowAdapter.OnItemCliclkListener listener = new MyFollowAdapter.OnItemCliclkListener() {
        @Override
        public void onItemCliclk(int position) {

        }
    };

    @OnClick({R.id.iv_top_back, R.id.iv_top_menu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.iv_top_menu:

                break;
        }
    }
}
