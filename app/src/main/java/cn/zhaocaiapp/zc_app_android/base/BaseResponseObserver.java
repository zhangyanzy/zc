package cn.zhaocaiapp.zc_app_android.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.refer.BusinessEnum;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by jinxunmediapty.ltd on 2018/1/5.
 */

public abstract class BaseResponseObserver<T> implements Observer<JsonObject> {

    @Override
    public void onSubscribe(Disposable d) {
        System.out.print(d);
    }

    @Override
    public void onNext(JsonObject result) {

        GsonBuilder builder = new GsonBuilder();

        // 解决gson时间格式化报错的问题
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    return format.parse(json.getAsString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return null;
            }
        });

        Gson gson = builder.create();
        Type type = new TypeToken<Response>() {}.getType();

        Response<T> response = gson.fromJson(result, type);

        if (response.getCode().equals(BusinessEnum.SUCCESS.getCode())) {
            JsonElement data = result.get("data");

            //获取范型类型
            ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
            //判断data类型
            if (data.isJsonArray()) {

                Type[] pts = (Type[]) pt.getActualTypeArguments();
                T t = (T) gson.fromJson(data.getAsJsonArray(), pts[0]);
                this.success(t);
            } else if (data.isJsonObject()) {

                Class<T> cls = (Class<T>) pt.getActualTypeArguments()[0];
                T t = gson.fromJson(data.getAsJsonObject(), cls);
                this.success(t);
            } else if (data.isJsonNull()) {
                //TODO
            } else if (data.isJsonPrimitive()) {
                T t = (T) gson.fromJson(data.getAsJsonPrimitive(), type);
                this.success(t);
            }
        } else {
            error(response);
        }
    }

    public abstract void success(T t);

    public abstract void error(Response<T> response);

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onComplete() {
    }
}
