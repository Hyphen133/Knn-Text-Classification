package knn.feature_extractionV2;

import knn.Utils;

import javax.rmi.CORBA.Util;

public class RemoveMoreThanOccurrencesInDocuments implements RawVectorProcessing {

    private int mostOccurences;

    public RemoveMoreThanOccurrencesInDocuments(int mostOccurences) {
        this.mostOccurences = mostOccurences;
    }

    @Override
    public void apply(float[][] rawVectors) {
        for (int j = 0; j < rawVectors[0].length; j++) {
            float sum = 0;
            for (int i = 0; i < rawVectors.length; i++) {
                sum += rawVectors[i][j];
            }

            if(sum > mostOccurences){
                Utils.zeroColumn(rawVectors,j);
            }
        }
    }
}
