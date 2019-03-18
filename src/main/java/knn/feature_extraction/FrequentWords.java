package knn.feature_extraction;

import java.util.*;

public class FrequentWords implements FeatureExtraction {
    Set<String> frequentWords;

    public FrequentWords(Set<String> frequentWords) {
        this.frequentWords = frequentWords;
    }

    @Override
    public Map<String, Integer>[] extractFeatures(String[][] wordsInTexts) {
        Map<String, Integer>[] featureTextVectors = new HashMap[wordsInTexts.length];


        for (int i = 0; i < wordsInTexts.length; i++) {
            String[] textWords = wordsInTexts[i];

            //Mapping word -> occurence count
            Map<String, Integer> wordOccurenceMap = new HashMap<>();

            for (int j = 0; j < textWords.length; j++) {
                String currentWord = textWords[j];


                if (frequentWords.contains(currentWord)) {   //Check if word is frequent
                    wordOccurenceMap.put(currentWord, 0);
                }

            }

            //Add complete wordOccurence map
            featureTextVectors[i] = wordOccurenceMap;
        }

        return featureTextVectors;
        }

}
