package knn;

import knn.classification.ClassProcessing;
import knn.feature_extraction.BagOfWords;
import knn.feature_extraction.FeatureExtraction;
import knn.loading.PlacesTagsLoader;
import knn.loading.ReutersLoader;
import knn.preprocessing.LeaveOnlyCharactersAndSpacesRule;
import knn.preprocessing.PreprocessingRule;
import knn.preprocessing.WordSplitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args){

        long startTime = System.currentTimeMillis();



        ArrayList<String>[] tags = PlacesTagsLoader.loadPlacesTagsFromReutersDirectory();

//        for (ArrayList<String> tag : tags) {
//            System.out.println(tag.size());
//        }

        Map<String,Integer> placesMap = PlacesTagsLoader.getAllPlacesMap();

//        for (String s : placesMap.keySet()) {
//            System.out.println(s);
//        }

        System.out.println(placesMap.keySet().size());

        ArrayList<String>[] texts = ReutersLoader.load();

        ArrayList<String>[] processedTexts = new ArrayList[texts.length];
        PreprocessingRule rule = new LeaveOnlyCharactersAndSpacesRule();
        for (int i = 0; i < texts.length; i++) {
            processedTexts[i] = new ArrayList<>();
            for (String text : texts[i]) {
                processedTexts[i].add(rule.applyTo(text));
            }
        }

//        for (ArrayList<String> text : processedTexts) {
//            System.out.println(text.size());
//            for (String s : text) {
//                System.out.println(s);
//            }
//        }

        String[] flattenedTexts = Utils.flatten(processedTexts, String.class);
        String[] flattenedTags = Utils.flatten(tags, String.class);

        System.out.println(flattenedTexts.length);
        System.out.println(flattenedTags.length);

        System.out.println(flattenedTexts[0]);
        System.out.println(flattenedTags[0]);
        System.out.println(flattenedTexts[1]);
        System.out.println(flattenedTags[1]);

        String[][] splittedTexts = WordSplitter.splitAllTextsBySpaces(flattenedTexts);
        System.out.println(Arrays.toString(splittedTexts[0]));
        System.out.println(Arrays.toString(splittedTexts[1]));


        int[][] tagVectors = ClassProcessing.convertTagsToVectorsWithSingleOne(flattenedTags,placesMap);
//        for (int[] tagVector : tagVectors) {
//            System.out.println(tagVector.length);
//            System.out.println(Arrays.toString(tagVector));
//        }


        FeatureExtraction featureExtraction = new BagOfWords();

        Map<String, Integer>[] wordVectors = featureExtraction.extractFeatures(splittedTexts);
        List<String> volcabulary = featureExtraction.getOrderedVolcabulary();

//        for (Map<String, Integer> wordVector : wordVectors) {
//            System.out.println(wordVector.keySet().size());
//        }


        //Slice first 1000 elements (for 21000 out of bounds)
        wordVectors = Arrays.copyOfRange(wordVectors, 0, 1000);
        tagVectors = Arrays.copyOfRange(tagVectors, 0, 1000);




        double[][] featureVectors = ClassProcessing.convertFeaturesToVectors(wordVectors, volcabulary);
        for (double[] featureVector : featureVectors) {
            System.out.println(Arrays.toString(featureVector));
        }


        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println(estimatedTime/1000 + "s ");
    }

}

