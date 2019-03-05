package knn.feature_extraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BagOfWords implements FeatureExtraction {
    @Override
    public Map<String, Integer>[] extractFeatures(String[][] wordsInTexts) {

        Map<String, Integer>[] featureTextVectors = new HashMap[wordsInTexts.length];

        //Mapping word -> onehot index
        Map<String, Boolean> volcabulary = new HashMap<>();


        for (int i = 0; i < wordsInTexts.length; i++) {
            String[] textWords = wordsInTexts[i];

            //Mapping word -> occurence count
            Map<String,Integer> wordOccurenceMap = new HashMap<>();

            for (int j = 0; j < textWords.length; j++) {
                String currentWord = textWords[j];

                //Add to volcabulary
                volcabulary.put(currentWord, true);

                //Add count
                if(wordOccurenceMap.containsKey("")){
                    int currentCount = wordOccurenceMap.get(textWords[j]);
                    wordOccurenceMap.put(currentWord, currentCount+1 );
                }else{
                    wordOccurenceMap.put(currentWord,1);
                }
            }

            //Add complete wordOccurence map
            featureTextVectors[i] = wordOccurenceMap;
        }


        //TODO -> return volcabulary as well
        return featureTextVectors;

    }
}
