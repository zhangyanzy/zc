package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

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
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

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
    @BindView(R.id.layout_wechat)
    RelativeLayout layout_wechat;
    @BindView(R.id.wechat_name)
    TextView wechat_name;
    @BindView(R.id.layout_ali)
    RelativeLayout layout_ali;
    @BindView(R.id.ali_name)
    TextView ali_name;
    @BindView(R.id.layout_bank)
    RelativeLayout layout_bank;
    @BindView(R.id.bank_name)
    TextView bank_name;

    private UMShareAPI umShareAPI;
    public static final String WITHDRAW_TYPE = "withdraw_type";
    private AccountResp accountResp;
    private int type; //0 支付宝  1 微信   2 银行卡

    private static final String TAG = "管理账户";

    @Override
    public int getContentViewResId() {
        return R.layout.layout_account_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        umShareAPI = ZcApplication.getUMShareAPI();

        getAccount();
    }

    private void getAccount() {
        HttpUtil.get(Constants.URL.GET_ACCOUNT_INFO).subscribe(new BaseResponseObserver<AccountResp>() {

            @Override
            public void success(AccountResp accountResp) {
                EBLog.i(TAG, accountResp.toString());
                ManageAccountActivity.this.accountResp = accountResp;
                showInfo();
            }

            @Override
            public void error(Response<AccountResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(ManageAccountActivity.this, response.getDesc());
            }
        });
    }

    private void showInfo() {
        if (accountResp.getWechatNo() != null)
            wechat_name.setText(accountResp.getWechatNo());
        if (accountResp.getAlipayNo() != null)
            ali_name.setText(accountResp.getAlipayNo());
        if (accountResp.getBankCard() != null)
            bank_name.setText(GeneralUtils.bankCardReplaceWithStar(accountResp.getBankCard()));
    }

    //解除账户关联
    private void removeBind() {
         Map<String, String>map = new HashMap<>();
         if (type == 0){
             map.put("alipayNo", accountResp.getAlipayNo());
         }
         if (type == 1){
             map.put("wechatNo", accountResp.getWechatNo());
         }
         if (type == 2){
             map.put("bankCard", accountResp.getBankCard());
         }

         HttpUtil.post(Constants.URL.REMOVE_ACCOUNT_BIND, map).subscribe(new BaseResponseObserver<CommonResp>() {

             @Override
             public void success(CommonResp commonResp) {
                 ToastUtil.makeText(ManageAccountActivity.this, commonResp.getDesc());
             }

             @Override
             public void error(Response<CommonResp> response) {
                 EBLog.e(TAG, response.getCode()+"");
                 ToastUtil.makeText(ManageAccountActivity.this, response.getDesc());
             }
         });
    }

    @OnClick({R.id.iv_top_back, R.id.layout_wechat, R.id.layout_ali, R.id.layout_bank})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.layout_wechat://微信账户
                type = 1;
                if (accountResp.getWechatIs()) removeBind();
                else getWechatAuth(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.layout_ali:// 支付宝账户
                type = 0;
                if (accountResp.getAlipayIs()) removeBind();
                else getAliAuth();
                break;
            case R.id.layout_bank://银行卡账户
                type = 2;
                if (accountResp.getBankIs()) removeBind();
                else openActivity(BindCardActivity.class);
                break;
        }
    }

    // 判断是否已获取三方授权
    private void isGetAuth(SHARE_MEDIA platform, Class<?> mClass) {
        Bundle bundle = new Bundle();
        bundle.putInt(WITHDRAW_TYPE, Constants.SPREF.TYPE_WECHAT);
        if (umShareAPI.isAuthorize(this, platform))
            openActivity(mClass, bundle);
        else getWechatAuth(platform);
    }

    //获取微信授权
    private void getWechatAuth(SHARE_MEDIA platform) {
        umShareAPI.getPlatformInfo(this, platform, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.i("UMENG", map.toString());
                ToastUtil.makeText(ManageAccountActivity.this, "授权成功");
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }

    //获取阿里授权
    private void getAliAuth() {

    }
}
