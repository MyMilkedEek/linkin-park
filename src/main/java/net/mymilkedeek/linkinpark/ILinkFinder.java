package net.mymilkedeek.linkinpark;

import java.io.IOException;

/**
 * @author MyMilkedEek <Michael>
 */
public interface ILinkFinder {

    public String[] findLinks(String start, String goal) throws IOException;
}