package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.bean.MessageEvent;
import cn.zhaocaiapp.zc_app_android.bean.response.home.LocationResp;
import cn.zhaocaiapp.zc_app_android.bean.response.my.UserDetailResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.capabilities.takephoto.PhotoHelper;
import cn.zhaocaiapp.zc_app_android.util.AreaUtil;
import cn.zhaocaiapp.zc_app_android.util.PhotoPickerUtil;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;

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

    private View rootView;
    private PhotoHelper photoHelper;

    private List<LocationResp> provinces;
    private List<List<LocationResp>> citys;
    private List<List<List<LocationResp>>> towns;
    private OptionsPickerView optionsPickerView;

    private UserDetailResp.BaseInfoBean baseInfoBean;

    private static final String TAG = "个人资料";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        photoHelper = PhotoHelper.of(rootView);

        citys = new ArrayList<>();
        towns = new ArrayList<>();
        getAreasList();
        optionsPickerView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

            }
        }).setTitleText("城市选择")
                .build();
        optionsPickerView.setPicker(provinces, citys, towns);

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getActivity().onTouchEvent(event);
                return true;
            }
        });
    }

    @Override
    public void loadData() {

    }

    //接收EventBus发送的消息，并处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent<UserDetailResp.BaseInfoBean> event) {
         baseInfoBean = event.getMessage();
        EBLog.i(TAG, baseInfoBean.toString());
    }

    private void getAreasList() {
        provinces = AreaUtil.initArea(getActivity());
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
    }

    @OnClick({R.id.iv_user_photo, R.id.tv_change_photo, R.id.tv_revise_phone, R.id.tv_submit,
            R.id.edit_user_address, R.id.edit_company_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_photo:
            case R.id.tv_change_photo:
                manageKeyBord(tv_submit, getActivity());
                //弹出获取照片选择框
                PhotoPickerUtil.init(getActivity());
                PhotoPickerUtil.setContent("选择照片", new String[]{"拍照", "从相册选择"}, null);
                PhotoPickerUtil.show(listener);
                break;
            case R.id.edit_user_address:
            case R.id.edit_company_address:
                optionsPickerView.show();
                break;
            case R.id.tv_revise_phone:

                break;
            case R.id.tv_submit:

                break;
        }
    }

    private PhotoPickerUtil.OnItemClickListener listener = new PhotoPickerUtil.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            photoHelper.onClick(position, getTakePhoto());
        }
    };

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String imgUrl = result.getImage().getCompressPath();
        PictureLoadUtil.loadPicture(getActivity(), imgUrl, iv_user_photo);
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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
