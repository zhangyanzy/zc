package cn.zhaocaiapp.zc_app_android.util;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.zhaocaiapp.zc_app_android.bean.Response;
import cn.zhaocaiapp.zc_app_android.bean.response.home.LocationResp;
import cn.zhaocaiapp.zc_app_android.constant.Constants;

/**
 * 全部地址三级数据
 * @author 林子
 * @filename AreaUtil.java
 * @data 2018-01-17 16:12
 */
public class AreaUtil {
    /**
     * 初始化地址数据 全部三级数据
     */
    public static List<LocationResp> initArea(Context context) {
        List<LocationResp> locationRespList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(context.getAssets().open(Constants.ASSETS.AREA), "UTF-8"));

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String tmp = sb.toString();
        if (!GeneralUtils.isNullOrZeroLenght(tmp)) {
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
            Response<List<LocationResp>> result = gson.fromJson(tmp, new TypeToken<Response<List<LocationResp>>>() {
            }.getType());
            locationRespList = result.getData();
        }
        return locationRespList;
    }
}
