package net.mymilkedeek.linkinpark.repository;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author MyMilkedEek <Michael>
 */
class FileSystem {

    private File rootFolder;
    private Map<String, Collection<String>> cache;

    private static final String rootFolderName = "/wikigame/";
    private static final String fileName = "wiki.db";

    FileSystem() {
        this.cache = new HashMap<String, Collection<String>>();
        this.rootFolder = new File(System.getProperty("user.home") + rootFolderName);

        if ( !this.rootFolder.exists() ) {
            this.rootFolder.mkdirs();
        } else {
            // read file into cache
        }
    }

    /**
     * Writes an article's links to the file system.
     *
     * @param articleName name of the article to be written
     * @param links contained in the article
     * @throws IOException
     */
    void addArticle(String articleName, Collection<String> links) {
        if ( ! this.cache.containsKey(articleName)) {
            this.cache.put(articleName, links);
        }
    }

    boolean hasArticleAlreadyBeenParsed(String articleName) {
        return this.cache.containsKey(articleName);
    }

    Collection<String> getArticleLinks(String articleName) throws IOException {
        Collection<String> output = this.cache.get(articleName);

        if ( output == null ) {
            output = new HashSet<String>();
        }

        return output;
    }

    void flush() throws IOException {
        File file = new File(this.rootFolder, this.fileName);

        StringBuilder stringBuilder = new StringBuilder();
        Set<String> articles = this.cache.keySet();

        for ( String article : articles ) {
            Collection<String> links = this.cache.get(article);

            for ( String link : links ) {
                stringBuilder.append(article).append(" -> ").append(link).append("\n");
            }
        }

        FileUtils.writeStringToFile(file, stringBuilder.toString(), "UTF-8", true);
    }
}