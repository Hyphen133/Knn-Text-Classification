package knn.feature_extractionV2;

import java.util.ArrayList;

public class LeaveOnlySpacesAndCharacters implements Preprocessing {

    @Override
    public void apply(ArrayList<String[]> texts) {
        for (int i = 0; i < texts.size(); i++) {
            for (int j = 0; j < texts.get(i).length; j++) {
                texts.get(i)[j] = texts.get(i)[j].replaceAll("[^A-Za-z\\s]", "").toLowerCase();
            }
        }
    }

}
