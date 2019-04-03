package knn;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.Comparator.comparing;

public class Utils {

    public static String[] splitBySpace(String text) {
        return text.split(" ");
    }

    public static int[] bottomN(final double[] input, final int n) {
        return IntStream.range(0, input.length)
                .boxed()
                .sorted(comparing(i -> input[i]))
                .mapToInt(i -> i)
                .limit(n)
                .toArray();
    }

    public static int[] topN(final float[] input, final int n) {
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

    public static float[] elementwiseSameLengthVectorSubtract(float[] v1, float[] v2) {
        if (v1.length != v2.length) {
            throw new IllegalArgumentException("Vectors have different length");
        }

        float[] out = new float[v1.length];
        for (int i = 0; i < v1.length; i++) {
            out[i] = v1[i] - v2[i];
        }

        return out;
    }

    public static int getIndexFromOneHotVector(int[] v) {

        if (v == null || v.length == 0) return -1; // null or empty

        for (int i = 0; i < v.length; i++) {
            if(v[i] == 1){
                return i;
            }
        }

        throw new IllegalStateException("One hot vector is not one hot vector");

    }

    public static int largestVectorElementIndex(int[] v) {

        if (v == null || v.length == 0) return -1; // null or empty

        int largest = 0;
        for (int i = 1; i < v.length; i++) {
            if (v[i] > v[largest]) largest = i;
        }
        return largest; // position of the first largest found

    }

    public static <T> T[] flatten(ArrayList<T>[] arr, Class<T> tClass) {
        int size = 0;
        for (ArrayList<T> ts : arr) {
            size += ts.size();
        }

        T[] out = (T[]) Array.newInstance(tClass, size);

        int index = 0;

        for (ArrayList<T> ts : arr) {
            for (T t : ts) {
                out[index] = t;
                index++;
            }
        }

        return out;
    }

    public static Map<String, Integer>[] combineFeatureMaps(Map<String, Integer>[] map1, Map<String, Integer>[] map2) {
        if (map1.length != map2.length) {
            throw new IllegalArgumentException("Maps have different length");
        }

        Map<String, Integer>[] outputMap = new HashMap[map1.length];

        for (int i = 0; i < outputMap.length; i++) {
            outputMap[i] = new HashMap<>();

            Map<String, Integer> submap1 = map1[i];
            Map<String, Integer> submap2 = map1[i];

            for (String s : submap1.keySet()) {
                if (outputMap[i].containsKey(s)) {
                    outputMap[i].put(s, outputMap[i].get(s) + submap1.get(s));
                } else {
                    outputMap[i].put(s, submap1.get(s));
                }
            }

            for (String s : submap2.keySet()) {
                if (outputMap[i].containsKey(s)) {
                    outputMap[i].put(s, outputMap[i].get(s) + submap2.get(s));
                } else {
                    outputMap[i].put(s, submap2.get(s));
                }
            }
        }

        return outputMap;
    }


    //Multiplies map1 elements by map2 elements
    public static Map<String, Integer>[] multiplyFeatureMaps(Map<String, Integer>[] map1, Map<String, Integer>[] map2) {
        if (map1.length != map2.length) {
            throw new IllegalArgumentException("Maps have different length");
        }

        Map<String, Integer>[] outputMap = new HashMap[map1.length];

        for (int i = 0; i < outputMap.length; i++) {
            outputMap[i] = new HashMap<>();

            Map<String, Integer> submap1 = map1[i];
            Map<String, Integer> submap2 = map2[i];

            for (String s : submap1.keySet()) {
                if(submap2.containsKey(s)){
                    submap1.put(s,submap1.get(s) * submap2.get(s));
                }else{
                    submap1.put(s, submap1.get(s));
                }
            }

            outputMap[i] = submap1;
        }

        return outputMap;

    }

    public static void measureFunctionTime(String functionName, Runnable f){
        long startTime = System.currentTimeMillis();

        f.run();

        long endTime = System.currentTimeMillis();

        System.out.println(functionName + " function took " + (endTime - startTime) + " milliseconds " + "[~" + (endTime - startTime + 500)/1000 + " seconds]");
    }

    public static void zeroColumn(float[][] vectors, int columnIndex){
        for (int i = 0; i < vectors.length; i++) {
            vectors[i][columnIndex] = 0;
        }
    }
}
