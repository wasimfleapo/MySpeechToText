package com.example.myspeechtotextdemo.model;

public class DictionaryModel {

    private String word;
    private int frequency;
    private boolean isBackgroundColoured = false;

    public DictionaryModel(String word, int frequency, boolean isBackgroundColoured) {
        this.word = word;
        this.frequency = frequency;
        this.isBackgroundColoured = isBackgroundColoured;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequenct(int frequency) {
        this.frequency = frequency;
    }

    public boolean isBackgroundColoured() {
        return isBackgroundColoured;
    }

    public void setBackgroundColoured(boolean backgroundColoured) {
        isBackgroundColoured = backgroundColoured;
    }
}
