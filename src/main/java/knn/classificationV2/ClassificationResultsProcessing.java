package knn.classificationV2;

import knn.Utils;

import java.util.ArrayList;
import java.util.Arrays;

public class ClassificationResultsProcessing {
    public static ConfusionMatrix getConfusionMatrix(ClassificationResults results){
        ArrayList<int[]> classifiedTags = results.getClassifiedTags();
        ArrayList<int[]> correctTags = results.getTags();


        int[][] matrix = new int[correctTags.get(0).length][correctTags.get(0).length];

        //Rows are correct values
        //Columns are predicted values
        for (int i = 0; i < classifiedTags.size(); i++) {
            matrix[Utils.getIndexFromOneHotVector(correctTags.get(i))][Utils.getIndexFromOneHotVector(classifiedTags.get(i))] += 1;
        }

        return new ConfusionMatrix(matrix);
    }
}
