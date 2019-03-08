package knn.similarity;

public class ChebyshevDistance implements SimilarityMeasure {
    @Override
    public double calculateSimilarity(short[] v1, short[] v2) {
        if (v1.length != v2.length) {
            throw new IllegalArgumentException("Vectors have different length");
        }

        int distance = 0;

        for (int i = 0; i < v1.length; i++) {
            if (Math.abs(v1[i] - v2[i]) > distance) {
                distance = Math.abs(v1[i] - v2[i]);
            }
        }

        return distance;
    }
}
