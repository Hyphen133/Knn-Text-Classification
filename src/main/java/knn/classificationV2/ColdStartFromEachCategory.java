package knn.classificationV2;

import knn.Utils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

public class ColdStartFromEachCategory implements ColdStart{
    int count;

    public ColdStartFromEachCategory(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    @Override
    public ColdStartSolution process(float[][] vectors, int[][] tags) {

        ColdStartSolution solution = new ColdStartSolution();


        ArrayList<Integer>[] tagIndexesForEachTag = new ArrayList[tags[0].length];
        Random random = new Random();

        for (int i = 0; i < tagIndexesForEachTag.length; i++) {
            tagIndexesForEachTag[i] = new ArrayList<>();
        }

        //Group indexes by tags
        for (int i = 0; i < tags.length; i++) {
            int index = Utils.getIndexFromOneHotVector(tags[i]);
            tagIndexesForEachTag[index].add(i);
        }

        ArrayList<Integer> selectedIndexes = new ArrayList<>();

        //"count" indexes for each tag
        for (int i = 0; i < tags[0].length; i++) {
            for (int j = 0; j < count; j++) {
                selectedIndexes.add(tagIndexesForEachTag[i].get(random.nextInt(tagIndexesForEachTag[i].size())));
            }
        }

        //sort in descending order (!!no need to decrease indexes on looping for removal)
        Collections.sort(selectedIndexes, Collections.reverseOrder());

        ArrayList<float[]> vectorsList = new ArrayList<>();
        ArrayList<int[]> tagsList = new ArrayList<>();

        //Constructing cold start solution + Removing selected vectors and tags
        for (int i = 0; i < selectedIndexes.size(); i++) {
            vectorsList.add(vectors[selectedIndexes.get(i)]);
            tagsList.add(tags[selectedIndexes.get(i)]);

            //can remove cause of descending order of indexes
            ArrayUtils.removeElement(vectors, selectedIndexes.get(i));
            ArrayUtils.removeElement(tags, selectedIndexes.get(i));

        }


        return new ColdStartSolution(vectorsList, tagsList);
    }
}
