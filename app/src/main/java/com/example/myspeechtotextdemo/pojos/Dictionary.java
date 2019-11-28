
package com.example.myspeechtotextdemo.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dictionary {

    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("frequency")
    @Expose
    private Integer frequency;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

}
