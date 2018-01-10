package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.assembly.XCRoundImageView;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;

/**
 * @author 林子
 * @filename MyActivity.java
 * @data 2018-01-05 18:02
 */
public class MyFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_main, container, false);
        return view;
    }

    @BindView(R.id.avatarImg)
    XCRoundImageView avatarImg;     //用户头像

    /*@Override
    public int getContentViewResId() {
        return R.layout.my_main;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }*/
}
