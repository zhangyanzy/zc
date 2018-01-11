package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.nio.file.Path;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;

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

    @Override
    public int getContentViewResId() {
        return R.layout.layout_invite_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @OnClick({R.id.iv_top_back, R.id.iv_top_menu, R.id.tv_copy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.iv_top_menu:

                break;
            case R.id.tv_copy:

                break;
        }
    }
}
