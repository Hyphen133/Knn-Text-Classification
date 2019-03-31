package knn.feature_extractionV2;

import knn.Utils;

public class RemoveLessThanOccurrencesInDocuments implements RawVectorProcessing {

    private int leastOccurences;

    public RemoveLessThanOccurrencesInDocuments(int leastOccurences) {
        this.leastOccurences = leastOccurences;
    }

    @Override
    public void apply(float[][] rawVectors) {
        for (int j = 0; j < rawVectors[0].length; j++) {
            float sum = 0;
            for (int i = 0; i < rawVectors.length; i++) {
                sum += rawVectors[i][j];
            }

            if(sum < leastOccurences){
                Utils.zeroColumn(rawVectors,j);
            }
        }
    }
}
