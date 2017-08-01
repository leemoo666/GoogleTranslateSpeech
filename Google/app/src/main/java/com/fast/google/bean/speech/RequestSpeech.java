package com.fast.google.bean.speech;

import java.io.Serializable;

/**
 * Created by lixiaoming on 2017/7/31.
 */

public class RequestSpeech implements Serializable {



//     "audio": {
//        "content": ""
//    },
//            "config": {
//        "encoding": "LINEAR16",
//                "languageCode": "cmn-Hans-CN",
//                "sampleRateHertz": 16000
//    }


    private Config config = new Config();
    private Audio audio = new Audio();

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public class Config implements Serializable {
        private String encoding = "LINEAR16";
        private int sampleRateHertz = 16000;
        private String languageCode = "cmn-Hans-CN";

        public String getEncoding() {
            return encoding;
        }

        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

        public int getSampleRateHertz() {
            return sampleRateHertz;
        }

        public void setSampleRateHertz(int sampleRateHertz) {
            this.sampleRateHertz = sampleRateHertz;
        }

        public String getLanguageCode() {
            return languageCode;
        }

        public void setLanguageCode(String languageCode) {
            this.languageCode = languageCode;
        }
    }

    public class Audio implements Serializable {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}
