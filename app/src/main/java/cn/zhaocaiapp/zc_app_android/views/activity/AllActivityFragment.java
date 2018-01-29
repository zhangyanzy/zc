package cn.zhaocaiapp.zc_app_android.views.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;

/**
 * Created by Administrator on 2018/1/29.
 */

public class AllActivityFragment extends BaseFragment {
    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.layout_list, container, false);
    }

    @Override
    public void init() {

    }

    @Override
    public void loadData() {

    }
}
