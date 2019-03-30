package knn;

import knn.classification.*;
import knn.feature_extraction.*;
import knn.loading.MostFrequentWordsLoader;
import knn.loading.PlacesTagsLoader;
import knn.loading.ReutersLoader;
import knn.preprocessing.LeaveOnlyCharactersAndSpacesRule;
import knn.preprocessing.PreprocessingRule;
import knn.preprocessing.WordSplitter;
import knn.similarity.*;
import knn.test_train_sets.TrainTestSets;
import knn.test_train_sets.TrainTestSetsSplitter;

import java.util.*;

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



        String[] flattenedTexts = Utils.flatten(texts, String.class);
        String[] flattenedTags = Utils.flatten(tags, String.class);



        //------------------- Preprocessing ------------------------
        PreprocessingRule rule = new LeaveOnlyCharactersAndSpacesRule();
        String[] processedTexts = new String[flattenedTags.length];
        for (int i = 0; i < flattenedTags.length; i++) {
            processedTexts[i] = rule.applyTo(flattenedTexts[i]);
        }

        //Each text is splitted in words and we get one-hot vector for each tag
        String[][] splittedTexts = WordSplitter.splitAllTextsBySpaces(processedTexts);
        int[][] tagVectors = ClassProcessing.convertTagsToVectorsWithSingleOne(flattenedTags,placesMap);


        //----------------------- Feature extraction --------------------------

        List<String> vocabulary = VocabularyCreator.getVolcabulary(splittedTexts);

        FeatureExtraction featureExtraction = new InversedBagOfWords();
        FeatureExtraction featureExtraction1 = new FrequentWords(MostFrequentWordsLoader.load());
        FeatureExtraction featureExtraction2 = new CaptialMiddleSentenceWords(rule, 1000);

        Map<String, Integer>[] wordVectors = featureExtraction.extractFeatures(splittedTexts);
        wordVectors = Utils.multiplyFeatureMaps(wordVectors, featureExtraction1.extractFeatures(splittedTexts));
        //Flattended text without preprocessing (with dots and captial numbers
        wordVectors = Utils.combineFeatureMaps(wordVectors, featureExtraction2.extractFeatures(WordSplitter.splitAllTextsBySpaces(flattenedTexts)));



        short[][] featureVectors = ClassProcessing.convertFeaturesToVectors(wordVectors, vocabulary);


        //------------------------- Train-Test Sets ----------------------------
        int trainSize = 1000;
        int testSize = 800;

        TrainTestSets sets = TrainTestSetsSplitter.split(featureVectors,tagVectors,trainSize, testSize);

        short[][] trainX = sets.getTrainX();
        int[][] trainY = sets.getTrainY();
        short[][] testX = sets.getTestX();
        int[][] testY = sets.getTestY();



        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Preprocessing time: " + estimatedTime/1000 + "s ");
        startTime = System.currentTimeMillis();


        //--------------------------   Classification --------------------------

        ClassificationAlgorithm classificationAlgorithm = new KNN(trainX,trainY, new EuclideanDistance(), 3);


        //----------------------- Testing -----------------------------
        System.out.println("Testing");
        List<ClassificationResult> testResults =  classificationAlgorithm.classify(testX);
        System.out.println("Accuracy: " + EvaluationMetrics.calculateAccuracy(testResults,testY)*100 + "%");


        estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Classsification time: " + estimatedTime/1000 + "s ");
    }

}