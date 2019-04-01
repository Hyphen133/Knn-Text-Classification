package knn.feature_extractionV2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirstParagraphExtractor implements CoocurrenceMapProcessing {
    ArrayList<String[]> paragraphs;
    int points;

    public FirstParagraphExtractor() {
        points = 1;
    }

    public FirstParagraphExtractor(int points) {
        this.points = points;
    }

    @Override
    public void remember(ArrayList<String[]> texts) {
        paragraphs = new ArrayList<>();
        for (int i = 0; i < texts.size(); i++) {
            ArrayList<String> paragraphWords = new ArrayList<>();
            for (int j = 0; j < texts.get(i).length; j++) {
                String currentWord = texts.get(i)[j];

                if(currentWord.contains(" ")){
                    break;
                }

                paragraphWords.add(currentWord);
            }

            paragraphs.add(paragraphWords.toArray(new String[0]));

        }
    }

    @Override
    public void apply(Map<String, Integer>[] coocurrenceMap, List<Preprocessing> preprocessingFilters, List<VocabularyReducing> vocabularyReducingFilters) {
        //Apply all filters
        for (Preprocessing preprocessingFilter : preprocessingFilters) {
            preprocessingFilter.apply(paragraphs);
        }
        for (VocabularyReducing vocabularyReducingFilter : vocabularyReducingFilters) {
            vocabularyReducingFilter.apply(paragraphs);
        }

        //Add to map
        for (int i = 0; i < paragraphs.size(); i++) {
            for (int j = 0; j < paragraphs.get(i).length; j++) {
                String currentWord = paragraphs.get(i)[j];

                if(coocurrenceMap[i].containsKey(currentWord))   {
                    coocurrenceMap[i].put(currentWord, coocurrenceMap[i].get(currentWord)+points);
                }else{
                    coocurrenceMap[i].put(currentWord, points);
                }
            }
        }

    }
}
