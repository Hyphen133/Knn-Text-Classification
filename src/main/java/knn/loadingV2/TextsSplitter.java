package knn.loadingV2;

import java.util.ArrayList;

import static knn.Config.PARAGRAPH_SYMBOL;

public class TextsSplitter {
    public static ArrayList<String[]> split(ArrayList<String> texts){
        ArrayList<String[]> splittedTexts = new ArrayList<>();

        for (String text : texts) {
            splittedTexts.add(text.replace("     ", PARAGRAPH_SYMBOL).split("\\s"));
        }

        return splittedTexts;
    }
}
