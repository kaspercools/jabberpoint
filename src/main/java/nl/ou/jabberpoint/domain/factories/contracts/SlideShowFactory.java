package nl.ou.jabberpoint.domain.factories.contracts;

import nl.ou.jabberpoint.domain.SlideShow;

import java.io.IOException;
import java.net.URISyntaxException;

public interface SlideShowFactory {
    SlideShow createDefault() throws IOException, URISyntaxException;

    SlideShow createBySequenceName(String name) throws IOException, URISyntaxException;
}
