package cn.zhaocaiapp.zc_app_android.util;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.widget.LoadingDialog;

/**
 * Created by Administrator on 2018/1/31.
 */

public class ShareUtil {
    private static Activity mActivity;
    private static ShareAction mShareAction;
    private static UMImage umImage;//分享的缩略图
    private static UMWeb umWeb;//要分享的网页
    private static String webUrl;//分享的链接
    private static String title;//标题
    private static String desc;//描述
    private static ShareUtil shareUtil;

    private ShareUtil() {
    }

    public static ShareUtil init(Activity mActivity) {
        ShareUtil.mActivity = mActivity;
        if (shareUtil == null)
            shareUtil = new ShareUtil();
        return shareUtil;
    }

    public ShareUtil setUrl(String webUrl) {
        ShareUtil.webUrl = webUrl;
        return shareUtil;
    }

    public ShareUtil setTitle(String title) {
        ShareUtil.title = title;
        return shareUtil;
    }

    //网络缩略图地址
    public ShareUtil setImgPath(String imgPath) {
        umImage = new UMImage(mActivity, imgPath);
        return shareUtil;
    }

    //本地资源图片地址
    public ShareUtil setSourceId(int sourceId) {
        umImage = new UMImage(mActivity, sourceId);
        return shareUtil;
    }

    public ShareUtil setDesc(String desc) {
        ShareUtil.desc = desc;
        return shareUtil;
    }

    //开启分享面板
    public static void openShare() {
        mShareAction = new ShareAction(mActivity);
        mShareAction.setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        if (share_media == SHARE_MEDIA.SINA) {
                            mShareAction.setShareContent(initShareContent());
                        } else {
                            mShareAction.withMedia(initMedia());
                        }
                        mShareAction.setPlatform(share_media)
                                .setCallback(shareListener)
                                .share();
                    }
                });
        mShareAction.open();
    }

    //初始化微博分享内容
    private static ShareContent initShareContent() {
        ShareContent shareContent = new ShareContent();
        desc = title;
        shareContent.mMedia = initMedia();
        return shareContent;
    }

    //初始化分享内容
    private static UMWeb initMedia() {
        String server = Constants.URL.H5_URL;
        StringBuilder sb = new StringBuilder();
        sb.append(server).append(webUrl);
        EBLog.i("TAG", sb.toString());
        umWeb = new UMWeb(sb.toString());
        umWeb.setTitle(title);
        umWeb.setThumb(umImage);
        umWeb.setDescription(desc);
        return umWeb;
    }

    //分享回调监听
    private static UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
//            SocializeUtils.safeShowDialog(dialog);
            LoadingDialog.showDialogForLoading(mActivity);
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
//            SocializeUtils.safeCloseDialog(dialog);
            ToastUtil.makeText(mActivity, "分享成功");
            LoadingDialog.cancelDialogForLoading();
            mActivity = null;
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
//            SocializeUtils.safeCloseDialog(dialog);
            LoadingDialog.cancelDialogForLoading();
            ToastUtil.makeText(mActivity, "分享失败");
            mActivity = null;
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            SocializeUtils.safeCloseDialog(dialog);
            LoadingDialog.cancelDialogForLoading();
            ToastUtil.makeText(mActivity, "取消分享");
            mActivity = null;
        }
    };

}
