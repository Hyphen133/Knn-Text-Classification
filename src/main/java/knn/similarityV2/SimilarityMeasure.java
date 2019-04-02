package knn.similarityV2;

public interface SimilarityMeasure {
    double calculateSimilarity(float[] v1, float[] v2);
}