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

import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.MessageEvent;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.CommonResp;
import cn.zhaocaiapp.zc_app_android.bean.response.my.MyResp;
import cn.zhaocaiapp.zc_app_android.bean.response.my.UserDetailResp;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.widget.TrembleBasesOsDialog;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.ActivityUtil;
import cn.zhaocaiapp.zc_app_android.util.AppUtil;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.views.activity.MyActivity;
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
    TextView tv_user_balance;
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


    private static final String TAG = "个人中心";
    private TrembleBasesOsDialog trembleBasesOsDialog;

    private MyResp userInfo;

    private static final int REQUEST_CODE = 5001;


    @Override
    public void onResume() {
        super.onResume();
        loadData();
        EBLog.i(TAG, "---onResume---加载个人中心数据");
    }

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_my_fragment, container, false);
    }

    @Override
    public void init() {
        iv_top_back.setVisibility(View.GONE);
        tv_top_title.setText("个人中心");

        //初始化退出登录弹窗
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

    //展示个人中心首页信息
    private void showUserInfo() {
        tv_user_name.setText(userInfo.getNickname());
        tv_user_identify.setText(userInfo.getRealInfoAudit());
        tv_user_balance.setText(userInfo.getAccountBalanceAmount() + "元");
        tv_user_income.setText(userInfo.getGrossIncomeAmount() + "元");
        tv_invite.setText(userInfo.getInviteMessage());
        tv_contact_phone.setText(userInfo.getCustomerPhone());
        tv_email.setText(userInfo.getEmail());
        if (userInfo.getMessage() > 0) {
            tv_msg.setText(userInfo.getMessage() + "");
            tv_msg.setVisibility(View.VISIBLE);
        }
        if (userInfo.getSubmit() > 0) {
            tv_deliver_msg.setText(userInfo.getSubmit() + "");
            tv_deliver_msg.setVisibility(View.VISIBLE);
        }
        if (userInfo.getAudit() > 0) {
            tv_verify_msg.setText(userInfo.getAudit() + "");
            tv_verify_msg.setVisibility(View.VISIBLE);
        }
        if (userInfo.getPay() > 0) {
            tv_reward_msg.setText(userInfo.getPay() + "");
            tv_reward_msg.setVisibility(View.VISIBLE);
        }
        if (userInfo.getUnPass() > 0) {
            tv_failed_msg.setText(userInfo.getUnPass() + "");
            tv_failed_msg.setVisibility(View.VISIBLE);
        }
        if (GeneralUtils.isNotNullOrZeroLenght(userInfo.getAvatar()))
            PictureLoadUtil.loadPicture(getActivity(), userInfo.getAvatar(), iv_user_photo);
        if (userInfo.getRealInfoAuditStatus() == 2)//通过实名认证
            SpUtils.put(Constants.SPREF.IS_CERTIFICATION, true);

        SpUtils.put(Constants.SPREF.INVITE_CODE, userInfo.getInviteCode());
    }

    @OnClick({R.id.iv_top_menu, R.id.iv_user_photo, R.id.tv_user_identify, R.id.tv_apply_cash, R.id.layout_all_task, R.id.layout_deliver_task,
            R.id.layout_verify_task, R.id.layout_reward_task, R.id.layout_failed_task, R.id.layout_invite, R.id.tv_account_manager,
            R.id.tv_follow, R.id.layout_contact, R.id.layout_email, R.id.tv_setting, R.id.tv_exit, R.id.tv_user_balance, R.id.tv_user_income})
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        Intent intent = null;
        // 用户未登录，跳转到登陆界面
        if (!(boolean) SpUtils.get(Constants.SPREF.IS_LOGIN, false)) {
            openActivity(LoginActivity.class);
            return;
        }

        switch (view.getId()) {
            case R.id.iv_user_photo: //个人资料
                openActivity(UserInfoActivity.class);
                break;
            case R.id.tv_exit: // 退出登录
                trembleBasesOsDialog.show();
                break;
            case R.id.tv_apply_cash: // 申请提现
                bundle.clear();
                bundle.putString("balance", userInfo.getAccountBalanceAmount() + "");
                openActivity(ApplyCashActivity.class, bundle);
                break;
            case R.id.layout_invite: // 邀请好友
                bundle.clear();
                bundle.putString("code", userInfo.getInviteCode());
                openActivity(InviteActivity.class, bundle);
                break;
            case R.id.tv_account_manager: // 管理提现账户
                openActivity(ManageAccountActivity.class);
                break;
            case R.id.tv_follow: // 我的关注
                openActivity(MyFollowAvtivity.class);
                break;
            case R.id.layout_contact: // 联系客服
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + userInfo.getCustomerPhone()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
                break;
            case R.id.tv_setting: // 设置
                openActivity(SettingActivity.class);
                break;
            case R.id.iv_top_menu://消息
                openActivity(MessageActivity.class);
                break;
            case R.id.layout_all_task: //全部活动
                bundle.clear();
                bundle.putInt("position", 0);
                openActivity(MyActivity.class, bundle);
                break;
            case R.id.layout_deliver_task: // 待交付活动
                bundle.clear();
                bundle.putInt("position", 1);
                openActivity(MyActivity.class, bundle);
                break;
            case R.id.layout_verify_task: // 待审核活动
                bundle.clear();
                bundle.putInt("position", 2);
                openActivity(MyActivity.class, bundle);
                break;
            case R.id.layout_reward_task: // 待领钱活动
                bundle.clear();
                bundle.putInt("position", 3);
                openActivity(MyActivity.class, bundle);
                break;
            case R.id.layout_failed_task: // 未通过活动
                bundle.clear();
                bundle.putInt("position", 4);
                openActivity(MyActivity.class, bundle);
                break;
            case R.id.layout_email: // 发送邮件
                Uri uri = Uri.parse("mailto: xxx@abc.com");
                intent = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(intent);
                break;
            case R.id.tv_user_balance: // 收支明细
            case R.id.tv_user_income:
                openActivity(IncomeActivity.class);
                break;

        }
    }

    //退出登录
    private void doLoginOut() {
        Map<String, String> params = new HashMap<>();
        params.put("token", (String) SpUtils.get(Constants.SPREF.TOKEN, ""));
        HttpUtil.post(Constants.URL.USER_LOGIN_OUT, params).subscribe(new BaseResponseObserver<CommonResp>() {
            @Override
            public void success(CommonResp result) {
                EBLog.i(TAG, result.toString());

                deleteAlias();
                SpUtils.clear();

                Bundle bundle = new Bundle();
                bundle.putBoolean("signOut", true);
                openActivity(LoginActivity.class, bundle);
            }

            @Override
            public void error(Response<CommonResp> response) {
                ToastUtil.makeText(getActivity(), response.getDesc());
                EBLog.i(TAG, response.getCode() + "");
            }
        });
    }

    private void deleteAlias() {
        PushAgent pushAgent = PushAgent.getInstance(getActivity());
        pushAgent.deleteAlias((String) SpUtils.get(Constants.SPREF.ALIAS, ""), "alias_user", new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean b, String s) {
                EBLog.i(TAG, s);
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
