package net.mymilkedeek.linkinpark;

import net.mymilkedeek.linkinpark.repository.WikipediaRepository;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author MyMilkedEek <Michael>
 */
public class Main {

    public static void main(String[] args) throws IOException {
        WikipediaRepository repository = new WikipediaRepository();

        Scanner scanner = new Scanner(System.in);
        String input = "";

        while ( !input.equalsIgnoreCase("quit")) {
            System.out.println("Which article do you want to parse? ");
            repository.addPageToRepository(scanner.nextLine());
        }
    }
}