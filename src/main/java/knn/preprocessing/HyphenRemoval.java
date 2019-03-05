package knn.preprocessing;

public class HyphenRemoval implements PreprocessingRule {
    @Override
    public String applyTo(String text) {
        return text.replaceAll("\\-.","");
    }
}
