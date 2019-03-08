package knn.classification;

public class ClassificationResult{
    int index;
    int[] classVector;

    public ClassificationResult(int index, int[] classVector) {
        this.index = index;
        this.classVector = classVector;
    }

    public int getIndex() {
        return index;
    }

    public int[] getClassVector() {
        return classVector;
    }
}

