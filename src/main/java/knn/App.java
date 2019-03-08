package knn;

import knn.classification.*;
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
import knn.similarity.CosineSimilarity;
import knn.similarity.EuclideanDistance;

import java.util.*;
import java.util.concurrent.*;

public class App {
    public static void main(String[] args){

        long startTime = System.currentTimeMillis();


        //-------------------- Loading -------------------
        ArrayList<String>[] tags = PlacesTagsLoader.loadPlacesTagsFromReutersDirectory();
        ArrayList<String>[] texts = ReutersLoader.load(false);
        String[] chosenTags = { "west-germany", "usa", "france", "uk", "canada", "japan"};
        Map<String,Integer> placesMap = PlacesTagsLoader.getPlacesMapFromChosenTags(chosenTags);

        //------------------- Filtering ---------------------

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


        //------------------- Preprocessing ------------------------
        ArrayList<String>[] processedTexts = new ArrayList[texts.length];
        PreprocessingRule rule = new LeaveOnlyCharactersAndSpacesRule();
        for (int i = 0; i < texts.length; i++) {
            processedTexts[i] = new ArrayList<>();
            for (String text : texts[i]) {
                processedTexts[i].add(rule.applyTo(text));
            }
        }


        String[] flattenedTexts = Utils.flatten(processedTexts, String.class);
        String[] flattenedTags = Utils.flatten(tags, String.class);

        String[][] splittedTexts = WordSplitter.splitAllTextsBySpaces(flattenedTexts);
        int[][] tagVectors = ClassProcessing.convertTagsToVectorsWithSingleOne(flattenedTags,placesMap);


        //----------------------- Feature extraction --------------------------
        FeatureExtraction featureExtraction = new NonFrequentBagOfWords(MostFrequentWordsLoader.load());
//        FeatureExtraction featureExtraction = new CapitalBagOfWords();

        Map<String, Integer>[] wordVectors = featureExtraction.extractFeatures(splittedTexts);
        List<String> volcabulary = featureExtraction.getOrderedVocabulary();


        //------------------------- Train-Test Sets ----------------------------
        int trainSize = 1200;
        int testSize = 800;

        Map<String, Integer>[] testWordVectors = Arrays.copyOfRange(wordVectors, trainSize, trainSize+testSize);
        int[][] testTagVectors = Arrays.copyOfRange(tagVectors, trainSize, trainSize + testSize);
        wordVectors = Arrays.copyOfRange(wordVectors, 0, trainSize);
        tagVectors = Arrays.copyOfRange(tagVectors, 0, trainSize);

        short[][] testFeatureVectors = ClassProcessing.convertFeaturesToVectors(testWordVectors, volcabulary);
        short[][] featureVectors = ClassProcessing.convertFeaturesToVectors(wordVectors, volcabulary);

        ClassificationAlgorithm classificationAlgorithm = new KNN(featureVectors,tagVectors, new EuclideanDistance(), 5);


        //----------------------- Testing -----------------------------
        System.out.println("Testing");
        List<ClassificationResult> testResults =  classificationAlgorithm.classify(testFeatureVectors);
        System.out.println("Accuracy: " + EvaluationMetrics.calculateAccuracy(testResults,testTagVectors)*100 + "%");


        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println(estimatedTime/1000 + "s ");
    }

}