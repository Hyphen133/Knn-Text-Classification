package knn.similarity;

public class InversedEuclideanDistance implements SimilarityMeasure {
    @Override
    public double calculateSimilarity(short[] v1, short[] v2) {
        if (v1.length != v2.length) {
            throw new IllegalArgumentException("Vectors have different length");
        }

        double distance = 0;

        for (int i = 0; i < v1.length; i++) {
            distance += Math.pow(1/(double)(v1[i]+1) - 1/(double)(v2[i]+1), 2);
        }

        return Math.sqrt(distance);
    }
}
