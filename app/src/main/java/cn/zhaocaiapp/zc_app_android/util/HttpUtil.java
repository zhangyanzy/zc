package cn.zhaocaiapp.zc_app_android.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.zhaocaiapp.zc_app_android.base.BaseHttpService;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求
 *
 * Created by jinxunmediapty.ltd on 2018/1/4.
 */

public class HttpUtil {

    private final static String SERVER_URL = "http://123:8080/";    //必须以／结尾否则初始化会报错
    private static final int DEFAULT_TIMEOUT = 5;                   //请求超时时间

    private static String USER_TOKEN = "123";

    private final static BaseHttpService http;

    static {
        Gson gson = new GsonBuilder()
                //配置你的Gson
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();

        OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)          //设置连接超时时间
                    .build();

        Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_URL)                                        //设置服务器地址
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))     //添加gson支持
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //添加RxJava2支持
                    .build();

        http = retrofit.create(BaseHttpService.class);
    }


    /**
     * get 请求
     *
     * @param url
     * @param params
     * @return
     */
    public static Observable get(String url,Map params){

        return http.get(url,USER_TOKEN,params);
    }

    /**
     * post 请求
     *
     * @param url
     * @param params
     * @return
     */
    public static Observable post(String url,Map params){

        return http.post(url,USER_TOKEN,params);
    }

    /**
     * put 请求
     *
     * @param url
     * @param params
     * @return
     */
    public static Observable put(String url,Map params){

        return http.put(url,USER_TOKEN,params);
    }

    /**
     * delete 请求
     *
     * @param url
     * @param params
     * @return
     */
    public static Observable delete(String url,Map params){

        return http.delete(url,USER_TOKEN,params);
    }

}
