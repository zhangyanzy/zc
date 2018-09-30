package cn.zhaocaiapp.zc_app_android.views.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseCompatActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.CommonResp;
import cn.zhaocaiapp.zc_app_android.bean.response.common.RelationListResp;
import cn.zhaocaiapp.zc_app_android.bean.response.merchant.RealNameEntity;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.databinding.ActivityRealNameBinding;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtils;
import cn.zhaocaiapp.zc_app_android.views.common.PrivacyActivity;
import cn.zhaocaiapp.zc_app_android.widget.NavigationTopBar;

public class RealNameActivity extends BaseCompatActivity implements NavigationTopBar.NavigationTopBarClickListener {

    private ActivityRealNameBinding binding;
    private NavigationTopBar mNavigationTopBar;
    private static final String TAG = "实名认证";
    private List<String> names;
    private OptionsPickerView optionsPickerView;


    private long mJobCode;//职业code
    private long mEduCode;//学历code
    private List<RelationListResp.CommInfo> qualification;//学历列表
    private List<RelationListResp.CommInfo> occupation;//职业列表

    private RealNameEntity mRealName;

    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_real_name);
        binding.setPresenter(new Presenter());
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        mNavigationTopBar = findViewById(R.id.real_name_top);
        mNavigationTopBar.setLeftImageResource(R.mipmap.finish_icon);
        mNavigationTopBar.setCenterTitleText("实名认证");
        mNavigationTopBar.setCenterTitleTextColor(R.color.colorBlack);
        mNavigationTopBar.setNavigationTopBarClickListener(this);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        getRealtion();
        names = new ArrayList<>();
        getUserRealNameInfo();
    }


    private void getUserRealNameInfo() {
        HttpUtil.get(Constants.URL.REAL_NAME).subscribe(new BaseResponseObserver<RealNameEntity>() {
            @Override
            public void success(RealNameEntity realNameEntity) {
                if (realNameEntity != null) {
                    mRealName = realNameEntity;
                    showRealName();
                }
            }

            @Override
            public void error(Response<RealNameEntity> response) {

            }
        });
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

    private void showRealName() {
        binding.enterName.setText(mRealName.getName());
        binding.enterIdCardNum.setText(mRealName.getIdCard());
        binding.chooseEducation.setText(mRealName.getEducational());
        binding.chooseOccupation.setText(mRealName.getJob());

        if (mRealName.getRealInfoAuditStatus() == 2) {
            binding.enterName.setCursorVisible(false);
            binding.enterName.setFocusable(false);
            binding.enterName.setFocusableInTouchMode(false);
            binding.enterIdCardNum.setCursorVisible(false);
            binding.enterIdCardNum.setFocusable(false);
            binding.enterIdCardNum.setFocusableInTouchMode(false);
            binding.chooseOccupation.setEnabled(false);
            binding.chooseEducation.setEnabled(false);
            binding.realNameBtn.setClickable(false);
        } else {
            binding.enterName.setCursorVisible(true);
            binding.enterName.setFocusable(true);
            binding.enterName.setFocusableInTouchMode(true);
            binding.enterIdCardNum.setCursorVisible(true);
            binding.enterIdCardNum.setFocusable(true);
            binding.enterIdCardNum.setFocusableInTouchMode(true);
            binding.chooseOccupation.setEnabled(true);
            binding.chooseEducation.setEnabled(true);
        }
    }

    //获取职业和学历列表
    private void getRealtion() {
        HttpUtil.get(Constants.URL.GET_RELATION_LIST).subscribe(new BaseResponseObserver<RelationListResp>() {

            @Override
            public void success(RelationListResp relationListResp) {
                if (relationListResp != null) {
                    qualification = relationListResp.getQualification();
                    occupation = relationListResp.getOccupation();
                }
            }

            @Override
            public void error(Response<RelationListResp> response) {
                ToastUtil.makeText(RealNameActivity.this, response.getDesc());
            }
        });
    }

    //实名认证提交
    private void postRealNameInfo() {
        if (binding.checkAgreement.isChecked()) {
            String name = binding.enterName.getText().toString();
            String id_card = binding.enterIdCardNum.getText().toString();
            if (!GeneralUtils.isNullOrZeroLenght(name) || !GeneralUtils.isNullOrZeroLenght(id_card)) {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("idCard", id_card);
                params.put("jobCode", mJobCode + "");
                params.put("educationalCode", mEduCode + "");
                HttpUtil.put(Constants.URL.REAL_NANE_DATA, params).subscribe(new BaseResponseObserver<CommonResp>() {

                    @Override
                    public void success(CommonResp commonResp) {
                        if (commonResp != null) {
                            ToastUtils.showShortToast(RealNameActivity.this, commonResp.getDesc());
                            binding.realNameBtn.setClickable(false);
                        }
                    }

                    @Override
                    public void error(Response<CommonResp> response) {
                        ToastUtils.showShortToast(RealNameActivity.this, response.getDesc());
                    }
                });
            } else {
                ToastUtils.showShortToast(this, "姓名或者身份证号不能为空");
            }
        } else {
            ToastUtil.makeText(this, "请阅读《隐私协议》");
        }

    }

    private void setItemContent(String title, List<RelationListResp.CommInfo> items, TextView view) {
        names.clear();
        for (RelationListResp.CommInfo commInfo : items) {
            names.add(commInfo.getDictValue1());
        }
        optionsPickerView = new OptionsPickerView.Builder(RealNameActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                view.setText(names.get(options1));
                if (view.getId() == R.id.choose_education)
                    mEduCode = items.get(options1).getDictCode();
                if (view.getId() == R.id.choose_occupation)
                    mJobCode = items.get(options1).getDictCode();
            }
        }).setTitleText(title)
                .setSelectOptions(0)
                .build();
        optionsPickerView.setPicker(names);
        optionsPickerView.show();
    }

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_agreement:
                    openActivity(PrivacyActivity.class);
                    break;
                case R.id.check_agreement:
                    break;
                case R.id.choose_occupation:
                    setItemContent("职业选择", occupation, binding.chooseOccupation);
                    break;
                case R.id.choose_education:
                    setItemContent("学历选择", qualification, binding.chooseEducation);
                    break;
                case R.id.real_name_btn:
                    postRealNameInfo();
                    break;
                default:
                    break;
            }
        }
    }
}
