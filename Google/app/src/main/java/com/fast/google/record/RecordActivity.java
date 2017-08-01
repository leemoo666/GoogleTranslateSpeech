package com.fast.google.record;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fast.google.R;
import com.fast.google.recordUtil.RecordManager;

/**
 * Created by lixiaoming on 2017/7/31.
 */

public class RecordActivity extends Activity {

    Button switchBtn;
    TextView status;

    private RecordManager waveCanvas;
    private String mFileName = "test";//文件名

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.activity_record);
        status = findViewById(R.id.status);
        switchBtn = findViewById(R.id.switchBtn);
        switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (waveCanvas == null || !waveCanvas.isRecording) {
                    status.setText("录音中...");
                    switchBtn.setText("停止录音");
                    startAudio();

                } else {
                    status.setText("停止录音");
                    switchBtn.setText("开始录音");
                    waveCanvas.Stop();
                    waveCanvas = null;
                }
            }
        });
    }


    /**
     * 开始录音
     */
    private void startAudio() {
        waveCanvas = new RecordManager();
        waveCanvas.Start(mFileName, new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                return true;
            }
        });
    }
}
