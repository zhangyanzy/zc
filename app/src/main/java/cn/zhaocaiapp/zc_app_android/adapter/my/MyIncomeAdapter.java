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
import cn.zhaocaiapp.zc_app_android.bean.response.my.IncomeResp;

/**
 * Created by Administrator on 2018/3/6.
 */

public class MyIncomeAdapter extends RecyclerView.Adapter<MyIncomeAdapter.ViewHolder> {
    private Context context;
    private List<IncomeResp>items;


    public MyIncomeAdapter(Context context, List<IncomeResp>items){
        this.context = context;
        this.items = items;
    }

    public void refresh(List<IncomeResp>items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_income_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_describe.setText(items.get(position).getBusinessName());
        holder.tv_time.setText(items.get(position).getTimeStr());

        if (items.get(position).getCashStatus() == 0){//已提交
            holder.tv_state.setText("已提交");
        }
        else if (items.get(position).getCashStatus() == 1) {//已通过
            holder.tv_state.setText("已通过");
        }
        else if (items.get(position).getCashStatus() == 2) {//未通过
            holder.tv_state.setText("未通过");
        }
        else if (items.get(position).getCashStatus() == 3) {//转账中
            holder.tv_state.setText("转账中");
        }
        else if (items.get(position).getCashStatus() == 4) {//已到账
            holder.tv_state.setText("已到账");
        }
        else if (items.get(position).getCashStatus() == 5) {//转账失败
            holder.tv_state.setText("转账失败");
        }

        if (items.get(position).getBillType() == 0) {//收入
           holder.tv_income.setText("+"+items.get(position).getAmount());
           holder.tv_income.setTextColor(context.getResources().getColor(R.color.colorRemind));
        }
        if (items.get(position).getBillType() == 1) {//
            holder.tv_income.setText("-"+items.get(position).getAmount());
            holder.tv_income.setTextColor(context.getResources().getColor(R.color.colorSuccess));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_describe)
        TextView tv_describe;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_income)
        TextView tv_income;
        @BindView(R.id.tv_state)
        TextView tv_state;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
