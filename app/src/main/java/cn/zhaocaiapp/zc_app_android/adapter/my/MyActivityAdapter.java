package cn.zhaocaiapp.zc_app_android.adapter.my;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;
import cn.zhaocaiapp.zc_app_android.widget.CircleImageView;

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
    private int type;

    public MyActivityAdapter(Context context, List<ActivityResp> items, int type) {
        this.context = context;
        this.items = items;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_activitys_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ActivityResp activity = items.get(position);
        PictureLoadUtil.loadPicture(context, activity.getMemberImg(), holder.iv_logo);
        holder.tv_name.setText(activity.getMemberName());
        holder.tv_state.setText(activity.getOnline());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void refresh(List<ActivityResp> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    private String getOnlineState(int state){
        switch (state){
            case
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_logo)
        CircleImageView iv_logo;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_state)
        TextView tv_state;
        @BindView(R.id.activity_item_img_i)
        SelectableRoundedImageView activity_item_img;
        @BindView(R.id.activity_item_img_state)
        TextView item_img_state;
        @BindView(R.id.activity_item_img_type)
        TextView item_img_type;
        @BindView(R.id.activity_item_img_vide)
        ImageView item_img_vide;
        @BindView(R.id.activity_item_text_title)
        TextView item_text_title;
        @BindView(R.id.activity_item_text_user0)
        CircleImageView item_text_user0;
        @BindView(R.id.activity_item_text_user1)
        CircleImageView item_text_user1;
        @BindView(R.id.activity_item_text_user2)
        CircleImageView item_text_user2;
        @BindView(R.id.activity_item_text_area_logo)
        ImageView item_text_area_logo;
        @BindView(R.id.activity_item_text_area_text)
        TextView item_text_area_text;
        @BindView(R.id.activity_item_text_collection)
        ImageView item_text_collection;
        @BindView(R.id.activity_item_text_share)
        ImageView item_text_share;
        @BindView(R.id.activity_item_text_amount)
        TextView item_text_amount;
        @BindView(R.id.activity_item_text_amount_progress)
        ProgressBar item_text_amount_progress;
        @BindView(R.id.activity_item_text_number)
        TextView item_text_number;
        @BindView(R.id.activity_item_text_number_progress)
        ProgressBar item_text_number_progress;
        @BindView(R.id.activity_item_text_reward)
        TextView item_text_reward;
        @BindView(R.id.count_down_time)
        CountdownView count_down_time;
        @BindView(R.id.tv_submit)
        TextView tv_submit;
        @BindView(R.id.tv_cancel)
        TextView tv_cancel;

        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }
}
