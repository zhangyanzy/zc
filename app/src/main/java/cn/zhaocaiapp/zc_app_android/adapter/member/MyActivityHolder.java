package cn.zhaocaiapp.zc_app_android.adapter.member;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.Date;

import cn.iwgang.countdownview.CountdownView;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.base.ClickableViewHolder;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;

/**
 * Created by admin on 2018/9/17.
 */

public class MyActivityHolder extends ClickableViewHolder {


    ImageView mPhotoTitle;//封面照片
    TextView mActivityName;//活动名称
    TextView mActivitylocation;//活动城市
    ImageView mActivityUserPhoto;//用户头像
    TextView mActivityUserName;//用户名
    TextView mActivityTitle;//活动标题
    ImageView mActivityFinish;//活动结束图标
    ProgressBar mActivityBar;//活动进度条
    TextView mActivityBarNum;//活动进度数
    TextView mActivityNum;//奖励金额
    TextView mActivityOverDay;//剩余天数
    ImageView mActivityAreaIcon;
    TextView tv_state;
    TextView tv_reward;   //点击领钱按钮
    TextView tv_submit;//点击提交活动按钮
    TextView tv_cancel; //点击取消按钮
    CountdownView count_down_time;
    TextView tv_subscrib;
    RelativeLayout delivery_root;

    public MyActivityHolder(View itemView) {
        super(itemView);
        mPhotoTitle = $(R.id.item_activity_photo);
        mActivityName = $(R.id.item_activity_name);
        mActivitylocation = $(R.id.item_activity_location_text);
        mActivityUserPhoto = $(R.id.item_activity_user_photo);
        mActivityUserName = $(R.id.item_activity_user_name);
        mActivityTitle = $(R.id.item_activity_title);
        mActivityFinish = $(R.id.item_activity_is_finish);
        mActivityBar = $(R.id.item_activity_text_amount_progress);
        mActivityBarNum = $(R.id.item_activity_text_amount_progress_num);
        mActivityNum = $(R.id.item_activity_reward_num);
        mActivityOverDay = $(R.id.item_activity_over_day);
        mActivityAreaIcon = $(R.id.activity_area_area);
        tv_state = $(R.id.tv_state);
        tv_reward = $(R.id.tv_reward);
        tv_submit = $(R.id.tv_submit);
        tv_cancel = $(R.id.tv_cancel);
        count_down_time = $(R.id.count_down_time);
        tv_subscrib = $(R.id.tv_subscrib);
        delivery_root = $(R.id.delivery_root);


    }

    public void createItem(ActivityResp entity) {
        if (entity != null) {

        }

    }

}
