package com.voices;

import com.voices.model.DictionaryModel;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static org.junit.Assert.assertTrue;

public class DictionaryTest {

    private ArrayList<DictionaryModel> dictionary;

    @BeforeClass
    public static void testClassSetup() {
        System.out.println("Getting dictionary test class ready");
    }

    @AfterClass
    public static void testClassCleanup() {
        System.out.println("Done with dictionary tests");
    }

    @Before
    public void setup() {
        dictionary = generateDictionary();
        System.out.println("Ready for testing!");
    }

    @After
    public void cleanup() {
        System.out.println("Done with unit test!");
    }

    @Test
    public void testListSize() {
        System.out.println("========Test List Size=========");
        assertTrue(dictionary.size() > 4);
        assertTrue(dictionary.size() < 13);
    }

    @Test
    public void testSingleWord() {
        System.out.println("========Single Word Test=========");
        HashMap<Integer, Integer> updatedPosition = getDictionaryMatchPosition("Hello");
        assertTrue(!updatedPosition.isEmpty());
        showDictionaryMatchFrequency(updatedPosition);
    }

    @Test
    public void testWordSequence() {
        System.out.println("========Word Sequence Test=========");
        HashMap<Integer, Integer> updatedPosition = getDictionaryMatchPosition("Navigate to Quantum Inventions 4th Street 2nd Exit");
        assertTrue(!updatedPosition.isEmpty());
        showDictionaryMatchFrequency(updatedPosition);
    }

    @Test
    public void testSameWordSequence() {
        System.out.println("========Same Word Sequence Test=========");
        HashMap<Integer, Integer> updatedPosition = getDictionaryMatchPosition("Hello Hello Navigate to Traffic Incidents at Twenty 4th Street");
        assertTrue(!updatedPosition.isEmpty());
        showDictionaryMatchFrequency(updatedPosition);
    }

    @Test
    public void testDictionaryFail() {
        System.out.println("========Dictionary Fail Test=========");
        HashMap<Integer, Integer> updatedPosition = getDictionaryMatchPosition("Hi How Are You");
        assertTrue("Sorry! No word found in the Dictionary", !updatedPosition.isEmpty());
    }


    private ArrayList<DictionaryModel> generateDictionary() {
        String[] wordArray = {"Hello", "World", "Quantum Inventions", "Inventions", "Twenty",
                "2nd Exit", "4th Street", "Traffic", "Incidents", "Navigate to"};
        ArrayList<DictionaryModel> dictionaryArray = new ArrayList<>();

        for (int i = 0; i < wordArray.length; i++) {
            dictionaryArray.add(new DictionaryModel(wordArray[i], 0));
        }
        return dictionaryArray;
    }

    private HashMap<Integer, Integer> getDictionaryMatchPosition(String speechTxt) {
        String[] speechWords = speechTxt.split("\\s+");
        HashMap<Integer, Integer> updatedPositions = new HashMap<>();

        //Check each individual word
        for (int i = 0; i < dictionary.size(); i++) {
            DictionaryModel dictionaryModel = dictionary.get(i);

            for (int j = 0; j < speechWords.length; j++) {
                if (dictionaryModel.getDictionaryWord().equalsIgnoreCase(speechWords[j]) ||
                        dictionaryModel.getDictionaryWord().equalsIgnoreCase(speechTxt)) {
                    dictionaryModel.setSpeechFrequency(dictionaryModel.getSpeechFrequency() + 1);
                    updatedPositions.put(i, i);
                }
            }
        }


        //Check word sequence
        for (int i = 0; i < dictionary.size(); i++) {
            DictionaryModel dictionaryModel = dictionary.get(i);

            if (speechTxt.toLowerCase().contains(dictionaryModel.getDictionaryWord().toLowerCase())) {
                if (!updatedPositions.containsKey(i)) {
                    dictionaryModel.setSpeechFrequency(dictionaryModel.getSpeechFrequency() + 1);
                    updatedPositions.put(i, i);
                }
            }
        }
        return updatedPositions;
    }

    private void showDictionaryMatchFrequency(HashMap<Integer, Integer> updatedPositions) {
        Iterator positionIterator = updatedPositions.keySet().iterator();
        while (positionIterator.hasNext()) {
            int key = (int) positionIterator.next();
            int value = updatedPositions.get(key);
            System.out.println(dictionary.get(value).getDictionaryWord() + " ========== " + dictionary.get(value).getSpeechFrequency());
        }
    }
}
