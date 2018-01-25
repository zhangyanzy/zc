package cn.zhaocaiapp.zc_app_android.views.my;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.bean.MessageEvent;
import cn.zhaocaiapp.zc_app_android.bean.response.my.UserDetailResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.KeyBoardUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

/**
 * Created by Administrator on 2018/1/12.
 */

public class IdentifyFragment extends BaseFragment {
    @BindView(R.id.tv_user_gender)
    TextView tv_user_gender;
    @BindView(R.id.tv_birth_day)
    TextView tv_birth_day;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.iv_add_icture)
    ImageView iv_add_icture;
    @BindView(R.id.edit_user_name)
    EditText edit_user_name;
    @BindView(R.id.edit_id_number)
    EditText edit_id_number;

    private View rootView;

    private OptionsPickerView optionsPickerView;
    private TimePickerView timePickerView;
    private List<String> genders = new ArrayList<>();
    private Calendar startDate;
    private Calendar endDate;

    private UserDetailResp.RealInfoBean realInfoBean;

    private static final String TAG = "用户实名信息";

    @Override
    public void onStart() {
        super.onStart();
        //注册EventBus消息订阅者
        EventBus.getDefault().register(this);
    }

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_identify_fragment, container, false);
        return rootView;
    }

    @Override
    public void init() {
        setGenderPicker();
        setDatePicker();
    }

    private void showUserInfo() {
        if (GeneralUtils.isNotNullOrZeroLenght(realInfoBean.getName()))
            edit_user_name.setText(realInfoBean.getName());
        if (GeneralUtils.isNotNullOrZeroLenght(realInfoBean.getIdCard()))
            edit_id_number.setText(realInfoBean.getIdCard());

        int sex = realInfoBean.getSex();
        if (sex == 0) tv_user_gender.setText("男");
        else tv_user_gender.setText("女");

    }

    //性别选择器初始化设置
    private void setGenderPicker() {
        genders.add("女");
        genders.add("男");
        optionsPickerView = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tv_user_gender.setText(genders.get(options1));
            }
        }).setTitleText("性别选择")
                .setSelectOptions(0)
                .build();
        optionsPickerView.setPicker(genders);
    }

    //日期选择起初始化设置、
    private void setDatePicker() {
        Calendar selectedDate = Calendar.getInstance();
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);
        endDate.set(2100, 11, 31);
        timePickerView = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tv_birth_day.setText(getBirthTime(date));
            }
        }).setTitleText("出生日期选择")
                .setType(new boolean[]{true, true, true, false, false, false})
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setDecorView(null)
                .build();
    }

    @Override
    public void loadData() {

    }

    //接收EventBus发送的消息，并处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent<UserDetailResp.RealInfoBean> event) {
        if (event.getMessage() instanceof UserDetailResp.RealInfoBean) {
            realInfoBean = event.getMessage();
            showUserInfo();
            EBLog.i(TAG, realInfoBean.toString());
        }

    }

    @OnClick({R.id.tv_user_gender, R.id.tv_birth_day, R.id.iv_add_icture})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_user_gender:
                manageKeyBord(tv_submit, getActivity());
                optionsPickerView.show();
                break;
            case R.id.tv_birth_day:
                manageKeyBord(tv_submit, getActivity());
                timePickerView.show();
                break;
            case R.id.iv_add_icture:

                break;
        }
    }

    private String getBirthTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

}
