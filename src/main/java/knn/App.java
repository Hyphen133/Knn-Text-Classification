package knn;

import knn.loading.PlacesTagsLoader;
import knn.loading.ReutersLoader;
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
import java.util.Map;

import static knn.loading.PlacesTagsLoader.getAllPlacesMap;

public class App {
    public static void main(String[] args){
        ArrayList<String>[] tags = PlacesTagsLoader.loadPlacesTagsFromReutersDirectory();

        for (ArrayList<String> tag : tags) {
            System.out.println(tag.size());
        }

        Map<String,Integer> placesMap = PlacesTagsLoader.getAllPlacesMap();

//        for (String s : placesMap.keySet()) {
//            System.out.println(s);
//        }

        System.out.println(placesMap.keySet().size());

        ArrayList<String>[] texts = ReutersLoader.load();
        for (ArrayList<String> text : texts) {
            System.out.println(text.size());
        }
    }

}

