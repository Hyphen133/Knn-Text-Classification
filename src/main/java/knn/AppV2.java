package knn;

import knn.loading.PlacesTagsLoader;
import knn.loading.ReutersLoader;
import knn.loadingV2.PlacesTagsLoaderV2;
import knn.loadingV2.ReuterWithTag;
import knn.loadingV2.ReutersLoaderV2;
import knn.loadingV2.SplittedReuterWithTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static knn.Config.PARAGRAPH_SYMBOL;

public class AppV2 {
    public static void main(String[] args) {



        ArrayList<String> tags = PlacesTagsLoaderV2.loadPlacesTagsFromReutersDirectory();
        ArrayList<String> texts = ReutersLoaderV2.load(false, true);

        Utils.measureFunctionTime("Loading", () -> {
            ArrayList<String> tags1 = PlacesTagsLoaderV2.loadPlacesTagsFromReutersDirectory();
            ArrayList<String> texts1 = ReutersLoaderV2.load(false, true);
            System.out.println(texts1.size());
            System.out.println(tags1.size());
//            System.out.println(tags1);
        });


        Utils.measureFunctionTime("Converting to class ", () -> {
            ArrayList<ReuterWithTag> reutersWithTags = new ArrayList<>();
            for (int i = 0; i < texts.size(); i++) {
                reutersWithTags.add(new ReuterWithTag(texts.get(i), tags.get(i)));
            }
        });


        ArrayList<ReuterWithTag> reutersWithTags = new ArrayList<>();
        for (int i = 0; i < texts.size(); i++) {
            reutersWithTags.add(new ReuterWithTag(texts.get(i), tags.get(i)));
        }


        Utils.measureFunctionTime("Filtering ", () -> {
            ArrayList<String> chosenTags = new ArrayList<>();
            chosenTags.addAll(Arrays.asList("west-germany", "usa", "france", "uk", "canada", "japan"));


            reutersWithTags.removeIf(x -> !chosenTags.contains(x.getTag()));
            System.out.println(reutersWithTags.size());
        });



        Utils.measureFunctionTime("Splitting ", () -> {
            ArrayList<SplittedReuterWithTag> splittedReutersWithTags = new ArrayList<>();
            for (ReuterWithTag reutersWithTag : reutersWithTags) {
                    splittedReutersWithTags.add(new SplittedReuterWithTag(reutersWithTag.getText().replace("     ", PARAGRAPH_SYMBOL).split("\\s"), reutersWithTag.getTag()));
            }
//            for (int i = 0; i < 10; i++) {
//                System.out.println(reutersWithTags.get(i).getText());
//                System.out.println(reutersWithTags.get(i).getText().replace("     ", " <<>> "));
//                System.out.println(Arrays.toString(reutersWithTags.get(i).getText().replace("     ", " <<>> ").split("\\s")));
//            }
        });




    }
}
