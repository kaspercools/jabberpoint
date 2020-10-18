package nl.ou.jabberpoint.processing.parsers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import nl.ou.jabberpoint.processing.bag.MasterPresentationBag;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class JabberPointXMLFileParser implements JabberPointFileParser {
    private final BufferedReader xmlRefData;

    public JabberPointXMLFileParser(FileInputStream f) {
        xmlRefData = new BufferedReader(new InputStreamReader(f));
    }

    /**
     * Parses the given document and returns a {@code MasterPresentationBag} that represents the contents of the XML
     * file
     *
     * @return an instance of the {@link MasterPresentationBag}
     * @throws IOException when document cannot be parsed to a {@link MasterPresentationBag}
     */
    @Override
    public MasterPresentationBag parseDocument() throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        MasterPresentationBag masterPresentationBag;
        masterPresentationBag = xmlMapper.readValue(xmlRefData, MasterPresentationBag.class);
        return masterPresentationBag;
    }
}
