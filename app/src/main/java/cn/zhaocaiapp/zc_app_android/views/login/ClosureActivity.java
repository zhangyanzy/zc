package cn.zhaocaiapp.zc_app_android.views.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.my.AboutAUsResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.AppUtil;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.views.my.AboutUsActivity;

/**
 * Created by Administrator on 2018/2/5.
 */

public class ClosureActivity extends BaseActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_back;
    @BindView(R.id.tv_top_title)
    TextView tv_title;
    @BindView(R.id.iv_top_menu)
    ImageView iv_menu;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_email)
    TextView tv_email;

    private AboutAUsResp aboutAUsResp;

    private static final String TAG = "账号封禁";

    @Override
    public int getContentViewResId() {
        return R.layout.layout_closure_activity;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tv_title.setVisibility(View.GONE);
        iv_menu.setVisibility(View.GONE);

        initDate();
    }

    private void initDate() {
        String url = String.format(Constants.URL.ABOUT_US, (long) SpUtils.get(Constants.SPREF.USER_ID, 0l));
        HttpUtil.get(url).subscribe(new BaseResponseObserver<AboutAUsResp>() {

            @Override
            public void success(AboutAUsResp aboutAUsResp) {
                EBLog.i(TAG, aboutAUsResp.toString());
                ClosureActivity.this.aboutAUsResp = aboutAUsResp;
                showInfo();
            }

            @Override
            public void error(Response<AboutAUsResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(ClosureActivity.this, response.getDesc());
            }
        });
    }

    private void showInfo() {
        tv_email.setText(getString(R.string.e_mail) + aboutAUsResp.getEmail());
        tv_phone.setText(getString(R.string.service_phone) + aboutAUsResp.getPhone());
    }

    @OnClick({R.id.iv_top_back, R.id.tv_phone, R.id.tv_email})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.tv_phone:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:021-68788170"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.tv_email:
                Uri uri = Uri.parse("mailto: xxx@abc.com");
                intent = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(intent);
                break;
        }
    }
}
