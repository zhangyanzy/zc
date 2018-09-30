package cn.zhaocaiapp.zc_app_android.network;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.IOException;

import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.GsonUtils;
import cn.zhaocaiapp.zc_app_android.util.IsEmpty;
import cn.zhaocaiapp.zc_app_android.util.SpUtils;
import cn.zhaocaiapp.zc_app_android.manager.TokenMgr;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import retrofit2.Retrofit;

/**
 * Created by zhangyan on 2018/9/20.
 */

public class ApiClient {


    private static Context context;
    private static String baseUrl;
    private static Gson gson = GsonUtils.getGsonInstance(false);

    public static void init(Context context, String baseUrl) {
        ApiClient.context = context;
        ApiClient.baseUrl = baseUrl;
    }

    private static Interceptor setUserCookie = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request;
            String token = (String) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get(Constants.SPREF.TOKEN, "");
            if (!TextUtils.isEmpty(token)) {
                request = chain.request().newBuilder().addHeader("Cookie", token).build();
            } else {
                request = chain.request();
            }
            return chain.proceed(request);
        }
    };

    private static Interceptor getUserCookie = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            String cookie = response.headers().get("Set-Cookie");
            if (!IsEmpty.string(cookie) && cookie.contains("JSESSIONID=")) {
//                TokenMgr.updateToken(cookie);
            }
            return response;
        }
    };


//    public static Retrofit instance(boolean useMock) {
//        OkHttpClient.Builder client = new OkHttpClient.Builder().retryOnConnectionFailure(true)
//                .addInterceptor().addInterceptor(getUserCookie).addInterceptor(setUserCookie)
//                .addInterceptor()
//
//    }
}
