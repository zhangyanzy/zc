package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.request.my.BindCardReq;
import cn.zhaocaiapp.zc_app_android.bean.response.common.CommonResp;
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
    EditText user_name;
    @BindView(R.id.card_number)
    EditText card_number;
    @BindView(R.id.bank_name)
    TextView bank_name;
    @BindView(R.id.bank_alias_name)
    EditText bank_alias_name;
    @BindView(R.id.tv_submit)
    TextView tv_submit;

    private BindCardReq bindCardReq;

    private static final String TAG = "绑定银行卡";

    @Override
    public int getContentViewResId() {
        return R.layout.layout_bind_card_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        iv_menu.setVisibility(View.GONE);
        tv_title.setText("绑定银行卡");
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

    private void doBindCard() {
        HttpUtil.put(Constants.URL.BIND_CARD, bindCardReq).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                EBLog.i(TAG, commonResp.toString());
                ToastUtil.makeText(BindCardActivity.this, commonResp.getDesc());
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

                break;
            case R.id.tv_submit:
                if (verify())
                    doBindCard();
                break;
        }
    }
}
