package knn.preprocessing;

public class LowerCaseRule implements PreprocessingRule {
    @Override
    public String applyTo(String text) {
        return text.toLowerCase();
    }
}
