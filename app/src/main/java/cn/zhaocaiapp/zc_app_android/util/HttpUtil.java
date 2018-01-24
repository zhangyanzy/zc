package cn.zhaocaiapp.zc_app_android.util;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.zhaocaiapp.zc_app_android.base.BaseHttpService;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求
 * <p>
 * Created by jinxunmediapty.ltd on 2018/1/4.
 */

public class HttpUtil {

    private static String USER_TOKEN;

    private final static BaseHttpService http;

    static {
        Gson gson = new GsonBuilder()
                //配置你的Gson
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Constants.CONFIG.DEFAULT_TIMEOUT, TimeUnit.SECONDS)          //设置连接超时时间
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL.SERVER)                                        //设置服务器地址
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))     //添加gson支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //添加RxJava2支持
                .build();

        http = retrofit.create(BaseHttpService.class);
        //TODO 临时使用 林子 2018年01月18日20:59:12
        //USER_TOKEN = (String) SpUtils.get(Constants.SPREF.TOKEN, "");
        USER_TOKEN = "c7077935cc3537cf503d2afa2fc5bcbf";
        EBLog.i("HTTP_TOKEN", USER_TOKEN);
    }


    /**
     * get 请求
     *
     * @param url
     * @return
     */
    public static Observable get(String url) {
//        if (GeneralUtils.isNotNullOrZeroLenght(USER_TOKEN)) {
//            return http.get(url)
//                    .subscribeOn(Schedulers.io())
//                    .unsubscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread());
//        } else {
            return http.get(url, USER_TOKEN)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
//        }
    }

    /**
     * get 请求
     *
     * @param url
     * @param params
     * @return
     */
    public static Observable get(String url, Map params) {
//        if (GeneralUtils.isNullOrZeroLenght(USER_TOKEN)) {
//            return http.get(url, params)
//                    .subscribeOn(Schedulers.io())
//                    .unsubscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread());
//        } else {
            return http.get(url, USER_TOKEN, params)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
//        }
    }

    /**
     * post 请求
     *
     * @param url
     * @return
     */
    public static Observable post(String url) {
//        if (GeneralUtils.isNullOrZeroLenght(USER_TOKEN)) {
//            return http.post(url)
//                    .subscribeOn(Schedulers.io())
//                    .unsubscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread());
//        } else {
            return http.post(url, USER_TOKEN)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
//        }
    }

    /**
     * post 请求
     *
     * @param url
     * @param params
     * @return
     */
    public static Observable post(String url, Map params) {
//        if (GeneralUtils.isNullOrZeroLenght(USER_TOKEN)) {
//            return http.post(url, params)
//                    .subscribeOn(Schedulers.io())
//                    .unsubscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread());
//        } else {
            return http.post(url, USER_TOKEN, params)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
//        }

    }

    /**
     * put 请求
     *
     * @param url
     * @return
     */
    public static Observable put(String url) {

        return http.put(url, USER_TOKEN)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * put 请求
     *
     * @param url
     * @param params
     * @return
     */
    public static Observable put(String url, Map params) {

        return http.put(url, USER_TOKEN, params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * delete 请求
     *
     * @param url
     * @return
     */
    public static Observable delete(String url) {

        return http.delete(url, USER_TOKEN)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * delete 请求
     *
     * @param url
     * @param params
     * @return
     */
    public static Observable delete(String url, Map params) {

        return http.delete(url, USER_TOKEN, params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
