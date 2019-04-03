package knn.classificationV2;

import knn.Utils;
import knn.similarityV2.SimilarityMeasure;

import java.util.ArrayList;
import java.util.List;

public class KNN implements ClassificationAlgorithm {

    ColdStart coldStart;
    SimilarityMeasure similarityMeasure;
    int k;

    public KNN(ColdStart coldStart, SimilarityMeasure similarityMeasure, int k) {
        this.coldStart = coldStart;
        this.similarityMeasure = similarityMeasure;
        this.k = k;
    }

    @Override
    public ClassificationResults classify(float[][] vectors, int[][] tags) {
        //Cold start
        ColdStartSolution coldStartSolution = coldStart.process(vectors,tags);
        ArrayList<float[]> classifiedVectors = coldStartSolution.getVectors();
        ArrayList<int[]> correctTags = coldStartSolution.getTags();

        int coldStartSize = classifiedVectors.size();

        //Classification
        ArrayList<int[]> classifiedTags = new ArrayList<>();
        for (int i = 0; i < vectors.length; i++) {
            //Calculate table of similarities
            double[] similiarityTable = new double[classifiedVectors.size()];

            for (int j = 0; j < similiarityTable.length; j++) {
                similiarityTable[j] = similarityMeasure.calculateSimilarity(vectors[i], classifiedVectors.get(j));
            }

            //Get bottom indexes
            int[] bottomIndexes = Utils.bottomN(similiarityTable, k);

            int[] classCounts = new int[tags[0].length];
            for (int j = 0; j < bottomIndexes.length; j++) {
                classCounts = Utils.elementwiseSameLengthVectorAdd(classCounts, correctTags.get(bottomIndexes[j]));
            }
            //Get class of more occurences
            int[] outClass = new int[tags[0].length];
            //TODO resolve for many larger indexes
            int largestIndex = Utils.largestVectorElementIndex(classCounts);
            outClass[largestIndex] = 1;

            classifiedVectors.add(vectors[i]);
            correctTags.add(tags[i]);
            classifiedTags.add(outClass);
        }

        //Remove vectors from cold start
        for (int i = 0; i < coldStartSize; i++) {
            classifiedVectors.remove(0);
            correctTags.remove(0);
        }

        return new ClassificationResults(classifiedVectors,correctTags, classifiedTags );
    }
}
