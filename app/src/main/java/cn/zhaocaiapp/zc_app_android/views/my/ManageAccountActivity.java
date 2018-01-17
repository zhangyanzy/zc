package cn.zhaocaiapp.zc_app_android.views.my;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    private UMShareAPI umShareAPI;
    public static final String WITHDRAW_TYPE = "withdraw_type";

    @Override
    public int getContentViewResId() {
        return R.layout.layout_account_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        umShareAPI = ZcApplication.getUMShareAPI();

    }

    @OnClick({R.id.iv_top_back, R.id.tv_wechat, R.id.tv_ali, R.id.tv_bank_card})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.tv_wechat://微信账户
                isGetAuth(SHARE_MEDIA.WEIXIN, AccountActivity.class);
                break;
            case R.id.tv_ali:

                break;
            case R.id.tv_bank_card:

                break;
        }
    }

    // 判断是否已获取三方授权
    private void isGetAuth(SHARE_MEDIA platform, Class<?>mClass){
        Bundle bundle = new Bundle();
        bundle.putInt(WITHDRAW_TYPE, Constants.SPREF.TYPE_WECHAT);
        if (umShareAPI.isAuthorize(this, platform))
            openActivity(mClass, bundle);
        else getAuth(platform);
    }

    //获取三方授权
    private void getAuth(SHARE_MEDIA platform) {
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
}
