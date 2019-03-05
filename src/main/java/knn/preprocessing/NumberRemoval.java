package knn.preprocessing;

public class NumberRemoval implements PreprocessingRule {
    @Override
    public String applyTo(String text) {
        return text.replaceAll("\\d","");
    }
}
