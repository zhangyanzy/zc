package cn.zhaocaiapp.zc_app_android.adapter.common;

import android.content.Context;
import android.content.Intent;
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
import com.joooonho.SelectableRoundedImageView;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.adapter.my.MyActivityAdapter;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.bean.response.member.MemberResp;
import cn.zhaocaiapp.zc_app_android.capabilities.dialog.widget.NormalDialog;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.DialogUtil;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import cn.zhaocaiapp.zc_app_android.util.LocationUtil;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;
import cn.zhaocaiapp.zc_app_android.util.ShareUtil;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.util.ToastUtil;
import cn.zhaocaiapp.zc_app_android.views.common.ActivityDetailActivity;
import cn.zhaocaiapp.zc_app_android.views.login.LoginActivity;
import cn.zhaocaiapp.zc_app_android.views.member.MemberDetailActivity;


/**
 * @author 林子
 * @filename MemberActivity.java
 * @data 2018-01-05 17:59
 */
public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    private List<ActivityResp> list;
    private Context context;
    private OnItemCliclkListener listener;
    private int type; //1首页默认列表 2商家详情列表
    private MemberResp memberResp; //商家详情

    public ActivityAdapter(Context context, List<ActivityResp> list) {
        this.list = list;
        this.context = context;
        this.type = 1;
    }

    public ActivityAdapter(Context context, List<ActivityResp> list, MemberResp memberResp) {
        this.list = list;
        this.context = context;
        this.type = 2;
        this.memberResp = memberResp;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        EBLog.i("tag", String.valueOf(viewType));
        View view;
        ViewHolder viewHolder;
        if (type == 2 && viewType == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.member_detail_header, parent, false);
            viewHolder = new ViewHolderMember(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.activity_item, parent, false);
            viewHolder = new ViewHolderActivity(view);
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHolderActivity viewHolderActivity = (ViewHolderActivity) holder;
        //商家图片
        PictureLoadUtil.loadPicture(context, list.get(position).getMemberImg(), viewHolderActivity.activity_item_member_logo);
        //商家名称
        viewHolderActivity.activity_item_member_name.setText(list.get(position).getMemberName());
        //活动区域
        viewHolderActivity.activity_item_member_area.setText(list.get(position).getCityName());
        //活动图片
        PictureLoadUtil.loadPicture(context, list.get(position).getActivityImage1(), viewHolderActivity.activity_item_img_i);
        //活动状态
        viewHolderActivity.activity_item_img_state.setText(getOnlineString(list.get(position).getOnline()));
        //活动类型
        viewHolderActivity.activity_item_img_type.setText(getActivityFormString(list.get(position).getActivityForm()));
        //活动名称
        SpannableStringBuilder spannableString = new SpannableStringBuilder("#" + getActivityFormString(list.get(position).getActivityForm()) + "#" + list.get(position).getName());
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolderActivity.activity_item_text_title.setText(spannableString);
//        //视频活动播放
//        if (list.get(position).getActivityForm() == 1) {
//            viewHolderActivity.activity_item_img_vide.setVisibility(View.VISIBLE);
//            viewHolderActivity.tv_member_area_logo.setVisibility(View.GONE);
//        } else {
//            viewHolderActivity.activity_item_img_vide.setVisibility(View.INVISIBLE);
//            viewHolderActivity.tv_member_area_logo.setVisibility(View.VISIBLE);
//        }
        //参与人头像
        viewHolderActivity.activity_item_text_user0.setVisibility(View.INVISIBLE);
        viewHolderActivity.activity_item_text_user1.setVisibility(View.INVISIBLE);
        viewHolderActivity.activity_item_text_user2.setVisibility(View.INVISIBLE);
        if (GeneralUtils.isNotNull(list.get(position).getUserList()) && list.get(position).getUserList().size() > 0) {
            if (list.get(position).getUserList().size() >= 1) {
                viewHolderActivity.activity_item_text_user0.setVisibility(View.VISIBLE);
                if (GeneralUtils.isNotNullOrZeroLenght(list.get(position).getUserList().get(0).getAvatar())) {
                    PictureLoadUtil.loadPicture(context, list.get(position).getUserList().get(0).getAvatar(), viewHolderActivity.activity_item_text_user0);
                }
            }
            if (list.get(position).getUserList().size() >= 2) {
                viewHolderActivity.activity_item_text_user1.setVisibility(View.VISIBLE);
                if (GeneralUtils.isNotNullOrZeroLenght(list.get(position).getUserList().get(1).getAvatar())) {
                    PictureLoadUtil.loadPicture(context, list.get(position).getUserList().get(1).getAvatar(), viewHolderActivity.activity_item_text_user1);
                }
            }
            if (list.get(position).getUserList().size() == 3) {
                viewHolderActivity.activity_item_text_user2.setVisibility(View.VISIBLE);
                if (GeneralUtils.isNotNullOrZeroLenght(list.get(position).getUserList().get(2).getAvatar())) {
                    PictureLoadUtil.loadPicture(context, list.get(position).getUserList().get(2).getAvatar(), viewHolderActivity.activity_item_text_user2);
                }
            }
        }

        //剩余额度
        viewHolderActivity.activity_item_text_amount.setText(GeneralUtils.getBigDecimalToTwo(list.get(position).getLeftAmount()));
        //已领取人数
        viewHolderActivity.activity_item_text_number.setText(String.valueOf(list.get(position).getFinishCount()));
        //剩余额度进度条
        double leftAmount = list.get(position).getLeftAmount().doubleValue();
        double totalAmount = list.get(position).getTotalAmount().doubleValue();
        double amount = (leftAmount / totalAmount) * 100;
        viewHolderActivity.activity_item_text_amount_progress.setProgress((int) amount);
        double finishCount = list.get(position).getFinishCount().intValue();
        double getMaxUser = list.get(position).getMaxUser().intValue();
        double pra = (finishCount / getMaxUser) * 100;
        //已领取人数进度条
        viewHolderActivity.activity_item_text_number_progress.setProgress((int) pra);
        //地址logo 距离
        if (list.get(position).getActivityType() != 1 && list.get(position).getActivityForm() == 0 && LocationUtil.getGps().getOpen()) {
//            viewHolderActivity.activity_item_text_area_logo.setVisibility(View.VISIBLE);
//            viewHolderActivity.activity_item_text_area_text.setVisibility(View.VISIBLE);
            //起始位置 我的位置
            DPoint startGps = new DPoint();
            startGps.setLatitude(LocationUtil.getGps().getLatitude());
            startGps.setLongitude(LocationUtil.getGps().getLongitude());
            //结束位置 活动位置
            DPoint stopGps = new DPoint();
            stopGps.setLatitude(list.get(position).getLatitude().doubleValue());
            stopGps.setLongitude(list.get(position).getLongitude().doubleValue());
            //两点距离
            float areaText = CoordinateConverter.calculateLineDistance(startGps, stopGps);
            viewHolderActivity.activity_item_text_area_text.setText(areaText > 1000 ? String.format("%.1f", (areaText / 1000)) + "km" : String.format("%.1f", (areaText)) + "m");
        }
//        else {
//            viewHolderActivity.activity_item_text_area_logo.setVisibility(View.INVISIBLE);
//            viewHolderActivity.activity_item_text_area_text.setVisibility(View.INVISIBLE);
//        }
        //收藏
        if (GeneralUtils.isNotNull((String) SpUtils.get(Constants.SPREF.TOKEN, "")) && list.get(position).getFollow()) {
            viewHolderActivity.activity_item_text_collection.setImageResource(R.mipmap.collection_on);
        } else {
            viewHolderActivity.activity_item_text_collection.setImageResource(R.mipmap.collection_off);
        }
        //奖励金额
        viewHolderActivity.activity_item_text_reward.setText(GeneralUtils.getBigDecimalToTwo(list.get(position).getRewardAmount()));
        //是否显示控件
        isContentVisible(list.get(position).getActivityForm(), viewHolderActivity);

        //商家图片 点击
        viewHolderActivity.activity_item_member_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MemberDetailActivity.class);
                intent.putExtra("memberId", list.get(position).getMemberId());
                context.startActivity(intent);
            }
        });
        //商家名称 点击
        viewHolderActivity.activity_item_member_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MemberDetailActivity.class);
                intent.putExtra("memberId", list.get(position).getMemberId());
                context.startActivity(intent);
            }
        });
        //活动图片 点击
        viewHolderActivity.activity_item_img_i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityDetailActivity.class);
                intent.putExtra("id", list.get(position).getKid());
                intent.putExtra("title", list.get(position).getName());
                intent.putExtra("isNeedQRCode", list.get(position).getIfCheck());
                context.startActivity(intent);
            }
        });
        //活动内容 点击
        viewHolderActivity.layout_activity_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityDetailActivity.class);
                intent.putExtra("id", list.get(position).getKid());
                intent.putExtra("title", list.get(position).getName());
                intent.putExtra("isNeedQRCode", list.get(position).getIfCheck());

                context.startActivity(intent);
            }
        });

        //收藏 点击
        viewHolderActivity.activity_item_text_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //未登录
                if (GeneralUtils.isNotNull((String) SpUtils.get(Constants.SPREF.TOKEN, ""))) {
                    //已经收藏
                    if (list.get(position).getFollow()) {
                        //取消收藏
                        Map<String, String> params = new HashMap<>();
                        params.put("follow", "0");
                        EBLog.i("tag", params.toString());
                        EBLog.i("tag", list.get(position).getKid().toString());

                        HttpUtil.post(String.format(Constants.URL.POST_ACTIVITY_FOLLOW, list.get(position).getKid()), params).subscribe(new BaseResponseObserver<String>() {
                            @Override
                            public void success(String result) {
                                list.get(position).setFollow(false);
                                viewHolderActivity.activity_item_text_collection.setImageResource(R.mipmap.collection_off);
                                EBLog.i("tag", result.toString());
                            }

                            @Override
                            public void error(Response<String> response) {

                            }

                        });
                    }
                    //未收藏
                    else {
                        //收藏
                        Map<String, String> params = new HashMap<>();
                        params.put("follow", "1");
                        EBLog.i("tag", params.toString());
                        EBLog.i("tag", list.get(position).getKid().toString());

                        HttpUtil.post(String.format(Constants.URL.POST_ACTIVITY_FOLLOW, list.get(position).getKid()), params).subscribe(new BaseResponseObserver<String>() {
                            @Override
                            public void success(String result) {
                                list.get(position).setFollow(true);
                                viewHolderActivity.activity_item_text_collection.setImageResource(R.mipmap.collection_on);
                                EBLog.i("tag", result.toString());
                            }

                            @Override
                            public void error(Response<String> response) {

                            }
                        });
                    }
                }
                //登录
                else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }
            }
        });

        // 分享
        viewHolderActivity.activity_item_text_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemCliclk(holder.getLayoutPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    //根据活动类型和状态判断控件内容是否显示
    private void isContentVisible(int activityType, ViewHolderActivity holder) {
        switch (activityType) {
            case 0: //线下活动
                holder.activity_item_text_area_logo.setVisibility(View.VISIBLE);
                holder.activity_item_text_area_text.setVisibility(View.VISIBLE);
                holder.activity_item_img_vide.setVisibility(View.GONE);
                holder.tv_member_area_logo.setVisibility(View.VISIBLE);
                holder.activity_item_member_area.setVisibility(View.VISIBLE);
                break;
            case 1: //视频活动
                holder.activity_item_text_area_logo.setVisibility(View.GONE);
                holder.activity_item_text_area_text.setVisibility(View.GONE);
                holder.activity_item_img_vide.setVisibility(View.VISIBLE);
                holder.tv_member_area_logo.setVisibility(View.GONE);
                holder.activity_item_member_area.setVisibility(View.GONE);
                break;
            case 2: //问卷活动
                holder.activity_item_text_area_logo.setVisibility(View.GONE);
                holder.activity_item_text_area_text.setVisibility(View.GONE);
                holder.activity_item_img_vide.setVisibility(View.GONE);
                holder.tv_member_area_logo.setVisibility(View.GONE);
                holder.activity_item_member_area.setVisibility(View.GONE);
                break;
        }
    }

    public void updata(List<ActivityResp> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void updataMember(MemberResp memberResp) {
        this.memberResp = memberResp;
        notifyDataSetChanged();
    }

    public interface OnItemCliclkListener {
        void onItemCliclk(int position);
    }

    public void setOnItemCliclkListener(OnItemCliclkListener listener) {
        this.listener = listener;
    }

    /**
     * 返回活动状态
     *
     * @param t
     * @return
     */
    private String getOnlineString(int t) {
        String online;
        switch (t) {
            case 0:
                online = context.getString(R.string.activity_state_0);
                break;
            case 1:
                online = context.getString(R.string.activity_state_1);
                break;
            case 2:
                online = context.getString(R.string.activity_state_2);
                break;
            default:
                online = context.getString(R.string.activity_state_0);
        }
        return online;
    }

    /**
     * 返回活动类型
     *
     * @param t
     * @return
     */
    private String getActivityFormString(Integer t) {
        String type;
        switch (t) {
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


    public class ViewHolder extends RecyclerView.ViewHolder {


        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }

    public class ViewHolderMember extends ViewHolder {

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


        View itemView;

        public ViewHolderMember(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }

    public class ViewHolderActivity extends ViewHolder {

        //商家图片
        @BindView(R.id.activity_item_member_logo)
        ImageView activity_item_member_logo;
        //商家名称
        @BindView(R.id.activity_item_member_name)
        TextView activity_item_member_name;
        //活动区域
        @BindView(R.id.activity_item_member_area)
        TextView activity_item_member_area;
        //活动图片
        @BindView(R.id.activity_item_img_i)
        SelectableRoundedImageView activity_item_img_i;
        //活动状态
        @BindView(R.id.activity_item_img_state)
        TextView activity_item_img_state;
        //活动类型
        @BindView(R.id.activity_item_img_type)
        TextView activity_item_img_type;
        //活动名称
        @BindView(R.id.activity_item_text_title)
        TextView activity_item_text_title;
        //视频活动播放
        @BindView(R.id.activity_item_img_vide)
        ImageView activity_item_img_vide;
        //参与人0
        @BindView(R.id.activity_item_text_user0)
        ImageView activity_item_text_user0;
        //参与人1
        @BindView(R.id.activity_item_text_user1)
        ImageView activity_item_text_user1;
        //参与人2
        @BindView(R.id.activity_item_text_user2)
        ImageView activity_item_text_user2;
        //剩余额度
        @BindView(R.id.activity_item_text_amount)
        TextView activity_item_text_amount;
        //已领取人数
        @BindView(R.id.activity_item_text_number)
        TextView activity_item_text_number;
        //剩余额度进度条
        @BindView(R.id.activity_item_text_amount_progress)
        ProgressBar activity_item_text_amount_progress;
        //已领取人数进度条
        @BindView(R.id.activity_item_text_number_progress)
        ProgressBar activity_item_text_number_progress;
        //地址logo
        @BindView(R.id.activity_item_text_area_logo)
        ImageView activity_item_text_area_logo;
        //地址距离
        @BindView(R.id.activity_item_text_area_text)
        TextView activity_item_text_area_text;
        //收藏
        @BindView(R.id.activity_item_text_collection)
        ImageView activity_item_text_collection;
        @BindView(R.id.activity_item_text_reward)
        TextView activity_item_text_reward;
        //分享
        @BindView(R.id.activity_item_text_share)
        ImageView activity_item_text_share;
        //进度条
        @BindView(R.id.activity_item_text_centent)
        LinearLayout activity_item_text_centent;
        //活动地区
        @BindView(R.id.tv_member_area_logo)
        TextView tv_member_area_logo;
        //活动内容
        @BindView(R.id.layout_activity_content)
        LinearLayout layout_activity_content;


        View itemView;


        public ViewHolderActivity(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }


}
