package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.text.BoringLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
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
    private boolean isChecked;

    @Override
    public int getContentViewResId() {
        return R.layout.layout_applycash_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        balance = tv_balance.getText().toString();
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
            case R.id.tv_withdraw_all:
                edit_apply_cash.setText(balance);
                break;
            case R.id.tv_submit:

                break;
            case R.id.withdraw_wechat:
                if (withdraw_wechat.isChecked()) {
                    ToastUtil.makeText(ApplyCashActivity.this, "微信提现");
                } else {
                    withdraw_wechat.setChecked(true);
                }
                break;
            case R.id.withdraw_ali:
                if (withdraw_ali.isChecked()) {
                    ToastUtil.makeText(ApplyCashActivity.this, "支付宝提现");

                } else {
                    withdraw_ali.setChecked(true);
                }
                break;
            case R.id.withdraw_bank:
                if (withdraw_bank.isChecked()) {
                    ToastUtil.makeText(ApplyCashActivity.this, "银行卡提现");

                } else {
                    withdraw_bank.setChecked(true);
                }
                break;
        }
    }

}
