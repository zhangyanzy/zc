package cn.zhaocaiapp.zc_app_android.views.member;

import android.os.Bundle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.bean.response.member.MemberResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;

/**
 * @author 林子
 * @filename MemberDetailActivity.java
 * @data 2018-01-15 11:36
 */
public class MemberDetailActivity extends BaseActivity {

    private long memberId;//商家
    private List<ActivityResp> activityRespList; //活动列表


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

        Map<String, String> params = new HashMap<>();
        params.put("memberId", String.valueOf(memberId));
        params.put("pageSize", "10");
        params.put("currentResult", "0");

        HttpUtil.get(Constants.URL.GET_ACTIVITY_LIST_MEMBER, params).subscribe(new BaseResponseObserver<List<ActivityResp>>() {
            @Override
            public void success(List<ActivityResp> result) {
                activityRespList = result;
                //memberAdapter.updata(memberRespList);
                EBLog.i("tag", result.toString());
                //member_refresh_layout.finishRefresh();
            }

            @Override
            public void error(Response<List<ActivityResp>> response) {

            }

        });
    }
}
