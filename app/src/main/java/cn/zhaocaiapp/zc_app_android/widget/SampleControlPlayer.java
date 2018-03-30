package cn.zhaocaiapp.zc_app_android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;


/**
 * Created by Administrator on 2018/3/28.
 */

public class SampleControlPlayer extends StandardGSYVideoPlayer {
    private String videoUrl;

    public SampleControlPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public SampleControlPlayer(Context context) {
        super(context);
    }

    public SampleControlPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public int getLayoutId() {
//        return R.layout.layout_sample_control_player;
//    }

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

//    /**
//     * 全屏时将对应处理参数逻辑赋给全屏播放器
//     *
//     * @param context
//     * @param actionBar
//     * @param statusBar
//     * @return
//     */
//    @Override
//    public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar) {
//        SampleControlPlayer player = (SampleControlPlayer) super.startWindowFullscreen(context, actionBar, statusBar);
//        resolveRotateUI();
//
//        //这里只是单纯的作为全屏播放显示，如果需要做大小屏幕切换，请记得在这里也设置上视频全屏需要的自定义配置
//        //比如已旋转角度之类的等等，可参考super中的实现
//        return player;
//    }
//
//    /**
//     * 退出全屏时将对应处理参数逻辑返回给非全屏播放器
//     *
//     * @param oldF
//     * @param vp
//     * @param gsyVideoPlayer
//     */
//    @Override
//    protected void resolveNormalVideoShow(View oldF, ViewGroup vp, GSYVideoPlayer gsyVideoPlayer) {
//        super.resolveNormalVideoShow(oldF, vp, gsyVideoPlayer);
//        if (gsyVideoPlayer != null) {
//            setUp(videoUrl, mCache, mCachePath, mTitle);
//            resolveRotateUI();
//        }
//    }
//
//    /**
//     * 旋转逻辑
//     */
//    private void resolveRotateUI() {
//        if (!mHadPlay) {
//            return;
//        }
//        mTextureView.setRotation(mRotate);
//        mTextureView.requestLayout();
//    }

    /**
     * 设置视频URL
     *
     * @param url           视频url
     * @param cacheWithPlay 是否边播边缓存
     * @param title         视频title
     * @return
     */
    public boolean setUrl(String url, boolean cacheWithPlay, String title) {
        videoUrl = url;
        return setUp(url, cacheWithPlay, title);
    }

}
