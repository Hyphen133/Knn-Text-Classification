package knn.classificationV2;

public interface ColdStart {
    //Removes from vectors and tags from test set
    ColdStartSolution process(float[][] vectors, int[][] tags);

}
