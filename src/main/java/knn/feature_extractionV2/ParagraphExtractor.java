package knn.feature_extractionV2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParagraphExtractor implements CoocurrenceMapProcessing {
    ArrayList<String[]> paragraphs;
    int points;

    public ParagraphExtractor() {
        points = 1;
    }

    public ParagraphExtractor(int points) {
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
    public void apply(Map<String, Integer>[] coocurrenceMap, List<Preprocessing> preprocessingFilters, List<VolcabularyReducing> volcabularyReducingFilters) {
        //Apply all filters
        for (Preprocessing preprocessingFilter : preprocessingFilters) {
            preprocessingFilter.apply(paragraphs);
        }
        for (VolcabularyReducing volcabularyReducingFilter : volcabularyReducingFilters) {
            volcabularyReducingFilter.apply(paragraphs);
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
