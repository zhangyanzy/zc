package cn.zhaocaiapp.zc_app_android.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

import cn.zhaocaiapp.zc_app_android.bean.response.common.BannerEntity;

/**
 * Created by admin on 2018/8/24.
 */

public class BannerImageLoader extends ImageLoader{

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        BannerEntity entity = (BannerEntity) path;
        Glide.with(context)
                .load(entity.getActivity_image1())
                .into(imageView);
    }
}

