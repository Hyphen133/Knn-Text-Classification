package knn;

import knn.classification.ClassProcessing;
import knn.classification.ClassificationAlgorithm;
import knn.classification.KNN;
import knn.feature_extraction.BagOfWords;
import knn.feature_extraction.CapitalBagOfWords;
import knn.feature_extraction.FeatureExtraction;
import knn.feature_extraction.NonFrequentBagOfWords;
import knn.loading.MostFrequentWordsLoader;
import knn.loading.PlacesTagsLoader;
import knn.loading.ReutersLoader;
import knn.preprocessing.LeaveOnlyCharactersAndSpacesRule;
import knn.preprocessing.PreprocessingRule;
import knn.preprocessing.WordSplitter;
import knn.similarity.EuclideanDistance;

import java.util.*;

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

//        System.out.println(placesMap.keySet().size());

        ArrayList<String>[] texts = ReutersLoader.load(true);


        //Filtering
        String[] chosenTags = { "west-germany", "usa", "france", "uk", "canada", "japan"};

        placesMap = new HashMap<>();
        for (int i = 0; i < chosenTags.length; i++) {
            placesMap.put(chosenTags[i], i);
        }

        ArrayList<String>[] filteredTags = new ArrayList[tags.length];
        ArrayList<String>[] filteredTexts = new ArrayList[texts.length];


        for (int i = 0; i < texts.length; i++) {
            filteredTags[i] = new ArrayList<>();
            filteredTexts[i] = new ArrayList<>();

            for (int j = 0; j < texts[i].size(); j++) {
                String currentTag = tags[i].get(j);
                String currentText = texts[i].get(j);
                if(placesMap.containsKey(currentTag)){
                    filteredTags[i].add(currentTag);
                    filteredTexts[i].add(currentText);
                }
            }
        }

        tags = filteredTags;
        texts = filteredTexts;




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

//        System.out.println(flattenedTexts.length);
//        System.out.println(flattenedTags.length);
//
//        System.out.println(flattenedTexts[0]);
//        System.out.println(flattenedTags[0]);
//        System.out.println(flattenedTexts[1]);
//        System.out.println(flattenedTags[1]);
//
        String[][] splittedTexts = WordSplitter.splitAllTextsBySpaces(flattenedTexts);
//        System.out.println(Arrays.toString(splittedTexts[0]));
//        System.out.println(Arrays.toString(splittedTexts[1]));


        int[][] tagVectors = ClassProcessing.convertTagsToVectorsWithSingleOne(flattenedTags,placesMap);
//        for (int[] tagVector : tagVectors) {
//            System.out.println(tagVector.length);
//            System.out.println(Arrays.toString(tagVector));
//        }


//        FeatureExtraction featureExtraction = new NonFrequentBagOfWords(MostFrequentWordsLoader.load());
        FeatureExtraction featureExtraction = new BagOfWords();

        Map<String, Integer>[] wordVectors = featureExtraction.extractFeatures(splittedTexts);


        List<String> volcabulary = featureExtraction.getOrderedVocabulary();

//        for (Map<String, Integer> wordVector : wordVectors) {
//            System.out.println(wordVector.keySet().size());
//        }


        //Slice 20 words for test and first 1000 elements (for 21000 out of bounds)
        int trainSize = 6000;
        int testSize = 4000;

        Map<String, Integer>[] testWordVectors = Arrays.copyOfRange(wordVectors, trainSize, trainSize+testSize);
        int[][] testTagVectors = Arrays.copyOfRange(tagVectors, trainSize, trainSize + testSize);
        wordVectors = Arrays.copyOfRange(wordVectors, 0, trainSize);
        tagVectors = Arrays.copyOfRange(tagVectors, 0, trainSize);

        short[][] testFeatureVectors = ClassProcessing.convertFeaturesToVectors(testWordVectors, volcabulary);




        short[][] featureVectors = ClassProcessing.convertFeaturesToVectors(wordVectors, volcabulary);
//        for (double[] featureVector : featureVectors) {
//            System.out.println(Arrays.toString(featureVector));
//        }


        ClassificationAlgorithm classificationAlgorithm = new KNN(featureVectors,tagVectors, new EuclideanDistance(), 12);


        System.out.println("Testing");

        //Testing
        int correctTests = 0;
        for (int i = 0; i < testSize; i++) {
            System.out.println(i + "/" + testSize);
            if(ClassProcessing.compareTags(testTagVectors[i], classificationAlgorithm.classify(testFeatureVectors[i]))){
                correctTests++;
            }
        }

        System.out.println("Accuracy: " + ((((double)correctTests)/testSize)*100) + "%");


        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println(estimatedTime/1000 + "s ");
    }

}

