package net.mymilkedeek.linkinpark.finders;

import net.mymilkedeek.linkinpark.ILinkFinder;
import net.mymilkedeek.linkinpark.repository.WikipediaRepository;

import java.io.IOException;
import java.util.*;

/**
 * @author MyMilkedEek <Michael>
 */
public class DepthFirstLinkFinder implements ILinkFinder {

    private static final int maximumPathLength = 6;
    private final WikipediaRepository repository;

    private Set<String> memory;
    private long sessionEndTime;

    public DepthFirstLinkFinder(WikipediaRepository repository) {
        this.repository = repository;
    }

    public List<String> findLinks(String start, String goal) throws IOException {
        this.memory = new HashSet<String>();

        long startTime = System.currentTimeMillis();
        sessionEndTime = startTime + 120000;
        List<String> path = findPath(start, goal, new ArrayList<String>());
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;

        System.out.println("Took me " + duration + " ms.");

        this.repository.persistRepository();

        return path;
    }

    private List<String> findPath(String start, String goal, List<String> currentPath) throws IOException {
        if ( !this.memory.contains(start)) {
            this.memory.add(start);
        } else {
            return currentPath; // we've already been here before, it's not a road you want to go down again,
            // TODO: maybe add previous depth here. lower depth gets recalculated.
        }

        if ( !currentPath.contains(start)) {
            currentPath.add(start); // make sure the start is in the path
        } else {
            return currentPath; // avoid dupes in path
        }

        if ( currentPath.contains(goal) ) {
            return currentPath;
        }

        if ( currentPath.size() == maximumPathLength ) {
            currentPath.remove(currentPath.size() - 1);
            return currentPath;
        }

        Collection<String> linksOnPage = this.repository.getLinksForArticle(start);

        if ( linksOnPage.contains(goal)) {
            currentPath.add(goal);
            return currentPath;
        }

        for ( String link : linksOnPage ) {
            if ( sessionEndTime > System.currentTimeMillis()) {
                findPath(link, goal, currentPath);
            }

            if ( currentPath.contains(goal)) {
                return currentPath;
            }
        }

        currentPath.remove(currentPath.size()-1);
        return currentPath;
    }
}