package cn.zhaocaiapp.zc_app_android.views.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import cn.zhaocaiapp.zc_app_android.bean.response.my.MyResp;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.widget.TrembleBasesOsDialog;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.views.login.LoginActivity;
import cn.zhaocaiapp.zc_app_android.widget.CircleImageView;

/**
 * @author 林子
 * @filename MyActivity.java
 * @data 2018-01-05 18:02
 */
public class MyFragment extends BaseFragment {
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.tv_top_title)
    TextView tv_top_title;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.iv_user_photo)
    CircleImageView iv_user_photo;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_user_identify)
    TextView tv_user_identify;
    @BindView(R.id.tv_user_balance)
    TextView tv_user_blance;
    @BindView(R.id.tv_user_income)
    TextView tv_user_income;
    @BindView(R.id.tv_apply_cash)
    TextView tv_apply_cash;
    @BindView(R.id.layout_all_task)
    LinearLayout layout_all_task;
    @BindView(R.id.layout_deliver_task)
    RelativeLayout layout_deliver_task;
    @BindView(R.id.layout_verify_task)
    RelativeLayout layout_verify_task;
    @BindView(R.id.layout_reward_task)
    RelativeLayout layout_reward_task;
    @BindView(R.id.layout_failed_task)
    RelativeLayout layout_failed_task;
    @BindView(R.id.layout_invite)
    LinearLayout layout_invite;
    @BindView(R.id.tv_invite)
    TextView tv_invite;
    @BindView(R.id.tv_account_manager)
    TextView tv_account_manager;
    @BindView(R.id.tv_follow)
    TextView tv_follow;
    @BindView(R.id.layout_contact)
    RelativeLayout layout_contact;
    @BindView(R.id.tv_contact_phone)
    TextView tv_contact_phone;
    @BindView(R.id.layout_email)
    RelativeLayout layout_email;
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_setting)
    TextView tv_setting;
    @BindView(R.id.tv_exit)
    TextView tv_exit;
    @BindView(R.id.tv_deliver_msg)
    TextView tv_deliver_msg;
    @BindView(R.id.tv_verify_msg)
    TextView tv_verify_msg;
    @BindView(R.id.tv_reward_msg)
    TextView tv_reward_msg;
    @BindView(R.id.tv_failed_msg)
    TextView tv_failed_msg;


    private static String TAG = "个人中心";
    private TrembleBasesOsDialog trembleBasesOsDialog;

    private MyResp userInfo;

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_my_fragment, container, false);
    }

    @Override
    public void init() {
        iv_top_back.setVisibility(View.GONE);
        tv_top_title.setText("个人中心");

        trembleBasesOsDialog = new TrembleBasesOsDialog(getActivity());
        trembleBasesOsDialog.setOnDialogClickListener(trembleListener);

    }

    @Override
    public void loadData() {
        HttpUtil.get(Constants.URL.GET_BRIEF_USER_INFO).subscribe(new BaseResponseObserver<MyResp>() {

            @Override
            public void success(MyResp result) {
                EBLog.i(TAG, result.toString());
                userInfo = result;
                showUserInfo();
            }

            @Override
            public void error(Response<MyResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    private void showUserInfo() {
        tv_msg.setText(userInfo.getMessage() + "");
        tv_user_name.setText(userInfo.getNickname());
        tv_user_identify.setText(userInfo.getRealInfoAudit());
        tv_user_blance.setText(userInfo.getAccountBalanceAmount() + "");
        tv_user_income.setText(userInfo.getGrossIncomeAmount() + "");
        tv_deliver_msg.setText(userInfo.getSubmit() + "");
        tv_verify_msg.setText(userInfo.getAudit() + "");
        tv_reward_msg.setText(userInfo.getPay() + "");
        tv_failed_msg.setText(userInfo.getUnPass() + "");
        tv_invite.setText(userInfo.getInviteMessage());
        tv_contact_phone.setText(userInfo.getCustomerPhone());
        tv_email.setText(userInfo.getEmail());

        if (GeneralUtils.isNotNullOrZeroLenght(userInfo.getAvatar()))
            PictureLoadUtil.loadPicture(getActivity(), userInfo.getAvatar(), iv_user_photo);

    }

    @OnClick({R.id.iv_top_menu, R.id.iv_user_photo, R.id.tv_user_identify, R.id.tv_apply_cash, R.id.layout_all_task, R.id.layout_deliver_task,
            R.id.layout_verify_task, R.id.layout_reward_task, R.id.layout_failed_task, R.id.layout_invite, R.id.tv_account_manager,
            R.id.tv_follow, R.id.layout_contact, R.id.layout_email, R.id.tv_setting, R.id.tv_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_photo: //个人资料
                openActivity(UserInfoActivity.class);
                break;
            case R.id.tv_exit: // 退出登录
                trembleBasesOsDialog.show();
                break;
            case R.id.tv_apply_cash: // 申请提现
                openActivity(ApplyCashActivity.class);
                break;
            case R.id.layout_invite: // 邀请好友
                openActivity(InviteActivity.class);
                break;
            case R.id.tv_account_manager: // 管理提现账户
                openActivity(ManageAccountActivity.class);
                break;
            case R.id.tv_follow: // 我的关注
                openActivity(MyFollowAvtivity.class);
                break;
            case R.id.layout_contact:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:021-68788170" ));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
                break;
            case R.id.tv_setting: // 设置
                openActivity(SettingActivity.class);
                break;
            case R.id.iv_top_menu://消息
                openActivity(MessageActivity.class);
                break;
        }
    }

    private void doLoginOut() {
        Map<String, String> params = new HashMap<>();
        params.put("token", (String) SpUtils.get(Constants.SPREF.TOKEN, ""));
        HttpUtil.post(Constants.URL.USER_LOGIN_OUT, params).subscribe(new BaseResponseObserver<CommonResp>() {
            @Override
            public void success(CommonResp result) {
                EBLog.i(TAG, result.toString());
                SpUtils.clear();
                openActivity(LoginActivity.class);
            }

            @Override
            public void error(Response<CommonResp> response) {
                ToastUtil.makeText(getActivity(), response.getDesc());
                EBLog.i(TAG, response.getCode() + "");
            }
        });
    }

    private TrembleBasesOsDialog.OnDialogClickListener trembleListener = new TrembleBasesOsDialog.OnDialogClickListener() {
        @Override
        public void onDialogClick(int resId) {
            if (resId == R.id.tv_exit)
                doLoginOut();
            trembleBasesOsDialog.dismiss();
        }
    };
}
