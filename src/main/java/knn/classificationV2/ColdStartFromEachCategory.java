package knn.classificationV2;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class ColdStartFromEachCategory implements ColdStart{
    int count;

    public ColdStartFromEachCategory(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    @Override
    public ColdStartSolution process(float[][] vectors, int[][] tags) {

        ColdStartSolution solution = new ColdStartSolution();

        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int randIndex = random.nextInt(vectors.length);

            solution.getVectors().add(vectors[randIndex]);
            solution.getTags().add(tags[randIndex]);

            //Remove elements from testSet
            ArrayUtils.removeElement(vectors, vectors[randIndex]);
            ArrayUtils.removeElement(tags, tags[randIndex]);
        }



        return null;
    }
}
