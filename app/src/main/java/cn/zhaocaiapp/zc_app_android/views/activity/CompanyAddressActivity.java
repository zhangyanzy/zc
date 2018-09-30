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
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.databinding.ActivityCompanyAddressBinding;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.widget.NavigationTopBar;

public class CompanyAddressActivity extends BaseCompatActivity implements NavigationTopBar.NavigationTopBarClickListener {

    private ActivityCompanyAddressBinding binding;
    private NavigationTopBar mNavigationTopBar;

    private int addressType; // 0-家庭住址  1-公司地址
    private List<LocationResp> provinces;
    private List<List<LocationResp>> citys;
    private List<List<List<LocationResp>>> towns;
    private OptionsPickerView optionsPickerView;

    private long cpCode;//公司省代码
    private String cpName;//公司省名称
    private long ccCode;//公司市代码
    private String ccName;//公司市名称
    private long ctCode;//公司区代码
    private String ctName;//公司区名称
    private String companyAddress;
    private String companyAddressDetail;

    private String cAddress;


    private String ccn;
    private String cpn;
    private String can;


    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_company_address);
        binding.setPresenter(new Presenter());
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        //初始化城市列表
        citys = new ArrayList<>();
        towns = new ArrayList<>();
        getAreasList();

        Intent intent = getIntent();
        ccn = intent.getStringExtra("ccn");
        cpn = intent.getStringExtra("cpn");
        can = intent.getStringExtra("can");

        companyAddressDetail = intent.getStringExtra("companyDetail");
        binding.enterCompanyAddress.setText(companyAddressDetail);

        cAddress = ccn + cpn + can;
        if (!GeneralUtils.isNullOrZeroLenght(cAddress)) {
            if (cAddress.equals("nullnullnull")){
                binding.companyAddress.setText("");
            }else {
                binding.companyAddress.setText(cAddress);
            }
        }
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        mNavigationTopBar = findViewById(R.id.company_address_top);
        mNavigationTopBar.setLeftImageResource(R.mipmap.finish_icon);
        mNavigationTopBar.setCenterTitleText("公司地址");
        mNavigationTopBar.setCenterTitleTextColor(R.color.colorBlack);
        mNavigationTopBar.setNavigationTopBarClickListener(this);
    }

    @Override
    protected void initListeners() {

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
        optionsPickerView = new OptionsPickerView.Builder(CompanyAddressActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                LocationResp province = provinces.get(options1);
                LocationResp city = citys.get(options1).get(options2);
                LocationResp town = towns.get(options1).get(options2).get(options3);

                if (addressType == 1) {//公司地址
                    cpCode = province.getAreaCode();
                    cpName = province.getAreaName();
                    ccCode = city.getAreaCode();
                    ccName = city.getAreaName();
                    ctCode = town.getAreaCode();
                    ctName = town.getAreaName();
                    binding.companyAddress.setText(cpName + ccName + ctName);
                }
            }
        }).setTitleText("城市选择")
                .build();
        if (GeneralUtils.isNotNullOrZeroSize(provinces) && GeneralUtils.isNotNullOrZeroSize(citys) && GeneralUtils.isNotNullOrZeroSize(towns))
            optionsPickerView.setPicker(provinces, citys, towns);
    }

    /**
     * private long cpCode;//公司省代码
     * private String cpName;//公司省名称
     * private long ccCode;//公司市代码
     * private String ccName;//公司市名称
     * private long ctCode;//公司区代码
     * private String ctName;//公司区名称
     *
     * @return
     */
    private boolean isNotEmpty() {
        companyAddress = binding.companyAddress.getText().toString();
        if (GeneralUtils.isNullOrZeroLenght(companyAddress)) {
            ToastUtil.makeText(CompanyAddressActivity.this, "公司省市不能为空");
            return false;
        }
        return true;
    }

    //校验是否可以上传到服务器
    private boolean isCanUpdate() {
        if (companyAddress.equals(cAddress)) {
            ToastUtil.makeText(CompanyAddressActivity.this, getString(R.string.not_revise));
            return false;
        }
        return true;
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

    private void postCompany() {

        HashMap<String, Object> params = new HashMap<>();
        companyAddressDetail = binding.enterCompanyAddress.getText().toString();

        params.put("companyProvinceCode", cpCode);
        params.put("companyProvinceName", cpName);
        params.put("companyCityCode", ccCode);
        params.put("companyCityName", ccName);
        params.put("companyAreaCode", ctCode);
        params.put("companyAreaName", ctName);
        params.put("companyAddressDetail", companyAddressDetail);
        HttpUtil.put(Constants.URL.REVISE_BASE_INFO, params).subscribe(new BaseResponseObserver<CommonResp>() {
            @Override
            public void success(CommonResp commonResp) {
                ToastUtil.makeText(CompanyAddressActivity.this, commonResp.getDesc());
            }

            @Override
            public void error(Response<CommonResp> response) {
                ToastUtil.makeText(CompanyAddressActivity.this, response.getDesc());
            }
        });

    }


    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.choose_company_address:
                    addressType = 1;
                    optionsPickerView.show();
                    break;
                case R.id.post_company_address:
                    if (isNotEmpty() || isCanUpdate()) postCompany();
                    break;
                default:
                    break;
            }
        }

    }
}
