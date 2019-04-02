package knn.feature_extractionV2;

import knn.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FeatureSelector {

    public static float[][] selectForEachCategory(float[][] vectors, List<String>  tags, List<String> choosenTags, int featuresCountForEachCategory){

        //Get all indexes
        ArrayList<Integer> columnIndexes = new ArrayList<>();

        for (String choosenTag : choosenTags) {
            columnIndexes.addAll(Arrays.stream(selectColumnIndexesForTag(vectors,tags, featuresCountForEachCategory, choosenTag)).boxed().collect(Collectors.toList()));
        }

        return selectFeatureVectors(vectors, columnIndexes);
    }


    private static float[][] selectFeatureVectors(float[][] vectors, ArrayList<Integer> columnIndexes){
        float[][] selectedVectors = new float[vectors[0].length][columnIndexes.size()];


        for (int i = 0; i < columnIndexes.size(); i++) {
            for (int j = 0; j < vectors.length; j++) {
                selectedVectors[j][i] = vectors[j][i];
            }
        }

        return selectedVectors;

    }

    private static int[] selectColumnIndexesForTag(float[][] vectors, List<String> tags, int indexesCount, String selectedTag){
        float[] countsForWords = new float[vectors[0].length];

        for (int i = 0; i < vectors.length; i++) {
            if(tags.get(i).equals(selectedTag)){
                for (int j = 0; j < vectors[0].length; j++) {
                    countsForWords[j] += vectors[i][j];
                }
            }
        }

        return Utils.topN(countsForWords, indexesCount);
    }

}
