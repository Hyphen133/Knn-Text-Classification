package knn.test_train_sets;

public class TrainTestSets {
    short[][] trainX;
    int[][] trainY;
    short[][] testX;
    int[][] testY;

    public short[][] getTrainX() {
        return trainX;
    }

    public void setTrainX(short[][] trainX) {
        this.trainX = trainX;
    }

    public int[][] getTrainY() {
        return trainY;
    }

    public void setTrainY(int[][] trainY) {
        this.trainY = trainY;
    }

    public short[][] getTestX() {
        return testX;
    }

    public void setTestX(short[][] testX) {
        this.testX = testX;
    }

    public int[][] getTestY() {
        return testY;
    }

    public void setTestY(int[][] testY) {
        this.testY = testY;
    }
}
