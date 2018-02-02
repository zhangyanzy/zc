package cn.zhaocaiapp.zc_app_android.views.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.adapter.home.LocationAdapter;
import cn.zhaocaiapp.zc_app_android.adapter.home.LocationDecoration;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.home.LocationResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.AreaUtil;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;

/**
 * @author 林子
 * @filename LocationActivity.java
 * @data 2018-01-15 13:36
 */
public class LocationActivity extends BaseActivity {

    @BindView(R.id.home_location_recycler)
    RecyclerView home_location_recycler;
    @BindView(R.id.indexBar)
    IndexBar indexBar;
    @BindView(R.id.tvSideBarHint)
    TextView tvSideBarHint;

    private Context mContext;
    private LinearLayoutManager linearLayoutManager;
    private LocationAdapter locationAdapter;
    private SuspensionDecoration mDecoration;

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    private List<LocationResp> locationRespsAllList = new ArrayList<>(); //所有地址集合
    private List<LocationResp> locationRespsList = new ArrayList<>(); //所有城市集合

    private Gson gson1;
    private GsonBuilder builder1;


    @Override
    public int getContentViewResId() {
        return R.layout.home_location;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        EBLog.i("tag", "初始化");
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void initView() {
        mContext = this;

        linearLayoutManager = new LinearLayoutManager(this);
        home_location_recycler.setLayoutManager(linearLayoutManager);

        locationAdapter = new LocationAdapter(this, locationRespsList);
        home_location_recycler.setAdapter(locationAdapter);
        locationAdapter.setOnItemCliclkListener(listener);

        home_location_recycler.addItemDecoration(mDecoration = new SuspensionDecoration(this, locationRespsList));
        //如果add两个，那么按照先后顺序，依次渲染。
        home_location_recycler.addItemDecoration(new LocationDecoration(this, LocationDecoration.VERTICAL_LIST));


        indexBar.setmPressedShowTextView(tvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(linearLayoutManager);//设置RecyclerView的LayoutManager
    }

    /**
     * 初始化数据
     */
    private void initData() {

        locationRespsAllList = ZcApplication.getProvinces();
        EBLog.i("tag", locationRespsAllList.toString());
        for (LocationResp list : locationRespsAllList) {
            for (LocationResp item : list.getAreaList()) {
                locationRespsList.add(item);
            }
        }
        EBLog.i("tag", locationRespsList.toString());
        locationAdapter.updata(locationRespsList);

        indexBar.setmSourceDatas(locationRespsList)//设置数据
                .invalidate();
        mDecoration.setmDatas(locationRespsList);
        EBLog.i("tag", "列表定位完成");

    }

    private LocationAdapter.OnItemCliclkListener listener = new LocationAdapter.OnItemCliclkListener() {
        @Override
        public void onItemCliclk(int position) {
            /*Bundle bd = new Bundle();
            bd.putLong("memberId", memberRespList.get(position).getKid());
            openActivity(MemberDetailActivity.class, bd);*/
            EBLog.i("tag", "您点击了第" + position + "条");
        }
    };
}
