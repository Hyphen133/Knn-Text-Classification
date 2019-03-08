package knn.classification;

import java.util.List;

public interface ClassificationAlgorithm {

    /*
     *   Input - vector of features
     *   Output - vector of class predictions for each of output classes
     */
    int[] classify(short[] vector);
    List<ClassificationResult> classify(short[][] vectors);
}
