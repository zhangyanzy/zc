package cn.zhaocaiapp.zc_app_android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.views.login.LoginActivity;

/**
 * @author 林子
 * @filename MemberActivity.java
 * @data 2018-01-05 17:59
 */
public class TestFragment extends BaseFragment {
    @BindView(R.id.button1)
    Button button1;

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_main, container, false);
    }

    @Override
    public void init() {

    }

    @OnClick({R.id.button1})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button1:
                openActivity(LoginActivity.class);
                break;
        }
    }

}
