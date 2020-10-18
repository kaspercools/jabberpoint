package nl.ou.jabberpoint.processing.factories;

import nl.ou.jabberpoint.processing.factories.contracts.FileParserFactory;
import nl.ou.jabberpoint.processing.parsers.JabberPointFileParser;
import nl.ou.jabberpoint.processing.parsers.JabberPointXMLFileParser;
import org.apache.tika.Tika;
import org.apache.tika.mime.MediaType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * Creates concrete file parser classes
 */
public class DefaultFileParserFactory implements FileParserFactory {
    /**
     * Creates a JabberPointFileParser dependon the file type of the given file.
     *
     * @param file the file that needs to be parsed
     * @return a concrete {@code JabberPointFileParser}
     * @throws IOException when the cannot be opened
     */
    public JabberPointFileParser createParser(File file) throws IOException {
        JabberPointFileParser jpParser = null;
        String type = new Tika().detect(file);

        if (MediaType.APPLICATION_XML.toString().equals(type)) {
            jpParser = new JabberPointXMLFileParser(new FileInputStream(file));
        }

        return jpParser;
    }
}
