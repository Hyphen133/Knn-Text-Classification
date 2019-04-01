package knn.feature_extractionV2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CapitalWordInMiddleOfSentence implements CoocurrenceMapProcessing {
    ArrayList<String[]> capitalWords;
    int points;

    public CapitalWordInMiddleOfSentence() {
        points = 1;
    }

    public CapitalWordInMiddleOfSentence(int points) {
        this.points = points;
    }

    @Override
    public void remember(ArrayList<String[]> texts) {
        capitalWords = new ArrayList<>();

        for (int i = 0; i < texts.size(); i++) {
            String[] currentText = texts.get(i);
            ArrayList<String> textCapitalWords = new ArrayList<>();

            if(currentText[0].length() != 0){
                //Can start at 1
                for (int j = 1; j < currentText.length; j++) {
                    if((currentText[j].length()>0) && (currentText[j-1].length()>0)  && Character.isUpperCase(currentText[j].charAt(0)) && !(currentText[j-1].charAt(currentText[j-1].length()-1) == '.')){
                        textCapitalWords.add(currentText[j]);
                    }
                }
            }

            capitalWords.add(textCapitalWords.toArray(new String[0]));
        }

    }

    @Override
    public void apply(Map<String, Integer>[] coocurrenceMap, List<Preprocessing> preprocessingFilters, List<VocabularyReducing> vocabularyReducingFilters) {
        //Apply all filters
        for (Preprocessing preprocessingFilter : preprocessingFilters) {
            preprocessingFilter.apply(capitalWords);
        }
        for (VocabularyReducing vocabularyReducingFilter : vocabularyReducingFilters) {
            vocabularyReducingFilter.apply(capitalWords);
        }

        //Add to map
        for (int i = 0; i < capitalWords.size(); i++) {
            for (int j = 0; j < capitalWords.get(i).length; j++) {
                String currentWord = capitalWords.get(i)[j];

                if(coocurrenceMap[i].containsKey(currentWord))   {
                    coocurrenceMap[i].put(currentWord, coocurrenceMap[i].get(currentWord)+points);
                }else{
                    coocurrenceMap[i].put(currentWord, points);
                }
            }
        }

    }
}
