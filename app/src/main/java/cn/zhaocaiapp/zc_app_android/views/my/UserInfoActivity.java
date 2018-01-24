package cn.zhaocaiapp.zc_app_android.views.my;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.MessageEvent;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.my.UserDetailResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.KeyBoardUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;

/**
 * Created by Administrator on 2018/1/12.
 */

public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.iv_top_back)
    ImageView iv_top_back;
    @BindView(R.id.tv_top_title)
    TextView tv_top_titlel;
    @BindView(R.id.iv_top_menu)
    ImageView iv_top_menu;
    @BindView(R.id.tab_title)
    TabLayout tab_user_info;
    @BindView(R.id.pager)
    ViewPager pager_user_info;

    private static final String TAG = "用户详细信息";

     private String[]tabTitles = new String[]{"个人资料", "实名信息", "相关信息", "个人标签", "修改密码"};
     private Map<Integer, Fragment> fragments = new HashMap<>();

     private UserDetailResp.ActivityInfoBean activityInfoBean;
     private UserDetailResp.BaseInfoBean baseInfoBean;
     private UserDetailResp.RealInfoBean realInfoBean;

    @Override
    public int getContentViewResId() {
        return R.layout.layout_tab_fragment;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        iv_top_menu.setVisibility(View.GONE);

        pager_user_info.setOffscreenPageLimit(tabTitles.length);
        pager_user_info.setAdapter(new UserinfoFragmentPagerAdapter(getSupportFragmentManager()));
        tab_user_info.setupWithViewPager(pager_user_info);
        pager_user_info.setCurrentItem(0);
    }

    private void initData(){
        HttpUtil.get(Constants.URL.GET_USER_INFO_DETAIL).subscribe(new BaseResponseObserver<UserDetailResp>() {

            @Override
            public void success(UserDetailResp userDetailResp) {
                 activityInfoBean = userDetailResp.getActivtiyInfo();
                 baseInfoBean = userDetailResp.getBaseInfo();
                 realInfoBean = userDetailResp.getRealInfo();

                EventBus.getDefault().post(new MessageEvent<UserDetailResp.BaseInfoBean>(baseInfoBean));
                EventBus.getDefault().post(new MessageEvent<UserDetailResp.RealInfoBean>(realInfoBean));
                EventBus.getDefault().post(new MessageEvent<UserDetailResp.ActivityInfoBean>(activityInfoBean));
            }

            @Override
            public void error(Response<UserDetailResp> response) {
                EBLog.e(TAG, response.getCode()+"");
                ToastUtil.makeText(UserInfoActivity.this, response.getDesc());
            }
        });
    }

    private class UserinfoFragmentPagerAdapter extends FragmentPagerAdapter {
        public UserinfoFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return getFragment(position);
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

    }

    private Fragment getFragment(int position){
        Fragment fragment = fragments.get(position);
        if (fragment == null){
            switch (position){
                case 0: // 个人资料
                    fragment = new UserInfoFragment();
                    break;
                case 1: // 实名信息
                    fragment = new IdentifyFragment();
                    break;
                case 2: // 活动相关信息
                    fragment = new TaskRelativeFragment();
                    break;
                case 3: // 标签信息
                    fragment = new LabelFragment();
                    break;
                case 4: // 修改密码
                    fragment = new RevisePassFragment();
                    break;
            }
            fragments.put(position, fragment);
        }
        return fragment;
    }

    @OnClick({R.id.iv_top_back})
    public void onClick(View view){
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KeyBoardUtils.closeKeybord(tv_top_titlel, this);
        return super.onTouchEvent(event);
    }

}
