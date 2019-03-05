package knn.preprocessing;

public class ComaRemoval implements PreprocessingRule {
    @Override
    public String applyTo(String text) {
        return text.replaceAll("\\,", "");
    }
}
