package knn.loadingV2;

import org.apache.lucene.benchmark.utils.ExtractReuters;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static knn.Config.*;

public class ReutersLoaderV2 {
    private static Path processSgmToTxtFiles(boolean doExtract) {
        URL res = ReutersLoaderV2.class.getClassLoader().getResource("reuters/README.txt");
        File file = null;
        try {
            file = Paths.get(res.toURI()).toFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String absolutePath = file.getAbsolutePath();

        Path reutersPath = Paths.get(absolutePath.replace("/README.txt", ""));
        Path outputPath = Paths.get(absolutePath.replace("/reuters/README.txt", "/output"));

        if(doExtract == true){
            ExtractReuters extractReuters = null;
            try {
                extractReuters = new ExtractReuters(reutersPath, outputPath);
                extractReuters.extract();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return outputPath;
    }

    public static ArrayList<String> load(boolean withTitle, boolean doExtract) {
        Path dirPath = processSgmToTxtFiles(doExtract);
        ArrayList<String> texts = new ArrayList<>();


        for (int i = 0; i < REUTERS_SIZE; i++) {
            String index = "";
            if (i < 10) {
                index += "0";
            }
            index += i;

            String basePath = dirPath + "/reut2-0" + index + ".sgm-";

            int reutersLength = (i != REUTERS_SIZE - 1) ? REUTERS_PACKET_SIZE : REUTERS_LAST_PACKET_SIZE;


            for (int j = 0; j < reutersLength; j++) {
                String path = basePath + j + ".txt";
                texts.add(processTxtFile(path, withTitle));
            }

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
