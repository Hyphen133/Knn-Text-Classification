package knn.classification;

public interface Classification {

    /*
     *   Input - vector of features
     *   Output - vector of class predictions for each of output classes
     */
    int[] classify(double[] vector);
}
