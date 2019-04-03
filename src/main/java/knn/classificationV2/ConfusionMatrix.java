package knn.classificationV2;

import java.util.Arrays;

public class ConfusionMatrix {
    //Columns are correct values
    //Rows are predicted values
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

    public double[] getPrecision(){
        double[] precision = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            double sum = 0;
            for (int j = 0; j < matrix.length; j++) {
                sum += matrix[j][i];
            }
            precision[i] = matrix[i][i] /sum;
        }

        return precision;
    }


    public double[] getRecall(){
        double[] precision = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            double sum = 0;
            for (int j = 0; j < matrix.length; j++) {
                sum += matrix[i][j];
            }
            precision[i] = matrix[i][i] / sum;
        }

        return precision;
    }

    public double[] getF1Score(){
        double[] precision = getPrecision();
        double[] recall = getRecall();

        double[] f1 = new double[recall.length];

        for (int i = 0; i < f1.length; i++) {
            f1[i] = (precision[i] * recall[i])/(precision[i] + recall[i]);
        }

        return f1;
    }
}
