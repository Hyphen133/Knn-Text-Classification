package knn;

import knn.loading.PlacesTagsLoader;
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
import java.util.ArrayList;
import java.util.Arrays;

public class App {
    public static void main(String[] args){
        ArrayList<String>[] tags = PlacesTagsLoader.loadPlacesTagsFromReutersDirectory();

        for (ArrayList<String> tag : tags) {
            System.out.println(tag.size());
        }
    }
}

