package knn.classification;

import java.util.Map;

public class ClassProcessing {

    public static int[][] convertTagsToVectorsWithSingleOne(String[] tags, Map<String,Integer> tagIndexMap){
        int tagIndexesSize = tagIndexMap.keySet().size();
        int[][] tagVectors = new int[tags.length][tagIndexesSize];

        for (int i = 0; i < tags.length; i++) {
            System.out.println(tags[i]);
            System.out.println(tagIndexMap.get(tags[i]));
            tagVectors[i][tagIndexMap.get(tags[i])] = 1;
        }

        return tagVectors;
    }


}
