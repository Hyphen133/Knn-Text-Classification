package knn.similarity;

public class EuclideanDistance implements SimilarityMeasure {
    @Override
    public double calculateSimilarity(double[] v1, double[] v2) {
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
