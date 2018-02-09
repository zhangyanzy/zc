package cn.zhaocaiapp.zc_app_android.views.my;

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
import cn.zhaocaiapp.zc_app_android.views.common.PrivacyActivity;
import cn.zhaocaiapp.zc_app_android.views.login.ClosureActivity;

/**
 * Created by Administrator on 2018/1/24.
 */

public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.tv_top_title)
    TextView tv_top_title;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.tv_privacy)
    TextView tv_privacy;
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_address)
    TextView tv_address;

    private AboutAUsResp aboutAUsResp;


    private static final String TAG = "关于我们";


    @Override
    public int getContentViewResId() {
        return R.layout.layout_about_us;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tv_top_title.setText("关于我们");
        initDate();
    }

    private void initDate() {
        String url = String.format(Constants.URL.ABOUT_US, (long) SpUtils.get(Constants.SPREF.USER_ID, 0l));
        HttpUtil.get(url).subscribe(new BaseResponseObserver<AboutAUsResp>() {

            @Override
            public void success(AboutAUsResp aboutAUsResp) {
                EBLog.i(TAG, aboutAUsResp.toString());
                AboutUsActivity.this.aboutAUsResp = aboutAUsResp;
                showInfo();
            }

            @Override
            public void error(Response<AboutAUsResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(AboutUsActivity.this, response.getDesc());
            }
        });
    }

    private void showInfo() {
        tv_version.setText("版本：V" + AppUtil.getAppVersionName(this));
        tv_email.setText(aboutAUsResp.getEmail());
        tv_phone.setText(aboutAUsResp.getPhone());
        tv_address.setText(aboutAUsResp.getAddress());
    }

    @OnClick({R.id.iv_top_back, R.id.tv_phone, R.id.tv_email, R.id.tv_privacy})
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
            case R.id.tv_privacy:
                openActivity(PrivacyActivity.class);
                break;
        }
    }
}
