package cn.zhaocaiapp.zc_app_android.adapter.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * @author 林子
 * @filename EmptyViewHolder.java
 * @data 2018-04-10 19:26
 */
public class EmptyViewHolder extends RecyclerView.ViewHolder {
    private View itemView;

    public EmptyViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }
}
