package cn.zhaocaiapp.zc_app_android;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.HashMap;

import cn.zhaocaiapp.zc_app_android.util.HttpUtil;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

        HttpUtil.get("message",new HashMap()).subscribe(new Observer<JsonObject>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.print("1");
            }

            @Override
            public void onNext(JsonObject value) {
                value.toString();
                System.out.print(value);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                System.out.print("1");
            }

            @Override
            public void onComplete() {
                System.out.print("1");
            }
        });
    }
}
