package cn.zhaocaiapp.zc_app_android.views.my;

import android.app.MediaRouteActionProvider;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.MessageEvent;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.CommonResp;
import cn.zhaocaiapp.zc_app_android.bean.response.common.RelationListResp;
import cn.zhaocaiapp.zc_app_android.bean.response.my.UserDetailResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

/**
 * Created by Administrator on 2018/1/12.
 */

public class RelativeInfoFragment extends BaseFragment {
    @BindView(R.id.tv_educational)
    TextView tv_educational;
    @BindView(R.id.tv_profession)
    TextView tv_profession;
    @BindView(R.id.tv_submit)
    TextView tv_submit;

    private View rootView;

    private List<String> names;
    private OptionsPickerView optionsPickerView;

    private UserDetailResp.ActivityInfoBean activityInfoBean;
    private List<RelationListResp.CommInfo> qualification;
    private List<RelationListResp.CommInfo> occupation;

    private long jobCode;
    private long educationalCode;
    private Map<String, String> params = new HashMap<>();
    private boolean isCanUpdate;

    private String profession;
    private String education;

    private static final String TAG = "活动相关信息";

    @Override
    public void onStart() {
        super.onStart();
        //注册EventBus消息订阅者
        EventBus.getDefault().register(this);
    }

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_task_relative_fragment, container, false);
        return rootView;
    }

    @Override
    public void init() {
        names = new ArrayList<>();
    }

    //获取职业和学历列表
    @Override
    public void loadData() {
        HttpUtil.get(Constants.URL.GET_RELATION_LIST).subscribe(new BaseResponseObserver<RelationListResp>() {

            @Override
            public void success(RelationListResp relationListResp) {
                EBLog.i(TAG, relationListResp.toString());
                qualification = relationListResp.getQualification();
                occupation = relationListResp.getOccupation();
            }

            @Override
            public void error(Response<RelationListResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    //接收EventBus发送的消息，并处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        if (event.getMessage() instanceof UserDetailResp.ActivityInfoBean) {
            activityInfoBean = (UserDetailResp.ActivityInfoBean) event.getMessage();
            showInfo();
            EBLog.i(TAG, activityInfoBean.toString());
        }
    }

    //展示用户学历和职业
    private void showInfo() {
        profession = activityInfoBean.getJob();
        education = activityInfoBean.getEducational();
        if (GeneralUtils.isNotNullOrZeroLenght(education))
            tv_educational.setText(education);
        if (GeneralUtils.isNotNullOrZeroLenght(profession))
            tv_profession.setText(profession);
    }

    //提交修改活动相关信息
    private void revise() {
        HttpUtil.put(Constants.URL.REVISE_ACTIVITY_INFO, params).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                EBLog.i(TAG, commonResp.toString());
                ToastUtil.makeText(getActivity(), commonResp.getDesc());
            }

            @Override
            public void error(Response<CommonResp> response) {
                EBLog.i(TAG, response.getCode() + "");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    private void verify() {
        String edu = tv_educational.getText().toString();
        String pro = tv_profession.getText().toString();
        if (GeneralUtils.isNullOrZeroLenght(edu)) {
            ToastUtil.makeText(getActivity(), "学历不能为空");
            isCanUpdate = false;
            return;
        } else if (!edu.equals(education)) {
            params.put("jobCode", jobCode + "");
            isCanUpdate = true;
        }
        if (GeneralUtils.isNullOrZeroLenght(pro)) {
            ToastUtil.makeText(getActivity(), "职业不能为空");
            isCanUpdate = false;
            return;
        } else if (!pro.equals(profession)) {
            params.put("educationalCode", educationalCode + "");
            isCanUpdate = true;
        }

    }

    @OnClick({R.id.tv_educational, R.id.tv_profession, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_educational:
                setItemContent("学历选择", qualification, tv_educational);
                optionsPickerView.show();
                break;
            case R.id.tv_profession:
                setItemContent("职业选择", occupation, tv_profession);
                optionsPickerView.show();
                break;
            case R.id.tv_submit:
                verify();
                if (isCanUpdate) revise();
                break;
        }
    }

    private void setItemContent(String title, List<RelationListResp.CommInfo> items, TextView view) {
        names.clear();
        for (RelationListResp.CommInfo commInfo : items) {
            names.add(commInfo.getDictValue1());
        }
        optionsPickerView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                view.setText(names.get(options1));
                if (view.getId() == R.id.tv_educational)
                    educationalCode = items.get(options1).getDictCode();
                if (view.getId() == R.id.tv_profession)
                    jobCode = items.get(options1).getDictCode();
            }
        }).setTitleText(title)
                .setSelectOptions(0)
                .build();
        optionsPickerView.setPicker(names);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
