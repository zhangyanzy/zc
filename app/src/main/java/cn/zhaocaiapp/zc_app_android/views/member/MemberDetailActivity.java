package cn.zhaocaiapp.zc_app_android.views.member;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.ZcApplication;
import cn.zhaocaiapp.zc_app_android.adapter.common.MemberActivityAdapter;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.bean.response.member.MemberResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;
import cn.zhaocaiapp.zc_app_android.util.ShareUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.views.login.LoginActivity;

/**
 * @author 林子
 * @filename MemberDetailActivity.java
 * @data 2018-01-15 11:36
 */
public class MemberDetailActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.member_detail_refresh)
    RefreshLayout member_detail_refresh;
    @BindView(R.id.member_detail_recycler)
    RecyclerView member_detail_recycler;
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.tv_top_title)
    TextView tv_top_title;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
    //商家头像
    @BindView(R.id.member_detail_logo)
    SelectableRoundedImageView member_detail_logo;
    //商家名称
    @BindView(R.id.member_detail_name)
    TextView member_detail_name;
    //商家电话
    @BindView(R.id.member_detail_phone)
    TextView member_detail_phone;
    //商家地址
    @BindView(R.id.member_detail_area)
    TextView member_detail_area;
    //商家活动 全部
    @BindView(R.id.member_detail_state)
    TextView member_detail_state;
    //商家活动 未开始
    @BindView(R.id.member_detail_state_0)
    TextView member_detail_state_0;
    //商家活动 进行中
    @BindView(R.id.member_detail_state_1)
    TextView member_detail_state_1;
    //商家活动 已结束
    @BindView(R.id.member_detail_state_2)
    TextView member_detail_state_2;
    //商家关注 按钮
    @BindView(R.id.member_detail_follow_layout)
    LinearLayout member_detail_follow_layout;
    //商家关注 图片
    @BindView(R.id.member_detail_follow_img)
    ImageView member_detail_follow_img;
    //商家关注 文案
    @BindView(R.id.member_detail_follow_text)
    TextView member_detail_follow_text;

    private long memberId;//商家id
    private int pageNumber = 1;//分页
    private MemberResp memberResp = new MemberResp(); //商家详情
    private List<ActivityResp> activityRespList = new ArrayList<>();//活动列表

    private MemberActivityAdapter activityAdapter;
    private UMShareAPI umShareAPI;

    private static final String TAG = "商家详情";

    @Override
    public int getContentViewResId() {
        return R.layout.member_detail;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        umShareAPI = ZcApplication.getUMShareAPI();

        tv_top_title.setText(TAG);
        iv_top_menu.setVisibility(View.GONE);

        Bundle bd = this.getIntent().getExtras();
        memberId = bd.getLong("memberId");
        EBLog.i(TAG, "接收到商家Id：" + memberId);

        getMemberData();

        member_detail_recycler.setLayoutManager(new LinearLayoutManager(this));
        activityAdapter = new MemberActivityAdapter(this, activityRespList);
        member_detail_recycler.setAdapter(activityAdapter);
        activityAdapter.setOnItemClickListener(listener);

        member_detail_refresh.setOnRefreshListener(this);
        member_detail_refresh.setOnLoadmoreListener(this);
        member_detail_refresh.autoRefresh();
    }

    //获取商家信息
    private void getMemberData() {
        HttpUtil.get(String.format(Constants.URL.GET_MEMBER_DETAIL, memberId)).subscribe(new BaseResponseObserver<MemberResp>() {
            @Override
            public void success(MemberResp result) {
                memberResp = result;
                setMemberData();
            }

            @Override
            public void error(Response<MemberResp> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(MemberDetailActivity.this, response.getDesc());
            }
        });
    }

    private void setMemberData() {
        //商家logo
        PictureLoadUtil.loadPicture(this, memberResp.getLogo(), member_detail_logo);
        //商家名称
        member_detail_name.setText(memberResp.getName());
        //商家电话
        member_detail_phone.setText(memberResp.getPhone());
        //商家地址
        member_detail_area.setText(memberResp.getProvinceName() + memberResp.getCityName() + memberResp.getAreaName() + memberResp.getAddressDetail());
        //商家活动 总数
        member_detail_state.setText(String.valueOf(memberResp.getTotal()));
        //商家活动 未开始
        member_detail_state_0.setText(String.valueOf(memberResp.getBeforeLine()));
        //商家活动 进行中
        member_detail_state_1.setText(String.valueOf(memberResp.getOnLine()));
        //商家活动 已结束
        member_detail_state_2.setText(String.valueOf(memberResp.getOffLine()));
        if (memberResp.getIsFollow() == 1) { //已关注
            //商家关注 按钮
            member_detail_follow_layout.setBackground(getResources().getDrawable(R.drawable.member_follow_on));
            //商家关注 图片
            member_detail_follow_img.setVisibility(View.GONE);
            //商家关注 文案
            member_detail_follow_text.setText("已关注");
            member_detail_follow_text.setTextColor(getResources().getColor(R.color.colorLine));
        }
    }

    //获取商家活动列表
    private void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("memberId", String.valueOf(memberId));
        params.put("pageSize", String.valueOf(Constants.CONFIG.PAGE_SIZE));
        params.put("currentResult", String.valueOf((pageNumber - 1) * Constants.CONFIG.PAGE_SIZE));
        EBLog.i(TAG, params.toString());

        HttpUtil.get(Constants.URL.GET_ACTIVITY_LIST_MEMBER, params).subscribe(new BaseResponseObserver<List<ActivityResp>>() {
            @Override
            public void success(List<ActivityResp> result) {
                EBLog.i(TAG, result.toString());
                if (pageNumber == 1) {
                    activityRespList = result;
                    //恢复没有更多数据的原始状态
                    member_detail_refresh.resetNoMoreData();
                } else {
                    activityRespList.addAll(result);
                }
                activityAdapter.updata(activityRespList);

                if (result.size() < Constants.CONFIG.PAGE_SIZE) {
                    //完成加载并标记没有更多数据
                    member_detail_refresh.finishLoadmoreWithNoMoreData();
                }
                if (member_detail_refresh.isRefreshing())
                    member_detail_refresh.finishRefresh();
                else if (member_detail_refresh.isLoading())
                    member_detail_refresh.finishLoadmore();
            }

            @Override
            public void error(Response<List<ActivityResp>> response) {
                EBLog.e(TAG, response.getCode() + "");
                ToastUtil.makeText(MemberDetailActivity.this, response.getDesc());
            }
        });
    }

    // 关注/取消关注
    private void doFollow(int state) {
        Map<String, String> params = new HashMap<>();
        params.put("follow", state + "");
        EBLog.i("tag", params.toString());

        HttpUtil.post(String.format(Constants.URL.POST_MEMBER_FOLLOW, memberResp.getKid()), params).subscribe(new BaseResponseObserver<String>() {
            @Override
            public void success(String result) {
                EBLog.i("tag", result.toString());
                if (state == 1) {
                    memberResp.setIsFollow(state);
                    //商家关注 按钮
                    member_detail_follow_layout.setBackground(getResources().getDrawable(R.drawable.member_follow_on));
                    //商家关注 图片
                    member_detail_follow_img.setVisibility(View.GONE);
                    //商家关注 文案
                    member_detail_follow_text.setText("已关注");
                    member_detail_follow_text.setTextColor(getResources().getColor(R.color.colorLine));
                } else if (state == 0) {
                    memberResp.setIsFollow(state);
                    //商家关注 按钮
                    member_detail_follow_layout.setBackground(getResources().getDrawable(R.drawable.member_follow_off));
                    //商家关注 图片
                    member_detail_follow_img.setVisibility(View.VISIBLE);
                    //商家关注 文案
                    member_detail_follow_text.setText("关注");
                    member_detail_follow_text.setTextColor(getResources().getColor(R.color.colorWhite));
                }
            }

            @Override
            public void error(Response<String> response) {
                EBLog.e("tag", response.getCode() + "");
                ToastUtil.makeText(MemberDetailActivity.this, response.getDesc());
            }
        });
    }

    private MemberActivityAdapter.OnItemClickListener listener = new MemberActivityAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            String webUrl = String.format(Constants.URL.SHARE_ACTIVITY_URL, activityRespList.get(position).getKid());
            String shareTitle = activityRespList.get(position).getName();
            String desc = getString(R.string.share_desc);
            ShareUtil.init(MemberDetailActivity.this)
                    .setUrl(webUrl)
                    .setSourceId(R.mipmap.icon_launcher)
                    .setTitle(shareTitle)
                    .setDesc(desc);
            ShareUtil.openShare();
        }
    };

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageNumber = 1;
        EBLog.i(TAG, "refresh --pageNumber：" + pageNumber);
        initData();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        pageNumber = pageNumber + 1;
        EBLog.i(TAG, "loadmore -- pageNumber：" + pageNumber);
        initData();
    }

    @OnClick({R.id.iv_top_back, R.id.member_detail_follow_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                finish();
                break;
            case R.id.member_detail_follow_layout:
                if ((boolean) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.IS_LOGIN, false)) {
                    if (memberResp.getIsFollow() == 1) { //已关注
                        doFollow(0);
                    } else if (memberResp.getIsFollow() == 0) { //未关注
                        doFollow(1);
                    }
                } else {
                    openActivity(LoginActivity.class);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        umShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        umShareAPI.release();
    }
}
