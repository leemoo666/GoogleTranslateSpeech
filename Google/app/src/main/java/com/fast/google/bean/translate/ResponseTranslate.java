package com.fast.google.bean.translate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiaoming on 2017/7/31.
 */

public class ResponseTranslate implements Serializable {


    private Data data;


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable {
        List<Translation> translations = new ArrayList<>();

        public List<Translation> getTranslations() {
            return translations;
        }

        public void setTranslations(List<Translation> translations) {
            this.translations = translations;
        }
    }

   public class Translation implements Serializable {
        private String translatedText;

        public String getTranslatedText() {
            return translatedText;
        }

        public void setTranslatedText(String translatedText) {
            this.translatedText = translatedText;
        }
    }

}
