package knn.loadingV2;

import java.util.ArrayList;
import java.util.List;

public class PlacesFilter {
    public static void filter(ArrayList<String> texts, ArrayList<String> tags, List<String> choosenTags){
        for (int i = 0; i < texts.size(); i++) {
            if(!choosenTags.contains(tags.get(i))){
                texts.remove(texts.get(i));
                tags.remove(tags.get(i));
                i--;
            }
        }
    }
}
