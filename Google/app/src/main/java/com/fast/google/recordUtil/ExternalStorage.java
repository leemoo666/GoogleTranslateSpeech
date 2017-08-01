package com.fast.google.recordUtil;

import android.os.Environment;

import java.io.File;

/**
 * Created by lixiaoming on 2017/8/1.
 */

public class ExternalStorage {

    public static final String DATA_DIRECTORY = Environment
            .getExternalStorageDirectory() + "/";

    /**
     * 创建不同的目录
     */
    public static void createDirectory() {
        if (sdCardExists()) {
            File file = new File(DATA_DIRECTORY);
            if (!file.exists()) {
                file.mkdirs();
            }
        } else {
            File file = new File(DATA_DIRECTORY);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    public static boolean sdCardExists() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
}
