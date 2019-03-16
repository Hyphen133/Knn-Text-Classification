package knn.test_train_sets;

public class TrainTestSets {
    short[][] trainX;
    int[][] trainY;
    short[][] testX;
    int[][] testY;

    public TrainTestSets(short[][] trainX, int[][] trainY, short[][] testX, int[][] testY) {
        this.trainX = trainX;
        this.trainY = trainY;
        this.testX = testX;
        this.testY = testY;
    }

    public short[][] getTrainX() {
        return trainX;
    }

    public int[][] getTrainY() {
        return trainY;
    }

    public short[][] getTestX() {
        return testX;
    }

    public int[][] getTestY() {
        return testY;
    }
}
