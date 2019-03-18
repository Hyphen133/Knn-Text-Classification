package knn;

import knn.classification.*;
import knn.feature_extraction.FeatureExtraction;
import knn.feature_extraction.InversedBagOfWords;
import knn.feature_extraction.FrequentWords;
import knn.feature_extraction.VocabularyCreator;
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

        String[][] textss = WordSplitter.splitAllTextsBySpaces(flattenedTexts);
        System.out.println(Arrays.toString(textss[0]));
        System.out.println(Arrays.toString(textss[1]));



        //------------------- Preprocessing ------------------------
        PreprocessingRule rule = new LeaveOnlyCharactersAndSpacesRule();
        for (int i = 0; i < flattenedTags.length; i++) {
            flattenedTexts[i] = rule.applyTo(flattenedTexts[i]);
        }

        //Each text is splitted in words and we get one-hot vector for each tag
        String[][] splittedTexts = WordSplitter.splitAllTextsBySpaces(flattenedTexts);
        int[][] tagVectors = ClassProcessing.convertTagsToVectorsWithSingleOne(flattenedTags,placesMap);


        //----------------------- Feature extraction --------------------------
        FeatureExtraction featureExtraction1 = new FrequentWords(MostFrequentWordsLoader.load());
        FeatureExtraction featureExtraction = new InversedBagOfWords();

        Map<String, Integer>[] wordVectors = featureExtraction.extractFeatures(splittedTexts);
        wordVectors = Utils.multiplyFeatureMaps(wordVectors, featureExtraction1.extractFeatures(splittedTexts));

        List<String> vocabulary = VocabularyCreator.getVolcabulary(splittedTexts);

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