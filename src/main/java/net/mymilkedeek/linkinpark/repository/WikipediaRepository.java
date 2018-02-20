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
    private boolean mobile;

    public WikipediaRepository(boolean mobile) {
        this.pageParser = new PageParser();
        this.fileSystem = new FileSystem();
        this.mobile = mobile;
    }

    public void addPageToRepository(String articleName) {
        this.addPageToRepository(articleName, 0);
    }

    void addPageToRepository(String articleName, int currentDepth) {
        try {
            if (! this.fileSystem.hasArticleAlreadyBeenParsed(articleName)) {
                Collection<String> parsedLinks = this.pageParser.parseToLinks(articleName, mobile);
                this.fileSystem.addArticle(articleName, parsedLinks);

                if (currentDepth <= MAX_DEPTH) {
                    for (String parsedLink : parsedLinks) {
                        this.addPageToRepository(parsedLink, currentDepth + 1);
                    }
                }
            }
        } catch ( IOException ioe ) {
            ioe.printStackTrace();
        }
    }

    public Collection<String> getLinksForArticle(String articleName, boolean fetch) throws IOException {
        if ( !this.fileSystem.hasArticleAlreadyBeenParsed(articleName)) {
            if ( fetch ) {
                this.addPageToRepository(articleName, MAX_DEPTH);
            } else {
                return null;
            }
        }

        return this.fileSystem.getArticleLinks(articleName);

    }

    public Collection<String> getLinksForArticle(String articleName) throws IOException {
        return this.getLinksForArticle(articleName, true);
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

    public boolean contains(String article) {
        return this.fileSystem.hasArticleAlreadyBeenParsed(article);
    }

    public Collection<String> getAll() {
        return fileSystem.getAllArticles();
    }
}