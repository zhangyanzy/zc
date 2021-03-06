package cn.zhaocaiapp.zc_app_android.views.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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


import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.MainActivity;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.adapter.home.LocationAdapter;
import cn.zhaocaiapp.zc_app_android.adapter.home.LocationDecoration;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.bean.MessageEvent;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.home.Gps;
import cn.zhaocaiapp.zc_app_android.bean.response.home.LocationResp;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.listener.OnBtnClickL;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.widget.NormalDialog;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.ActivityUtil;
import cn.zhaocaiapp.zc_app_android.util.AreaUtil;
import cn.zhaocaiapp.zc_app_android.util.DialogUtil;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.LocationUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.widget.NavigationTopBar;

/**
 * @author 林子
 * @filename LocationActivity.java
 * @data 2018-01-15 13:36
 */
public class LocationActivity extends BaseActivity implements NavigationTopBar.NavigationTopBarClickListener {
    @BindView(R.id.home_location_recycler)
    RecyclerView home_location_recycler;
    @BindView(R.id.indexBar)
    IndexBar indexBar;
    @BindView(R.id.tvSideBarHint)
    TextView tvSideBarHint;
    @BindView(R.id.home_location_area_text)
    TextView home_location_area_text;
    @BindView(R.id.home_location_switch)
    Button home_location_switch;
//    @BindView(R.id.iv_top_back)
//    ImageView iv_top_back;
//    @BindView(R.id.tv_top_title)
//    TextView tv_top_title;
//    @BindView(R.id.iv_top_menu)
//    ImageView iv_top_menu;

    private static final String TAG = "城市列表";

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
    private NavigationTopBar mNavigationTopBar;


    @Override
    public int getContentViewResId() {
        return R.layout.home_location;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        ActivityUtil.addActivity(this);

        EBLog.i("tag", "初始化");
//        iv_top_menu.setVisibility(View.GONE);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void initView() {
        mNavigationTopBar = findViewById(R.id.location_top_bar);
        mNavigationTopBar.setLeftImageResource(R.mipmap.finish_icon);
        mNavigationTopBar.setCenterTitleText("城市列表");
        mNavigationTopBar.setCenterTitleTextColor(R.color.colorBlack);
        mNavigationTopBar.setNavigationTopBarClickListener(this);

//        tv_top_title.setText(TAG);
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


        /**
         * 切换城市
         */
        Gps gps = LocationUtil.getGps();
        String areaName = (String) SpUtils.init(Constants.SPREF.FILE_APP_NAME).get(Constants.SPREF.AREA_NAME, Constants.CONFIG.AREA_NAME);
        String areaCode = (String) SpUtils.init(Constants.SPREF.FILE_APP_NAME).get(Constants.SPREF.AREA_CODE, Constants.CONFIG.AREA_CODE);
        if (gps.getOpen()) {
            if (areaName.equals(gps.getCity())) {
                home_location_switch.setVisibility(View.INVISIBLE);
                home_location_area_text.setText(gps.getCity());
            } else {
                home_location_switch.setVisibility(View.VISIBLE);
                home_location_area_text.setText(gps.getCity());
            }
        } else {
            home_location_area_text.setText("无法定位到当前城市");
        }
        EBLog.i("tag", "列表定位完成");

    }

    private LocationAdapter.OnItemCliclkListener listener = new LocationAdapter.OnItemCliclkListener() {
        @Override
        public void onItemCliclk(int position) {
            NormalDialog normalDialog = DialogUtil.showDialogTwoBut(LocationActivity.this, "提示", "是否切换城市！", "取消", "确定");
            normalDialog.setOnBtnClickL(new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    EBLog.i("tag", "您点击了取消");
                    normalDialog.cancel();
                }
            }, new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    EBLog.i("tag", "您点击了确认");
                    SpUtils.init(Constants.SPREF.FILE_APP_NAME).put(Constants.SPREF.AREA_NAME, locationRespsList.get(position).getAreaName());
                    SpUtils.init(Constants.SPREF.FILE_APP_NAME).put(Constants.SPREF.AREA_CODE, String.valueOf(locationRespsList.get(position).getAreaCode()));
                    normalDialog.dismiss();
                    EventBus.getDefault().post(new MessageEvent<String>("home_location"));
                    LocationActivity.this.finish();
                }
            });
            EBLog.i("tag", "您点击了第" + position + "条");
        }
    };

    @OnClick({
            R.id.home_location_switch,
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_location_switch:
                Gps gps = LocationUtil.getGps();
                SpUtils.init(Constants.SPREF.FILE_APP_NAME).put(Constants.SPREF.AREA_NAME, gps.getCity());
                SpUtils.init(Constants.SPREF.FILE_APP_NAME).put(Constants.SPREF.AREA_CODE, gps.getAdCode());
                EventBus.getDefault().post(new MessageEvent<String>("home_location"));
                LocationActivity.this.finish();
                break;
//            case R.id.iv_top_back:
//                finish();
//                break;
            default:
                break;
        }
    }

    @Override
    public void leftImageClick() {
        finish();

    }

    @Override
    public void rightImageClick() {

    }

    @Override
    public void alignRightLeftImageClick() {

    }
}
