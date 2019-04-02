package knn.classificationV2;

public interface ColdStart {
    //Removes from vectors and tags
    ColdStartSolution process(float[][] vectors, int[][] tags);

}
