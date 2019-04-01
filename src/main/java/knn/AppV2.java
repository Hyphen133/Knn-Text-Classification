package knn;

import knn.feature_extractionV2.*;
import knn.loading.MostFrequentWordsLoader;

import java.util.ArrayList;
import java.util.Arrays;
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
        textTabs.add(new String[]{"Hello", "I", "really", "like", "fishing", "colourful", "and", "enormous", "fishes" });
        textTabs.add(new String[]{"the", "biggest" , "is", "really", "biggest", "out", "of", "all", "big", "fishes" });

        Preprocessing preprocessing = new LeaveOnlySpacesAndCharacters();
        preprocessing.apply(textTabs);

        for (String[] textTab : textTabs) {
            System.out.println(Arrays.toString(textTab));
        }

        VolcabularyReducing volcabularyReducing1 = new PorterStemming();
        volcabularyReducing1.apply(textTabs);

        for (String[] textTab : textTabs) {
            System.out.println(Arrays.toString(textTab));
        }

        VolcabularyReducing volcabularyReducing2 = new StoplistRemoving(MostFrequentWordsLoader.load());
        volcabularyReducing2.apply(textTabs);

        for (String[] textTab : textTabs) {
            System.out.println(Arrays.toString(textTab));
        }

        CoocurrenceMapCreating coocurrenceMapCreating = new Unigram();

        Map<String, Integer>[] coocurrenceMap = coocurrenceMapCreating.create(textTabs);
        Map<String, Integer> dictonary = coocurrenceMapCreating.getDictonary(textTabs);

        for (Map<String, Integer> stringIntegerMap : coocurrenceMap) {
            for (Map.Entry<String, Integer> entry : stringIntegerMap.entrySet()) {
                System.out.println("Key="+entry.getKey()+", Value="+entry.getValue());
            }
            System.out.println();
        }

        RawVectorCreating rawVectorCreating = new RawVectorCreatingImpl();

        float[][] vectors = rawVectorCreating.apply(coocurrenceMap, dictonary);

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
