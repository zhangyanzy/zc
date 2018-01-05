package cn.zhaocaiapp.zc_app_android;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;

import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;

import cn.zhaocaiapp.zc_app_android.bean.message.Message;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;


import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("cn.zhaocaiapp.zc_app_android", appContext.getPackageName());

        /*HttpUtil.get("message",new HashMap()).subscribe(new BaseResponseObserver<Response<List<Message>>>() {
            //判断code返回
            @Override
            public void onNext(Response<List<Message>> response) {
                if(response.getCode().equals(0)){
                    System.out.print(response.getData().toString());
                }
            }
        });*/
        synchronized (appContext) {

            HttpUtil.get("/message", new HashMap()).subscribe(new BaseResponseObserver<List<Message>>() {
                @Override
                public void success(List<Message> result) {

                    for(Message message : result){
                        System.out.print(message.getMsg());
                    }
                }
            });
            appContext.wait();

        }
    }
}
