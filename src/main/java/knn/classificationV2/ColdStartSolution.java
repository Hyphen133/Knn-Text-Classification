package knn.classificationV2;

import java.util.ArrayList;

public class ColdStartSolution {
    ArrayList<float[]> vectors;
    ArrayList<int[]> tags;

    public ColdStartSolution() {
        vectors = new ArrayList<>();
        tags = new ArrayList<>();
    }

    public ColdStartSolution(ArrayList<float[]> vectors, ArrayList<int[]> tags) {
        this.vectors = vectors;
        this.tags = tags;
    }

    public ArrayList<float[]> getVectors() {
        return vectors;
    }

    public ArrayList<int[]> getTags() {
        return tags;
    }
}
