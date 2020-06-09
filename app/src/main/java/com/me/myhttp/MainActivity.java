package com.me.myhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.me.myhttp.network.BaseObserver;
import com.me.myhttp.network.NetworkManager;
import com.me.myhttp.network.ProgressListener;
import com.me.myhttp.network.RxManager;
import com.me.myhttp.network.SchedulerTransformer;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.progress);


        getBean();
    }

    public void getString() {

        String url = "http://tcc.taobao.com/";



        NetworkManager.getInstance().getService().getTelAddress("13718269453")
                .compose(new SchedulerTransformer())
                .subscribe();


    }




    public void getBean() {

        String url = "https://wis.qq.com/weather/";

        NetworkManager.getInstance().getService().getWeatherTips("山西", "晋城")
                .compose(new SchedulerTransformer<>())
                .subscribe(new BaseObserver<WeatherTip>() {
                    @Override
                    public void onSuccess(WeatherTip weatherTip) {

                    }

                });


    }

    public void getDownload() {
        String url = "https://download.sj.qq.com/upload/connAssitantDownload/upload/MobileAssistant_1.apk";
        String savePath = "";
        NetworkManager.getInstance().download(url, "", new ProgressListener() {
            @Override
            public void onProgress(long total, long progress) {

            }
        });


    }


    public void upload(String path) {
        String name = "";
        String filePath = "";
        NetworkManager.getInstance().upload(name, new File(filePath), new ProgressListener() {
            @Override
            public void onProgress(long total, long progress) {

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxManager.getIns().cancel();
    }
}
