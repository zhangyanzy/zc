package cn.zhaocaiapp.zc_app_android.adapter.my;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.zhaocaiapp.zc_app_android.R;
import cn.zhaocaiapp.zc_app_android.bean.response.my.MessageResp;

/**
 * Created by Administrator on 2018/1/25.
 */

public class MyMessageAdapter extends RecyclerView.Adapter<MyMessageAdapter.ViewHolder> {
    private Context context;
    private List<MessageResp> messageResps;

    public MyMessageAdapter(Context context, List<MessageResp> messageResps) {
        this.context = context;
        this.messageResps = messageResps;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_message_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return messageResps.size() == 0 ? 5 : messageResps.size();
    }

    public void refresh(List<MessageResp> messageResps){
        this.messageResps = messageResps;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_describe)
        TextView tv_describe;
        @BindView(R.id.tv_time)
        TextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
