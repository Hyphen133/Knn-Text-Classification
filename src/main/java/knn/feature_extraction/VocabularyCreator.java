package knn.feature_extraction;

import java.util.*;

public class VocabularyCreator {
    public static List<String> getVolcabulary(String[][] wordsInTexts){
        Set<String> vocabularySet = new TreeSet<>();


        for (int i = 0; i < wordsInTexts.length; i++) {
            String[] textWords = wordsInTexts[i];


            for (int j = 0; j < textWords.length; j++) {
                vocabularySet.add(textWords[j]);
            }


        }

        List<String> vocabulary = new ArrayList<>();
        vocabulary.addAll(vocabularySet);
        return vocabulary;
    }
}
