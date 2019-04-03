package knn.feature_extractionV2;

import knn.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagSpecificMeanSelector implements FeatureSelector {
    public float[][] selectForEachCategory(float[][] vectors, List<String>  tags, List<String> choosenTags, int featuresCountForEachCategory){

        //Get all indexes
        ArrayList<Integer> columnIndexes = new ArrayList<>();

        for (String choosenTag : choosenTags) {
            columnIndexes.addAll(Arrays.stream(selectColumnIndexesForTag(vectors,tags, featuresCountForEachCategory, choosenTag)).boxed().collect(Collectors.toList()));
        }

        return selectFeatureVectors(vectors, columnIndexes);
    }


    private float[][] selectFeatureVectors(float[][] vectors, ArrayList<Integer> columnIndexes){
        float[][] selectedVectors = new float[vectors.length][columnIndexes.size()];


        for (int i = 0; i < columnIndexes.size(); i++) {
            for (int j = 0; j < vectors.length; j++) {
                selectedVectors[j][i] = vectors[j][i];
            }
        }

        return selectedVectors;

    }

    private int[] selectColumnIndexesForTag(float[][] vectors, List<String> tags, int indexesCount, String selectedTag){
        float[] countsForWordsForThisTags = new float[vectors[0].length];
        float[] countsForWordsForOtherTags = new float[vectors[0].length];
        int thisCount = 0;
        int otherCount = 0;

        for (int i = 0; i < vectors.length; i++) {
            for (int j = 0; j < vectors[0].length; j++) {
                if(tags.get(i).equals(selectedTag)){
                    countsForWordsForThisTags[j] += vectors[i][j];
                    thisCount++;
                }else{
                    countsForWordsForOtherTags[j] -= vectors[i][j];
                    otherCount++;
                }
            }

        }

        for (int i = 0; i < countsForWordsForThisTags.length; i++) {
            countsForWordsForThisTags[i] = countsForWordsForThisTags[i]/thisCount;
        }
        for (int i = 0; i < countsForWordsForOtherTags.length; i++) {
            countsForWordsForOtherTags[i] = countsForWordsForOtherTags[i]/otherCount;
        }



        return Utils.topN(Utils.elementwiseSameLengthVectorSubtract(countsForWordsForThisTags,countsForWordsForOtherTags), indexesCount);
    }

}
