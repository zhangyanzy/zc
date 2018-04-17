package cn.zhaocaiapp.zc_app_android.views.my;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.AuthTask;
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
import cn.zhaocaiapp.zc_app_android.bean.AuthResult;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.request.my.BindCardReq;
import cn.zhaocaiapp.zc_app_android.bean.response.common.CommonResp;
import cn.zhaocaiapp.zc_app_android.bean.response.my.AccountResp;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.listener.OnBtnClickL;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.widget.NormalDialog;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.DialogUtil;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
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
    private AccountResp account;
    private int type; //0 支付宝  1 微信   2 银行卡
    private NormalDialog dialog;

    private static final int REQUEST_CODE = 6001;
//    private static final int RESULT_CODE = 6010;

    private static final String TAG = "管理账户";

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            @SuppressWarnings("unchecked")
            AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
            String resultStatus = authResult.getResultStatus();
            EBLog.i(TAG, authResult.toString());

            // 判断resultStatus 为“9000”且result_code为“200”则代表授权成功
            if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                // 获取alipay_open_id，调支付时作为参数extern_token 的value传入，则支付账户为该授权账户
                ToastUtil.makeText(ManageAccountActivity.this,
                        "授权成功");

                bindAccount(authResult.getUserId());
            } else {
                // 其他状态值则为授权失败
                ToastUtil.makeText(ManageAccountActivity.this,
                        "授权失败");
            }
        }
    };

    @Override
    public int getContentViewResId() {
        return R.layout.layout_account_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        umShareAPI = ZcApplication.getUMShareAPI();

        tv_top_titlel.setText("管理提现账户");
        iv_top_menu.setVisibility(View.GONE);

        getAccount();
    }

    //获取绑定账户信息
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
                ToastUtil.makeText(ManageAccountActivity.this, response.getDesc());
            }
        });
    }

    private void showInfo() {
        if (account.getAlipayIs()) ali_name.setText("已绑定");
        else ali_name.setText("未绑定");
        if (account.getWechatIs()) wechat_name.setText("已绑定");
        else wechat_name.setText("未绑定");
        if (account.getBankIs()) bank_name.setText("已绑定");
        else bank_name.setText("未绑定");
    }

    //绑定三方账户
    private void bindAccount(String uid) {
        BindCardReq bindCardReq = new BindCardReq();
        if (type == 0) { // 支付宝绑定
            bindCardReq.setAlipayNo("");
            bindCardReq.setAlipayOpenId(uid);
        }
        if (type == 1) { // 微信绑定
            bindCardReq.setWechatNo("");
            bindCardReq.setWechatOpenId(uid);
        }
        bindCardReq.setType(type);

        HttpUtil.put(Constants.URL.BIND_ACCOUNT, bindCardReq).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                ToastUtil.makeText(ManageAccountActivity.this, commonResp.getDesc());
                getAccount();
            }

            @Override
            public void error(Response<CommonResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(ManageAccountActivity.this, response.getDesc());
            }
        });
    }

    //解除账户关联
    private void removeBind() {
        Map<String, String> map = new HashMap<>();
        if (type == 0) {
            map.put("alipayNo", "");
        }
        if (type == 1) {
            map.put("wechatNo", "");
        }
        if (type == 2) {
            map.put("bankCard", "");
            map.put("name", "");
        }
        map.put("type", type + "");

        HttpUtil.post(Constants.URL.REMOVE_ACCOUNT_BIND, map).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                ToastUtil.makeText(ManageAccountActivity.this, commonResp.getDesc());
                getAccount();
            }

            @Override
            public void error(Response<CommonResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(ManageAccountActivity.this, response.getDesc());
            }
        });
    }

    @OnClick({R.id.iv_top_back, R.id.layout_wechat, R.id.layout_ali, R.id.layout_bank})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
//                setResult(RESULT_CODE);
                finish();
                break;
            case R.id.layout_wechat://微信账户
                type = 1;
                if (account.getWechatIs()) showDialog(false);
                else getWechatAuth(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.layout_ali:// 支付宝账户
                type = 0;
                if (account.getAlipayIs()) showDialog(false);
                else getAliAuthInfo();
                break;
            case R.id.layout_bank://银行卡账户
                type = 2;
                if (account.getBankIs()) showDialog(false);
                else if (!(boolean) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.IS_CERTIFICATION, false))
                    showDialog(true);
                else openActivityForResult(BindCardActivity.class, REQUEST_CODE);
                break;
        }
    }

    private void showDialog(boolean isTurn) {
        String content = "";
        if (isTurn) content = getString(R.string.not_certification);
        else content = getString(R.string.remove_bind);
        dialog = DialogUtil.showDialogTwoBut(this, null, content, "取消", "确定 ");
        dialog.isTitleShow(false);
        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                dialog.dismiss();
            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                if (isTurn) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("curPosition", 1);
                    openActivity(UserInfoActivity.class, bundle);
                } else removeBind();
                dialog.dismiss();
            }
        });
    }

    private void getAliAuthInfo() {
        HttpUtil.get(Constants.URL.ALIPAY_OTHUR).subscribe(new BaseResponseObserver<String>() {

            @Override
            public void success(String s) {
                EBLog.i(TAG, s);
                getAliAuth(s);
            }

            @Override
            public void error(Response<String> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(ManageAccountActivity.this, response.getDesc());
            }
        });
    }

    //获取微信授权
    private void getWechatAuth(SHARE_MEDIA platform) {
        umShareAPI.getPlatformInfo(this, platform, authListener);
    }

    //支付宝账户授权业务
    private void getAliAuth(String authInfo) {
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(ManageAccountActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    private UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            EBLog.i("友盟---", "授权成功的回调");
            bindAccount(data.get("uid"));
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            EBLog.i("友盟---", "授权失败的回调");
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            EBLog.i("友盟---", "授权取消的回调");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        umShareAPI.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            getAccount();
        }
    }
}
