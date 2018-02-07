package net.mymilkedeek.linkinpark;

import net.mymilkedeek.linkinpark.repository.WikipediaRepository;

import java.io.IOException;

/**
 * @author MyMilkedEek <Michael>
 */
public class Main {

    public static void main(String[] args) throws IOException {
        WikipediaRepository repository = new WikipediaRepository();
        repository.addPageToRepository("OpenPDF");
    }
}