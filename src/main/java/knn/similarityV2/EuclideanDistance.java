package knn.similarityV2;

import knn.similarityV2.SimilarityMeasure;

public class EuclideanDistance implements SimilarityMeasure {
    @Override
    public double calculateSimilarity(float[] v1, float[] v2) {
        if (v1.length != v2.length) {
            throw new IllegalArgumentException("Vectors have different length");
        }

        double distance = 0;

        for (int i = 0; i < v1.length; i++) {
            distance += Math.pow(v1[i] - v2[i], 2);
        }

        return Math.sqrt(distance);
    }
}
