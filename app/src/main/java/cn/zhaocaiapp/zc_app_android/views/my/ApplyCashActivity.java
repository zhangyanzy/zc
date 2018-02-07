package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.text.BoringLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.CommonResp;
import cn.zhaocaiapp.zc_app_android.bean.response.my.AccountResp;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.listener.OnBtnClickL;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.widget.NormalDialog;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.AppUtil;
import cn.zhaocaiapp.zc_app_android.util.DialogUtil;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.KeyBoardUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

/**
 * Created by ASUS on 2017/11/8.
 */

public class ApplyCashActivity extends BaseActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
    @BindView(R.id.tv_top_title)
    TextView tv_top_title;
    @BindView(R.id.tv_balance)
    TextView tv_balance;
    @BindView(R.id.edit_apply_cash)
    EditText edit_apply_cash;
    @BindView(R.id.tv_withdraw_all)
    TextView tv_withdraw_all;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.withdraw_wechat)
    RadioButton withdraw_wechat;
    @BindView(R.id.withdraw_ali)
    RadioButton withdraw_ali;
    @BindView(R.id.withdraw_bank)
    RadioButton withdraw_bank;

    private String balance;
    private int type = -1;//提现方式    0 支付宝  1 微信   2 银行卡
    private AccountResp accountResp;
    private String amount; // 提现金额
    private UMShareAPI umShareAPI;

    private static final String TAG = "申请提现";

    @Override
    public int getContentViewResId() {
        return R.layout.layout_applycash_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        AppUtil.cancelAllAuth(this);

        umShareAPI = ZcApplication.getUMShareAPI();
        balance = getIntent().getStringExtra("balance");
        getAccount();
    }

    private void getAccount() {
        HttpUtil.get(Constants.URL.GET_ACCOUNT_INFO).subscribe(new BaseResponseObserver<AccountResp>() {

            @Override
            public void success(AccountResp accountResp) {
                EBLog.i(TAG, accountResp.toString());
                ApplyCashActivity.this.accountResp = accountResp;
                showInfo();
            }

            @Override
            public void error(Response<AccountResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(ApplyCashActivity.this, response.getDesc());
            }
        });
    }

    private void showInfo() {
        tv_balance.setText(balance);

        if (accountResp.getWechatIs())
            withdraw_wechat.setText(accountResp.getWechatNo());
        if (accountResp.getAlipayIs())
            withdraw_ali.setText(accountResp.getAlipayNo());
        if (accountResp.getBankIs())
            withdraw_bank.setText(accountResp.getBankCard());
    }

    private void doWithdraw() {
        Map<String, String> map = new HashMap<>();
        map.put("type", type + "");
        map.put("amount", amount);

        HttpUtil.post(Constants.URL.DO_WITHDRAW, map).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                ToastUtil.makeText(ApplyCashActivity.this, getString(R.string.withdraw_success));
            }

            @Override
            public void error(Response<CommonResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(ApplyCashActivity.this, response.getDesc());
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KeyBoardUtils.closeKeybord(tv_submit, this);
        return super.onTouchEvent(event);
    }

    @OnClick({R.id.iv_top_back, R.id.iv_top_menu, R.id.tv_withdraw_all, R.id.tv_submit, R.id.withdraw_wechat, R.id.withdraw_ali, R.id.withdraw_bank})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.iv_top_menu: // 收支明细
                openActivity(IncomeActivity.class);
                break;
            case R.id.tv_withdraw_all: // 全部提现
                edit_apply_cash.setText(tv_balance.getText().toString());
                break;
            case R.id.tv_submit:
                BigDecimal money = new BigDecimal(edit_apply_cash.getText().toString());
                if (money.compareTo(new BigDecimal(20)) == -1) {
                    ToastUtil.makeText(ApplyCashActivity.this, getString(R.string.withdraw_limit));
                } else if (type == -1) {
                    ToastUtil.makeText(ApplyCashActivity.this, getString(R.string.withdraw_type));
                } else {
                    amount = GeneralUtils.getBigDecimalToTwo(money);
                    doWithdraw();
                }
                break;
            case R.id.withdraw_wechat:
                if (accountResp.getWechatIs()) {
                    type = 1;
                    withdraw_wechat.setChecked(true);
                    ToastUtil.makeText(ApplyCashActivity.this, "微信提现");
                    withdraw_ali.setChecked(false);
                    withdraw_bank.setChecked(false);
                } else {
                    showDialog();
                }
                break;
            case R.id.withdraw_ali:
                if (accountResp.getAlipayIs()) {
                    type = 0;
                    withdraw_ali.setChecked(true);
                    ToastUtil.makeText(ApplyCashActivity.this, "支付宝提现");
                    withdraw_wechat.setChecked(false);
                    withdraw_bank.setChecked(false);
                } else {
                    showDialog();
                }
                break;
            case R.id.withdraw_bank:
                if (accountResp.getBankIs()) {
                    type = 2;
                    withdraw_bank.setChecked(true);
                    ToastUtil.makeText(ApplyCashActivity.this, "银行卡提现");
                    withdraw_wechat.setChecked(false);
                    withdraw_ali.setChecked(false);
                } else {
                    showDialog();
                }
                break;
        }
    }

    private void showDialog() {
        NormalDialog dialog = DialogUtil.showDialogTwoBut(this, null,
                "该账号还未绑定， 请先绑定", "取消", "确定");
        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        openActivity(ManageAccountActivity.class);
                        dialog.dismiss();
                    }
                });

    }

}
