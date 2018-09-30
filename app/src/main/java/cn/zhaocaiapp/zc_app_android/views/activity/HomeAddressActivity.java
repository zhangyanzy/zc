package cn.zhaocaiapp.zc_app_android.views.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bigkoo.pickerview.OptionsPickerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.base.BaseCompatActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.CommonResp;
import cn.zhaocaiapp.zc_app_android.bean.response.home.LocationResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.capabilities.takephoto.PhotoHelper;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.databinding.ActivityHomeAddressBinding;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtils;
import cn.zhaocaiapp.zc_app_android.widget.NavigationTopBar;

public class HomeAddressActivity extends BaseCompatActivity implements NavigationTopBar.NavigationTopBarClickListener {

    private ActivityHomeAddressBinding binding;
    private NavigationTopBar mNavigationTopBar;


    private List<LocationResp> provinces;
    private List<List<LocationResp>> citys;
    private List<List<List<LocationResp>>> towns;
    private OptionsPickerView optionsPickerView;

    private String homeAddress;
    private String homeAddressDetail;
    private long hpCode;//家庭省代码
    private String hpName;//家庭省名称
    private long hcCode;//家庭市代码
    private String hcName;//家庭市名称
    private long htCode;//家庭区代码
    private String htName;//家庭区名称


    private String hAddress;//家庭住址省市区
    private String hDetail;//家庭详细住址


    private int addressType; // 0-家庭住址  1-公司地址

    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_address);
        binding.setPresenter(new Presenter());
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        //初始化城市列表
        citys = new ArrayList<>();
        towns = new ArrayList<>();
        getAreasList();

        //监听点击空白处，隐藏软键盘
//        rootView.setOnTouchListener(onTouchListener);
        Intent intent = getIntent();
        String hpn = intent.getStringExtra("hpn");
        String hcn = intent.getStringExtra("hcn");
        String han = intent.getStringExtra("han");
        String detailAddress = intent.getStringExtra("detailAddress");
        if (hpn != null && hcn != null && han != null) {
            String home = hpn + hcn + han;
            binding.homeAddress.setText(home);
        }
        if (detailAddress != null) {
            binding.enterHomeAddress.setText(detailAddress);
        }
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        mNavigationTopBar = findViewById(R.id.home_address_top);
        mNavigationTopBar.setLeftImageResource(R.mipmap.finish_icon);
        mNavigationTopBar.setCenterTitleText("家庭住址");
        mNavigationTopBar.setCenterTitleTextColor(R.color.colorBlack);
        mNavigationTopBar.setNavigationTopBarClickListener(this);
    }

    //获取省、市、区三级列表
    private void getAreasList() {
        provinces = ZcApplication.getProvinces();
        for (int i = 0; i < provinces.size(); i++) {
            List<LocationResp> cityList = provinces.get(i).getAreaList();//该省的城市列表（第二级）
            List<List<LocationResp>> province_townList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int j = 0; j < cityList.size(); j++) {
                List<LocationResp> city_townList = cityList.get(j).getAreaList();
                province_townList.add(city_townList);
            }
            citys.add(cityList);
            towns.add(province_townList);
        }
        setCityPicker();
    }


    //城市选择器初始化和设置
    private void setCityPicker() {
        optionsPickerView = new OptionsPickerView.Builder(HomeAddressActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                LocationResp province = provinces.get(options1);
                LocationResp city = citys.get(options1).get(options2);
                LocationResp town = towns.get(options1).get(options2).get(options3);

                if (addressType == 0) {//家庭地址
                    hpCode = province.getAreaCode();
                    hpName = province.getAreaName();
                    hcCode = city.getAreaCode();
                    hcName = city.getAreaName();
                    htCode = town.getAreaCode();
                    htName = town.getAreaName();
                    binding.homeAddress.setText(hpName + hcName + htName);

                }
            }
        }).setTitleText("城市选择")
                .build();
        if (GeneralUtils.isNotNullOrZeroSize(provinces) && GeneralUtils.isNotNullOrZeroSize(citys) && GeneralUtils.isNotNullOrZeroSize(towns))
            optionsPickerView.setPicker(provinces, citys, towns);
    }


    private void postHomeAddress() {
        HashMap<String, Object> params = new HashMap<>();
        homeAddressDetail = binding.enterHomeAddress.getText().toString();

        params.put("homeProvinceCode", hpCode);
        params.put("homeProvinceName", hpName);
        params.put("homeCityCode", hcCode);
        params.put("homeCityName", hcName);
        params.put("homeAreaCode", htCode);
        params.put("homeAreaName", htName);
        params.put("homeAddressDetail", homeAddressDetail);
        HttpUtil.put(Constants.URL.REVISE_BASE_INFO, params).subscribe(new BaseResponseObserver<CommonResp>() {
            @Override
            public void success(CommonResp commonResp) {
                ToastUtil.makeText(HomeAddressActivity.this, commonResp.getDesc());
            }

            @Override
            public void error(Response<CommonResp> response) {
                ToastUtil.makeText(HomeAddressActivity.this, response.getDesc());
            }
        });

    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

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

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.choose_home_address:
                    addressType = 0;
                    optionsPickerView.show();
                    break;
                case R.id.post_home_address:
                    if (hpName == null && hcName == null && htName == null) {
                        ToastUtils.showShortToast(HomeAddressActivity.this, "省市区任何一项不的为空");
                    } else {
                        postHomeAddress();
                    }

                    break;
                default:
                    break;
            }
        }

    }
}
