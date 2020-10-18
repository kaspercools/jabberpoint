package nl.ou.jabberpoint.domain.factories;

import nl.ou.jabberpoint.configuration.JabberPointConfiguration;
import nl.ou.jabberpoint.domain.MasterPresentation;
import nl.ou.jabberpoint.domain.factories.contracts.MasterPresentationFactory;
import nl.ou.jabberpoint.processing.bag.MasterPresentationBag;
import nl.ou.jabberpoint.processing.factories.DefaultFileParserFactory;
import nl.ou.jabberpoint.processing.parsers.JabberPointFileParser;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class DefaultMasterPresentationFactory implements MasterPresentationFactory {
    private final DefaultFileParserFactory fileParserFactory;

    public DefaultMasterPresentationFactory(DefaultFileParserFactory fileParserFactory) {
        this.fileParserFactory = fileParserFactory;
    }

    public MasterPresentation createFromFile(File fileInput) throws IOException {
        JabberPointFileParser parser = fileParserFactory.createParser(fileInput);

        MasterPresentationBag masterPresentationBag = parser.parseDocument();
        if (Optional.ofNullable(masterPresentationBag.getSlides()).isEmpty() || masterPresentationBag.getSlides().size() == 0) {
            throw new IllegalStateException("Je slideshow bevat nog geen slides! gelieve een andere slideshow te openen");
        }
        if (Optional.ofNullable(masterPresentationBag.getTheme()).isEmpty()) {
            throw new IllegalStateException("Er werden geen thema's gevonden in je presentatie, voeg een thema toe en probeer opnieuw.");
        }
        if (!masterPresentationBag.getTheme().getName().equals(masterPresentationBag.getMetadata().getTheme())) {
            throw new IllegalStateException("Het opgegeven thema is niet beschikbaar in je presentatie, selecteer een ander thema of voeg het ontbrekende thema toe.");
        }
        return JabberPointConfiguration.getConfig().mapper().map(masterPresentationBag, MasterPresentation.class);
    }
}
