package knn.classificationV2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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



        return null;
    }
}
