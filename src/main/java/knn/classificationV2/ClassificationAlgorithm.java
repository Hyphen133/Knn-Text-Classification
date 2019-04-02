package knn.classificationV2;

import knn.classification.ClassificationResult;

import java.util.List;

public interface ClassificationAlgorithm {
    int[] classify(float[] vector, int[] tag);
    List<int[][]> classify(float[][] vectors, int[][] tags);
}
