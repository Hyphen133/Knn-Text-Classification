package knn.preprocessing;

public class BracketsRemoval implements PreprocessingRule {
    @Override
    public String applyTo(String text) {
        return text.replaceAll("[\\[\\](){}]","");
    }
}
