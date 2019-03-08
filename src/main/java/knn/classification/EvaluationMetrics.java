package knn.classification;

import java.util.List;

public class EvaluationMetrics {
    public static double calculateAccuracy(List<ClassificationResult> testResults, int[][] testTagVectors){
        int correctTests = 0;
        for (ClassificationResult testResult : testResults) {
            if(ClassProcessing.compareTags(testResult.getClassVector(), testTagVectors[testResult.getIndex()]) == true){
                correctTests++;
            }
        }

        return ((double)correctTests)/testResults.size();
    }
}
