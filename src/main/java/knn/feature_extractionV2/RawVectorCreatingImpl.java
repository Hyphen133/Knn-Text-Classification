package knn.feature_extractionV2;

import java.util.Map;

public class RawVectorCreatingImpl  implements RawVectorCreating {
    @Override
    public float[][] apply(Map<String, Integer>[] coocurrenceMap, Map<String, Integer> volcabulary) {
        float[][] vector = new float[coocurrenceMap.length][volcabulary.keySet().size()];

        for (int i = 0; i < coocurrenceMap.length; i++) {
            for (String key : coocurrenceMap[i].keySet()) {
                vector[i][volcabulary.get(key)] += coocurrenceMap[i].get(key);
            }
        }

        return vector;
    }
}
