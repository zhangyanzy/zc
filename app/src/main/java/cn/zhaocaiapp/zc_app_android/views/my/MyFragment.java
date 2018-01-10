package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.views.login.LoginActivity;
import cn.zhaocaiapp.zc_app_android.widget.CircleImageView;

/**
 * @author 林子
 * @filename MyActivity.java
 * @data 2018-01-05 18:02
 */
public class MyFragment extends BaseFragment {
    @BindView(R.id.avatarImg)
    CircleImageView avatarImg;     //用户头像

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_main, container, false);
    }

    @Override
    public void init() {

    }

    @OnClick({R.id.avatarImg})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.avatarImg:
                openActivity(LoginActivity.class);
                break;
        }
    }


    /*@Override
    public int getContentViewResId() {
        return R.layout.my_main;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }*/
}
