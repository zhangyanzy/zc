package cn.zhaocaiapp.zc_app_android.adapter.my;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.google.zxing.client.result.VCardResultParser;
import com.joooonho.SelectableRoundedImageView;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.bean.response.common.FinishUserResp;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.LocationUtil;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.widget.CircleImageView;
import cn.iwgang.countdownview.CountdownView.OnCountdownEndListener;

/**
 * Created by Administrator on 2018/2/2.
 */

public class MyActivityAdapter extends RecyclerView.Adapter<MyActivityAdapter.ViewHolder> {
    public static final int MYACTIVITY_ALL = 1001;
    public static final int MYACTIVITY_DELIVER = 1002;
    public static final int MYACTIVITY_VERIFY = 1003;
    public static final int MYACTIVITY_REWARD = 1004;
    public static final int MYACTIVITY_UNPASS = 1005;

    private Context context;
    private List<ActivityResp> items;
    private int state; //活动状态
    private OnItemClickListener listener;

    public MyActivityAdapter(Context context, List<ActivityResp> items, int state) {
        this.context = context;
        this.items = items;
        this.state = state;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_activitys_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ActivityResp activity = items.get(position);
        //商家logo
        PictureLoadUtil.loadPicture(context, activity.getMemberImg(), holder.iv_logo);
        //活动广告图
        PictureLoadUtil.loadPicture(context, activity.getActivityImage1(), holder.activity_item_img);
        //商家名称
        holder.tv_name.setText(activity.getMemberName());
        //活动进行状态
        holder.item_img_state.setText(getOnlineState(activity.getOnline()));
        //活动类型
        holder.item_img_type.setText(getActivityType(activity.getActivityForm()));
        //活动名称
        SpannableStringBuilder spannableString = new SpannableStringBuilder("#" + getActivityType(activity.getActivityForm()) + "#" + activity.getName());
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.item_text_title.setText(spannableString);
        //活动地点距当前距离
        if (activity.getActivityForm() != 1 && activity.getActivityForm() != 2 && LocationUtil.getGps().getOpen()) {
            holder.item_text_area_text.setText(getDistance(activity));
        }
        //活动剩余额度
        holder.item_text_amount.setText(GeneralUtils.getBigDecimalToTwo(activity.getLeftAmount()));
        //剩余额度进度条
        double leftAmount = activity.getLeftAmount().doubleValue();
        double totalAmount = activity.getTotalAmount().doubleValue();
        double amount = (leftAmount / totalAmount) * 100;
        holder.item_text_amount_progress.setProgress((int) amount);
        //已领取人数
        holder.item_text_number.setText(activity.getFinishCount() + "");
        //已领取人数进度条
        double finishCount = activity.getFinishCount().intValue();
        double getMaxUser = activity.getMaxUser().intValue();
        double account = (finishCount / getMaxUser) * 100;
        holder.item_text_number_progress.setProgress((int) account);
        //活动奖励金额
        holder.item_text_reward.setText(GeneralUtils.getBigDecimalToTwo(activity.getRewardAmount()));
        if ((boolean) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.IS_LOGIN, false)) {
            if (activity.getFollow())
                holder.item_text_collection.setImageResource(R.mipmap.collection_on);
            else
                holder.item_text_collection.setImageResource(R.mipmap.collection_off);
        }
        isContentVisible(activity.getActivityForm(), holder);
        setActivityButton(activity.getActivityStatus(), position, holder);
        showUserPhoto(context, activity.getUserList(), holder);

        //点击活动大图
        holder.activity_item_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getLayoutPosition(), holder.activity_item_img);
            }
        });

        //点击提交活动按钮
        holder.tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getLayoutPosition(), holder.tv_submit);
            }
        });

        //点击取消按钮
        holder.tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getLayoutPosition(), holder.tv_cancel);
            }
        });

        //点击领钱按钮
        holder.tv_reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getLayoutPosition(), holder.tv_reward);
            }
        });

        //点击活动内容
        holder.layout_activity_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getLayoutPosition(), holder.layout_activity_content);
            }
        });

        //点击商家logo
        holder.iv_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getLayoutPosition(), holder.iv_logo);
            }
        });

        //点击商家名称
        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getLayoutPosition(), holder.tv_name);
            }
        });

        //点击关注活动
        holder.item_text_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getLayoutPosition(), holder.item_text_collection);
            }
        });

        //点击分享活动
        holder.item_text_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getLayoutPosition(), holder.item_text_share);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void refresh(List<ActivityResp> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    private void showUserPhoto(Context context, List<FinishUserResp> userList, ViewHolder holder) {
        for (int i = 0; i < userList.size(); i++) {
            ImageView imageView = (ImageView) holder.layout_user.getChildAt(i);
            imageView.setVisibility(View.VISIBLE);
            if (GeneralUtils.isNotNullOrZeroLenght(userList.get(i).getAvatar())) {
                PictureLoadUtil.loadPicture(context, userList.get(i).getAvatar(), imageView);
            }
        }
    }

    //计算活动地点距当前距离
    private String getDistance(ActivityResp activity) {
        //起始位置 我的位置
        DPoint startGps = new DPoint();
        startGps.setLatitude(LocationUtil.getGps().getLatitude());
        startGps.setLongitude(LocationUtil.getGps().getLongitude());
        //结束位置 活动位置
        DPoint stopGps = new DPoint(31.235221, 121.499508);
        if (GeneralUtils.isNotNull(activity.getLatitude()) && GeneralUtils.isNotNull(activity.getLongitude())){
            stopGps.setLatitude(activity.getLatitude().doubleValue());
            stopGps.setLongitude(activity.getLongitude().doubleValue());
        }
        //两点距离
        float areaText = CoordinateConverter.calculateLineDistance(startGps, stopGps);
        return (areaText > 1000 ? String.format("%.1f", (areaText / 1000)) + "km" : String.format("%.1f", (areaText)) + "m");
    }

    //根据活动类型和状态判断控件内容是否显示
    private void isContentVisible(int activityType, ViewHolder holder) {
        switch (activityType) {
            case 0: //线下活动
                holder.item_text_area_logo.setVisibility(View.VISIBLE);
                holder.item_text_area_text.setVisibility(View.VISIBLE);
                holder.item_img_vide.setVisibility(View.GONE);
                break;
            case 1: //视频活动
                holder.item_text_area_logo.setVisibility(View.GONE);
                holder.item_text_area_text.setVisibility(View.GONE);
                holder.item_img_vide.setVisibility(View.VISIBLE);
                break;
            case 2: //问卷活动
                holder.item_text_area_logo.setVisibility(View.GONE);
                holder.item_text_area_text.setVisibility(View.GONE);
                holder.item_img_vide.setVisibility(View.GONE);
                break;
        }
    }

    //设置活动列表中按钮是否显示
    private void setActivityButton(int activityStatus, int position, ViewHolder holder) {
        switch (activityStatus) { //0待交付 1待审核 2待领钱 3未通过 4已完成 5已关闭
            case 0:
                holder.tv_state.setText("待交付");
                holder.tv_reward.setVisibility(View.GONE);
                holder.tv_submit.setVisibility(View.VISIBLE);
                holder.tv_cancel.setVisibility(View.VISIBLE);

                setCountdown(position, holder);
                holder.count_down_time.setOnCountdownEndListener(countdownEndListener);
                break;
            case 1:
                holder.tv_state.setText("待审核");
                holder.tv_cancel.setVisibility(View.GONE);
                holder.tv_submit.setVisibility(View.GONE);
                holder.tv_reward.setVisibility(View.GONE);
                break;
            case 2:
                holder.tv_state.setText("待领钱");
                holder.tv_cancel.setVisibility(View.GONE);
                holder.tv_submit.setVisibility(View.GONE);
                holder.tv_reward.setVisibility(View.VISIBLE);
                break;
            case 3:
                holder.tv_state.setText("未通过");
                holder.tv_cancel.setVisibility(View.GONE);
                holder.tv_submit.setVisibility(View.VISIBLE);
                holder.tv_reward.setVisibility(View.GONE);

                setCountdown(position, holder);
                holder.count_down_time.setOnCountdownEndListener(countdownEndListener);
                break;
            case 4:
                holder.tv_state.setText("已完成");
                holder.tv_cancel.setVisibility(View.GONE);
                holder.tv_submit.setVisibility(View.GONE);
                holder.tv_reward.setVisibility(View.GONE);
                break;
            case 5:
                holder.tv_state.setText("已关闭");
                holder.tv_cancel.setVisibility(View.GONE);
                holder.tv_submit.setVisibility(View.GONE);
                holder.tv_reward.setVisibility(View.GONE);
                break;
        }
    }

    private void setCountdown(int position, ViewHolder holder){
        //启动倒计时
        long countdownTime = items.get(position).getDeadLine().getTime() - items.get(position).getNowDate().getTime();
        if (countdownTime > 0) {
            holder.tv_subscrib.setVisibility(View.VISIBLE);
            holder.count_down_time.setVisibility(View.VISIBLE);
            holder.count_down_time.setTag(holder.count_down_time.getId(), position);
            holder.count_down_time.start(countdownTime);
        }
    }

    private OnCountdownEndListener countdownEndListener = new OnCountdownEndListener() {
        @Override
        public void onEnd(CountdownView cv) {
            if (null != cv.getTag(R.id.count_down_time)) {

            }
        }
    };

    //返回活动进行状态
    private String getOnlineState(int i) {
        String state = "";
        switch (i) {
            case 0:
                state = context.getString(R.string.activity_state_0);
                break;
            case 1:
                state = context.getString(R.string.activity_state_1);
                break;
            case 2:
                state = context.getString(R.string.activity_state_2);
                break;
            default:
                state = context.getString(R.string.activity_state_0);
        }
        return state;
    }

    //返回活动类型
    private String getActivityType(int i) {
        String type = "";
        switch (i) {
            case 0:
                type = context.getString(R.string.activity_type_0);
                break;
            case 1:
                type = context.getString(R.string.activity_type_1);
                break;
            case 2:
                type = context.getString(R.string.activity_type_2);
                break;
            default:
                type = context.getString(R.string.activity_type_0);
        }
        return type;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //商家logo
        @BindView(R.id.iv_logo)
        CircleImageView iv_logo;
        //商家名称
        @BindView(R.id.tv_name)
        TextView tv_name;
        //用户活动交付状态
        @BindView(R.id.tv_state)
        TextView tv_state;
        //活动广告图
        @BindView(R.id.activity_item_img_i)
        SelectableRoundedImageView activity_item_img;
        //活动进行状态
        @BindView(R.id.activity_item_img_state)
        TextView item_img_state;
        //活动类型
        @BindView(R.id.activity_item_img_type)
        TextView item_img_type;
        //视频活动播放按钮
        @BindView(R.id.activity_item_img_vide)
        ImageView item_img_vide;
        //活动名称
        @BindView(R.id.activity_item_text_title)
        TextView item_text_title;
        //参与用户
        @BindView(R.id.layout_user)
        LinearLayout layout_user;
        //参与用户头像
        @BindView(R.id.activity_item_text_user0)
        CircleImageView item_text_user0;
        //参与用户头像
        @BindView(R.id.activity_item_text_user1)
        CircleImageView item_text_user1;
        //参与用户头像
        @BindView(R.id.activity_item_text_user2)
        CircleImageView item_text_user2;
        //活动定位标识
        @BindView(R.id.activity_item_text_area_logo)
        ImageView item_text_area_logo;
        //活动地点距当前距离
        @BindView(R.id.activity_item_text_area_text)
        TextView item_text_area_text;
        //关注
        @BindView(R.id.activity_item_text_collection)
        ImageView item_text_collection;
        //分享
        @BindView(R.id.activity_item_text_share)
        ImageView item_text_share;
        //活动剩余额度
        @BindView(R.id.activity_item_text_amount)
        TextView item_text_amount;
        //剩余额度进度条
        @BindView(R.id.activity_item_text_amount_progress)
        ProgressBar item_text_amount_progress;
        //已领取活动人数
        @BindView(R.id.activity_item_text_number)
        TextView item_text_number;
        //已领取人数进度条
        @BindView(R.id.activity_item_text_number_progress)
        ProgressBar item_text_number_progress;
        //活动奖励金额
        @BindView(R.id.activity_item_text_reward)
        TextView item_text_reward;
        @BindView(R.id.tv_subscrib)
        TextView tv_subscrib;
        //活动截止倒计时
        @BindView(R.id.count_down_time)
        CountdownView count_down_time;
        //提交
        @BindView(R.id.tv_submit)
        TextView tv_submit;
        //取消
        @BindView(R.id.tv_cancel)
        TextView tv_cancel;
        //领钱
        @BindView(R.id.tv_reward)
        TextView tv_reward;
        //进度条
        @BindView(R.id.activity_item_text_centent)
        LinearLayout activity_item_text_centent;
        //活动内容
        @BindView(R.id.layout_activity_content)
        LinearLayout layout_activity_content;

        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }
}
