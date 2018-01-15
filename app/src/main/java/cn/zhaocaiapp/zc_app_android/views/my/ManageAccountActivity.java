package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;

/**
 * Created by Administrator on 2018/1/15.
 */

public class ManageAccountActivity extends BaseActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.tv_top_title)
    TextView tv_top_titlel;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
    @BindView(R.id.tv_wechat)
    TextView tv_wechat;
    @BindView(R.id.tv_ali)
    TextView tv_ali;
    @BindView(R.id.tv_bank_card)
    TextView tv_bank_card;


    @Override
    public int getContentViewResId() {
        return R.layout.layout_account_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @OnClick({R.id.iv_top_back, R.id.tv_wechat, R.id.tv_ali, R.id.tv_bank_card})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.tv_wechat:

                break;
            case R.id.tv_ali:

                break;
            case R.id.tv_bank_card:

                break;
        }
    }
}
