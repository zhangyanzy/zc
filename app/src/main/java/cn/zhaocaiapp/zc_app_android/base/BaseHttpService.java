package cn.zhaocaiapp.zc_app_android.base;

import com.google.gson.JsonObject;

import java.util.Map;

import io.reactivex.Observable;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * 基础网络请求service
 *
 * Created by jinxunmediapty.ltd on 2018/1/4.
 */

public interface BaseHttpService {

    /**
     * get请求
     *
     * @param url
     * @param token
     * @param params
     * @return
     */
    @GET
    Observable<JsonObject> get(@Url String url, @Query("token") String token, @QueryMap Map<String, String> params);


    /**
     * post请求
     *
     * @param url
     * @param token
     * @param params
     * @return
     */
    @POST
    Observable<JsonObject> post(@Url String url, @Query("token") String token,@FieldMap Map<String, String> params);

    /**
     * put请求
     *
     * @param url
     * @param token
     * @param params
     * @return
     */
    @PUT
    Observable<JsonObject> put(@Url String url, @Query("token") String token,@FieldMap Map<String, String> params);

    /**
     * delete请求
     *
     * @param url
     * @param token
     * @param params
     * @return
     */
    @DELETE
    Observable<JsonObject> delete(@Url String url, @Query("token") String token,@FieldMap Map<String, String> params);

    /**
     * 上传
     *
     * @param description
     * @param file
     * @return
     */
    @Multipart
    @POST
    Observable<JsonObject> upload(@Part("description") RequestBody description,
                                    @Part MultipartBody.Part file);
}
