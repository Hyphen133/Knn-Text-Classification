package knn.preprocessing;

public class WordSplitter {

    private static String[] splitTextBySpaces(String text){
        return text.split("\\s+");
    }

    public static String[][] splitAllTextsBySpaces(String[] texts){
        String[][] splittedTexts = new String[texts.length][];
        for (int i = 0; i < texts.length; i++) {
            splittedTexts[i] = splitTextBySpaces(texts[i]);
        }
        return splittedTexts;
    }
}
