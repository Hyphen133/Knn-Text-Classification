package knn.classificationV2;

import java.util.ArrayList;

public class ClassificationResults {
    ArrayList<float[]> vectors;
    ArrayList<int[]> tags;
    ArrayList<int[]> classifiedTags;

    public ClassificationResults(ArrayList<float[]> vectors, ArrayList<int[]> tags, ArrayList<int[]> classifiedTags) {
        this.vectors = vectors;
        this.tags = tags;
        this.classifiedTags = classifiedTags;
    }

    public ArrayList<float[]> getVectors() {
        return vectors;
    }

    public ArrayList<int[]> getTags() {
        return tags;
    }

    public ArrayList<int[]> getClassifiedTags() {
        return classifiedTags;
    }
}
