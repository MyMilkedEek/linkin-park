package net.mymilkedeek.linkinpark;

import java.io.IOException;
import java.util.List;

/**
 * @author MyMilkedEek <Michael>
 */
public interface ILinkFinder {

    public List<String> findLinks(String start, String goal) throws IOException;
}