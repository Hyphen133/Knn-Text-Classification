package knn.feature_extractionV2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CoocurrenceMapProcessing {
    void remember(ArrayList<String[]> texts);
    Map<String,Integer> apply(Map<String, Integer>[] coocurrenceMap ,List<Preprocessing> preprocessingFilters, List<VolcabularyReducing> volcabularyReducingFilters);
}
