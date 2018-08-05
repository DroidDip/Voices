package com.voices.model;

public class DictionaryModel {

    private String dictionaryWord;
    private int speechFrequency = 0;
    private boolean isUpdated;

    public DictionaryModel(String dictionaryWord, int speechFrequency) {
        this.dictionaryWord = dictionaryWord;
        this.speechFrequency = speechFrequency;
    }

    public String getDictionaryWord() {
        return dictionaryWord;
    }

    public void setDictionaryWord(String dictionaryWord) {
        this.dictionaryWord = dictionaryWord;
    }

    public int getSpeechFrequency() {
        return speechFrequency;
    }

    public void setSpeechFrequency(int speechFrequency) {
        this.speechFrequency = speechFrequency;
    }


    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }
}
