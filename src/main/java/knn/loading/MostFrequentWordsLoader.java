package knn.loading;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;

public class MostFrequentWordsLoader {
    public static Set<String> load(){
        String filepath = "topfrequencywords.txt";


        URL res = ReutersLoader.class.getClassLoader().getResource(filepath);
        File file = null;
        try {
            file = Paths.get(res.toURI()).toFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String absolutePath = file.getAbsolutePath();

        Set<String> wordSet = new TreeSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(absolutePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                wordSet.add(line.trim().toLowerCase());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wordSet;
    }
}
