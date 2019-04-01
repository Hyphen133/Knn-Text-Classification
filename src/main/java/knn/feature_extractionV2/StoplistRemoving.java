package knn.feature_extractionV2;

import java.util.ArrayList;
import java.util.Set;

public class StoplistRemoving implements VocabularyReducing {
    Set<String> frequentWords;

    public StoplistRemoving(Set<String> frequentWords) {
        this.frequentWords = frequentWords;
    }

    @Override
    public void apply(ArrayList<String[]> texts) {
        for (int i = 0; i < texts.size(); i++) {
            ArrayList<String> words = new ArrayList<>();
            for (int j = 0; j < texts.get(i).length; j++) {
                if(!frequentWords.contains(texts.get(i)[j])){
                    words.add(texts.get(i)[j]);
                }

            }
            texts.set(i, words.toArray(new String[0]));
        }
    }
}
