package cn.zhaocaiapp.zc_app_android.views.my;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.umeng.socialize.utils.SocializeUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.util.ShareUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.widget.LoadingDialog;

/**
 * Created by Administrator on 2018/1/11.
 */

public class InviteActivity extends BaseActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.tv_top_title)
    TextView tv_top_titlel;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
    @BindView(R.id.tv_copy)
    TextView tv_copy;

    private String webUrl = "http://m.zhaocaiapp.local/#/activity/detail?id=1";
    private String shareTitle = "一个可以赚钱的APP";
    private String shareDesc = "你看广告，我发钱";

//    private ProgressDialog dialog;
    private UMShareAPI umShareAPI;

    @Override
    public int getContentViewResId() {
        return R.layout.layout_invite_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        iv_top_menu.setImageResource(R.mipmap.share);
        tv_top_titlel.setText("邀请好友");

        umShareAPI = ZcApplication.getUMShareAPI();
//        dialog = new ProgressDialog(this);
    }

    @OnClick({R.id.iv_top_back, R.id.iv_top_menu, R.id.tv_copy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.iv_top_menu:
                ShareUtil.init(this)
                        .setUrl(webUrl)
                        .setSourceId(R.mipmap.logo)
                        .setTitle(shareTitle)
                        .setDesc(shareDesc);
                ShareUtil.openShare();
                break;
            case R.id.tv_copy:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        umShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        umShareAPI.release();
    }
}
