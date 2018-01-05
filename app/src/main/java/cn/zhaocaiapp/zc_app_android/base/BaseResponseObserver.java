package cn.zhaocaiapp.zc_app_android.base;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
    public void onNext(JsonObject result){

        Gson gson = new Gson();
        Type type = new TypeToken<Response>(){}.getType();

        Response<T> response = gson.fromJson(result,type);

        JsonElement data = result.get("data");

        //获取范型类型
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        //判断data类型
        if(data.isJsonArray()){

            Type[] pts = (Type[]) pt.getActualTypeArguments();
            T t = (T) gson.fromJson(data.getAsJsonArray(),pts[0]);
            this.success(t);
        }else if(data.isJsonObject()){

            Class<T> cls = (Class<T>) pt.getActualTypeArguments()[0];
            T t = gson.fromJson(data.getAsJsonObject(),cls);
            this.success(t);
        }else if(data.isJsonNull()){
            //TODO
        }else if(data.isJsonPrimitive()){
            //TODO
        }

        if(response.getCode().equals(BusinessEnum.SUCCESS.getCode())){
            System.out.print(111);
        }
    };

    public abstract void success(T result);
    public void error(Response<T> response){

    };

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onComplete() {

    }
}
