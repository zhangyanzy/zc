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
 * Created by Administrator on 2018/3/23.
 */

public class MessageDetailActivity extends BaseActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.tv_top_title)
    TextView tv_top_title;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
    @BindView(R.id.tv_content)
    TextView tv_content;

    @Override
    public int getContentViewResId() {
        return R.layout.layout_message_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        iv_top_menu.setVisibility(View.GONE);
        tv_top_title.setText("消息详情");

        String content = getIntent().getStringExtra("message");
        tv_content.setText(content);

    }

    @OnClick({R.id.iv_top_back})
    public void onClick(View view){
        finish();
    }
}
