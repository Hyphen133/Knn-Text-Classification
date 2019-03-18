package knn.preprocessing;

public class LeaveOnlyCharactersAndSpacesRule implements PreprocessingRule {
    @Override
    public String applyTo(String text) {
        return text.replaceAll("[^A-Za-z\\s]", "").toLowerCase();
    }
}
