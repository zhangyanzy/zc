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
 * Created by Administrator on 2018/1/24.
 */

public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.tv_top_title)
    TextView tv_top_title;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.tv_privacy)
    TextView tv_privacy;


    @Override
    public int getContentViewResId() {
        return R.layout.layout_about_us;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tv_top_title.setText("关于我们");
    }

    @OnClick({R.id.iv_top_back, R.id.tv_privacy})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.tv_privacy:

                break;
        }
    }
}
