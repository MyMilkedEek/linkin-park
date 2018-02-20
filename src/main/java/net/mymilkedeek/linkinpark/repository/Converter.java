package net.mymilkedeek.linkinpark.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author MyMilkedEek <Michael>
 */
public class Converter {

    private FileSystem fileSystem;

    public Converter() {
        this.fileSystem = new FileSystem();
    }

    public void convert() {
        Collection<String> allArticles = this.fileSystem.getAllArticles();
        Map<Integer, String> articleIds = new HashMap<Integer, String>(allArticles.size());

        Iterator<String> iterator = allArticles.iterator();
        int counter = 0;

        while (iterator.hasNext()) {
            articleIds.put(counter, iterator.next());
            counter++;
        }

        System.out.println();
    }

    public static void main(String[] args) {
        new Converter().convert();
    }
}