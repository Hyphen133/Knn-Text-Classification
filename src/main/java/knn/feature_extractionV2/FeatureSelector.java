package knn.feature_extractionV2;

import java.util.List;

public interface FeatureSelector {
    float[][] selectForEachCategory(float[][] vectors, List<String> tags, List<String> choosenTags, int featuresCountForEachCategory);
}
