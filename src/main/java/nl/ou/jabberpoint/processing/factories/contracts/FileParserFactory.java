package nl.ou.jabberpoint.processing.factories.contracts;

import nl.ou.jabberpoint.processing.parsers.JabberPointFileParser;

import java.io.File;
import java.io.IOException;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * The {@code FileParserFactory} defines the contract used by the JabberPoint file parser factories
 */
public interface FileParserFactory {
    JabberPointFileParser createParser(File file) throws IOException;
}
