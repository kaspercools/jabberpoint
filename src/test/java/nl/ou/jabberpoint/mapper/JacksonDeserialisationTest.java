package nl.ou.jabberpoint.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import nl.ou.jabberpoint.configuration.JabberPointConfiguration;
import nl.ou.jabberpoint.processing.bag.SlideBag;
import nl.ou.jabberpoint.processing.bag.SlideShowBag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Jackson deserialisation Tests")
class JacksonDeserialisationTest {

    private BufferedReader xmlRefData = null;

    @BeforeEach
    void setup() throws FileNotFoundException, URISyntaxException {
        xmlRefData = new BufferedReader(new InputStreamReader(new FileInputStream(JabberPointConfiguration.getConfig().getCurrentPresentationAsFile())));
    }

    @Test
    void xmlSerialisedMetadataAuthorToJavaTest() throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        SlideShowBag value = xmlMapper.readValue(xmlRefData, SlideShowBag.class);

        assertEquals(value.getMetadata().getAuthor(), "Kasper Cools");
    }

    @Test
    void xmlSerialisedMetadataCreationDateToJavaTest() throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        SlideShowBag value = xmlMapper.readValue(xmlRefData, SlideShowBag.class);

        assertEquals(value.getMetadata().getCreationDate(), LocalDate.of(2019, 9, 9));
    }

    @Test
    void xmlSerialisedMetadataShowSlideNumbersToJavaTest() throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        SlideShowBag value = xmlMapper.readValue(xmlRefData, SlideShowBag.class);

        assertTrue(value.getMetadata().showSlideNumbers());
    }

    @Test
    void xmlSerialisedSlidesToJavaTest() throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        SlideShowBag value = xmlMapper.readValue(xmlRefData, SlideShowBag.class);

        assertEquals(value.getSlides().size(), 6);

        SlideBag refSlide = value.getSlides().get(1);

        assertEquals(refSlide.getItems().size(), 10);
    }
}
