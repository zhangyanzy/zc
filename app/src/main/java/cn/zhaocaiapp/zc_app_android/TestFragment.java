package cn.zhaocaiapp.zc_app_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.bean.response.my.LabelResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.views.activity.ActivityActivityDetail;
import cn.zhaocaiapp.zc_app_android.views.home.LocationActivity;
import cn.zhaocaiapp.zc_app_android.views.login.LoginActivity;
import cn.zhaocaiapp.zc_app_android.views.my.LabelFragment;

/**
 * @author 林子
 * @filename MemberActivity.java
 * @data 2018-01-05 17:59
 */
public class TestFragment extends BaseFragment {
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;

    Button goH5;


    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_main, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //h5页面
        goH5 = (Button) getActivity().findViewById(R.id.goH5);
        goH5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.makeText(getContext(), "跑到这里了");
                Intent intent = new Intent(getActivity(), ActivityActivityDetail.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void init() {

    }

    @Override
    public void loadData() {

    }

    @OnClick({R.id.button1, R.id.button2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                openActivity(LoginActivity.class);
                break;
            case R.id.button2:
                openActivity(LocationActivity.class);
                break;
        }
    }

}
