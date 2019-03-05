package knn.similarity;

public interface SimilarityMeasure {
    double calculateSimilarity(double[] v1, double[] v2);
}
