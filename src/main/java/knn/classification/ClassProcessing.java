package knn.classification;

public class ClassProcessing {

    public static int[] convertClassNumberToVectorWithOne(int integer, int length){
        int[] vec = new int[length];
        vec[integer] = 1;

        return vec;
    }
}
