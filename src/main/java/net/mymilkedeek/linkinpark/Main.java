package net.mymilkedeek.linkinpark;

import net.mymilkedeek.linkinpark.finders.DepthFirstLinkFinder;
import net.mymilkedeek.linkinpark.repository.WikipediaRepository;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * @author MyMilkedEek <Michael>
 */
public class Main {

    public static void main(String[] args) throws IOException {
        WikipediaRepository repository = new WikipediaRepository();
        ILinkFinder linkFinder = new DepthFirstLinkFinder(repository);

        Scanner scanner = new Scanner(System.in);
        String input = "";

        while ( !input.equalsIgnoreCase("quit")) {
            System.out.println("What do you want to do? ");
            input = scanner.nextLine();

            if ( input.startsWith("add ")) {
                repository.addPageToRepository(input.replaceAll("add ", ""));
            } else if ( input.startsWith("find ")) {
                String goal = input.replace("find ", "");
                System.out.println("What's the starting location?");
                String start = scanner.nextLine();

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
        }

        repository.persistRepository();
    }
}