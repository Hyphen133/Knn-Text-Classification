package knn.preprocessing;

public class DotRemoval implements PreprocessingRule {
    @Override
    public String applyTo(String text) {
        return text.replaceAll("\\.","");
    }
}
