package cn.zhaocaiapp.zc_app_android.views.member;

import android.os.Bundle;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;

/**
 * @author 林子
 * @filename MemberDetailActivity.java
 * @data 2018-01-15 11:36
 */
public class MemberDetailActivity extends BaseActivity {

    private long memberId;//商家

    @Override
    public int getContentViewResId() {
        return R.layout.member_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        Bundle bd = this.getIntent().getExtras();
        memberId = bd.getLong("memberId");
        EBLog.i("tag", "接受到商家Id：" + memberId);
    }
}
