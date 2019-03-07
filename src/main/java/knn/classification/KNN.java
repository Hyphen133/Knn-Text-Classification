package knn.classification;

import knn.Utils;
import knn.similarity.SimilarityMeasure;

public class KNN implements ClassificationAlgorithm {

    double[][] baseVectors;
    int[][] baseVectorsClasses;
    SimilarityMeasure similarityMeasure;
    int k;

    public KNN(double[][] baseVectors, int[][] baseVectorsClasses, SimilarityMeasure similarityMeasure, int k) {
        this.baseVectors = baseVectors;
        this.baseVectorsClasses = baseVectorsClasses;
        this.similarityMeasure = similarityMeasure;
        this.k = k;
    }

    public int[] classify(double[] vector) {
        // Calculate similarities for each of base vector
        double[] similarities = new double[baseVectors.length];

        for (int i = 0; i < similarities.length; i++) {
            similarities[i] = similarityMeasure.calculateSimilarity(baseVectors[i],vector);
        }

        //Get K first similarities
        int[] topIndexes = Utils.topN(similarities,k);

        //Calculate class counts in this k first elements
        int numberOfClasses = baseVectorsClasses[0].length;
        int[] classCounts = new int[numberOfClasses];
        for (int i = 0; i < k; i++) {
            classCounts = Utils.elementwiseSameLengthVectorAdd(classCounts,baseVectorsClasses[topIndexes[i]]);
        }

        //Set output classification vector
        int maxClassIndex = Utils.largestVectorElementIndex(classCounts);
        int[] outClasses = new int[numberOfClasses];
        outClasses[maxClassIndex] = 1;
        return outClasses;
    }

}
