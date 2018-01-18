package cn.zhaocaiapp.zc_app_android.capabilities.takephoto;

import android.net.Uri;
import android.os.Environment;
import android.view.View;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TakePhotoOptions;

import java.io.File;

/**
 * Created by Administrator on 2017/9/1.
 */

public class PhotoHelper {
    private View rootView;

    public PhotoHelper(View rootView) {
        this.rootView = rootView;
    }

    public static PhotoHelper of(View rootView) {
        return new PhotoHelper(rootView);
    }

    public void onClick(int position, TakePhoto takePhoto) {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);

        switch (position){
            case 0:
                //从相机获取图片（裁剪）
                takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                break;
            case 1:
                //从相册中获取图片（裁剪）
                takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());

                break;
        }
    }

    //裁剪图片属性
    private CropOptions getCropOptions() {
        CropOptions.Builder builder=new CropOptions.Builder();
        builder.setAspectX(600).setAspectY(600);//裁剪时的尺寸比例
        builder.setWithOwnCrop(true);//是否使用TakePhoto自带的裁剪工具
        return builder.create();
    }

    //配置图片属性
    private void configTakePhotoOption(TakePhoto photo) {
        TakePhotoOptions.Builder builder=new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(false);//是否使用TakePhoto自带相册
        builder.setCorrectImage(true);//是否纠正旋转角度
        photo.setTakePhotoOptions(builder.create());
    }

    //    配置压缩
    private void configCompress(TakePhoto takePhoto){
        CompressConfig config=new CompressConfig.Builder()
                .setMaxSize(102400)//大小不超过100k
                .setMaxPixel(1200)//最大像素500
                .enableReserveRaw(true)//是否压缩
                .create();
        takePhoto.onEnableCompress(config,false);//这个true代表显示压缩进度条
    }

}
