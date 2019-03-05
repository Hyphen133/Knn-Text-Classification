package knn;

import org.apache.lucene.benchmark.utils.ExtractReuters;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class App {
    public static void main(String[] args) throws Exception {
//        ExtractReuters extractReuters = new ExtractReuters();
        URL res = App.class.getClassLoader().getResource("reuters/README.txt");
        File file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();
        System.out.println(absolutePath.replace("/README.txt", ""));
        System.out.println(absolutePath.replace("/reuters/README.txt", "/output"));

        Path reutersPath = Paths.get(absolutePath.replace("/README.txt", ""));
        Path outputPath = Paths.get(absolutePath.replace("/reuters/README.txt", "/output"));

        ExtractReuters extractReuters = new ExtractReuters(reutersPath, outputPath);
        extractReuters.extract();
//
//        Thread.sleep(1000000000);
    }
}

