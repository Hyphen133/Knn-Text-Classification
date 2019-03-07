package knn.similarity;

public interface SimilarityMeasure {
    double calculateSimilarity(short[] v1, short[] v2);
}
