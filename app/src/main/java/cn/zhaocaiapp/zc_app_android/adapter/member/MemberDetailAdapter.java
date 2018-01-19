package cn.zhaocaiapp.zc_app_android.adapter.member;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.bean.response.common.ActivityResp;
import cn.zhaocaiapp.zc_app_android.bean.response.member.MemberResp;


/**
 * @author 林子
 * @filename MemberActivity.java
 * @data 2018-01-05 17:59
 */
public class MemberDetailAdapter extends RecyclerView.Adapter<MemberDetailAdapter.ViewHolder> {

    private List<ActivityResp> list;
    private Context context;
    private OnItemCliclkListener listene;

    public MemberDetailAdapter(Context context, List<ActivityResp> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.member_detail_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context)
                .load(list.get(position).getMemberImg())
                .into(holder.activity_item_member_logo);

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.activity_item_member_logo)
        ImageView activity_item_member_logo;

        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }


}
