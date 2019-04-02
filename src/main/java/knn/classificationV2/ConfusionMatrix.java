package knn.classificationV2;

import java.util.Arrays;

public class ConfusionMatrix {
    int[][] matrix;

    public ConfusionMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(matrix).replace("], ", "]\n").replace("[[", "[").replace("]]", "]");
    }

    public double getAccurracy(){
        int diagonalSum = 0;
        int sum = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                sum += matrix[i][j];

                if(i==j){
                    diagonalSum += matrix[i][j];
                }
            }
        }

        return ((double)diagonalSum) / sum;
    }
}
