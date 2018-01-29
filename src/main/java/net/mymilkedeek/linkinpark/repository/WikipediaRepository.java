package net.mymilkedeek.linkinpark.repository;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author MyMilkedEek <Michael>
 */
public class WikipediaRepository {

    private PageParser pageParser;

    public WikipediaRepository() {
        this.pageParser = new PageParser();
    }

    public void addPageToRepository(String articleName) throws IOException {
        Set<String> parsedLinks = (Set<String>) this.pageParser.parseToLinks(articleName, false);
        Set<String> parsedLinksMobile = (Set<String>) this.pageParser.parseToLinks(articleName, true);

        Set<String> sharedLinks = new HashSet<String>();
        Set<String> onlyDesktop = new HashSet<String>();
        Set<String> onlyMobile  = new HashSet<String>();

        for ( String link : parsedLinks ) {
            if ( parsedLinksMobile.contains(link) ) {
                sharedLinks.add(link);
            } else {
                onlyDesktop.add(link);
            }
        }

        for ( String link : parsedLinksMobile ){
            if ( !sharedLinks.contains(link) ) {
                onlyMobile.add(link);
            }
        }

        write(parsedLinks, new File("desktop.txt"));
        write(parsedLinksMobile, new File("mobile.txt"));
        write(sharedLinks, new File("shared.txt"));
        write(onlyDesktop, new File("onlyDesktop.txt"));
        write(onlyMobile, new File("onlymobile.txt"));


        System.out.println();
    }

    private void write(Collection<String> content, File file) throws IOException {
        file.delete();
        for ( String line : content ) {
            FileUtils.writeStringToFile(file, line, "UTF-8", true);
            FileUtils.writeStringToFile(file, "\n", "UTF-8", true);
        }
    }
}