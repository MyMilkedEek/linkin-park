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
    private List<String> toWrite;

    private static final String rootFolderName = "/wikigame/";
    private static final String fileName = "wiki.db";

    FileSystem() {
        this.cache = new HashMap<String, Collection<String>>();
        this.toWrite = new ArrayList<String>();
        this.rootFolder = new File(System.getProperty("user.home") + rootFolderName);

        if ( !this.rootFolder.exists() ) {
            this.rootFolder.mkdirs();
        } else {
            try {
                List<String> strings = FileUtils.readLines(new File(this.rootFolder, fileName), "UTF-8");

                for ( String line : strings ) {
                    String article = line.substring(0, line.indexOf(" -> "));
                    String link = line.substring(line.indexOf(" -> ") + 4);

                    Collection<String> links = this.cache.get(article);

                    if ( links == null ) {
                        links = new HashSet<String>();
                    }

                    links.add(link);

                    this.cache.put(article, links);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
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
        String temp = articleName.toUpperCase();
        if ( ! this.cache.containsKey(temp)) {
            this.cache.put(temp, links);
            this.toWrite.add(temp);

            if (this.toWrite.size() > 1000 ) {
                try {
                    this.flush();
                } catch (IOException e) {
                    e.printStackTrace(); // TODO catch exception properly
                }
            }
        }
    }

    boolean hasArticleAlreadyBeenParsed(String articleName) {
        return this.cache.containsKey(articleName.toUpperCase());
    }

    Collection<String> getArticleLinks(String articleName) throws IOException {
        Collection<String> output = this.cache.get(articleName.toUpperCase());

        if ( output == null ) {
            output = new HashSet<String>();
        }

        return output;
    }

    void flush() throws IOException {
        File file = new File(this.rootFolder, this.fileName);

        StringBuilder stringBuilder = new StringBuilder();
        Set<String> articles = this.cache.keySet();

        for ( String article : toWrite ) {
            Collection<String> links = this.cache.get(article);

            for ( String link : links ) {
                stringBuilder.append(article).append(" -> ").append(link).append("\n");
            }
        }

        FileUtils.writeStringToFile(file, stringBuilder.toString(), "UTF-8", true);
        this.toWrite = new ArrayList<String>();
    }

    public Collection<String> getAllArticles() {
        return this.cache.keySet();
    }
}