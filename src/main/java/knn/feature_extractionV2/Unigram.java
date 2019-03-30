package knn.feature_extractionV2;

import java.util.*;

public class Unigram implements CoocurrenceMapCreating {
    Map<String,Integer> vocabulary;

    public Unigram() {
        vocabulary = new HashMap<>();
    }

    @Override
    public Map<String, Integer>[] create(ArrayList<String[]> texts) {

        Map<String, Integer>[] featureTextVectors = new HashMap[texts.size()];

        //Mapping word
//        Map<String,Integer> vocabulary = new HashMap<>();

        for (int i = 0; i < texts.size(); i++) {
            String[] textWords = texts.get(i);

            //Mapping word -> occurence count
            Map<String,Integer> wordOccurenceMap = new HashMap<>();

            for (int j = 0; j < textWords.length; j++) {
                String currentWord = textWords[j];

                //Add to vocabulary
                if(!vocabulary.keySet().contains(currentWord)){
                    vocabulary.put(currentWord, vocabulary.keySet().size());
                }

                //Add count
                if(wordOccurenceMap.containsKey(currentWord)){
                    int currentCount = wordOccurenceMap.get(currentWord);
                    wordOccurenceMap.put(currentWord, currentCount+1 );
                }else{
                    wordOccurenceMap.put(currentWord,1);
                }
            }

            //Add complete wordOccurence map
            featureTextVectors[i] = wordOccurenceMap;
        }


        return featureTextVectors;

    }

    @Override
    public Map<String, Integer> getDictonary(ArrayList<String[]> texts) {
        return vocabulary;
    }
}
