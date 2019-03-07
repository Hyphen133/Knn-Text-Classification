package knn.classification;

public interface ClassificationAlgorithm {

    /*
     *   Input - vector of features
     *   Output - vector of class predictions for each of output classes
     */
    int[] classify(double[] vector);
}
