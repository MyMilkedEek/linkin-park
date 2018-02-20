package net.mymilkedeek.linkinpark.data;

import net.mymilkedeek.linkinpark.repository.WikipediaRepository;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * Gets the dataset for the competition
 *
 * @author MyMilkedEek <Michael>
 */
public class DataSetPreparer {

    public static void main(String[] args) throws IOException {
        WikipediaRepository repository = new WikipediaRepository(false);
        Collection<String> allArticles = repository.getAll();

        Iterator<String> iterator = allArticles.iterator();
        StringBuilder builder = new StringBuilder();

        for ( int i = 0; i < 100; i++ ) {
            String start = getLinkedArticle(repository, iterator.next());
            String goal = getLinkedArticle(repository, iterator.next());

            builder.append(start).append("\t").append(goal).append("\n");
        }

        FileUtils.writeStringToFile(new File("data.txt"), builder.toString(), "UTF-8");
    }

    private static String getLinkedArticle(WikipediaRepository repository, String article) throws IOException {
        Collection<String> linksForArticle = repository.getLinksForArticle(article);
        Iterator<String> iterator = linksForArticle.iterator();
        return iterator.next();
    }


}