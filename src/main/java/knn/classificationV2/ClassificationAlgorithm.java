package knn.classificationV2;

import knn.classification.ClassificationResult;

import java.util.List;

public interface ClassificationAlgorithm {
    ClassificationResults classify(float[][] vectors, int[][] tags);
}
