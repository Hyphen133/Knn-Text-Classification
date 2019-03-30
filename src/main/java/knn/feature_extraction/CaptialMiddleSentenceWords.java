package knn.feature_extraction;

import knn.preprocessing.PreprocessingRule;

import java.util.HashMap;
import java.util.Map;

public class CaptialMiddleSentenceWords implements FeatureExtraction {

    private PreprocessingRule rule;
    private int weight;

    public CaptialMiddleSentenceWords(PreprocessingRule rule, int weight) {
        this.rule = rule;
        this.weight = weight;
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


                if (j!=0 && !textWords[j-1].contains(".")  && Character.isUpperCase(currentWord.charAt(0))) {   //Check if word is frequent
                    wordOccurenceMap.put(rule.applyTo(currentWord), weight);
                }
            }

            //Add complete wordOccurence map
            featureTextVectors[i] = wordOccurenceMap;
        }

        return featureTextVectors;
    }

}

