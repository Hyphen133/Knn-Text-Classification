package knn.loading;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class PlacesTagsLoader {
    final static int REUTERS_SIZE = 22;

    public static ArrayList<String>[] loadPlacesTagsFromReutersDirectory(){

        ArrayList<String>[] reuterTags = new ArrayList[REUTERS_SIZE];

        for (int i = 0; i < REUTERS_SIZE; i++) {
            String index = "";
            if(i<10){
                index+="0";
            }
            index += i;

            reuterTags[i] = loadPlacesTagsFromFile("reuters/reut2-0" + index + ".sgm");
        }

        return reuterTags;

    }

    public static ArrayList<String> loadPlacesTagsFromFile(String filepath) {
        ArrayList<String> placeTags = new ArrayList<>();

        System.out.println(filepath);
        URL res = ReutersLoader.class.getClassLoader().getResource(filepath);
        File file = null;
        try {
            file = Paths.get(res.toURI()).toFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String absolutePath = file.getAbsolutePath();

        try (BufferedReader br = new BufferedReader(new FileReader(absolutePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() > "<PLACES>".length() && line.substring(0, "<PLACES>".length()).equals("<PLACES>")) {
                    line = line.replaceFirst("<PLACES><", "");
                    if (line.charAt(0) == 'D') { //then we have tag
                        line = line.replaceFirst("D>", "");

                        String tag = line.substring(0, line.indexOf('<'));
                        placeTags.add(tag);
                    } else {      //then tag is empty
                        placeTags.add("empty");
                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return placeTags;
    }

}
