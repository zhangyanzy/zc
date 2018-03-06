package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
    @BindView(R.id.tv_infostate)
    TextView tv_infostate;
    @BindView(R.id.layout_edu)
    RelativeLayout layout_edu;
    @BindView(R.id.layout_pro)
    RelativeLayout layout_pro;

    private View rootView;

    private List<String> names;
    private OptionsPickerView optionsPickerView;

    private UserDetailResp.ActivityInfoBean activityInfoBean;
    private List<RelationListResp.CommInfo> qualification;//学历列表
    private List<RelationListResp.CommInfo> occupation;//职业列表

    private long proCode;//职业code
    private long eduCode;//学历code
    private String proName;//职业
    private String eduName;//学历
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
        proCode = activityInfoBean.getJobCode();
        eduCode = activityInfoBean.getEducationalCode();
        if (GeneralUtils.isNotNullOrZeroLenght(education))
            tv_educational.setText(education);
        if (GeneralUtils.isNotNullOrZeroLenght(profession))
            tv_profession.setText(profession);
        switch (activityInfoBean.getActivtiyInfoAudit()) {
            case 0:
                tv_infostate.setText("未变动");
                break;
            case 1:
            case 2:
                tv_infostate.setText("变动" + activityInfoBean.getActivityInfoAlterCount() + "次");
                break;
            case 3:
                tv_infostate.setText("待审核");
                break;
            case 4:
                tv_infostate.setText("审核通过");
                break;
            case 5:
                tv_infostate.setText("审核未通过");
                break;
        }
    }

    //提交修改活动相关信息
    private void revise() {
        Map<String, String> params = new HashMap<>();
        params.put("jobCode", proCode + "");
        params.put("educationalCode", eduCode + "");

        HttpUtil.put(Constants.URL.REVISE_ACTIVITY_INFO, params).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                EBLog.i(TAG, commonResp.toString());
                ToastUtil.makeText(getActivity(), commonResp.getDesc());

                if (activityInfoBean.getActivityInfoAlterCount() < 3) {
                    activityInfoBean.setActivtiyInfoAudit(activityInfoBean.getActivtiyInfoAudit() + 1);
                } else activityInfoBean.setActivtiyInfoAudit(3);
                activityInfoBean.setActivityInfoAlterCount(activityInfoBean.getActivityInfoAlterCount() + 1);
                showInfo();
            }

            @Override
            public void error(Response<CommonResp> response) {
                EBLog.i(TAG, response.getCode() + "");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    //校验信息是否为空
    private boolean isNotEmpty() {
        eduName = tv_educational.getText().toString();
        proName = tv_profession.getText().toString();
        if (GeneralUtils.isNullOrZeroLenght(eduName)) {
            ToastUtil.makeText(getActivity(), "学历不能为空");
            return false;
        }
        if (GeneralUtils.isNullOrZeroLenght(proName)) {
            ToastUtil.makeText(getActivity(), "职业不能为空");
            return false;
        }
        return true;
    }

    private boolean isCanUpdate() {
        if (eduName.equals(education) && proName.equals(profession)) {
            ToastUtil.makeText(getActivity(), getString(R.string.not_revise));
            return false;
        }
        return true;
    }

    @OnClick({R.id.layout_edu, R.id.layout_pro, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_edu:
                setItemContent("学历选择", qualification, tv_educational);
                break;
            case R.id.layout_pro:
                setItemContent("职业选择", occupation, tv_profession);
                break;
            case R.id.tv_submit:
                if (isNotEmpty() && isCanUpdate()) {
                    if (activityInfoBean.getActivtiyInfoAudit() != 3)
                        revise();
                    else ToastUtil.makeText(getActivity(), getString(R.string.wait_verify));
                }
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
                    eduCode = items.get(options1).getDictCode();
                if (view.getId() == R.id.tv_profession)
                    proCode = items.get(options1).getDictCode();
            }
        }).setTitleText(title)
                .setSelectOptions(0)
                .build();
        optionsPickerView.setPicker(names);
        optionsPickerView.show();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
