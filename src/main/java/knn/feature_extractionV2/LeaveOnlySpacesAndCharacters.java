package knn.feature_extractionV2;

import java.util.ArrayList;

public class LeaveOnlySpacesAndCharacters implements Preprocessing {

    @Override
    public void apply(ArrayList<String[]> texts) {
        for (int i = 0; i < texts.size(); i++) {
            ArrayList<String> words = new ArrayList<>();
            for (int j = 0; j < texts.get(i).length; j++) {
                String editedWord = texts.get(i)[j].replaceAll("[^A-Za-z\\s]", "").toLowerCase();

                if(editedWord.trim().length() != 0){
                    words.add(editedWord);
                }
            }

            texts.set(i,words.toArray(new String[0]));
        }
    }

}
