package knn.classification;

import java.util.List;
import java.util.Map;

public class ClassProcessing {

    public static int[][] convertTagsToVectorsWithSingleOne(String[] tags, Map<String, Integer> tagIndexMap) {
        int tagIndexesSize = tagIndexMap.keySet().size();
        int[][] tagVectors = new int[tags.length][tagIndexesSize];

        for (int i = 0; i < tags.length; i++) {
            tagVectors[i][tagIndexMap.get(tags[i])] = 1;
        }

        return tagVectors;
    }

    public static short[][] convertFeaturesToVectors(Map<String, Integer>[] features, List<String> volcabulary) {

        short[][] featureVectors = new short[features.length][volcabulary.size()];

        for (int i = 0; i < features.length; i++){
            for (int j = 0; j < volcabulary.size(); j++) {
                String currentWord = volcabulary.get(j);
                if (features[i].containsKey(currentWord)) {
                    featureVectors[i][j] = features[i].get(currentWord).shortValue();
                    featureVectors[i][j] = 0;
                }
            }
        }

        return featureVectors;
    }

    public static boolean compareTags(int[] tag1, int[] tag2) {
        if (tag1.length != tag2.length) {
            throw new IllegalArgumentException("Tags vectors should have same length");
        }

        for (int i = 0; i < tag1.length; i++) {
            if (tag1[i] != tag2[i]) {
                return false;
            }
        }
        return true;
    }


}
