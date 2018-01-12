package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseFragment;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
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
    TextView tv_top_titlel;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
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
    LinearLayout layout_deliver_task;
    @BindView(R.id.layout_verify_task)
    LinearLayout layout_verify_task;
    @BindView(R.id.layout_reward_task)
    LinearLayout layout_reward_task;
    @BindView(R.id.layout_failed_task)
    LinearLayout layout_failed_task;
    @BindView(R.id.layout_invite)
    LinearLayout layout_invite;
    @BindView(R.id.tv_account_manager)
    TextView tv_account_manager;
    @BindView(R.id.tv_follow)
    TextView tv_follow;
    @BindView(R.id.layout_contact)
    LinearLayout layout_contact;
    @BindView(R.id.layout_email)
    LinearLayout layout_email;
    @BindView(R.id.tv_setting)
    TextView tv_setting;
    @BindView(R.id.tv_exit)
    TextView tv_exit;


    @Override
    public View setContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_my_fragment, container, false);
    }

    @Override
    public void init() {

    }

    @OnClick({R.id.iv_top_menu, R.id.iv_user_photo, R.id.tv_user_identify, R.id.tv_apply_cash, R.id.layout_all_task, R.id.layout_deliver_task,
              R.id.layout_verify_task, R.id.layout_reward_task, R.id.layout_failed_task, R.id.layout_invite, R.id.tv_account_manager,
              R.id.tv_follow, R.id.layout_contact, R.id.layout_email, R.id.tv_setting, R.id.tv_exit})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_user_photo:

                break;
            case R.id.tv_exit:
                doLoginOut();
                break;

            case R.id.tv_apply_cash: // 申请提现
                openActivity(ApplyCashActivity.class);
                break;
            case R.id.layout_invite: // 邀请好友
                openActivity(InviteActivity.class);
                break;
            case R.id.tv_follow:
                openActivity(MyFollowAvtivity.class);
                break;
            case R.id.tv_setting:
                openActivity(SettingActivity.class);
                break;

        }
    }

    private void doLoginOut(){
        Map<String, String>params = new HashMap<>();
        params.put("token", (String) SpUtils.get(Constants.SPREF.TOKEN, ""));
        HttpUtil.post(Constants.URL.USER_LOGIN_OUT, params).subscribe(new BaseResponseObserver<String>() {
            @Override
            public void success(String result) {
                SpUtils.clear();
                openActivity(LoginActivity.class);
                EBLog.i(this.getClass().getName(), result);
            }
        });
    }

}
