package knn.feature_extractionV2;

import java.util.Map;

public interface RawVectorCreating {
    float[][] apply(Map<String, Integer>[] coocurrenceMap, Map<String,Integer> volcabulary);
}
