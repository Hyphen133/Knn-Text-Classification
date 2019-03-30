package knn.feature_extractionV2;

import opennlp.tools.stemmer.PorterStemmer;

import java.util.ArrayList;

public class PorterStemming implements VolcabularyReducing {
    @Override
    public void apply(ArrayList<String[]> texts) {
        PorterStemmer stemmer = new PorterStemmer();

        for (int i = 0; i < texts.size(); i++) {
            for (int j = 0; j < texts.get(i).length; j++) {
                texts.get(i)[j] = stemmer.stem(texts.get(i)[j]);
            }
        }
    }
}
