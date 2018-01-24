package cn.zhaocaiapp.zc_app_android.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by ASUS on 2017/11/2.
 */

public class PullScrollView extends ScrollView{
    private View childView;// 子View（ScrollerView的唯一子类）
    private int y;// 点击时y坐标
    private Rect rect = new Rect();// 矩形(用来保存inner的初始状态，判断是够需要动画回弹效果)
    public PullScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    /**
     * 在xml布局绘制为界面完成时调用，
     * 获取ScrollerView中唯一的直系子布局（ScrollerView中不许包含一层ViewGroup，有且只有一个）
     */
    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            childView = getChildAt(0);
        }
        super.onFinishInflate();
    }
    /**
     * touch 事件处理
     **/
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (childView != null) {
            handleTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    /***
     * 触摸事件
     *
     * @param ev
     */
    public void handleTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                y = (int) ev.getY();//按下的时候获取到y坐标
                break;
            case MotionEvent.ACTION_MOVE:
                int nowY = (int) ev.getY(); // 移动时的实时y坐标
                int delayY = y - nowY;  // 移动时的间隔
                y = nowY;  // 将本次移动结束时的y坐标赋值给下次移动的起始坐标（也就是nowY）
                if (isNeedMove()) {
                    if (rect.isEmpty()) {
                        //rect保存childView的初始位置信息
                        rect.set(childView.getLeft(), childView.getTop(), childView.getRight(), childView.getBottom());
                    }
                    //移动布局（屏幕移动的距离等于手指滑动距离的一般）
                    childView.layout(childView.getLeft(), childView.getTop() - delayY / 2, childView.getRight(), childView.getBottom() - delayY / 2);
                }

                break;
            case MotionEvent.ACTION_UP:
                if (isNeedAnimation()) {// 判断rect是否为空，也就是是否被重置了
                    startAnim();//开始回弹动画
                }
                break;
            default:
                break;

        }
    }
    private void startAnim() {
        TranslateAnimation anim = new TranslateAnimation(0, 0, childView.getTop(), rect.top);
        anim.setDuration(200);
        anim.setInterpolator(new OvershootInterpolator());//加速器
        childView.startAnimation(anim);
        // 将inner布局重新回到起始位置
        childView.layout(rect.left, rect.top, rect.right, rect.bottom);
        rect.setEmpty();

    }
    /**
     *  判断布局是否需要移动
     * @return
     */
    private boolean isNeedMove() {
        int offset = childView.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        // 0是顶部，后面的参数是底部
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }
    public boolean isNeedAnimation() {
        return !rect.isEmpty();
    }

}
