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

    public void addPageToRepository(String articleName) throws IOException {
        this.addPageToRepository(articleName, 0);
    }

    void addPageToRepository(String articleName, int currentDepth) throws IOException {
        if ( ! this.fileSystem.hasArticleAlreadyBeenParsed(articleName) ) {
            Collection<String> parsedLinks = this.pageParser.parseToLinks(articleName, false);
            this.fileSystem.writeToArticleFile(articleName, parsedLinks);

            if ( currentDepth <= MAX_DEPTH ) {
                for (String parsedLink : parsedLinks) {
                    this.addPageToRepository(parsedLink, currentDepth + 1);
                }
            }
        }
    }

    public Collection<String> getLinksForArticle(String articleName) throws IOException {
        if ( !this.fileSystem.hasArticleAlreadyBeenParsed(articleName)) {
            this.addPageToRepository(articleName, MAX_DEPTH);
        }

        return this.fileSystem.getArticleLinks(articleName);
    }
}