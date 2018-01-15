package cn.zhaocaiapp.zc_app_android.views.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;

/**
 * @author 林子
 * @filename LocationActivity.java
 * @data 2018-01-15 13:36
 */
public class LocationActivity extends BaseActivity {

    @BindView(R.id.home_location_recycler)
    RecyclerView home_location_recycler;

    private Context mContext;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public int getContentViewResId() {
        return R.layout.home_location;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        initData();
    }

    private void initView() {
        mContext = this;

        linearLayoutManager = new LinearLayoutManager(this);
        home_location_recycler.setLayoutManager(linearLayoutManager);
    }

    private void initData() {


    }
}
