package knn.feature_extractionV2;

public class TdIdf implements RawVectorProcessing {
    @Override
    public void apply(float[][] rawVectors) {
        //Term frequency
        float[] tfCounts = new float[rawVectors[0].length];
        //Idf Denominator
        float[] idfCounts = new float[rawVectors[0].length];

        for (int j = 0; j < rawVectors[0].length; j++) {
            for (int i = 0; i < rawVectors.length; i++) {
                tfCounts[j] += rawVectors[i][j];

                if(rawVectors[i][j] > 0){
                    idfCounts[j]+=1;
                }
            }
        }

        int documentsLength = rawVectors.length;

        for (int i = 0; i < rawVectors.length; i++) {
            for (int j = 0; j < rawVectors[0].length; j++) {
                rawVectors[i][j] = (float) ((Math.log(documentsLength/idfCounts[j])) * (documentsLength/idfCounts[j]));
            }
        }
    }
}
