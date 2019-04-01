package knn;

import knn.feature_extractionV2.*;
import knn.loading.MostFrequentWordsLoader;
import knn.loadingV2.PlacesTagsLoaderV2;
import knn.loadingV2.ReutersLoaderV2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AppV2 {
    public static void main(String[] args) {



//        ArrayList<String> tags = PlacesTagsLoaderV2.loadPlacesTagsFromReutersDirectory();
//        ArrayList<String> texts = ReutersLoaderV2.load(false, true);
//
//        Utils.measureFunctionTime("Loading", () -> {
//            ArrayList<String> tags1 = PlacesTagsLoaderV2.loadPlacesTagsFromReutersDirectory();
//            ArrayList<String> texts1 = ReutersLoaderV2.load(false, true);
//            System.out.println(texts1.size());
//            System.out.println(tags1.size());
////            System.out.println(tags1);
//        });
//
//
//        Utils.measureFunctionTime("Converting to class ", () -> {
//            ArrayList<ReuterWithTag> reutersWithTags = new ArrayList<>();
//            for (int i = 0; i < texts.size(); i++) {
//                reutersWithTags.add(new ReuterWithTag(texts.get(i), tags.get(i)));
//            }
//        });
//
//
//        ArrayList<ReuterWithTag> reutersWithTags = new ArrayList<>();
//        for (int i = 0; i < texts.size(); i++) {
//            reutersWithTags.add(new ReuterWithTag(texts.get(i), tags.get(i)));
//        }
//
//
//        Utils.measureFunctionTime("Filtering ", () -> {
//            ArrayList<String> chosenTags = new ArrayList<>();
//            chosenTags.addAll(Arrays.asList("west-germany", "usa", "france", "uk", "canada", "japan"));
//
//
//            reutersWithTags.removeIf(x -> !chosenTags.contains(x.getTag()));
//            System.out.println(reutersWithTags.size());
//        });
//
//
//
//        Utils.measureFunctionTime("Splitting ", () -> {
//            ArrayList<SplittedReuterWithTag> splittedReutersWithTags = new ArrayList<>();
//            for (ReuterWithTag reutersWithTag : reutersWithTags) {
//                    splittedReutersWithTags.add(new SplittedReuterWithTag(reutersWithTag.getText().replace("     ", PARAGRAPH_SYMBOL).split("\\s"), reutersWithTag.getTag()));
//            }
////            for (int i = 0; i < 10; i++) {
////                System.out.println(reutersWithTags.get(i).getText());
////                System.out.println(reutersWithTags.get(i).getText().replace("     ", " <<>> "));
////                System.out.println(Arrays.toString(reutersWithTags.get(i).getText().replace("     ", " <<>> ").split("\\s")));
////            }
//        });


        ArrayList<String[]> textTabs = new ArrayList<>();
        textTabs.add(new String[]{"Hello", "I", "really", "like", " ", "fishing", "colourful", "and", "enormous", "fishes" });
        textTabs.add(new String[]{"Big", "of", "the", "biggest" , "is", "really", "Biggest", "out", "of", "all", "big", "fishes" });

        List<Preprocessing> preprocessingList = new ArrayList<>();
        preprocessingList.add(new LeaveOnlySpacesAndCharacters());


        List<VocabularyReducing> vocabularyReducingList = new ArrayList<>();
        vocabularyReducingList.add(new StoplistRemoving(MostFrequentWordsLoader.load()));
        vocabularyReducingList.add(new PorterStemming());

        CoocurrenceMapCreating coocurrenceMapCreating = new Unigram();

        List<CoocurrenceMapProcessing> coocurrenceMapProcessingList = new ArrayList<>();
        coocurrenceMapProcessingList.add(new CapitalWordInMiddleOfSentence());
        coocurrenceMapProcessingList.add(new FirstParagraphExtractor());

        RawVectorCreating rawVectorCreating = new RawVectorCreatingImpl();

        List<RawVectorProcessing> rawVectorProcessingList = new ArrayList<>();
//        rawVectorProcessingList.add(new TdIdf());

        FeatureExtraction featureExtraction = new FeatureExtraction(preprocessingList,vocabularyReducingList, coocurrenceMapCreating, coocurrenceMapProcessingList, rawVectorCreating, rawVectorProcessingList);

        float[][] featureVectors = featureExtraction.extractFeatures(textTabs);

        System.out.println(Arrays.deepToString(featureVectors));

    }

    static void featureExtraction1(){
        ArrayList<String[]> textTabs = new ArrayList<>();
        textTabs.add(new String[]{"Hello", "I", "really", "like", " ", "fishing", "colourful", "and", "enormous", "fishes" });
        textTabs.add(new String[]{"Big", "of", "the", "biggest" , "is", "really", "Biggest", "out", "of", "all", "big", "fishes" });


        CoocurrenceMapProcessing coocurrenceMapProcessing1 = new CapitalWordInMiddleOfSentence();
        CoocurrenceMapProcessing coocurrenceMapProcessing2 = new FirstParagraphExtractor();

        coocurrenceMapProcessing1.remember(textTabs);
        coocurrenceMapProcessing2.remember(textTabs);


        List<Preprocessing> preprocessingList = new ArrayList<>();
        Preprocessing preprocessing = new LeaveOnlySpacesAndCharacters();
        preprocessingList.add(preprocessing);
        preprocessing.apply(textTabs);

        for (String[] textTab : textTabs) {
            System.out.println(Arrays.toString(textTab));
        }

        List<VocabularyReducing> vocabularyReducingList = new ArrayList<>();
        VocabularyReducing vocabularyReducing1 = new PorterStemming();
        vocabularyReducingList.add(vocabularyReducing1);
        vocabularyReducing1.apply(textTabs);

        for (String[] textTab : textTabs) {
            System.out.println(Arrays.toString(textTab));
        }

        VocabularyReducing vocabularyReducing2 = new StoplistRemoving(MostFrequentWordsLoader.load());
        vocabularyReducingList.add(vocabularyReducing2);
        vocabularyReducing2.apply(textTabs);

        for (String[] textTab : textTabs) {
            System.out.println(Arrays.toString(textTab));
        }

        CoocurrenceMapCreating coocurrenceMapCreating = new Unigram();

        Map<String, Integer>[] coocurrenceMap = coocurrenceMapCreating.create(textTabs);
        Map<String, Integer> vocabulary = coocurrenceMapCreating.getVocabulary(textTabs);

        for (Map<String, Integer> stringIntegerMap : coocurrenceMap) {
            for (Map.Entry<String, Integer> entry : stringIntegerMap.entrySet()) {
                System.out.println("Key="+entry.getKey()+", Value="+entry.getValue());
            }
            System.out.println();
        }


        coocurrenceMapProcessing1.apply(coocurrenceMap, preprocessingList, vocabularyReducingList);
        coocurrenceMapProcessing2.apply(coocurrenceMap, preprocessingList, vocabularyReducingList);


        RawVectorCreating rawVectorCreating = new RawVectorCreatingImpl();

        float[][] vectors = rawVectorCreating.apply(coocurrenceMap, vocabulary);

        System.out.println(Arrays.deepToString(vectors));

//        RawVectorProcessing rawVectorProcessing1 = new RemoveMoreThanOccurrencesInDocuments(1);
//        rawVectorProcessing1.apply(vectors);
//        System.out.println(Arrays.deepToString(vectors));

        RawVectorProcessing rawVectorProcessing2 = new RemoveLessThanOccurrencesInDocuments(2);
        rawVectorProcessing2.apply(vectors);
        System.out.println(Arrays.deepToString(vectors));



        RawVectorProcessing rawVectorProcessing3 = new TdIdf();
        rawVectorProcessing3.apply(vectors);
        System.out.println(Arrays.deepToString(vectors));
    }
}
