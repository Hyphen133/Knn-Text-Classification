package knn.test_train_sets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrainTestSetsSplitter {
    public static TrainTestSets split(short[][] featureVectors, int[][] tags, int trainSize, int testSize){
        List<Integer> indecies = getRandomValuesFromZeroToN(trainSize + testSize);

        short[][] trainX = new short[trainSize][featureVectors[0].length];
        int[][] trainY = new int[trainSize][tags[0].length];
        short[][] testX = new short[testSize][featureVectors[0].length];
        int[][] testY = new int[testSize][tags[0].length];

        for (int i = 0; i < trainSize; i++) {
            trainX[i] = featureVectors[indecies.get(i)];
            trainY[i] = tags[indecies.get(i)];
        }

        for (int i = 0; i < testSize; i++) {
            testX[i] = featureVectors[indecies.get(trainSize-1 + i)];
            testY[i] = tags[indecies.get(trainSize-1 + i)];
        }

        return new TrainTestSets(trainX, trainY, testX, testY);
    }

    private static List<Integer> getRandomValuesFromZeroToN(int n){
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            list.add(i);
        }

        Collections.shuffle(list);
        return list;
    }

}
