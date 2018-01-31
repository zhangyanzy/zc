
package cn.zhaocaiapp.zc_app_android.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Context;
import android.util.Base64;

import com.alibaba.idst.nls.internal.utils.Base64Encoder;


public class FileUtil {

    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }

    //图片文件转化为Base64
    public static String fileToStream(File file){
        FileInputStream inputStream = null;
        String str = "";
        try {
           inputStream = new FileInputStream(file);
           byte[]bytes = new byte[inputStream.available()];
           inputStream.read(bytes);
           inputStream.close();
           str = Base64Encoder.encode(bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }

}
