package cn.zhaocaiapp.zc_app_android.views.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.MainActivity;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.CheckIsCommerclal;
import cn.zhaocaiapp.zc_app_android.bean.response.my.AccountResp;
import cn.zhaocaiapp.zc_app_android.bean.response.my.IsVia;
import cn.zhaocaiapp.zc_app_android.bean.response.my.MyResp;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.widget.TrembleBasesOsDialog;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.util.ToastUtils;
import cn.zhaocaiapp.zc_app_android.views.activity.MyActivity;
import cn.zhaocaiapp.zc_app_android.views.activity.MyActivityActivity;
import cn.zhaocaiapp.zc_app_android.views.activity.MyInComeActivity;
import cn.zhaocaiapp.zc_app_android.views.activity.NewSettingActivity;
import cn.zhaocaiapp.zc_app_android.views.activity.PersonalDataActivity;
import cn.zhaocaiapp.zc_app_android.views.activity.RealNameActivity;
import cn.zhaocaiapp.zc_app_android.views.commerclal.CASuccessActivity;
import cn.zhaocaiapp.zc_app_android.views.commerclal.MerchantCAActivity;
import cn.zhaocaiapp.zc_app_android.views.commerclal.MerchantHomeActivity;
import cn.zhaocaiapp.zc_app_android.views.login.LoginActivity;
import cn.zhaocaiapp.zc_app_android.views.my.FeedbackActivity;
import cn.zhaocaiapp.zc_app_android.views.my.InviteActivity;
import cn.zhaocaiapp.zc_app_android.views.my.ManageAccountActivity;
import cn.zhaocaiapp.zc_app_android.views.my.MessageActivity;
import cn.zhaocaiapp.zc_app_android.views.my.MyFollowAvtivity;
import cn.zhaocaiapp.zc_app_android.widget.CircleImageView;

/**
 * Created by zhangyan on 2018/8/31.
 * 个人中心
 */

public class NewMyFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.go_goAuthentication)
    TextView mGoAuthentication;
    @BindView(R.id.new_user_name)
    TextView mUserName;

    @BindView(R.id.my_income_icon_root)
    LinearLayout mIncomeRoot;
    @BindView(R.id.my_activity_root)
    LinearLayout mMyActivityRoot;
    @BindView(R.id.invite_friends_root)
    LinearLayout mInviteFriendsRoot;
    @BindView(R.id.imanagement_platform_root)
    LinearLayout mImanagementPlatformRoot;
    @BindView(R.id.platform_in_root)
    LinearLayout mPlatformInRoot;

    @BindView(R.id.my_attention_root)
    RelativeLayout mMyAttentionRoot;
    @BindView(R.id.contact_the_customer_root)
    RelativeLayout mContactCustomerRoot;
    @BindView(R.id.feedback_root)
    RelativeLayout mFeedBackRoot;
    @BindView(R.id.setting_root)
    RelativeLayout mSettingRoot;
    @BindView(R.id.company_mail_root)
    RelativeLayout mCompanyMail;
    @BindView(R.id.center_message)
    ImageView mMessage;
    @BindView(R.id.one_rmb)
    ImageView rmb;
    @BindView(R.id.platform_in_tv)
    TextView mPlatformIn;
    @BindView(R.id.message_count)
    TextView mMessageCount;
    @BindView(R.id.message_count_root)
    FrameLayout mMessageRoot;


    private static final String TAG = "个人中心";
    private TrembleBasesOsDialog trembleBasesOsDialog;

    private MyResp userInfo;
    private boolean isFirst = true;
    private AccountResp account;
    private IsVia mIsVia;
    private View view;
    private CircleImageView mPhoto;

    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getContext(), R.layout.fragment_my_new, null);
        return view;
    }

    @Override
    public void init() {
        mPhoto = view.findViewById(R.id.my_photo);
    }

    @Override
    public void loadData() {
        getUserInfo();
        checkIsCommerclal();
        getAccount();
    }

    private void checkIsCommerclal() {
        HttpUtil.get(Constants.URL.CHECK_IS_MERCHANT).subscribe(new BaseResponseObserver<IsVia>() {
            @Override
            public void success(IsVia checkIsCommerclal) {
                if (checkIsCommerclal != null) {
                    mIsVia = checkIsCommerclal;
                }
            }

            @Override
            public void error(Response<IsVia> response) {

            }
        });

    }

    private void getUserInfo() {
        HttpUtil.get(Constants.URL.GET_BRIEF_USER_INFO).subscribe(new BaseResponseObserver<MyResp>() {

            @Override
            public void success(MyResp result) {
                if (result != null) {
                    EBLog.i(TAG, result.toString());
                    userInfo = result;
                    showUserInfo();
                }

            }

            @Override
            public void error(Response<MyResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(getActivity(), response.getDesc());
            }
        });
    }

    private void showUserInfo() {
        if (userInfo != null) {
            mUserName.setText(userInfo.getNickname());
            if (userInfo.getMessage() == 0) {
                mMessageCount.setVisibility(View.GONE);
            } else {
                mMessageCount.setVisibility(View.VISIBLE);
                mMessageCount.setText(userInfo.getMessage() + "");
            }
            if (GeneralUtils.isNotNullOrZeroLenght(userInfo.getAvatar()))
                PictureLoadUtil.loadPicture(getActivity(), userInfo.getAvatar(), mPhoto);

            if (userInfo.getRealInfoAuditStatus() == 2) {
                //通过实名认证
                SpUtils.init(Constants.SPREF.FILE_USER_NAME).put(Constants.SPREF.IS_CERTIFICATION, true);
                mGoAuthentication.setText("已认证");
                mGoAuthentication.setBackgroundResource(R.mipmap.isgoauthentication);
                SpUtils.init(Constants.SPREF.FILE_USER_NAME).put(Constants.SPREF.IS_CERTIFICATION, true);
            } else if (userInfo.getRealInfoAuditStatus() == 3) {
                mGoAuthentication.setText("未通过");
                mGoAuthentication.setBackgroundResource(R.mipmap.center_authentication);
                SpUtils.init(Constants.SPREF.FILE_USER_NAME).put(Constants.SPREF.IS_CERTIFICATION, false);
            } else if (userInfo.getRealInfoAuditStatus() == 0) {
                mGoAuthentication.setText("未认证");
                mGoAuthentication.setBackgroundResource(R.mipmap.center_authentication);
                SpUtils.init(Constants.SPREF.FILE_USER_NAME).put(Constants.SPREF.IS_CERTIFICATION, false);
            }
            SpUtils.init(Constants.SPREF.FILE_USER_NAME).put(Constants.SPREF.INVITE_CODE, userInfo.getInviteCode());
            SpUtils.init(Constants.SPREF.FILE_USER_NAME).put(Constants.SPREF.SERVICE_PHONE, userInfo.getCustomerPhone());
            if (userInfo.getMemberAudit().equals("2")) {
                mPlatformIn.setText("进入商家");
            } else {
                mPlatformIn.setText("平台入驻");
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!isFirst) {
            getUserInfo();
            getAccount();
            checkIsCommerclal();
        } else {
            getUserInfo();
            getAccount();
            checkIsCommerclal();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isFirst = true;
    }

    //获取用户账户信息
    private void getAccount() {
        HttpUtil.get(Constants.URL.GET_ACCOUNT_INFO).subscribe(new BaseResponseObserver<AccountResp>() {

            @Override
            public void success(AccountResp accountResp) {
                EBLog.i(TAG, accountResp.toString());
                if (accountResp != null) {
                    account = accountResp;
                    if (accountResp.getCashAmount() == 1) {
                        rmb.setVisibility(View.VISIBLE);
                    } else {
                        rmb.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void error(Response<AccountResp> response) {
                EBLog.e(TAG, response.getCode() + "");
            }
        });
    }


    @OnClick({R.id.go_goAuthentication, R.id.my_income_icon_root, R.id.my_activity_root, R.id.invite_friends_root, R.id.imanagement_platform_root
            , R.id.platform_in_root, R.id.my_attention_root, R.id.contact_the_customer_root, R.id.feedback_root, R.id.setting_root,
            R.id.company_mail_root, R.id.center_message, R.id.my_photo, R.id.message_count_root
    })
    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        Intent intent = null;
        //判断用户是否登录
        if (!(boolean) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.IS_LOGIN, false)) {
            openActivity(LoginActivity.class);
            return;
        }
        switch (v.getId()) {
            case R.id.go_goAuthentication://去认证
                if (userInfo.getRealInfoAuditStatus() == 2) {
                    ToastUtils.showShortToast(getActivity(), "已认证通过");
                    return;
                }
                openActivity(RealNameActivity.class);
                break;
            case R.id.my_income_icon_root://我的收入
                intent = new Intent(getActivity(), MyInComeActivity.class);
                String balance = userInfo.getAccountBalanceAmount().toString();
                String grossIncomeAmount = userInfo.getGrossIncomeAmount().toString();
                intent.putExtra("balance", balance);
                intent.putExtra("grossIncomeAmount", grossIncomeAmount);
                startActivity(intent);
                break;
            case R.id.my_attention_root://我的关注
                openActivity(MyFollowAvtivity.class);
                break;
            case R.id.contact_the_customer_root:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "021—566669882"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.company_mail_root:
                Uri uri = Uri.parse("mailto: xxx@abc.com");
//                Uri uri = Uri.parse("yuezhi@jinxunwenhua.cn");
                intent = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(intent);
                break;
            case R.id.invite_friends_root:
                bundle.clear();
                bundle.putString("code", userInfo.getInviteCode());
                openActivity(InviteActivity.class, bundle);
                break;
            case R.id.feedback_root:
                openActivity(FeedbackActivity.class);
                break;
            case R.id.setting_root:
                openActivity(NewSettingActivity.class);
                break;
            case R.id.imanagement_platform_root:
                openActivity(ManageAccountActivity.class);
                break;
            case R.id.center_message:
                openActivity(MessageActivity.class);
                break;
            case R.id.platform_in_root:
                if (mIsVia.getAuditStatus() == 0) {//未提交
                    openActivity(MerchantCAActivity.class);
                } else if (mIsVia.getAuditStatus() == 1) {//待审核
                    openActivity(CASuccessActivity.class);
                } else if (mIsVia.getAuditStatus() == 2) {//已经通过 显示入驻红包
                    Intent mIntent = new Intent(getActivity(), MerchantHomeActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("mIsVia", mIsVia);
                    mIntent.putExtras(mBundle);
                    getActivity().startActivity(mIntent);
                } else if (mIsVia.getAuditStatus() == 3) { //未通过
                    Intent mIntent = new Intent(getActivity(), MerchantCAActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("mIsVia", mIsVia);
                    mIntent.putExtras(mBundle);
                    getActivity().startActivity(mIntent);
                } else { //通过 不显示入驻红包
                    openActivity(MerchantHomeActivity.class);
                }
                break;
            case R.id.my_photo:
                openActivity(PersonalDataActivity.class);
                break;
            case R.id.message_count_root:
                openActivity(MessageActivity.class);
                break;
            case R.id.my_activity_root:
                openActivity(MyActivityActivity.class);
                break;
            default:
                break;
        }
    }
}
