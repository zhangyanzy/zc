package cn.zhaocaiapp.zc_app_android;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import cn.zhaocaiapp.zc_app_android.base.BaseActivity;
import cn.zhaocaiapp.zc_app_android.base.BaseResponseObserver;
import cn.zhaocaiapp.zc_app_android.bean.response.login.LoginResp;
import cn.zhaocaiapp.zc_app_android.capabilities.log.EBLog;
import cn.zhaocaiapp.zc_app_android.constant.Constants;
import cn.zhaocaiapp.zc_app_android.util.HttpUtil;

public class MainActivity extends BaseActivity {

    private Button button;
    private HttpUtil httpUtil;


    @Override
    public void init(Bundle savedInstanceState) {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        button = (Button) findViewById(R.id.testBtn);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Map<String, String> mMap = new HashMap<String, String>();
                mMap.put("account", "15044441111");
                mMap.put("password", "123456");
                EBLog.i("tag", mMap.toString());
                EBLog.i("tag", Constants.URL.USER_LOGIN);
                EBLog.i("tag", String.format("user/11100/12", "10001", "22"));
                /*HttpUtil.post(URLUtil.USER_LOGIN, new HashMap()).subscribe(new BaseResponseObserver<LoginResp>() {
                    @Override
                    public void success(LoginResp result) {
                        EBLog.i("tag", result.getToken());
                    }
                });*/
                HttpUtil.post(Constants.URL.USER_LOGIN).subscribe(new BaseResponseObserver<LoginResp>() {
                    @Override
                    public void success(LoginResp result) {
                        EBLog.i("tag", result.getToken());
                    }
                });
                /*HttpUtil.get("/message", new HashMap()).subscribe(new BaseResponseObserver<List<Message>>() {
                    @Override
                    public void success(List<Message> result) {
                        EBLog.i("tag", result.get(0).getMsg());
                        //System.out.print(result.getMsg());
                    }
                });*/
                Toast.makeText(MainActivity.this, "测试接口使用，误删", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
