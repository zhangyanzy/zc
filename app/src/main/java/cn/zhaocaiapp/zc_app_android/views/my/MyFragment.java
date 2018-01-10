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
    @BindView(R.id.iv_user_photo)
    CircleImageView iv_user_photo;     //用户头像

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_my_fragment, container, false);
    }

    @Override
    public void init() {

    }

    @OnClick({R.id.iv_user_photo})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_user_photo:

                break;
        }
    }

}
