package knn.feature_extraction;

import java.util.*;

public class InversedBagOfWords implements FeatureExtraction
{
    final int REVERSION_CONSTANT = 100;

    @Override
    public Map<String, Integer>[] extractFeatures(String[][] wordsInTexts) {

        Map<String, Integer>[] featureTextVectors = new HashMap[wordsInTexts.length];

        for (int i = 0; i < wordsInTexts.length; i++) {
            String[] textWords = wordsInTexts[i];

            //Mapping word -> occurence count
            Map<String,Integer> wordOccurenceMap = new HashMap<>();

            for (int j = 0; j < textWords.length; j++) {
                String currentWord = textWords[j];

                //Add count
                if(wordOccurenceMap.containsKey(currentWord)){
                    int currentCount = wordOccurenceMap.get(currentWord);
                    wordOccurenceMap.put(currentWord, currentCount+1 );
                }else{
                    wordOccurenceMap.put(currentWord,1);
                }
            }

            for (String s : wordOccurenceMap.keySet()) {
                wordOccurenceMap.put(s, (int)(1.0/wordOccurenceMap.get(s)*REVERSION_CONSTANT)) ;
            }

            //Add complete wordOccurence map
            featureTextVectors[i] = wordOccurenceMap;
        }

        return featureTextVectors;

    }

}
