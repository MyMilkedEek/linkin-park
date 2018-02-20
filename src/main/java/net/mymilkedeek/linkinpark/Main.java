package net.mymilkedeek.linkinpark;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.mymilkedeek.linkinpark.finders.DepthFirstLinkFinder;
import net.mymilkedeek.linkinpark.repository.WikipediaRepository;

/**
 * @author MyMilkedEek <Michael>
 */
public class Main {

    public static void main(String[] args) throws IOException {

        List<String[]> tuples = new ArrayList<String[]>();
        Scanner sc = new Scanner("data.txt");
        while(sc.hasNextLine())
        {
            tuples.add(sc.nextLine().split("\t"));
        }
        sc.close();

        WikipediaRepository repository = new WikipediaRepository(true);
        ILinkFinder linkFinder = new DepthFirstLinkFinder(repository);

        for ( String[] strings : tuples ) {
            String start = strings[0];
            String goal = strings[1];

            List<String> links = linkFinder.findLinks(start, goal);

            if ( links.size() > 0) {
                System.out.println("Links found!");
            } else {
                System.out.println("Uhoh");
            }

            for ( String link : links ) {
                System.out.println(link);
            }
        }

        repository.persistRepository();
    }
}