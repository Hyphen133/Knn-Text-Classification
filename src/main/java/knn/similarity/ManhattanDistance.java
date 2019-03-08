package knn.similarity;

public class ManhattanDistance implements SimilarityMeasure {
    @Override
    public double calculateSimilarity(short[] v1, short[] v2) {
        if (v1.length != v2.length) {
            throw new IllegalArgumentException("Vectors have different length");
        }

        double distance = 0;

        for (int i = 0; i < v1.length; i++) {
            distance += Math.abs(v1[i] - v2[i]);
        }

        return distance;
    }
}
