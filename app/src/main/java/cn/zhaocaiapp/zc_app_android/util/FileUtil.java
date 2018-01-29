/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package cn.zhaocaiapp.zc_app_android.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

import com.autonavi.amap.mapcore.Convert;

import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;

public class FileUtil {

    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }

    public static String fileToStream(File file){
        FileInputStream inputStream = null;
        StringBuilder builder = null;
        try {
           inputStream = new FileInputStream(file);
           byte[]bytes = new byte[inputStream.available()];
           builder = new StringBuilder();
           inputStream.read(bytes);
           for (byte b : bytes){
               builder.append(Integer.toHexString(b));
           }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        EBLog.i("图片流", builder.toString().replaceAll("\\\\", "//"));
        return builder.toString().replaceAll("\\\\", "//");
    }

}
