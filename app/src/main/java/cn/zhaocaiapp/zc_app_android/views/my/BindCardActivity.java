package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.MessageEvent;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.request.my.BindCardReq;
import cn.zhaocaiapp.zc_app_android.bean.response.common.CommonResp;
import cn.zhaocaiapp.zc_app_android.bean.response.my.UserDetailResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

/**
 * Created by Administrator on 2018/2/3.
 */

public class BindCardActivity extends BaseActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_back;
    @BindView(R.id.tv_top_title)
    TextView tv_title;
    @BindView(R.id.iv_top_menu)
    ImageView iv_menu;
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.card_number)
    EditText card_number;
    @BindView(R.id.bank_name)
    TextView bank_name;
    @BindView(R.id.bank_alias_name)
    EditText bank_alias_name;
    @BindView(R.id.tv_submit)
    TextView tv_submit;

    private BindCardReq bindCardReq;
    private OptionsPickerView optionsPickerView;
    private List<String>bankNames = new ArrayList<>();

    private UserDetailResp.RealInfoBean realInfoBean;

    private static final String TAG = "绑定银行卡";

    @Override
    public int getContentViewResId() {
        return R.layout.layout_bind_card_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        iv_menu.setVisibility(View.GONE);
        tv_title.setText("绑定银行卡");

        initData();

        bankNames.add("工商银行");
        bankNames.add("农业银行");
        bankNames.add("中国银行");
        bankNames.add("建设银行");
        bankNames.add("交通银行");
        bankNames.add("邮储银行");
        bankNames.add("招商银行");
        bankNames.add("光大银行");
        bankNames.add("中信银行");
        bankNames.add("华夏银行");
        bankNames.add("浦发银行");
        bankNames.add("民生银行");
        bankNames.add("平安银行");
        bankNames.add("广发银行");
        bankNames.add("兴业银行");
        bankNames.add("北京银行");
        bankNames.add("上海银行");
        setPickView();
    }

    //获取用户基本信息
    private void initData() {
        HttpUtil.get(Constants.URL.GET_USER_INFO_DETAIL).subscribe(new BaseResponseObserver<UserDetailResp>() {

            @Override
            public void success(UserDetailResp userDetailResp) {
                realInfoBean = userDetailResp.getRealInfo();
                user_name.setText(realInfoBean.getName());
            }

            @Override
            public void error(Response<UserDetailResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(BindCardActivity.this, response.getDesc());
            }
        });
    }

    private boolean verify() {
        String name = user_name.getText().toString();
        String cardNumber = card_number.getText().toString();
        String bankName = bank_name.getText().toString();
        String bankAliasName = bank_alias_name.getText().toString();
        bindCardReq = new BindCardReq();
        if (GeneralUtils.isNullOrZeroLenght(name)) {
            ToastUtil.makeText(this, getString(R.string.card_username_not_null));
            return false;
        }
        if (GeneralUtils.isNullOrZeroLenght(cardNumber)) {
            ToastUtil.makeText(this, getString(R.string.cardnumber_not_null));
            return false;
        }
        if (GeneralUtils.isNullOrZeroLenght(bankName)) {
            ToastUtil.makeText(this, getString(R.string.bankname_not_null));
            return false;
        }
        if (GeneralUtils.isNullOrZeroLenght(bankAliasName)) {
            ToastUtil.makeText(this, getString(R.string.bankaliasname_not_null));
            return false;
        }
        bindCardReq.setType(2);
        bindCardReq.setName(name);
        bindCardReq.setBankCard(cardNumber);
        bindCardReq.setBankName(bankName);
        bindCardReq.setBankNameBranch(bankAliasName);

        return true;
    }

    private void setPickView(){
        optionsPickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                bank_name.setText(bankNames.get(options1));
            }
        }).setTitleText("城市选择").build();
        optionsPickerView.setPicker(bankNames);
    }

    private void doBindCard() {
        HttpUtil.put(Constants.URL.BIND_ACCOUNT, bindCardReq).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                EBLog.i(TAG, commonResp.toString());
                ToastUtil.makeText(BindCardActivity.this, commonResp.getDesc());
                finish();
            }

            @Override
            public void error(Response<CommonResp> response) {
                EBLog.i(TAG, response.getCode() + "");
                ToastUtil.makeText(BindCardActivity.this, response.getDesc());
            }
        });
    }

    @OnClick({R.id.iv_top_back, R.id.bank_name, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.bank_name:
                optionsPickerView.show();
                break;
            case R.id.tv_submit:
                if (verify())
                    doBindCard();
                break;
        }
    }
}
