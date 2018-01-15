package cn.zhaocaiapp.zc_app_android.views.member;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author 林子
 * @filename MemberDivider.java
 * @data 2018-01-13 16:11
 */
public class MemberDivider extends RecyclerView.ItemDecoration {
    private int leftRight;
    private int topBottom;
    private Context context;

    public MemberDivider(Context context, int leftRight, int topBottom) {
        this.leftRight = leftRight;
        this.topBottom = topBottom;
        this.context = context;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(leftRight, leftRight, leftRight, leftRight);//设置itemView中内容相对边框左，上，右，下距离
    }
}
