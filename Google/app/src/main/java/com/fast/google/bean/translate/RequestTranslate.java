package com.fast.google.bean.translate;

import java.io.Serializable;

/**
 * Created by lixiaoming on 2017/7/31.
 */

public class RequestTranslate implements Serializable{


    //https://translation.googleapis.com/language/translate/v2?
    // q=我是谁，我在哪&format=text&source=zh-CN&key=AIzaSyCp1yMRF_hTmWSJC3Bm0soY0X9nKG-WTsw&target=ja
    private String q;
    private String format;
    private String source;
    private String target;
    private String key;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
