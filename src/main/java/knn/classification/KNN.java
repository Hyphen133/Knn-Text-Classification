package knn.classification;

import knn.Utils;
import knn.similarity.SimilarityMeasure;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class KNN implements ClassificationAlgorithm {

    short[][] baseVectors;
    int[][] baseVectorsClasses;
    SimilarityMeasure similarityMeasure;
    int k;

    public KNN(short[][] baseVectors, int[][] baseVectorsClasses, SimilarityMeasure similarityMeasure, int k) {
        this.baseVectors = baseVectors;
        this.baseVectorsClasses = baseVectorsClasses;
        this.similarityMeasure = similarityMeasure;
        this.k = k;
    }

    public int[] classify(short[] vector) {
        // Calculate similarities for each of base vector
        double[] similarities = new double[baseVectors.length];

        for (int i = 0; i < similarities.length; i++) {
            similarities[i] = similarityMeasure.calculateSimilarity(baseVectors[i],vector);
        }

        //Get K first similarities
        int[] topIndexes = Utils.bottomN(similarities,k);

        //Calculate class counts in this k first elements
        int numberOfClasses = baseVectorsClasses[0].length;
        int[] classCounts = new int[numberOfClasses];
        for (int i = 0; i < k; i++) {
            classCounts = Utils.elementwiseSameLengthVectorAdd(classCounts,baseVectorsClasses[topIndexes[i]]);
        }

        //Set output classification vector
        int maxClassIndex = Utils.largestVectorElementIndex(classCounts);
        int[] outClasses = new int[numberOfClasses];
        outClasses[maxClassIndex] = 1;
        return outClasses;
    }

    public List<ClassificationResult> classify(short[][] testFeatureVectors){
        List<Callable<ClassificationResult>> tasks = new ArrayList<>();
        for (int i = 0; i < testFeatureVectors.length; i++) {
            int index = i;
            short[] featuresVector = testFeatureVectors[i];
            Callable<ClassificationResult> c = () -> new ClassificationResult(index, classify(featuresVector));
            tasks.add(c);
        }

        ExecutorService exec = Executors.newCachedThreadPool();
//        ExecutorService exec = Executors.newFixedThreadPool(4);

        List<ClassificationResult> testResults = new ArrayList<>();

        try {

            List<Future<ClassificationResult>> results = exec.invokeAll(tasks);
            for (Future<ClassificationResult> fr : results) {
                testResults.add(fr.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            exec.shutdown();
        }

        return testResults;
    }

}
