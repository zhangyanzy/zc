package cn.zhaocaiapp.zc_app_android.views.member;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
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
        /*GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        //判断总的数量是否可以整除
        int totalCount = layoutManager.getItemCount(); //元素总数
        int surplusCount = totalCount % layoutManager.getSpanCount(); //元素总数取余
        int childPosition = parent.getChildAdapterPosition(view);//当前元素的位置
        //竖直方向的
        if (layoutManager.getOrientation() == GridLayoutManager.VERTICAL) {

            if (surplusCount == 0 && childPosition > totalCount - layoutManager.getSpanCount() - 1) {
                //后面几项需要bottom
                outRect.bottom = topBottom;
            } else if (surplusCount != 0 && childPosition > totalCount - surplusCount - 1) {
                outRect.bottom = topBottom;
            }
            if ((childPosition + 1) % layoutManager.getSpanCount() == 0) {//被整除的需要右边
                outRect.right = leftRight;
            }
            outRect.top = topBottom;
            outRect.left = leftRight;
        }
        //水平方向的
        else {
            if (surplusCount == 0 && childPosition > totalCount - layoutManager.getSpanCount() - 1) {
                //后面几项需要右边
                outRect.right = leftRight;
            } else if (surplusCount != 0 && childPosition > totalCount - surplusCount - 1) {
                outRect.right = leftRight;
            }
            if ((childPosition + 1) % layoutManager.getSpanCount() == 0) {//被整除的需要下边
                outRect.bottom = topBottom;
            }
            outRect.top = topBottom;
            outRect.left = leftRight;
        }*/
        outRect.set(leftRight, leftRight, leftRight, leftRight);//设置itemView中内容相对边框左，上，右，下距离
    }
}
