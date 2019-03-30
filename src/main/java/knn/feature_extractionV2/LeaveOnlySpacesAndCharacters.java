package knn.feature_extractionV2;

import java.util.ArrayList;

public class LeaveOnlySpacesAndCharacters implements Preprocessing {

    @Override
    public void apply(ArrayList<String>[] texts) {
        for (int i = 0; i < texts.length; i++) {
            for (int j = 0; j < texts[i].size(); j++) {
                texts[i].set(j,texts[i].get(j).replaceAll("[^A-Za-z\\s]", "").toLowerCase());
            }
        }
    }
}
