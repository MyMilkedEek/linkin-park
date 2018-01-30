package net.mymilkedeek.linkinpark.repository;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * @author MyMilkedEek <Michael>
 */
class FileSystem {

    private File rootFolder;

    private static final String rootFolderName = "/wikigame/";

    FileSystem() {
        this.rootFolder = new File(System.getProperty("user.home") + rootFolderName);

        if ( !this.rootFolder.exists() ) {
            this.rootFolder.mkdirs();
        }
    }

    /**
     * Writes an article's links to the file system.
     *
     * @param articleName name of the article to be written
     * @param links contained in the article
     * @throws IOException
     */
    void writeToArticleFile(String articleName, Collection<String> links) throws IOException {
        File file = new File(this.rootFolder, articleName);
        if ( file.exists() ) {
            file.delete();
        }

        for ( String link : links ) {
            FileUtils.writeStringToFile(file, link, "UTF-8", true);
            FileUtils.writeStringToFile(file, "\n", "UTF-8", true);
        }
    }

    boolean hasArticleAlreadyBeenParsed(String articleName) {
        return new File(this.rootFolder, articleName).exists();
    }

    Collection<String> getArticleLinks(String articleName) throws IOException {
        return FileUtils.readLines(new File(this.rootFolder, articleName), "UTF-8");
    }
}