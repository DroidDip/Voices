package com.voices.dictionary;

import android.content.Context;
import android.content.res.Resources;

import com.voices.R;
import com.voices.model.DictionaryModel;

import java.util.ArrayList;
import java.util.HashMap;

public class Dictionary {

    private ArrayList<DictionaryModel> dictionaryArray = null;

    /**
     * Method to create dictionary with set of words & return the list to display
     *
     * @param mResources
     * @return ArrayList of DictionaryModel
     */
    public ArrayList<DictionaryModel> createDictionary(Resources mResources) {
        if (mResources != null) {
            String[] dictionary = mResources.getStringArray(R.array.speech_dictionary);
            dictionaryArray = new ArrayList<>();

            for (int i = 0; i < dictionary.length; i++) {
                dictionaryArray.add(new DictionaryModel(dictionary[i], 0));
            }
        }
        return dictionaryArray;
    }

    public HashMap<Integer, Integer> getDictionaryMatchPosition(String speechTxt) {
        String[] speechWords = speechTxt.split("\\s+");
        HashMap<Integer, Integer> updatedPositions = new HashMap<>();

        //Check each individual word
        for (int i = 0; i < dictionaryArray.size(); i++) {
            DictionaryModel dictionaryModel = dictionaryArray.get(i);

            for (int j = 0; j < speechWords.length; j++) {
                if (dictionaryModel.getDictionaryWord().equalsIgnoreCase(speechWords[j]) ||
                        dictionaryModel.getDictionaryWord().equalsIgnoreCase(speechTxt)) {
                    dictionaryModel.setSpeechFrequency(dictionaryModel.getSpeechFrequency() + 1);
                    updatedPositions.put(i, i);
                }
            }
        }


        //Check word sequence
        for (int i = 0; i < dictionaryArray.size(); i++) {
            DictionaryModel dictionaryModel = dictionaryArray.get(i);

            if (speechTxt.toLowerCase().contains(dictionaryModel.getDictionaryWord().toLowerCase())) {
                if (!updatedPositions.containsKey(i)) {
                    dictionaryModel.setSpeechFrequency(dictionaryModel.getSpeechFrequency() + 1);
                    updatedPositions.put(i, i);
                }
            }
        }
        return updatedPositions;
    }
}
