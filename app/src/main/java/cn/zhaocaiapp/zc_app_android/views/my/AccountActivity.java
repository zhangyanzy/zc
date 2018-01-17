package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

/**
 * Created by Administrator on 2018/1/15.
 */

public class AccountActivity extends BaseActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.tv_top_title)
    TextView tv_top_titlel;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
    @BindView(R.id.tv_account)
    TextView tv_account;
    @BindView(R.id.tv_submit)
    TextView tv_submit;

    private UMShareAPI umShareAPI;
    private SHARE_MEDIA platform;

    @Override
    public int getContentViewResId() {
        return R.layout.layout_wechat_account;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        umShareAPI = ZcApplication.getUMShareAPI();
        int type = getIntent().getIntExtra(ManageAccountActivity.WITHDRAW_TYPE, -1);
        switch (type){
            case Constants.SPREF.TYPE_WECHAT:
                platform = SHARE_MEDIA.WEIXIN;
                break;
            case Constants.SPREF.TYPE_ALI:
                platform = SHARE_MEDIA.ALIPAY;
                break;
            case Constants.SPREF.TYPE_BANK:

                break;

        }
    }

    @OnClick({R.id.iv_top_back, R.id.tv_submit})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.tv_submit:
                cancelAuth(platform);
                break;
        }
    }

    //取消三方授权
    private void cancelAuth(SHARE_MEDIA platform) {
        umShareAPI.deleteOauth(this, platform, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                ToastUtil.makeText(AccountActivity.this, "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                ToastUtil.makeText(AccountActivity.this, "取消授权成功");
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                ToastUtil.makeText(AccountActivity.this, "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                ToastUtil.makeText(AccountActivity.this, "已取消授权");
            }
        });
    }
}
