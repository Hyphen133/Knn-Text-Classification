package knn.similarity;

public class CosineSimilarity implements SimilarityMeasure {
    @Override
    public double calculateSimilarity(short[] v1, short[] v2) {
        if (v1.length != v2.length) {
            throw new IllegalArgumentException("Vectors have different length");
        }

        int numerator = 0;
        int denominator1 = 0;
        int denominator2 = 0;

        for (int i = 0; i < v1.length; i++) {
            numerator += v1[i]*v2[i];
            denominator1 += Math.pow(v1[i],2);
            denominator2 += Math.pow(v2[i],2);
        }

        return numerator/(Math.sqrt(denominator1)*Math.sqrt(denominator2));
    }
}
