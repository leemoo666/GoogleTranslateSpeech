package com.fast.google.recordUtil;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 录音和写入文件使用了两个不同的线程，以免造成卡机现象
 * 录音波形绘制
 */
public class RecordManager {

    private ArrayList<byte[]> write_data = new ArrayList<>();//写入文件数据
    public boolean isRecording = false;// 录音线程控制标记
    private boolean isWriting = false;// 录音线程控制标记

    private int recBufSize;// 录音最小buffer大小
    private String savePcmPath;//保存pcm文件路径
    private String saveWavPath;//保存wav文件路径

    private int readsize;

    private static final int FREQUENCY = 16000;// 设置音频采样率，44100是目前的标准，但是某些设备仍然支持22050，16000，11025
    private static final int CHANNELCONGIFIGURATION = AudioFormat.CHANNEL_IN_MONO;// 设置单声道声道
    private static final int AUDIOENCODING = AudioFormat.ENCODING_PCM_16BIT;// 音频数据格式：每个样本16位
    public final static int AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;// 音频获取源
    private AudioRecord audioRecord;

    public RecordManager() {
        recBufSize = AudioRecord.getMinBufferSize(FREQUENCY,
                CHANNELCONGIFIGURATION, AUDIOENCODING);// 录音组件
        audioRecord = new AudioRecord(AUDIO_SOURCE,// 指定音频来源，这里为麦克风
                FREQUENCY, // 16000HZ采样频率
                CHANNELCONGIFIGURATION,// 录制通道
                AUDIO_SOURCE,// 录制编码格式
                recBufSize);// 录制缓冲区大小 //先修改
        ExternalStorage.createDirectory();
    }

    /**
     * 开始录音
     *
     * @param audioName
     */
    public void Start(String audioName, Callback callback) {
        isRecording = true;
        isWriting = true;
        String path = ExternalStorage.DATA_DIRECTORY;
        savePcmPath = path + audioName + ".pcm";
        saveWavPath = path + audioName + ".wav";
        new Thread(new WriteRunnable()).start();//开线程写文件
        new RecordTask(audioRecord, recBufSize, callback).execute();
    }


    /**
     * 停止录音
     */
    public void Stop() {
        Log.e("test", "stop start");
        isRecording = false;
        audioRecord.stop();
    }

    /**
     * 异步录音程序
     *
     * @author cokus
     */
    class RecordTask extends AsyncTask<Object, Object, Object> {
        private int recBufSize;
        private AudioRecord audioRecord;
        private Callback callback;


        public RecordTask(AudioRecord audioRecord, int recBufSize, Callback callback) {
            this.audioRecord = audioRecord;
            this.recBufSize = recBufSize;
            this.callback = callback;
        }

        @Override
        protected Object doInBackground(Object... params) {
            try {
                short[] buffer = new short[recBufSize];
                audioRecord.startRecording();// 开始录制
                while (isRecording) {
                    // 从MIC保存数据到缓冲区  
                    readsize = audioRecord.read(buffer, 0, recBufSize);
                    publishProgress();
                    if (AudioRecord.ERROR_INVALID_OPERATION != readsize) {
                        synchronized (write_data) {
                            byte bys[] = new byte[readsize * 2];
                            //因为arm字节序问题，所以需要高低位交换
                            for (int i = 0; i < readsize; i++) {
                                byte ss[] = getBytes(buffer[i]);
                                bys[i * 2] = ss[0];
                                bys[i * 2 + 1] = ss[1];
                            }
                            write_data.add(bys);
                        }
                    }
                }
                isWriting = false;
            } catch (Throwable t) {
                Message msg = new Message();
                msg.arg1 = -2;
                msg.obj = t.getMessage();
                callback.handleMessage(msg);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Object... values) {
        }


        public byte[] getBytes(short s) {
            byte[] buf = new byte[2];
            for (int i = 0; i < buf.length; i++) {
                buf[i] = (byte) (s & 0x00ff);
                s >>= 8;
            }
            return buf;
        }


    }


    /**
     * 异步写文件
     *
     * @author cokus
     */
    class WriteRunnable implements Runnable {
        @Override
        public void run() {
            try {
                FileOutputStream fos2wav = null;
                File file2wav = null;
                try {
                    file2wav = new File(savePcmPath);
                    if (file2wav.exists()) {
                        file2wav.delete();
                    }
                    fos2wav = new FileOutputStream(file2wav);// 建立一个可存取字节的文件
                } catch (Exception e) {
                    e.printStackTrace();
                }
                while (isWriting || write_data.size() > 0) {
                    byte[] buffer = null;
                    synchronized (write_data) {
                        if (write_data.size() > 0) {
                            buffer = write_data.get(0);
                            write_data.remove(0);
                        }
                    }
                    try {
                        if (buffer != null) {
                            fos2wav.write(buffer);
                            fos2wav.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                fos2wav.close();
                Pcm2Wav p2w = new Pcm2Wav();//将pcm格式转换成wav 其实就尼玛加了一个44字节的头信息
                p2w.convertAudioFiles(savePcmPath, saveWavPath);
            } catch (Throwable t) {
            }
        }
    }


}
