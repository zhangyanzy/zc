package cn.zhaocaiapp.zc_app_android.views.activity;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.umeng.socialize.UMShareAPI;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.base.BaseCompatActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.CommonResp;
import cn.zhaocaiapp.zc_app_android.bean.response.login.ObtainCodeResp;
import cn.zhaocaiapp.zc_app_android.bean.response.my.AccountResp;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.listener.OnBtnClickL;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.widget.NormalDialog;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.widget.NormalInputDialog;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.databinding.ActivityMyInComeBinding;
import cn.zhaocaiapp.zc_app_android.util.ButtomOnClickUtils;
import cn.zhaocaiapp.zc_app_android.util.DialogUtil;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.KeyBoardUtils;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtils;
import cn.zhaocaiapp.zc_app_android.views.my.ApplyCashActivity;
import cn.zhaocaiapp.zc_app_android.views.my.IncomeActivity;
import cn.zhaocaiapp.zc_app_android.views.my.ManageAccountActivity;
import cn.zhaocaiapp.zc_app_android.widget.NavigationTopBar;

public class MyInComeActivity extends BaseCompatActivity implements NavigationTopBar.NavigationTopBarClickListener {

    private ActivityMyInComeBinding binding;
    private NavigationTopBar mNavigationTopBar;

    private BigDecimal mBalance;//余额
    private String mGrossIncomeAmount;//总收入
    private int type = -1;//提现方式    0 支付宝  1 微信   2 银行卡
    private AccountResp account;
    private String amount; // 提现金额
    private int selectType = -1;


    private static final int REQUEST_CODE = 4001;

    private NormalInputDialog inputDialog;
    private UMShareAPI umShareAPI;

    private static final String TAG = "申请提现";


    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_in_come);
        binding.setPresenter(new Presenter());
        umShareAPI = ZcApplication.getUMShareAPI();
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mBalance = new BigDecimal(intent.getStringExtra("balance"));
        mGrossIncomeAmount = intent.getStringExtra("grossIncomeAmount");
        getAccount();

        //初始化输入框弹窗
        inputDialog = new NormalInputDialog(this);
        inputDialog.setTitle("提现申请");
        inputDialog.setOnDialogClickListener(inputListener);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        mNavigationTopBar = findViewById(R.id.my_income_bar);
        mNavigationTopBar.setLeftImageResource(R.mipmap.finish_icon);
        mNavigationTopBar.setCenterTitleText(R.string.my_income);
        mNavigationTopBar.setCenterTitleTextColor(R.color.colorBlack);
        mNavigationTopBar.setRightImageResource(R.mipmap.detail_info_icon);
        mNavigationTopBar.setNavigationTopBarClickListener(this);
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
        openActivity(IncomeActivity.class);
    }

    @Override
    public void alignRightLeftImageClick() {

    }

    //校验是否达到提现条件
    private void verifyAmount() {
        int limit = 30;
        boolean isCertification = (boolean) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.IS_CERTIFICATION, false);

        if (GeneralUtils.isNotNullOrZeroLenght(binding.withdrawDeposit.getText().toString())) {
            BigDecimal money = new BigDecimal(binding.withdrawDeposit.getText().toString());
            if (account.getCashAmount() == 1) {
                amount = GeneralUtils.getBigDecimalToTwo(money);
                inputDialog.show();
            } else {
                if (selectType == 2) {
                    limit = 200;
                }
                if (selectType == -1) {
                    ToastUtil.makeText(getApplicationContext(), getString(R.string.withdraw_type));
                } else if (money.compareTo(new BigDecimal(limit)) == -1) {
                    ToastUtil.makeText(getApplicationContext(), String.format(getString(R.string.withdraw_limit), String.valueOf(limit)));
                } else if (!isCertification) {
                    ToastUtil.makeText(getApplicationContext(), getString(R.string.not_certification));
//                    showRealName();
                } else {
                    amount = GeneralUtils.getBigDecimalToTwo(money);
                    inputDialog.show();
//                    doWithdraw();
                }
            }
        } else
            ToastUtil.makeText(getApplicationContext(), getString(R.string.input_cash));
    }


    private NormalInputDialog.OnDialogClickListener inputListener = new NormalInputDialog.OnDialogClickListener() {
        @Override
        public void onDialogClick(View view, @Nullable String str1, String str2) {
            String phone = str1;
            String code = str2;
            if (view.getId() == R.id.tv_get_idntify_code) {
                if (GeneralUtils.isNullOrZeroLenght(phone)) {
                    ToastUtil.makeText(getApplicationContext(), getString(R.string.input_phone_number));
                } else {
                    requestIdentifyCode(phone, view);
                }
            }
            if (view.getId() == R.id.tv_next) {
                if (GeneralUtils.isNullOrZeroLenght(phone))
                    ToastUtil.makeText(getApplicationContext(), getString(R.string.input_phone_number));
                else if (GeneralUtils.isNullOrZeroLenght(code))
                    ToastUtil.makeText(getApplicationContext(), getString(R.string.input_identify_code));
                else verifyPhone(phone, code);
            }
        }
    };

    //获取验证码
    private void requestIdentifyCode(String phone, View view) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        HttpUtil.post(Constants.URL.GET_IDENTIFY_CODE, params).subscribe(new BaseResponseObserver<ObtainCodeResp>() {

            @Override
            public void success(ObtainCodeResp result) {
                EBLog.i(TAG, result.toString());
                ToastUtil.makeText(getApplicationContext(), result.getDesc());
                waitTimer((TextView) view);
            }

            @Override
            public void error(Response<ObtainCodeResp> response) {
                ToastUtil.makeText(getApplicationContext(), response.getDesc());
                EBLog.i(TAG, response.getCode() + "");
            }
        });
    }


    //校验密码
    private void verifyPhone(String phone, String code) {
        Map<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("code", code);
        HttpUtil.post(Constants.URL.VEIRFY_CODE, params).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                EBLog.i(TAG, commonResp.getDesc());
                inputDialog.dismiss();

                if (ButtomOnClickUtils.isFastClick()) {
                    doWithdraw();
                } else {
                    ToastUtils.showShortToast(MyInComeActivity.this, "小伙子是单身20年的手速啊，666");
                }
            }

            @Override
            public void error(Response<CommonResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(getApplicationContext(), response.getDesc());
            }
        });
    }


    private void showRealName() {
        NormalDialog dialog = DialogUtil.showDialogTwoBut(this, null,
                "您还未通过实名认证，请前往个人资料中进行实名认证！", "再说吧", "去认证");
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
                        openActivity(RealNameActivity.class);
                        dialog.dismiss();
                    }
                });
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


    //获取用户账户信息
    private void getAccount() {
        HttpUtil.get(Constants.URL.GET_ACCOUNT_INFO).subscribe(new BaseResponseObserver<AccountResp>() {

            @Override
            public void success(AccountResp accountResp) {
                EBLog.i(TAG, accountResp.toString());
                if (accountResp != null) {
                    account = accountResp;
                }
                showInfo();
            }

            @Override
            public void error(Response<AccountResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(getApplicationContext(), response.getDesc());
            }
        });
    }

    //账户信息展示
    private void showInfo() {
        if (mGrossIncomeAmount != null)
            binding.generalIncome.setText(mGrossIncomeAmount + "");
        if (mBalance != null)
            binding.balanceOfAccount.setText(mBalance + "");
        if (account != null) {
            if (account.getCashAmount() == 1) {
                binding.withdrawBtn.setText("首单一元可提现");
                binding.tvWithdrawLimit.setText("首单一元可提现");
            } else {
                binding.withdrawBtn.setText("提现");
                binding.tvWithdrawLimit.setText(R.string.withdraw_min_limit);
            }
        }
        if (account.getWechatIs())
            binding.withdrawWechat.setText("已绑定");
        else
            binding.withdrawWechat.setText("未绑定");
        if (account.getAlipayIs())
            binding.withdrawAli.setText("已绑定");
        else
            binding.withdrawAli.setText("未绑定");


//        if (account.getBankIs())
//            withdraw_bank.setText("已绑定");
//        else
//            withdraw_bank.setText("未绑定");
        setSelect();
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
                binding.withdrawAli.setCompoundDrawables(null, null, selDrawable, null);
                selectType = 0;
                binding.withdrawWechat.setCompoundDrawables(null, null, unDrawable, null);
//                withdraw_bank.setCompoundDrawables(null, null, unDrawable, null);
                if (account.getCashAmount() == 1) {
                    binding.withdrawBtn.setText("首单一元提现");
                } else {
                    binding.tvWithdrawLimit.setText(R.string.withdraw_min_limit);
                    binding.tvWithdrawLimit.setText(getString(R.string.withdraw_min_limit));
                }
                binding.tvWithdrawLimit.setTextColor(getResources().getColor(R.color.colorFont6));
                break;
            case 1:
                binding.withdrawWechat.setCompoundDrawables(null, null, selDrawable, null);
                selectType = 1;
                binding.withdrawAli.setCompoundDrawables(null, null, unDrawable, null);
//                withdraw_bank.setCompoundDrawables(null, null, unDrawable, null);
                if (account.getCashAmount() == 1) {
                    binding.tvWithdrawLimit.setText("首单1元即可提现.注：请确保您单认证信息的正确性，否则不予提现");
                    binding.withdrawBtn.setText("首单一元提现");
                } else {
                    binding.withdrawWechat.setText(R.string.withdraw_min_limit);
                    binding.withdrawWechat.setText(getString(R.string.withdraw_min_limit));
                }
                binding.withdrawWechat.setTextColor(getResources().getColor(R.color.colorFont6));
                break;
            case 2:
//                withdraw_bank.setCompoundDrawables(null, null, selDrawable, null);
//                selectType = 2;
//
//                withdraw_wechat.setCompoundDrawables(null, null, unDrawable, null);
//                withdraw_ali.setCompoundDrawables(null, null, unDrawable, null);
//                tv_withdraw_limit.setText(getString(R.string.add_card_remaind));
//                tv_withdraw_limit.setTextColor(getResources().getColor(R.color.colorRemind));
                break;
            default:
                break;
        }
    }

    //申请提现
    private void doWithdraw() {
        Map<String, String> map = new HashMap<>();
        map.put("type", selectType + "");
        map.put("amount", amount);
        if (type != 0) {
            ToastUtils.showShortToast(getApplicationContext(), "提现方式出错请重新提现");
            return;
        }
        HttpUtil.post(Constants.URL.DO_WITHDRAW, map).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                ToastUtil.makeText(getApplicationContext(), getString(R.string.withdraw_success));
                mBalance = mBalance.subtract(new BigDecimal(amount));
                binding.balanceOfAccount.setText(mBalance.toString());
                binding.withdrawDeposit.setText("");
            }

            @Override
            public void error(Response<CommonResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                if (response.getCode() == 3018) {
                    showRealName();
                }
                ToastUtil.makeText(getApplicationContext(), response.getDesc());
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KeyBoardUtils.closeKeybord(binding.withdrawBtn, this);
        return super.onTouchEvent(event);
    }

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.all_withdraw_deposit:
                    if (mBalance != null)
                        binding.withdrawDeposit.setText(mBalance.toString());
                    break;
                case R.id.withdraw_btn:

                    boolean isCertification = (boolean) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.IS_CERTIFICATION, false);
                    if (ButtomOnClickUtils.isFastClick()) {
                        if (!isCertification) {
                            showRealName();
                        } else {
                            verifyAmount();
                        }

                    } else {
                        ToastUtils.showShortToast(MyInComeActivity.this, "小伙子是单身20年的手速啊，666");
                    }

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
                default:
                    break;

            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            getAccount();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
