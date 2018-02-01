package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.jph.takephoto.model.TResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseImage;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.MessageEvent;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.CommonResp;
import cn.zhaocaiapp.zc_app_android.bean.response.home.LocationResp;
import cn.zhaocaiapp.zc_app_android.bean.response.my.UserDetailResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.capabilities.takephoto.PhotoHelper;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.FileUtil;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.PhotoPickerUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;

/**
 * Created by Administrator on 2018/1/12.
 */

public class UserInfoFragment extends BaseFragment {
    @BindView(R.id.iv_user_photo)
    ImageView iv_user_photo;
    @BindView(R.id.tv_change_photo)
    TextView tv_change_photo;
    @BindView(R.id.tv_revise_phone)
    TextView tv_revise_phone;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.edit_user_address)
    TextView edit_user_address;
    @BindView(R.id.edit_company_address)
    TextView edit_company_address;
    @BindView(R.id.edit_user_nickname)
    EditText edit_user_nickname;
    @BindView(R.id.edit_user_phone)
    EditText edit_user_phone;
    @BindView(R.id.home_address_detail)
    EditText home_address_detail;
    @BindView(R.id.company_address_detail)
    EditText company_address_detail;

    private View rootView;
    private PhotoHelper photoHelper;

    private List<LocationResp> provinces;
    private List<List<LocationResp>> citys;
    private List<List<List<LocationResp>>> towns;
    private OptionsPickerView optionsPickerView;

    private UserDetailResp.BaseInfoBean baseInfoBean;
    private LocationResp province, city, town;
    //    private String avatar;
    private String nickName;
    private String homeAddress;
    private String companyAddress;
    private String homeAddressDetail;
    private String companyAddressDetail;

    private String hAddress;
    private String hDetail;
    private String cAddress;
    private String cDetail;
    private String name;
    private String imgUrl;

    private boolean isCanUpdate;

    private int addressType; // 0-家庭住址  1-公司地址
    private Map<String, String> params = new HashMap<>();

    private static final String TAG = "个人资料";

    @Override
    public void onStart() {
        super.onStart();
        //注册EventBus消息订阅者
        EventBus.getDefault().register(this);
    }

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_userinfo_fragment, container, false);
        return rootView;
    }

    @Override
    public void init() {
        //初始化城市列表
        citys = new ArrayList<>();
        towns = new ArrayList<>();
        getAreasList();

        //初始化照片选择器设置
        photoHelper = PhotoHelper.of(rootView, true);

        //监听点击空白处，隐藏软键盘
        rootView.setOnTouchListener(onTouchListener);
    }

    //城市选择器初始化和设置
    private void setCityPicker() {
        optionsPickerView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                province = provinces.get(options1);
                city = citys.get(options1).get(options2);
                town = towns.get(options1).get(options2).get(options3);

                if (addressType == 0) {
                    homeAddress = province.getAreaName() + city.getAreaName() + town.getAreaName();
                    edit_user_address.setText(homeAddress);
                }
                if (addressType == 1) {
                    companyAddress = province.getAreaName() + city.getAreaName() + town.getAreaName();
                    edit_company_address.setText(companyAddress);
                }
            }
        }).setTitleText("城市选择")
                .build();
        if (GeneralUtils.isNotNullOrZeroSize(provinces) && GeneralUtils.isNotNullOrZeroSize(citys) && GeneralUtils.isNotNullOrZeroSize(towns))
            optionsPickerView.setPicker(provinces, citys, towns);
    }

    @Override
    public void loadData() {
    }

    //显示用户信息
    private void showUserInfo() {
        name = baseInfoBean.getNickname();
        imgUrl = baseInfoBean.getAvatar();
        hAddress = baseInfoBean.getHomeProvinceName() + baseInfoBean.getHomeCityName() + baseInfoBean.getHomeAreaName();
        hDetail = baseInfoBean.getHomeAddressDetail();
        cAddress = baseInfoBean.getCompanyProvinceName() + baseInfoBean.getCompanyCityName() + baseInfoBean.getCompanyAreaName();
        cDetail = baseInfoBean.getCompanyAddressDetail();

        if (GeneralUtils.isNotNullOrZeroLenght(name))
            edit_user_nickname.setText(name);
        if (GeneralUtils.isNotNullOrZeroLenght(hAddress))
            edit_user_address.setText(hAddress);
        if (GeneralUtils.isNotNullOrZeroLenght(hDetail))
            home_address_detail.setText(hDetail);
        if (GeneralUtils.isNotNullOrZeroLenght(cAddress))
            edit_company_address.setText(cAddress);
        if (GeneralUtils.isNotNullOrZeroLenght(cDetail))
            company_address_detail.setText(cDetail);
        if (GeneralUtils.isNotNullOrZeroLenght(imgUrl))
            BaseImage.getInstance().displayCricleImage(getActivity(), imgUrl, iv_user_photo);
        edit_user_phone.setText(baseInfoBean.getPhone());
    }

    //接收EventBus发送的消息，并处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        if (event.getMessage() instanceof UserDetailResp.BaseInfoBean) {
            baseInfoBean = (UserDetailResp.BaseInfoBean) event.getMessage();
            showUserInfo();
            EBLog.i(TAG, baseInfoBean.toString());
        } else if (event.getMessage() instanceof String) {
            edit_user_phone.setText((String) event.getMessage());
        }
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

    @OnClick({R.id.iv_user_photo, R.id.tv_change_photo, R.id.tv_revise_phone, R.id.tv_submit,
            R.id.edit_user_address, R.id.edit_company_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_photo: //更换头像
            case R.id.tv_change_photo:
                manageKeyBord(tv_submit, getActivity());
                //弹出获取照片选择框
                PhotoPickerUtil.init(getActivity());
                PhotoPickerUtil.setContent("选择照片", new String[]{"拍照", "从相册选择"}, null);
                PhotoPickerUtil.show(listener);
                break;
            case R.id.edit_user_address: //选择地址
                addressType = 0;
                optionsPickerView.show();
                break;
            case R.id.edit_company_address:
                addressType = 1;
                optionsPickerView.show();
                break;
            case R.id.tv_revise_phone: // 更换手机号
                openActivity(ChangePhoneActivity.class);
                break;
            case R.id.tv_submit:
                isCanUpdate();
                if (isCanUpdate) reviceBaseInfo();
                break;
        }
    }

    //判断是否修改内容
    private void isCanUpdate() {
        nickName = edit_user_nickname.getText().toString();
        homeAddress = edit_user_address.getText().toString();
        homeAddressDetail = home_address_detail.getText().toString();
        companyAddress = edit_company_address.getText().toString();
        companyAddressDetail = company_address_detail.getText().toString();

        if (GeneralUtils.isNullOrZeroLenght(nickName)) {
            ToastUtil.makeText(getActivity(), "用户昵称不能为空");
            isCanUpdate = false;
            return;
        } else if (!name.equals(nickName)) {
            params.put("nickname", nickName);
            isCanUpdate = true;
        }
        if (GeneralUtils.isNullOrZeroLenght(homeAddressDetail)){
            ToastUtil.makeText(getActivity(), "家庭详细地址不能为空");
            isCanUpdate = false;
            return;
        }else if (!hAddress.equals(homeAddress)) {
            params.put("homeProvinceCode", province.getAreaCode() + "");
            params.put("homeProvinceName", province.getAreaName());
            params.put("homeCityCode", city.getAreaCode() + "");
            params.put("homeCityName", city.getAreaName());
            params.put("homeAreaCode", town.getAreaCode() + "");
            params.put("homeAreaName", town.getAreaName());
            params.put("homeAddressDetail", homeAddressDetail);
            isCanUpdate = true;
        }else if (!hDetail.equals(homeAddressDetail)) {
            params.put("homeProvinceCode", province.getAreaCode() + "");
            params.put("homeProvinceName", province.getAreaName());
            params.put("homeCityCode", city.getAreaCode() + "");
            params.put("homeCityName", city.getAreaName());
            params.put("homeAreaCode", town.getAreaCode() + "");
            params.put("homeAreaName", town.getAreaName());
            params.put("homeAddressDetail", homeAddressDetail);
            isCanUpdate = true;
        }
        if (GeneralUtils.isNullOrZeroLenght(companyAddressDetail)){
            ToastUtil.makeText(getActivity(), "公司详细地址不能为空");
            isCanUpdate = false;
            return;
        }else if (!cAddress.equals(companyAddress)) {
            params.put("companyProvinceCode", province.getAreaCode() + "");
            params.put("companyProvinceName", province.getAreaName());
            params.put("companyCityCode", city.getAreaCode() + "");
            params.put("companyCityName", city.getAreaName());
            params.put("companyAreaCode", town.getAreaCode() + "");
            params.put("companyAreaName", town.getAreaName());
            params.put("companyAddressDetail", companyAddressDetail);
            isCanUpdate = true;
        }else if (!cDetail.equals(companyAddressDetail)) {
            params.put("companyProvinceCode", province.getAreaCode() + "");
            params.put("companyProvinceName", province.getAreaName());
            params.put("companyCityCode", city.getAreaCode() + "");
            params.put("companyCityName", city.getAreaName());
            params.put("companyAreaCode", town.getAreaCode() + "");
            params.put("companyAreaName", town.getAreaName());
            params.put("companyAddressDetail", companyAddressDetail);
            isCanUpdate = true;
        }
    }

    private PhotoPickerUtil.OnItemClickListener listener = new PhotoPickerUtil.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            photoHelper.onClick(position, getTakePhoto());
        }
    };

    private void reviceBaseInfo() {
        HttpUtil.put(Constants.URL.REVISE_BASE_INFO, params).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                ToastUtil.makeText(getActivity(), commonResp.getDesc());
                isCanUpdate = false;
            }

            @Override
            public void error(Response<CommonResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    private void uploadImage(File file) {
        Map<String, String> map = new HashMap<>();
        map.put("postfix", ".jpg");
        map.put("base64Str", FileUtil.fileToStream(file));

        HttpUtil.post(Constants.URL.UPLOAD_IMAGE, map).subscribe(new BaseResponseObserver<String>() {

            @Override
            public void success(String s) {
                EBLog.i(TAG, s);
                if (!imgUrl.equals(s))
                    params.put("avatar", s);
                isCanUpdate = true;
            }

            @Override
            public void error(Response<String> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String imgUrl = result.getImage().getCompressPath();
        BaseImage.getInstance().displayImage(getActivity(), imgUrl, iv_user_photo);

        File file = new File(imgUrl);
        uploadImage(file);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
