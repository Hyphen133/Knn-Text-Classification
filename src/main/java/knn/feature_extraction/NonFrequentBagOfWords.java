package knn.feature_extraction;

import java.util.*;

public class NonFrequentBagOfWords implements FeatureExtraction {
    Set<String> frequentWords;
    List<String> vocabulary;

    public NonFrequentBagOfWords(Set<String> frequentWords) {
        this.frequentWords = frequentWords;
    }

    @Override
    public Map<String, Integer>[] extractFeatures(String[][] wordsInTexts) {
        Map<String, Integer>[] featureTextVectors = new HashMap[wordsInTexts.length];

        //Mapping word -> onehot index
        Set<String> vocabularySet = new TreeSet<>();


        for (int i = 0; i < wordsInTexts.length; i++) {
            String[] textWords = wordsInTexts[i];

            //Mapping word -> occurence count
            Map<String,Integer> wordOccurenceMap = new HashMap<>();

            for (int j = 0; j < textWords.length; j++) {
                String currentWord = textWords[j];

                if(currentWord.length() > 0){
                    if(Character.isUpperCase(currentWord.charAt(0)) ){
                        currentWord = currentWord.toLowerCase();            //!! Lowercasing


                        if(!frequentWords.contains(currentWord)){   //Check if word is frequent
                            //Add to vocabulary
                            vocabularySet.add(currentWord);

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

            }

            //Add complete wordOccurence map
            featureTextVectors[i] = wordOccurenceMap;
        }

        vocabulary = new ArrayList<>();
        vocabulary.addAll(vocabularySet);

        return featureTextVectors;
    }

    @Override
    public List<String> getOrderedVocabulary() {
        return vocabulary;
    }
}
