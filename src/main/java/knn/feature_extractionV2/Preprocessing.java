package knn.feature_extractionV2;

import java.util.ArrayList;

public interface Preprocessing {
    void apply(ArrayList<String[]> texts);
//    String apply(String word);
}
