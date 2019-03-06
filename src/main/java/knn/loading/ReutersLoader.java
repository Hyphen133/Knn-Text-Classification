package knn.loading;

import knn.App;
import org.apache.lucene.benchmark.utils.ExtractReuters;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReutersLoader {
    public static Path load() throws IOException, URISyntaxException {
        URL res = ReutersLoader.class.getClassLoader().getResource("reuters/README.txt");
        File file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();
        System.out.println(absolutePath.replace("/README.txt", ""));
        System.out.println(absolutePath.replace("/reuters/README.txt", "/output"));

        Path reutersPath = Paths.get(absolutePath.replace("/README.txt", ""));
        Path outputPath = Paths.get(absolutePath.replace("/reuters/README.txt", "/output"));

        ExtractReuters extractReuters = new ExtractReuters(reutersPath, outputPath);
        extractReuters.extract();

        //TODO ->  add reading places tags
        return outputPath;
    }
}
