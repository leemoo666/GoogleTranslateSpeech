package com.fast.google;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
//import android.util.Base64;
//import android.util.Base64;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.fast.google.bean.speech.RequestSpeech;
import com.fast.google.bean.speech.ResponseSpeech;
import com.fast.google.bean.translate.ResponseTranslate;
import com.fast.google.record.RecordActivity;
import com.fast.google.rest.RestClientManager;
import com.fast.google.rest.base.MSubscriber;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.List;

import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tvTranslate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                translate();
            }
        });
        findViewById(R.id.tvSpeech).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speech();
            }
        });

        findViewById(R.id.tvToRecord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(intent);
            }
        });
    }


    private void translate() {
        RestClientManager.getUserApiService().translate("我是谁，我在哪", "text", "zh-CN", "ja", "AIzaSyCp1yMRF_hTmWSJC3Bm0soY0X9nKG-WTsw")
                .subscribeOn(Schedulers.newThread())//请求在新的线程中执行
                .observeOn(Schedulers.io())
                .subscribe(new MSubscriber<ResponseTranslate>() {
                    @Override
                    public void onSuccess(ResponseTranslate responseTranslate) {
                        Log.i("lxm", "success");
                        List<ResponseTranslate.Translation> translations = responseTranslate.getData().getTranslations();
                        for (ResponseTranslate.Translation translation : translations) {
                            Log.i("lxm", "...." + translation.getTranslatedText());
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        Log.i("lxm", "e ===" + e.toString());
                    }
                });
    }


    private void speech() {

        final RequestSpeech speech = new RequestSpeech();
        RxPermissions rxpermission = new RxPermissions(this);
        rxpermission.request(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            speech.getAudio().setContent(base64ToString());
                        }
                    }
                });


        RestClientManager.getLoginApiService().speech("AIzaSyCp1yMRF_hTmWSJC3Bm0soY0X9nKG-WTsw", speech)
                .subscribeOn(Schedulers.newThread())//请求在新的线程中执行
                .observeOn(Schedulers.io())
                .subscribe(new MSubscriber<ResponseSpeech>() {
                    @Override
                    public void onSuccess(ResponseSpeech responseSpeech) {
                        Log.i("lxm", "success" + responseSpeech.getResults().get(0).getAlternatives().get(0).getTranscript());
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        Log.i("lxm", "e ===" + e.toString());
                    }
                });


    }

    private String base64ToString() {
        String path = Environment.getExternalStorageDirectory().getPath() + "/test.wav";
        try {
            File file = new File(path);
            FileInputStream inputFile = new FileInputStream(file);

            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            String base64Str = Base64.encodeToString(buffer, Base64.DEFAULT).replaceAll("\n", "");

            return base64Str;
        } catch (Exception e) {
            Log.i("", "e . ." + e.toString());
        }
        return "";
    }
}
