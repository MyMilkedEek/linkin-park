package net.mymilkedeek.linkinpark.repository;

import java.io.IOException;
import java.util.List;

/**
 * @author MyMilkedEek <Michael>
 */
public class WikipediaRepository {

    private PageParser pageParser;

    public WikipediaRepository() {
        this.pageParser = new PageParser();
    }

    public void addPageToRepository(String articleName) throws IOException {
        List<String> parsedLinks = this.pageParser.parseToLinks(articleName, false);
        List<String> parsedLinksMobile = this.pageParser.parseToLinks(articleName, true);

        System.out.println();
    }
}