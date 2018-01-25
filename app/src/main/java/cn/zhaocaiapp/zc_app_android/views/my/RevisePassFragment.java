package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.CommonResp;
import cn.zhaocaiapp.zc_app_android.bean.response.common.FinishUserResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.views.login.LoginActivity;

/**
 * Created by Administrator on 2018/1/12.
 */

public class RevisePassFragment extends BaseFragment {
    @BindView(R.id.edit_old_pass)
    EditText edit_old_pass;
    @BindView(R.id.edit_new_pass)
    EditText edit_new_pass;
    @BindView(R.id.edit_confirm_pass)
    EditText edit_confirm_pass;
    @BindView(R.id.tv_submit)
    TextView tv_submit;

    private static final String TAG = "修改密码";

    private View rootView;
    private String oldPass;
    private String newPass;
    private String confirmPass;

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_revisepass_fragment, container, false);
        return rootView;
    }

    @Override
    public void init() {
        //监听点击空白处，隐藏软键盘
        rootView.setOnTouchListener(onTouchListener);
    }

    @Override
    public void loadData() {
    }

    @OnClick({R.id.tv_submit})
    public void onClick(View view) {
        oldPass = edit_old_pass.getText().toString();
        newPass = edit_new_pass.getText().toString();
        confirmPass = edit_confirm_pass.getText().toString();

        if (verify()) doRevisePass();
    }

    private void doRevisePass() {
        Map<String, String> params = new HashMap<>();
        params.put("oldPassword", oldPass);
        params.put("newPassword", newPass);
        params.put("confirmNewPassword", confirmPass);

        HttpUtil.post(Constants.URL.REVISE_PASS, params).subscribe(new BaseResponseObserver<CommonResp>() {

            @Override
            public void success(CommonResp commonResp) {
                ToastUtil.makeText(getActivity(), commonResp.getDesc());
                openActivity(LoginActivity.class);
                getActivity().finish();
            }

            @Override
            public void error(Response<CommonResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    private boolean verify() {
        if (GeneralUtils.isNullOrZeroLenght(oldPass)) {
            ToastUtil.makeText(getActivity(), "请输入原密码");
            return false;
        }
        if (GeneralUtils.isNullOrZeroLenght(newPass)) {
            ToastUtil.makeText(getActivity(), "请输入新密码");
            return false;
        }
        if (GeneralUtils.isNullOrZeroLenght(confirmPass)) {
            ToastUtil.makeText(getActivity(), "请再次输入新密码");
            return false;
        }
        if (!newPass.equals(confirmPass)) {
            ToastUtil.makeText(getActivity(), "两次输入的密码不一致，请重新输入");
            return false;
        }
        return true;
    }

}
