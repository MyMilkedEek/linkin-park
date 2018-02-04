package net.mymilkedeek.linkinpark.finders;

import net.mymilkedeek.linkinpark.ILinkFinder;
import net.mymilkedeek.linkinpark.repository.WikipediaRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author MyMilkedEek <Michael>
 */
public class SeeminglyRandomLinkFinder implements ILinkFinder {

    private WikipediaRepository repository;

    public SeeminglyRandomLinkFinder(WikipediaRepository repository) {
        this.repository = repository;
    }

    public String[] findLinks(String start, String goal) throws IOException {
        List<String> currentPath = new ArrayList<String>();
        String currentPage = start;

        while ( !currentPage.equalsIgnoreCase(goal) ) {
            System.out.println(currentPage);
            currentPath.add(currentPage);

            Collection<String> links = this.repository.getLinksForArticle(start);

            if ( links.contains(goal)) {
                currentPage = goal;
            } else {
                Iterator<String> iterator = links.iterator();
                iterator.next();
                iterator.next();
                iterator.next();
                iterator.next();
                iterator.next();
                currentPage = iterator.next();
            }
        }

        System.out.println("Goal reached!");
        currentPath.add(goal);

        return currentPath.toArray(new String[currentPath.size()]);
    }
}