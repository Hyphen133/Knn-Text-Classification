package knn.feature_extractionV2;

import java.util.ArrayList;
import java.util.Map;

public interface CoocurrenceMapCreating {
    Map<String,Integer>[] create(ArrayList<String[]> texts);
    Map<String,Integer> getDictonary(ArrayList<String[]> texts);
}
