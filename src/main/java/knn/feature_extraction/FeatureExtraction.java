package knn.feature_extraction;

import java.util.HashMap;
import java.util.Map;

public interface FeatureExtraction {
    /*
    *   Input:          list of separated words from text
    *   Output map:     rows - index; column - word, 3rd dim - word counts
    *   TODO -> 3rd dim int or double??
     */
    public Map<String,Integer>[] extractFeatures(String[][] wordsInTexts);

    public Map<String, Boolean> getVolcabulary();
}
