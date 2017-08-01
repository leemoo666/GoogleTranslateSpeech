package com.fast.google.bean.speech;

import java.io.PipedReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiaoming on 2017/7/31.
 */

public class ResponseSpeech implements Serializable {


    private List<Result> results = new ArrayList<>();

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }


    public class Result implements Serializable {

        private List<Alternatives> alternatives = new ArrayList<>();

        public List<Alternatives> getAlternatives() {
            return alternatives;
        }

        public void setAlternatives(List<Alternatives> alternatives) {
            this.alternatives = alternatives;
        }
    }

    public class Alternatives implements Serializable {

        private String transcript;
        private float confidence;

        public String getTranscript() {
            return transcript;
        }

        public void setTranscript(String transcript) {
            this.transcript = transcript;
        }

        public float getConfidence() {
            return confidence;
        }

        public void setConfidence(float confidence) {
            this.confidence = confidence;
        }
    }

}
