package cn.zhaocaiapp.zc_app_android.adapter.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;


/**
 * @author 林子
 * @filename MemberActivity.java
 * @data 2018-01-05 17:59
 */
public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    private List<ActivityResp> list;
    private Context context;
    private OnItemCliclkListener listene;

    public ActivityAdapter(Context context, List<ActivityResp> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int k = position;
        //商家图片
        PictureLoadUtil.loadPicture(context, list.get(k).getMemberImg(), holder.activity_item_member_logo);
        //商家名称
        holder.activity_item_member_name.setText(list.get(k).getMemberName());
        //活动区域
        holder.activity_item_member_area.setText(list.get(k).getCityName());
        //活动图片
        PictureLoadUtil.loadPicture(context, list.get(k).getActivityImage1(), holder.activity_item_img_i);
        //活动状态
        holder.activity_item_img_state.setText(getOnlineString(list.get(k).getOnline()));
        //活动类型
        holder.activity_item_img_type.setText(getActivityFormString(list.get(k).getActivityForm()));
        //活动名称
        SpannableStringBuilder spannableString = new SpannableStringBuilder("#" + getActivityFormString(list.get(k).getActivityForm()) + "#" + list.get(k).getName());
        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.activity_item_text_title.setText(spannableString);
        //视频活动播放
        if (list.get(k).getActivityForm() == 1) {
            holder.activity_item_img_vide.setVisibility(View.VISIBLE);
        }
        //参与人头像
        if (GeneralUtils.isNotNull(list.get(k).getUserList()) && list.get(k).getUserList().size() > 0) {
            if (list.get(k).getUserList().size() >= 1) {
                holder.activity_item_text_user0.setVisibility(View.VISIBLE);
                PictureLoadUtil.loadPicture(context, list.get(k).getUserList().get(0).getAvatar(), holder.activity_item_text_user0);
            }
            if (list.get(k).getUserList().size() >= 2) {
                holder.activity_item_text_user1.setVisibility(View.VISIBLE);
                PictureLoadUtil.loadPicture(context, list.get(k).getUserList().get(1).getAvatar(), holder.activity_item_text_user1);
            }
            if (list.get(k).getUserList().size() == 3) {
                holder.activity_item_text_user2.setVisibility(View.VISIBLE);
                PictureLoadUtil.loadPicture(context, list.get(k).getUserList().get(2).getAvatar(), holder.activity_item_text_user2);
            }
        }
        //剩余额度
        DecimalFormat df2 = new DecimalFormat("#.00"); // #.00 表示两位小数 #.0000四位小数
        String str2 = df2.format(list.get(k).getLeftAmount());
        holder.activity_item_text_amount.setText(str2);
        //已领取人数
        //holder.activity_item_text_number.setText(list.get(k).getActualUser());
        //剩余额度进度条
        holder.activity_item_text_amount_progress.setProgress(50);
        //已领取人数进度条
        holder.activity_item_text_number_progress.setProgress(50);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listene.onItemCliclk(holder.getLayoutPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public void updata(List<ActivityResp> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public interface OnItemCliclkListener {
        void onItemCliclk(int position);
    }

    public void setOnItemCliclkListener(OnItemCliclkListener listener) {
        this.listene = listener;
    }

    /**
     * 返回活动状态
     *
     * @param t
     * @return
     */
    private String getOnlineString(Integer t) {
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


        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }


}
