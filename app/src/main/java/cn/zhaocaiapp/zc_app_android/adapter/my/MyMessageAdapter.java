package cn.zhaocaiapp.zc_app_android.adapter.my;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.bean.response.my.MessageResp;
import cn.zhaocaiapp.zc_app_android.util.GeneralUtils;
import cn.zhaocaiapp.zc_app_android.util.PictureLoadUtil;

/**
 * Created by Administrator on 2018/1/25.
 */

public class MyMessageAdapter extends RecyclerView.Adapter<MyMessageAdapter.ViewHolder> {
    private Context context;
    private List<MessageResp> messageResps;
    private OnItemCliclkListener listene;
    private int type;
    private int viewType;

    public MyMessageAdapter(Context context, List<MessageResp> messageResps, int type) {
        this.context = context;
        this.messageResps = messageResps;
        this.type = type;
    }

    @Override
    public int getItemViewType(int position) {
        if (messageResps.size() <= 0) return -1;
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        ViewHolder holder = null;
        if (viewType == -1) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_no_data, parent, false);
            holder = new EmptyViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.my_message_item, parent, false);
            holder = new MessageViewHolder(view);
        }
        this.viewType = viewType;
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewholder, int position) {
        if (viewType != -1 && viewholder instanceof MessageViewHolder) {
            MessageViewHolder holder = (MessageViewHolder) viewholder;
            if (type == 0) holder.iv_logo.setImageResource(R.mipmap.message_logo);
            if (type == 1) holder.iv_logo.setImageResource(R.mipmap.message_logo);
            if (GeneralUtils.isNotNullOrZeroLenght(messageResps.get(position).getTitle()))
                holder.tv_title.setText(messageResps.get(position).getTitle());
            holder.tv_describe.setText(messageResps.get(position).getContent());
            String time = new SimpleDateFormat("yyyy-MM-dd").format(messageResps.get(position).getCreateTime());
            holder.tv_time.setText(time);
            if (messageResps.get(position).getReadStatus() == 0) {//未读
                holder.tv_title.setTextColor(context.getResources().getColor(R.color.colorFont3));
                holder.tv_describe.setTextColor(context.getResources().getColor(R.color.colorFont6));
                holder.tv_time.setTextColor(context.getResources().getColor(R.color.colorFont9));
            }
            if (messageResps.get(position).getReadStatus() == 1) {//已读
                holder.tv_title.setTextColor(context.getResources().getColor(R.color.colorLine));
                holder.tv_describe.setTextColor(context.getResources().getColor(R.color.colorLine));
                holder.tv_time.setTextColor(context.getResources().getColor(R.color.colorLine));
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listene.onItemCliclk(holder.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return messageResps.size() > 0 ? messageResps.size() : 1;
    }

    public void refresh(List<MessageResp> messageResps) {
        this.messageResps = messageResps;
        notifyDataSetChanged();
    }

    public interface OnItemCliclkListener {
        void onItemCliclk(int position);
    }

    public void setOnItemCliclkListener(OnItemCliclkListener listener) {
        this.listene = listener;
    }

    public class MessageViewHolder extends ViewHolder {
        @BindView(R.id.iv_logo)
        ImageView iv_logo;
        @BindView(R.id.tv_describe)
        TextView tv_describe;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_title)
        TextView tv_title;

        View itemView;

        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class EmptyViewHolder extends ViewHolder {
        View itemView;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }
}
