package knn.classificationV2;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;

public class RandomColdStart implements ColdStart {
    int count;

    public RandomColdStart(int count) {
        this.count = count;
    }

    @Override
    public ColdStartSolution process(float[][] vectors, int[][] tags) {

        ColdStartSolution solution = new ColdStartSolution();

        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int randIndex = random.nextInt(vectors.length);

            solution.getVectors().add(vectors[randIndex]);
            solution.getTags().add(tags[randIndex]);

            ArrayUtils.removeElement(vectors, vectors[randIndex]);
            ArrayUtils.removeElement(tags, tags[randIndex]);
        }

        return solution;
    }
}
