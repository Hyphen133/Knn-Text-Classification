package knn.feature_extractionV2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CapitalWordInMiddleOfSentence implements CoocurrenceMapProcessing {
    ArrayList<String>[] capitalWords;
    float points;

    public CapitalWordInMiddleOfSentence() {
        points = 1;
    }

    public CapitalWordInMiddleOfSentence(float points) {
        this.points = points;
    }

    @Override
    public void remember(ArrayList<String[]> texts) {
        capitalWords = new ArrayList[texts.size()];

        for (int i = 0; i < texts.size(); i++) {
            String[] currentText = texts.get(i);
            capitalWords[i] = new ArrayList<>();

            if(currentText[0].length() != 0){
                //Can start at 1
                for (int j = 1; j < currentText[i].length(); j++) {
                    if(Character.isUpperCase(currentText[j].charAt(0)) && !(currentText[j-1].charAt(currentText[j-1].length()-1) == '.')){
                        capitalWords[i].add(currentText[j]);
                    }
                }
            }

        }
    }

    @Override
    public void apply(Map<String, Integer>[] coocurrenceMap, List<Preprocessing> preprocessingFilters, List<VolcabularyReducing> volcabularyReducingFilters) {
//        for (int i = 0; i < capitalWords.length; i++) {
//            for (int j = 0; j < capitalWords[i].size(); j++) {
//                //Apply all filters
//                for (Preprocessing preprocessingFilter : preprocessingFilters) {
//                    preprocessingFilter.apply(capitalWords);
//                }
//            }
//        }

    }
}
