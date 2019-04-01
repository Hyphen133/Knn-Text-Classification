package knn.feature_extractionV2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FeatureExtraction {
    private List<Preprocessing> preprocesssingFilters;
    private List<VocabularyReducing> vocabularyReducingFilters;
    private CoocurrenceMapCreating coocurrenceMapCreatingMethod;
    private List<CoocurrenceMapProcessing> coocurrenceMapProcessingFilters;
    private RawVectorCreating rawVectorCreating;
    private List<RawVectorProcessing> rawVectorProcessingMethods;

    public FeatureExtraction(List<Preprocessing> preprocesssingFilters, List<VocabularyReducing> vocabularyReducingFilters, CoocurrenceMapCreating coocurrenceMapCreatingMethod, List<CoocurrenceMapProcessing> coocurrenceMapProcessingFilters, RawVectorCreating rawVectorCreating, List<RawVectorProcessing> rawVectorProcessingMethods) {
        if(coocurrenceMapCreatingMethod == null){
            throw new IllegalArgumentException("Cocurrence Map creating method was not selected");
        }else if(rawVectorCreating == null){
            throw new IllegalArgumentException("Raw Vector creating method was not selected");
        }
        else if(preprocesssingFilters == null){
            preprocesssingFilters = new ArrayList<>();
        }
        else if(vocabularyReducingFilters == null){
            vocabularyReducingFilters = new ArrayList<>();
        }
        else if(coocurrenceMapProcessingFilters == null){
            coocurrenceMapProcessingFilters = new ArrayList<>();
        }
        else if(rawVectorProcessingMethods == null){
            rawVectorProcessingMethods = new ArrayList<>();
        }

        this.preprocesssingFilters = preprocesssingFilters;
        this.vocabularyReducingFilters = vocabularyReducingFilters;
        this.coocurrenceMapCreatingMethod = coocurrenceMapCreatingMethod;
        this.coocurrenceMapProcessingFilters = coocurrenceMapProcessingFilters;
        this.rawVectorCreating = rawVectorCreating;
        this.rawVectorProcessingMethods = rawVectorProcessingMethods;
    }

    public float[][] extractFeatures(ArrayList<String[]> texts){

        //(1) Remembering phase
        for (CoocurrenceMapProcessing coocurrenceMapProcessingFilter : coocurrenceMapProcessingFilters) {
            coocurrenceMapProcessingFilter.remember(texts);
        }

        //(2) Texts processing -> Preprocessing filters
        for (Preprocessing preprocesssingFilter : preprocesssingFilters) {
            preprocesssingFilter.apply(texts);
        }

        //(3) Texts processing -> VocabularyReducing filters
        for (VocabularyReducing vocabularyReducingFilter : vocabularyReducingFilters) {
            vocabularyReducingFilter.apply(texts);
        }

        //(4) Creating cooccurrence map
        Map<String, Integer>[] coocurrenceMap = coocurrenceMapCreatingMethod.create(texts);
        Map<String,Integer> volcabulary = coocurrenceMapCreatingMethod.getVocabulary(texts);

        //(5) Processing coocurrence map
        for (CoocurrenceMapProcessing coocurrenceMapProcessingFilter : coocurrenceMapProcessingFilters) {
            coocurrenceMapProcessingFilter.apply(coocurrenceMap, preprocesssingFilters, vocabularyReducingFilters);
        }

        //(6) Creating raw vectors
        float[][] rawVectors = rawVectorCreating.apply(coocurrenceMap, volcabulary);

        //(7) Processing raw vectors
        for (RawVectorProcessing rawVectorProcessingMethod : rawVectorProcessingMethods) {
            rawVectorProcessingMethod.apply(rawVectors);
        }

        return rawVectors;
    }
}
