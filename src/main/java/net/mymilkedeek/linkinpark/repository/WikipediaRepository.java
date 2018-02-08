package net.mymilkedeek.linkinpark.repository;

import java.io.IOException;
import java.util.Collection;

/**
 * @author MyMilkedEek <Michael>
 */
public class WikipediaRepository {

    private static final int MAX_DEPTH = 1;

    private PageParser pageParser;
    private FileSystem fileSystem;

    public WikipediaRepository() {
        this.pageParser = new PageParser();
        this.fileSystem = new FileSystem();
    }

    public void addPageToRepository(String articleName) {
        this.addPageToRepository(articleName, 0);
    }

    void addPageToRepository(String articleName, int currentDepth) {
        System.out.println("Getting " + articleName + ".");
        try {
            if (! this.fileSystem.hasArticleAlreadyBeenParsed(articleName)) {
                System.out.println("Cache miss - retrieving article online.");
                Collection<String> parsedLinks = this.pageParser.parseToLinks(articleName, false);
                System.out.println(parsedLinks.size() + " links found.");
                this.fileSystem.addArticle(articleName, parsedLinks);

                if (currentDepth <= MAX_DEPTH) {
                    for (String parsedLink : parsedLinks) {
                        this.addPageToRepository(parsedLink, currentDepth + 1);
                    }
                }
            }
        } catch ( IOException ioe ) {
            System.out.println("Fetching the page went wrong!");
            ioe.printStackTrace();
        }
    }

    public Collection<String> getLinksForArticle(String articleName) throws IOException {
        if ( !this.fileSystem.hasArticleAlreadyBeenParsed(articleName)) {
            this.addPageToRepository(articleName, MAX_DEPTH);
        }

        return this.fileSystem.getArticleLinks(articleName);
    }

    public void persistRepository() throws IOException {
        this.fileSystem.flush();
    }

    public void collectMetaDataOnArticle(String articleName) throws IOException {
        if ( !this.fileSystem.hasArticleAlreadyBeenParsed(articleName)) {
            this.addPageToRepository(articleName, MAX_DEPTH);
        }

        this.pageParser.parseMetadata(articleName);
    }
}