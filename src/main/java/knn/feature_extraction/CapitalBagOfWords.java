package knn.feature_extraction;

import java.util.*;

public class CapitalBagOfWords implements FeatureExtraction {
    List<String> volcabulary = new ArrayList<>();

    @Override
    public Map<String, Integer>[] extractFeatures(String[][] wordsInTexts) {
        Map<String, Integer>[] featureTextVectors = new HashMap[wordsInTexts.length];

        //Mapping word -> onehot index
        Set<String> volcabularySet = new TreeSet<>();


        for (int i = 0; i < wordsInTexts.length; i++) {
            String[] textWords = wordsInTexts[i];

            //Mapping word -> occurence count
            Map<String,Integer> wordOccurenceMap = new HashMap<>();

            for (int j = 0; j < textWords.length; j++) {
                String currentWord = textWords[j];

                if(currentWord.length() > 0){
                    if(Character.isUpperCase(currentWord.charAt(0)) ){
                        currentWord = currentWord.toLowerCase();            //!! Lowercasing

                        //Add to volcabulary
                        volcabularySet.add(currentWord);

                        //Add count
                        if(wordOccurenceMap.containsKey(currentWord)){
                            int currentCount = wordOccurenceMap.get(currentWord);
                            wordOccurenceMap.put(currentWord, currentCount+1 );
                        }else{
                            wordOccurenceMap.put(currentWord,1);
                        }
                    }
                }

            }

            //Add complete wordOccurence map
            featureTextVectors[i] = wordOccurenceMap;
        }

        volcabulary = new ArrayList<>();
        volcabulary.addAll(volcabularySet);
        Collections.sort(volcabulary);

        return featureTextVectors;
    }

    @Override
    public List<String> getOrderedVolcabulary() {
        return volcabulary;
    }
}
