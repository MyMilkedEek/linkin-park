package net.mymilkedeek.linkinpark.finders;

import net.mymilkedeek.linkinpark.ILinkFinder;
import net.mymilkedeek.linkinpark.repository.WikipediaRepository;

import java.io.IOException;
import java.util.*;

/**
 * @author MyMilkedEek <Michael>
 */
public class NotEnoughTimeLinkFinder implements ILinkFinder {

    private WikipediaRepository repository;
    private long startTime;
    private long sessionEndTime;

    public NotEnoughTimeLinkFinder(WikipediaRepository repository) {
        this.repository = repository;
    }

    public List<String> findLinks(String start, String goal) throws IOException {
        if ( this.repository.contains(start) && this.repository.contains(goal) ) {
            Set<String> visitedNodes = new HashSet<String>();
            visitedNodes.add(start);
            List<String> currentPath = new ArrayList<String>();
            currentPath.add(start);


            this.startTime = System.currentTimeMillis();
            this.sessionEndTime = this.startTime + 20000;
            this.find(visitedNodes, currentPath, goal);
            long endTime = System.currentTimeMillis();

            if (currentPath.contains(goal)) {
                return currentPath;
            } else {
                return new ArrayList<String>();
            }
        } else {
            return new ArrayList<String>();
        }
    }

    private void find(Set<String> visitedNodes, List<String> currentPath, String goal) throws IOException {
        if ( this.sessionEndTime < System.currentTimeMillis()) {
            return;
        }

        Collection<String> linksForArticle = this.repository.getLinksForArticle(currentPath.get(currentPath.size() - 1));

        if ( linksForArticle.contains(goal) ) {
            visitedNodes.add(goal);
            currentPath.add(goal);

            return;
        } else {
            int max = 0;
            String article = "";

            for (String linkInArticle : linksForArticle) {
                if (! visitedNodes.contains(linkInArticle) ) {
                    Collection<String> linksForArticleCollection = this.repository.getLinksForArticle(linkInArticle, false);
                    if ( linksForArticleCollection != null ) {
                        int size = linksForArticleCollection.size();
                        if (max < size) {
                            max = size;
                            article = linkInArticle;
                        }
                    }
                }
            }

            if ( max != 0 ) {
                currentPath.add(article);
                visitedNodes.add(article);
                find(visitedNodes, currentPath, goal);
            }
        }
    }
}