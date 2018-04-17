package cn.zhaocaiapp.zc_app_android.views.my;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaExtractor;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import java.nio.file.attribute.PosixFilePermission;
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
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.widget.NormalInputDialog;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.AppUtil;
import cn.zhaocaiapp.zc_app_android.util.DialogUtil;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.KeyBoardUtils;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
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
    TextView withdraw_wechat;
    @BindView(R.id.withdraw_ali)
    TextView withdraw_ali;
    @BindView(R.id.withdraw_bank)
    TextView withdraw_bank;
    @BindView(R.id.tv_withdraw_limit)
    TextView tv_withdraw_limit;

    private BigDecimal balance;
    private int type = -1;//提现方式    0 支付宝  1 微信   2 银行卡
    private AccountResp account;
    private String amount; // 提现金额
    private int selectType = -1;

    private static final int REQUEST_CODE = 4001;

    private NormalInputDialog inputDialog;
    private UMShareAPI umShareAPI;

    private static final String TAG = "申请提现";

    @Override
    public int getContentViewResId() {
        return R.layout.layout_applycash_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        umShareAPI = ZcApplication.getUMShareAPI();
        balance = new BigDecimal(getIntent().getStringExtra("balance"));

        tv_top_title.setText("提现");
        iv_top_menu.setImageResource(R.mipmap.trade_detail);

        getAccount();

        //初始化输入框弹窗
        inputDialog = new NormalInputDialog(this);
        inputDialog.setOnDialogClickListener(inputListener);
    }

    //获取用户账户信息
    private void getAccount() {
        HttpUtil.get(Constants.URL.GET_ACCOUNT_INFO).subscribe(new BaseResponseObserver<AccountResp>() {

            @Override
            public void success(AccountResp accountResp) {
                EBLog.i(TAG, accountResp.toString());
                account = accountResp;
                showInfo();
            }

            @Override
            public void error(Response<AccountResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(ApplyCashActivity.this, response.getDesc());
            }
        });
    }

    //账户信息展示
    private void showInfo() {
        tv_balance.setText(balance.toString());
        if (account.getWechatIs())
            withdraw_wechat.setText("已绑定");
        else
            withdraw_wechat.setText("未绑定");
        if (account.getAlipayIs())
            withdraw_ali.setText("已绑定");
        else
            withdraw_ali.setText("未绑定");
        if (account.getBankIs())
            withdraw_bank.setText("已绑定");
        else
            withdraw_bank.setText("未绑定");
        setSelect();
    }

    //申请提现
    private void doWithdraw() {
        Map<String, String> map = new HashMap<>();
        map.put("type", selectType + "");
        map.put("amount", amount);

        HttpUtil.post(Constants.URL.DO_WITHDRAW, map).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                ToastUtil.makeText(ApplyCashActivity.this, getString(R.string.withdraw_success));
                balance = balance.subtract(new BigDecimal(amount));
                tv_balance.setText(balance.toString());
                edit_apply_cash.setText("");
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
                verifyAmount();
                break;
            case R.id.withdraw_wechat:
                type = 1;
                if (account.getWechatIs()) {
                    setDrawable(type);
                } else {
                    showDialog();
                }
                break;
            case R.id.withdraw_ali:
                type = 0;
                if (account.getAlipayIs()) {
                    setDrawable(type);
                } else {
                    showDialog();
                }
                break;
            case R.id.withdraw_bank:
                type = 2;
                if (account.getBankIs()) {
                    setDrawable(type);
                } else {
                    showDialog();
                }
                break;
        }
    }

    //校验是否达到提现条件
    private void verifyAmount() {
        int limit = 20;
        boolean isCertification = (boolean) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.IS_CERTIFICATION, false);
        if (GeneralUtils.isNotNullOrZeroLenght(edit_apply_cash.getText().toString())) {
            BigDecimal money = new BigDecimal(edit_apply_cash.getText().toString());
            if (selectType == 2) {
                limit = 200;
            }
            if (selectType == -1) {
                ToastUtil.makeText(ApplyCashActivity.this, getString(R.string.withdraw_type));
            } else if (money.compareTo(new BigDecimal(limit)) == -1) {
                ToastUtil.makeText(ApplyCashActivity.this, String.format(getString(R.string.withdraw_limit), String.valueOf(limit)));
            }else if (!isCertification) {
                ToastUtil.makeText(ApplyCashActivity.this, getString(R.string.not_certification));
            } else {
                amount = GeneralUtils.getBigDecimalToTwo(money);
                inputDialog.show();
            }
        } else
            ToastUtil.makeText(ApplyCashActivity.this, getString(R.string.input_cash));
    }

    private NormalInputDialog.OnDialogClickListener inputListener = new NormalInputDialog.OnDialogClickListener() {
        @Override
        public void onDialogClick(int resId, @Nullable String content) {
            if (resId == R.id.tv_submit)
                if (GeneralUtils.isNullOrZeroLenght(content)) {
                    ToastUtil.makeText(ApplyCashActivity.this, getString(R.string.input_pass_word));
                }else {
                    verifyPass(content);
                }
            else inputDialog.dismiss();
        }
    };

    //校验密码
    private void verifyPass(String content) {
        Map<String, String> params = new HashMap<>();
        params.put("password", content);
        HttpUtil.post(Constants.URL.WITHDRAW_VERIFU_PASS, params).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                EBLog.i(TAG, commonResp.getDesc());
                inputDialog.dismiss();
                doWithdraw();
            }

            @Override
            public void error(Response<CommonResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(ApplyCashActivity.this, response.getDesc());
            }
        });
    }

    //设置支付方式的选中状态
    private void setSelect() {
        switch (type) {
            case 0:
                if (account.getAlipayIs()) selectType = 0;
                break;
            case 1:
                if (account.getWechatIs()) selectType = 1;
                break;
            case 2:
                if (account.getBankIs()) selectType = 2;
                break;
            default:
                return;
        }
        setDrawable(selectType);
    }

    private void setDrawable(int position) {
        Drawable selDrawable = getResources().getDrawable(R.mipmap.checked);
        Drawable unDrawable = getResources().getDrawable(R.mipmap.unchecked);
        selDrawable.setBounds(0, 0, 48, 48);
        unDrawable.setBounds(0, 0, 48, 48);
        switch (position) {
            case 0:
                withdraw_ali.setCompoundDrawables(null, null, selDrawable, null);
                selectType = 0;

                withdraw_wechat.setCompoundDrawables(null, null, unDrawable, null);
                withdraw_bank.setCompoundDrawables(null, null, unDrawable, null);
                break;
            case 1:
                withdraw_wechat.setCompoundDrawables(null, null, selDrawable, null);
                selectType = 1;

                withdraw_ali.setCompoundDrawables(null, null, unDrawable, null);
                withdraw_bank.setCompoundDrawables(null, null, unDrawable, null);
                break;
            case 2:
                withdraw_bank.setCompoundDrawables(null, null, selDrawable, null);
                selectType = 2;

                withdraw_wechat.setCompoundDrawables(null, null, unDrawable, null);
                withdraw_ali.setCompoundDrawables(null, null, unDrawable, null);
                tv_withdraw_limit.setText("提现金额200元起");
                ToastUtil.makeTextLong(this, "选择银行卡提现时，手续费会从账户内直接扣除，请谨慎选择");
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
                        openActivityForResult(ManageAccountActivity.class, REQUEST_CODE);
                        dialog.dismiss();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            getAccount();
        }
    }
}
