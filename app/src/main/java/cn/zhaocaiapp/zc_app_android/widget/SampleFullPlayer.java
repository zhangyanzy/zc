package cn.zhaocaiapp.zc_app_android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import cn.zhaocaiapp.zc_app_android.R;

/**
 * Created by Administrator on 2018/3/14.
 */

public class SampleFullPlayer extends StandardGSYVideoPlayer {

    public SampleFullPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public SampleFullPlayer(Context context) {
        super(context);
    }

    public SampleFullPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void touchSurfaceMoveFullLogic(float absDeltaX, float absDeltaY) {
        super.touchSurfaceMoveFullLogic(absDeltaX, absDeltaY);
        //不给触摸快进，如果需要，屏蔽下方代码即可
        mChangePosition = false;

        //不给触摸音量，如果需要，屏蔽下方代码即可
        mChangeVolume = false;

        //不给触摸亮度，如果需要，屏蔽下方代码即可
        mBrightness = false;
    }

    /**
     * 双击暂停/播放
     * 如果不需要，重载为空方法即可
     */
    @Override
    protected void touchDoubleUp() {
        super.touchDoubleUp();
    }
}
