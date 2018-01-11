package cn.zhaocaiapp.zc_app_android.views.member;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import butterknife.BindView;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;

/**
 * @author 林子
 * @filename MemberActivity.java
 * @data 2018-01-05 17:59
 */
public class MemberFragment extends BaseFragment {

    @BindView(R.id.member_grid)
    GridLayout member_grid;

    @Nullable
    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_main, container, false);
        return view;
    }

    @Override
    public void init() {
        int count = 1;
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 5; j++) {
                Button btn = new Button(getContext());
                btn.setWidth(40);
                btn.setText(String.valueOf(count));
                count++;
                GridLayout.Spec rowSpec = GridLayout.spec(i);     //设置它的行和列
                GridLayout.Spec columnSpec = GridLayout.spec(j);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                params.setGravity(Gravity.LEFT);
                member_grid.addView(btn, params);
            }
    }

    //动态添加数据
    private void Data() {

    }


}
