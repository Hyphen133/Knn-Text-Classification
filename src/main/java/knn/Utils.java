package knn;

import java.util.Dictionary;
import java.util.stream.IntStream;

import static java.util.Comparator.comparing;

public class Utils {
    public static double[] convertFeatureDictonaryToVector(Dictionary<String, Integer> featureDictonary) {

        return null;
    }


    public static String[] splitBySpace(String text) {
        return text.split(" ");
    }

    public static int[] topN(final double[] input, final int n) {
        return IntStream.range(0, input.length)
                .boxed()
                .sorted(comparing(i -> -input[i]))
                .mapToInt(i -> i)
                .limit(n)
                .toArray();
    }

    public static int[] elementwiseSameLengthVectorAdd(int[] v1, int[] v2) {
        if (v1.length != v2.length) {
            throw new IllegalArgumentException("Vectors have different length");
        }

        int[] out = new int[v1.length];
        for (int i = 0; i < v1.length; i++) {
            out[i] = v1[i] + v2[i];
        }

        return out;
    }

    public static int largestVectorElementIndex(int[] v) {

        if (v == null || v.length == 0) return -1; // null or empty

        int largest = 0;
        for (int i = 1; i < v.length; i++) {
            if (v[i] > v[largest]) largest = i;
        }
        return largest; // position of the first largest found

    }
}
