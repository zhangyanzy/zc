package cn.zhaocaiapp.zc_app_android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import java.io.File;

import cn.zhaocaiapp.zc_app_android.R;


/**
 * Created by Administrator on 2018/3/14.
 */

public class SampleFullPlayer extends StandardGSYVideoPlayer {
    private String videoUrl;
    private String videoTitle;

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
    public int getLayoutId() {
        return R.layout.layout_video_player;
    }

    @Override
    protected void init(Context context) {
        super.init(context);
    }

    @Override
    protected void touchSurfaceMoveFullLogic(float absDeltaX, float absDeltaY) {
        super.touchSurfaceMoveFullLogic(absDeltaX, absDeltaY);
        //禁止触摸快进，如果需要，屏蔽下方代码即可
        mChangePosition = false;

        //禁止触摸音量，如果需要，屏蔽下方代码即可
        mChangeVolume = false;

        //禁止触摸亮度，如果需要，屏蔽下方代码即可
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

    /**
     * 在屏幕尺寸发生变化的时候重新处理
     */
    @Override
    public void onSurfaceSizeChanged(Surface surface, int width, int height) {
        super.onSurfaceSizeChanged(surface, width, height);
    }

    @Override
    public void onSurfaceAvailable(Surface surface) {
        super.onSurfaceAvailable(surface);
        resolveRotateUI();
    }

    /**
     * 设置视频URL
     *
     * @param url           视频url
     * @param cacheWithPlay 是否边播边缓存
     * @param title         视频title
     * @return
     */
    public boolean setUp(String url, boolean cacheWithPlay, String title) {
        videoUrl = url;
        return setUp(url, cacheWithPlay, title);
    }

    /**
     * 设置视频URL
     *
     * @param url           视频url
     * @param cacheWithPlay 是否边播边缓存
     * @param cachePath     缓存路径，如果是M3U8或者HLS，请设置为false
     * @param title         视频itle
     * @return
     */
    public boolean setUp(String url, boolean cacheWithPlay, File cachePath, String title) {
        videoUrl = url;
        return setUp(url, cacheWithPlay, cachePath, title);
    }

    /**
     * 全屏时将对应处理参数逻辑赋给全屏播放器
     *
     * @param context
     * @param actionBar
     * @param statusBar
     * @return
     */
    @Override
    public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar) {
        SampleFullPlayer sampleVideo = (SampleFullPlayer) super.startWindowFullscreen(context, actionBar, statusBar);
        sampleVideo.resolveRotateUI();

        //这里只是单纯的作为全屏播放显示，如果需要做大小屏幕切换，请记得在这里也设置上视频全屏需要的自定义配置
        //比如已旋转角度之类的等等，可参考super中的实现
        return sampleVideo;
    }

    /**
     * 退出全屏时将对应处理参数逻辑返回给非全屏播放器
     *
     * @param oldF
     * @param vp
     * @param gsyVideoPlayer
     */
    @Override
    protected void resolveNormalVideoShow(View oldF, ViewGroup vp, GSYVideoPlayer gsyVideoPlayer) {
        super.resolveNormalVideoShow(oldF, vp, gsyVideoPlayer);
        if (gsyVideoPlayer != null) {
            setUp(videoUrl, mCache, mCachePath, mTitle);
            resolveRotateUI();
        }
    }

    /**
     * 旋转逻辑
     */
    private void resolveRotateUI() {
        if (!mHadPlay) {
            return;
        }
        mTextureView.setRotation(mRotate);
        mTextureView.requestLayout();
    }

}
