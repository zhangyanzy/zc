package cn.zhaocaiapp.zc_app_android.views.commerclal;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.jph.takephoto.model.TResult;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.base.BaseCompatActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.request.commerclal.ApproveRequest;
import cn.zhaocaiapp.zc_app_android.bean.request.commerclal.PhotoImages;
import cn.zhaocaiapp.zc_app_android.bean.response.Entity;
import cn.zhaocaiapp.zc_app_android.bean.response.common.CommonResp;
import cn.zhaocaiapp.zc_app_android.bean.response.home.LocationResp;
import cn.zhaocaiapp.zc_app_android.bean.response.my.IsVia;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.listener.IEditTextChangeListener;
import cn.zhaocaiapp.zc_app_android.capabilities.takephoto.PhotoHelper;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.databinding.ActivityMerchantCaBinding;
import cn.zhaocaiapp.zc_app_android.util.FileUtil;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.PhotoPickerUtil;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtils;
import cn.zhaocaiapp.zc_app_android.util.WorkSizeCheckUtil;
import cn.zhaocaiapp.zc_app_android.views.activity.HomeAddressActivity;
import cn.zhaocaiapp.zc_app_android.views.activity.PersonalDataActivity;
import cn.zhaocaiapp.zc_app_android.widget.NavigationTopBar;

public class MerchantCAActivity extends BaseCompatActivity implements NavigationTopBar.NavigationTopBarClickListener {

    private ActivityMerchantCaBinding binding;

    private NavigationTopBar mNavigationTopBar;

    private long capCode;//商户省代码
    private String capName;//商户省名称
    private long cacCode;//商户市代码
    private String cacName;//商户市名称
    private long caaCode;//商户区代码
    private String caaName;//商户区名称
    private String homeAddressDetail;//商户具体地址
    private String address;

    private String mName;
    private String mPhone;
    private PhotoHelper photoHelper;

    private List<LocationResp> provinces;
    private List<List<LocationResp>> citys;
    private List<List<List<LocationResp>>> towns;
    private OptionsPickerView optionsPickerView;

    private int addressType;//0商户地址
    private ApproveRequest request;

    private String mAliLOGO;
    private String mLOGO;
    private String mALICover;
    private String mCover;

    private String imageUrl;
    private IsVia mMerchantInfo;

    private int flag;//点击时间图片类型  1（营业执照） 2（商家LOGO）


    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_merchant_ca);
        binding.setPresenter(new Presenter());
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        //初始化城市列表
        citys = new ArrayList<>();
        towns = new ArrayList<>();
        getAreasList();
        photoHelper = PhotoHelper.of(binding.merchantRoot, false);
        mMerchantInfo = new IsVia();
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            mMerchantInfo = (IsVia) bundle.getSerializable("mIsVia");
        }
        if (mMerchantInfo != null) {
            if (mMerchantInfo.getAuditStatus() == 3) {
                showNoVla();
            }
        }
    }


    private void showNoVla() {
        binding.merchantName.setText(mMerchantInfo.getName());
        binding.merchantPhoneNum.setText(mMerchantInfo.getPhone());
        binding.chooseAddressMerchant.setText(mMerchantInfo.getProvinceName() + mMerchantInfo.getCityName() + mMerchantInfo.getAreaName());
        binding.detailAddress.setText(mMerchantInfo.getAddressDetail());
        Glide.with(this).load(mMerchantInfo.getLicense()).into(binding.businessLicenseImage);
        Glide.with(this).load(mMerchantInfo.getLogo()).into(binding.businessLogo);
        binding.errorMessage.setVisibility(View.VISIBLE);
        binding.errorMessage.setText(mMerchantInfo.getAuditMemo());
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
        optionsPickerView = new OptionsPickerView.Builder(MerchantCAActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                LocationResp province = provinces.get(options1);
                LocationResp city = citys.get(options1).get(options2);
                LocationResp town = towns.get(options1).get(options2).get(options3);

                capCode = province.getAreaCode();
                capName = province.getAreaName();

                cacCode = city.getAreaCode();
                cacName = city.getAreaName();

                caaCode = town.getAreaCode();
                caaName = town.getAreaName();
                address = capName + cacName + caaName + "";
                binding.chooseAddressMerchant.setText(address);

            }
        }).setTitleText("城市选择")
                .build();
        if (GeneralUtils.isNotNullOrZeroSize(provinces) && GeneralUtils.isNotNullOrZeroSize(citys) && GeneralUtils.isNotNullOrZeroSize(towns))
            optionsPickerView.setPicker(provinces, citys, towns);
    }


    //获取认证参数
    private void approveRequest() {
        request = new ApproveRequest();
        request.setPhone(binding.merchantPhoneNum.getText().toString());
        request.setName(binding.merchantName.getText().toString());
        request.setProvinceName(capName);
        request.setProvinceCode(String.valueOf(capCode));
        request.setAreaName(caaName);
        request.setAreaCode(String.valueOf(caaCode));
        request.setCityCode(String.valueOf(cacCode));
        request.setCityName(cacName);
        request.setAddressDetail(binding.detailAddress.getText().toString());
        request.setLogo(mAliLOGO);
        request.setLicense(mALICover);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        mNavigationTopBar = findViewById(R.id.merchant_top);
        mNavigationTopBar.setLeftImageResource(R.mipmap.finish_icon);
        mNavigationTopBar.setCenterTitleText("商户认证");
        mNavigationTopBar.setCenterTitleTextColor(R.color.colorBlack);
        mNavigationTopBar.setNavigationTopBarClickListener(this);
    }

    @Override
    protected void initListeners() {
//        WorkSizeCheckUtil.textChangeListener textChangeListener = new WorkSizeCheckUtil.textChangeListener(binding.merchantCaSubmit);
//        textChangeListener.addAllEditText(binding.merchantName, binding.merchantPhoneNum);
//        WorkSizeCheckUtil.setChangeListener(new IEditTextChangeListener() {
//            @Override
//            public void textChange(boolean isHasContent) {
//                if (isHasContent) {
//                    binding.merchantCaSubmit.setBackgroundResource(R.color.textButton);
//                }
//            }
//        });
    }


    //提交商户认证
    private void merchantCA() {
        if (request != null) {
            HashMap<String, String> params = new HashMap<>();
            params.put("name", request.getName());
            params.put("logo", request.getLogo());
            params.put("phone", request.getPhone());
            params.put("provinceCode", request.getProvinceCode());
            params.put("provinceName", request.getProvinceName());
            params.put("cityCode", request.getCityCode());
            params.put("cityName", request.getCityName());
            params.put("areaCode", request.getAreaCode());
            params.put("areaName", request.getAreaName());
            params.put("addressDetail", request.getAddressDetail());
            params.put("license", request.getLicense());
            HttpUtil.post(Constants.URL.MERCHANT_INFORMATION_SUBMITTED, params).subscribe(new BaseResponseObserver<String>() {

                @Override
                public void success(String s) {
                    openActivity(CASuccessActivity.class);
                    MerchantCAActivity.this.finish();
                }

                @Override
                public void error(Response<String> response) {
                    ToastUtils.showShortToast(MerchantCAActivity.this, response.getDesc());
                }
            });
        }

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

    private void postPhoto(View view, Context context) {
        manageKeyBord(view, this);
        PhotoPickerUtil.init(context);
        PhotoPickerUtil.setContent("选择照片", new String[]{"拍照", "从相册选择"}, null);
        PhotoPickerUtil.show(photoListener);
    }

    private PhotoPickerUtil.OnItemClickListener photoListener = new PhotoPickerUtil.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            photoHelper.onClick(position, getTakePhoto());
        }
    };


    //上传照片到阿里云
    private void uploadCoverImage(File file) {
        Map<String, String> map = new HashMap<>();
        map.put("postfix", ".jpg");
        map.put("base64Str", FileUtil.fileToStream(file));
        HttpUtil.post(Constants.URL.UPLOAD_IMAGE, map).subscribe(new BaseResponseObserver<String>() {
            @Override
            public void success(String s) {
                if (GeneralUtils.isNotNullOrZeroLenght(s)) {

                    if (flag == 1) {
                        mALICover = s;
                        PictureLoadUtil.loadPicture(MerchantCAActivity.this, mALICover, binding.businessLicenseImage);
                        binding.businessLicense.setVisibility(View.GONE);
                    } else if (flag == 2) {
                        mAliLOGO = s;
                        PictureLoadUtil.loadPicture(MerchantCAActivity.this, mAliLOGO, binding.logoImage);
                        binding.businessLogo.setVisibility(View.GONE);
                    }

                } else {
                    ToastUtil.makeText(MerchantCAActivity.this, getString(R.string.cover_upload_failure));
                }
            }

            @Override
            public void error(Response<String> response) {

            }
        });
    }

    //获取照片成功
    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        if (flag == 1) {
            imageUrl = result.getImage().getCompressPath();
        } else if (flag == 2) {
            imageUrl = result.getImage().getCompressPath();
        }
        File file = new File(imageUrl);
        uploadCoverImage(file);
    }

    //取消获取
    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    //获取失败
    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.merchant_ca_submit:
                    approveRequest();
                    if (TextUtils.isEmpty(request.getName()) && TextUtils.isEmpty(request.getPhone()))
                        ToastUtils.showShortToast(MerchantCAActivity.this, "请完善用户信息");
                    else if (TextUtils.isEmpty(request.getProvinceName()) && TextUtils.isEmpty(request.getCityName())
                            && TextUtils.isEmpty(request.getAreaName()) && TextUtils.isEmpty(request.getAddressDetail()))
                        ToastUtils.showShortToast(MerchantCAActivity.this, "请完善商家地址");
                    else if (TextUtils.isEmpty(request.getLicense()) && TextUtils.isEmpty(request.getLogo()))
                        ToastUtils.showShortToast(MerchantCAActivity.this, "请完善商家营业执照和商家LOGO");
                    else {
                        merchantCA();
                    }
                    break;
                case R.id.choose_address_merchant:
                    optionsPickerView.show();
                    break;
                case R.id.business_license_root://上传营业执照
                    postPhoto(binding.merchantCaSubmit, MerchantCAActivity.this);
                    flag = 1;
                    break;
                case R.id.logo_icon://上传logo
                    postPhoto(binding.merchantCaSubmit, MerchantCAActivity.this);
                    flag = 2;
                    break;
                default:
                    break;
            }
        }
    }
}
