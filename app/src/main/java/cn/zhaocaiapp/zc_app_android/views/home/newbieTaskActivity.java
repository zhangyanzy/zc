package cn.zhaocaiapp.zc_app_android.views.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.views.my.UserInfoActivity;

/**
 * @author 林子
 * @filename newbieTaskActivity.java
 * @data 2018-02-03 13:07
 */
public class newbieTaskActivity extends BaseActivity {

    @BindView(R.id.newbie_task_text)
    TextView newbie_task_text;
    @BindView(R.id.newbie_task_btn)
    Button newbie_task_btn;

    private String newbieAmount = "";

    @Override
    public int getContentViewResId() {
        return R.layout.newbie_task;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Bundle bd = this.getIntent().getExtras();
        newbieAmount = bd.getString("newbieAmount");
        newbie_task_text.setText(newbieAmount + "元");
    }

    @OnClick({
            R.id.newbie_task_btn
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.newbie_task_btn:
                openActivity(UserInfoActivity.class);
                break;
        }
    }
}