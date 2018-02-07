package net.mymilkedeek.linkinpark.repository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author MyMilkedEek <Michael>
 */
public class PageParser {

    private static final int TIMEOUT = 10000;

    public Collection<String> parseToLinks(String wikipediaArticle, boolean mobile) throws IOException {
        String urlPrefix = mobile ? "http://en.m." : "http://en.";
        URL wikipediaArticleURL = new URL(urlPrefix + "wikipedia.org/wiki/" + wikipediaArticle);
        Document document = Jsoup.parse(wikipediaArticleURL, TIMEOUT);
        Elements linksInPage = document.select("a");

        Set<String> articleLinks = new HashSet<String>();

        for (Element linkElement : linksInPage ) {
            String href = linkElement.attr("href");

            if (href.startsWith("/wiki/")) {
                if(href.contains("Category:") || href.contains("Special:") ||
                        href.contains("Template:") || href.contains("Portal:") ||
                        href.contains("Talk:") || href.contains("Help:") ||
                        href.contains("Template_talk:") || href.contains("File:") ||
                        href.contains("Book:") || href.contains("Wikipedia:") ||
                        href.contains("Main_Page")) {
                    continue;
                }

                articleLinks.add(normalizeLink(href.substring(6)));
            }
        }

        return articleLinks;
    }

    private String normalizeLink(String link) {
        return link.trim();
    }

    public Collection<String> parseMetadata(String articleName) {
        return null;
    }
}