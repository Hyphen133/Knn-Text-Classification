package knn.loading;

import knn.App;
import knn.Config;
import org.apache.lucene.benchmark.utils.ExtractReuters;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static knn.Config.REUTERS_SIZE;

public class ReutersLoader {
    private static Path processSgmToTxtFiles() {
        URL res = ReutersLoader.class.getClassLoader().getResource("reuters/README.txt");
        File file = null;
        try {
            file = Paths.get(res.toURI()).toFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String absolutePath = file.getAbsolutePath();

        Path reutersPath = Paths.get(absolutePath.replace("/README.txt", ""));
        Path outputPath = Paths.get(absolutePath.replace("/reuters/README.txt", "/output"));

        ExtractReuters extractReuters = null;
        try {
            extractReuters = new ExtractReuters(reutersPath, outputPath);
            extractReuters.extract();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputPath;
    }

    public static ArrayList<String>[] load() {
        Path dirPath = processSgmToTxtFiles();
        ArrayList<String>[] texts = new ArrayList[REUTERS_SIZE];


        for (int i = 0; i < REUTERS_SIZE; i++) {
            String index = "";
            if (i < 10) {
                index += "0";
            }
            index += i;

            String basePath = dirPath + "/reut2-0" + index + ".sgm-";

            int reutersLength = (i != REUTERS_SIZE - 1) ? 1000 : 578;

            ArrayList<String> groupTexts = new ArrayList<>();

            for (int j = 0; j < reutersLength; j++) {
                String path = basePath + j + ".txt";
                groupTexts.add(processTxtFile(path, false));
            }

            texts[i] = groupTexts;
        }

        return texts;
    }

    private static String processTxtFile(String filepath, boolean withTitle) {
        String text = "";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            int i = 1;
            while ((line = br.readLine()) != null) {
                if (withTitle) {
                    if (i == 3) {           // 3rd line contains title, 5th line contains text
                        text += line + " ";
                    }
                }
                if (i == 5) {           // 3rd line contains title, 5th line contains text
                    text += line + " ";
                }

                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text; //remove last space ??
    }


}
