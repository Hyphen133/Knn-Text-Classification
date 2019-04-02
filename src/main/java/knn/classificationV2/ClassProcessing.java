package knn.classificationV2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassProcessing {
    public static int[][] convertTagsToVectorsWithSingleOne(ArrayList<String> tags, List<String> chosenTags) {
        Map<String, Integer> tagIndexMap = new HashMap<>();

        for (int i = 0; i < chosenTags.size(); i++) {
            tagIndexMap.put(chosenTags.get(i), i);
        }

        int tagIndexesSize = tagIndexMap.keySet().size();
        int[][] tagVectors = new int[tags.size()][tagIndexesSize];

        for (int i = 0; i < tags.size(); i++) {
            tagVectors[i][tagIndexMap.get(tags.get(i))] = 1;
        }

        return tagVectors;
    }
}
