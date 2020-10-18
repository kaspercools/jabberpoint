package nl.ou.jabberpoint.processing.parsers;

import nl.ou.jabberpoint.processing.bag.MasterPresentationBag;

import java.io.IOException;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public interface JabberPointFileParser {
    MasterPresentationBag parseDocument() throws IOException;
}
