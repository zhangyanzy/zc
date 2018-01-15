package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

/**
 * Created by Administrator on 2018/1/12.
 */

public class TaskRelativeFragment extends BaseFragment {
    @BindView(R.id.tv_educational)
    TextView tv_educational;
    @BindView(R.id.tv_profession)
    TextView tv_profession;

    private View rootView;

    private List<String> educationalNames;
    private List<String> professionNames;
    private OptionsPickerView optionsPickerView;

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_task_relative_fragment, container, false);
        return rootView;
    }

    @Override
    public void init() {
        educationalNames = new ArrayList<>();
        educationalNames.add("初中");
        educationalNames.add("高中");
        educationalNames.add("大专");
        educationalNames.add("本科");
        educationalNames.add("研究生");
        educationalNames.add("博士");

        professionNames = new ArrayList<>();
        professionNames.add("学生党");
        professionNames.add("自由工作者");
        professionNames.add("白领");
        professionNames.add("企业高管");
        professionNames.add("其他");
    }

    @Override
    public void loadData() {

    }

    @OnClick({R.id.tv_educational, R.id.tv_profession})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_educational:
                setItemContent("学历选择", educationalNames, tv_educational);
                optionsPickerView.show();
                break;
            case R.id.tv_profession:
                setItemContent("职业选择", professionNames, tv_profession);
                optionsPickerView.show();
                break;
        }
    }

    private void setItemContent(String title, List<String>items, TextView view){
        optionsPickerView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                view.setText(items.get(options1));
            }
        }).setTitleText(title)
                .setSelectOptions(0)
                .build();
        optionsPickerView.setPicker(items);
    }

}