package knn.classification;

import java.util.List;
import java.util.Map;

public class ClassProcessing {

    public static int[][] convertTagsToVectorsWithSingleOne(String[] tags, Map<String,Integer> tagIndexMap){
        int tagIndexesSize = tagIndexMap.keySet().size();
        int[][] tagVectors = new int[tags.length][tagIndexesSize];

        for (int i = 0; i < tags.length; i++) {
            tagVectors[i][tagIndexMap.get(tags[i])] = 1;
        }

        return tagVectors;
    }

    public static double[][] convertFeaturesToVectors(Map<String,Integer>[] features, List<String> volcabulary){
        
        double[][] featureVectors = new double[features.length][volcabulary.size()];

        for (int i = 0; i < features.length; i++) {
            for (int j = 0; j < volcabulary.size(); j++) {
                String currentWord = volcabulary.get(j);
                if(features[i].containsKey(currentWord)){
                    featureVectors[i][j] = features[i].get(currentWord);
                }else{
                    featureVectors[i][j] = 0;
                }
            }
        }
        return featureVectors;
    }



}
