package nl.ou.jabberpoint.domain.factories.contracts;

import nl.ou.jabberpoint.domain.MasterPresentation;

import java.io.File;
import java.io.IOException;

public interface MasterPresentationFactory {
    MasterPresentation createFromFile(File fileInput) throws IOException;
}
