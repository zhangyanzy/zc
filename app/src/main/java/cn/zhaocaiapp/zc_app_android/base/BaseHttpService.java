package cn.zhaocaiapp.zc_app_android.base;

import com.google.gson.JsonObject;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * 基础网络请求service
 * <p>
 * Created by jinxunmediapty.ltd on 2018/1/4.
 */

public interface BaseHttpService {

    /**
     * get请求
     *
     * @param url
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json", "x-requested-with:XMLHttpRequest"})
    @GET
    Observable<JsonObject> get(@Url String url);

    /**
     * get请求
     *
     * @param url
     * @param token
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json", "x-requested-with:XMLHttpRequest"})
    @GET
    Observable<JsonObject> get(@Url String url, @Query("token") String token);

    /**
     * get请求
     *
     * @param url
     * @param params
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json", "x-requested-with:XMLHttpRequest"})
    @GET
    Observable<JsonObject> get(@Url String url, @QueryMap Map<String, String> params);

    /**
     * get请求
     *
     * @param url
     * @param token
     * @param params
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json", "x-requested-with:XMLHttpRequest"})
    @GET
    Observable<JsonObject> get(@Url String url, @Query("token") String token, @QueryMap Map<String, String> params);

    /**
     * post请求
     *
     * @param url
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json", "x-requested-with:XMLHttpRequest"})
    @POST
    Observable<JsonObject> post(@Url String url);

    /**
     * post请求
     *
     * @param url
     * @param token
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json", "x-requested-with:XMLHttpRequest"})
    @POST
    Observable<JsonObject> post(@Url String url, @Query("token") String token);

    /**
     * post请求
     *
     * @param url
     * @param params
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json", "x-requested-with:XMLHttpRequest"})
    @POST
    Observable<JsonObject> post(@Url String url, @Body Map params);

    /**
     * post请求
     *
     * @param url
     * @param token
     * @param params
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json", "x-requested-with:XMLHttpRequest"})
    @POST
    Observable<JsonObject> post(@Url String url, @Query("token") String token, @Body Map params);


    /**
     * put请求
     *
     * @param url
     * @param token
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json", "x-requested-with:XMLHttpRequest"})
    @PUT
    Observable<JsonObject> put(@Url String url, @Query("token") String token);

    /**
     * put请求
     *
     * @param url
     * @param token
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json", "x-requested-with:XMLHttpRequest"})
    @PUT
    Observable<JsonObject> put(@Url String url, @Query("token") String token, @Body Object Object);

    /**
     * put请求
     *
     * @param url
     * @param token
     * @param params
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json", "x-requested-with:XMLHttpRequest"})
    @PUT
    Observable<JsonObject> put(@Url String url, @Query("token") String token, @Body Map params);

    /**
     * delete请求
     *
     * @param url
     * @param token
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json", "x-requested-with:XMLHttpRequest"})
    @DELETE
    Observable<JsonObject> delete(@Url String url, @Query("token") String token);

    /**
     * delete请求
     *
     * @param url
     * @param token
     * @param params
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json", "x-requested-with:XMLHttpRequest"})
    @DELETE
    Observable<JsonObject> delete(@Url String url, @Query("token") String token, @QueryMap Map<String, String> params);


}
