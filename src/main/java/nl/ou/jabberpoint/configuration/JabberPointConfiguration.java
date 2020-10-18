package nl.ou.jabberpoint.configuration;

import javafx.scene.text.Font;
import nl.ou.jabberpoint.configuration.annotations.ConfigField;
import nl.ou.jabberpoint.configuration.annotations.Configuration;
import nl.ou.jabberpoint.configuration.exceptions.ConfigurationParseException;
import nl.ou.jabberpoint.domain.*;
import nl.ou.jabberpoint.processing.bag.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * JabberPointConfiguration is a singleton implementation which is used througout the application. It provides access
 * to appliction setting, found in the app.properties file. The accompanying file that populates this configuration
 * class is {the @link ConfigurationParser} class
 */
@Configuration
public class JabberPointConfiguration {
    private static JabberPointConfiguration instance;

    @ConfigField("folders.assets")
    private String assetsFolder;
    @ConfigField("folders.assets.images")
    private String imageFolder;
    @ConfigField("folders.assets.data")
    private String dataFolder;
    @ConfigField("defaultPresentation")
    private String defaultPresentationPath;
    private ModelMapper modelMapper;
    private String currentPresentationPath;
    private String sequenceName;

    private JabberPointConfiguration() {
        initMapper();
    }

    /**
     * Returns a reference to the JabberPointConfiguration instance
     *
     * @return a JabberPointConfiguration instance
     */
    public static JabberPointConfiguration getConfig() {
        if (Optional.ofNullable(instance).isEmpty()) {
            ConfigurationParser<JabberPointConfiguration> cs = new ConfigurationPropertiesParser<>();
            try {
                instance = cs.parse(new JabberPointConfiguration());
            } catch (ConfigurationParseException e) {
                throw new UnsupportedOperationException();
            }
        }
        return instance;
    }

    /**
     * Returns the currently selected presentation file
     *
     * @return a {@link java.io.File} object
     */
    public File getCurrentPresentationAsFile() throws URISyntaxException {
        File resultFile;
        if (Optional.ofNullable(currentPresentationPath).isEmpty()) {
            resultFile = getDefaultPresentationAsFile();
        } else {
            resultFile = new File(currentPresentationPath);
        }
        return resultFile;
    }

    /**
     * Sets the current presentation path
     *
     * @param presentationPath the path to the new presentation file
     */
    public void setPresentationPath(String presentationPath) {
        if (Optional.ofNullable(currentPresentationPath).isPresent() && currentPresentationPath.equals(presentationPath)) {
            throw new IllegalStateException("Deze presentatie werd al ingeladen.");
        }

        currentPresentationPath = presentationPath;
    }

    /**
     * Returns a preconfigured JabberPoint model mapper
     *
     * @return model mapper instance
     */
    public ModelMapper mapper() {
        return modelMapper;
    }

    /**
     * Returns an object reference to the image filepath
     *
     * @param imagePath the path to a local or absolute image
     * @return a {@link File}
     */
    private File getResourceImageAsFile(String imagePath) throws URISyntaxException {
        return getAsFile(Objects.requireNonNull(getClass().getClassLoader().getResource(String.format("%s/%s/%s", assetsFolder, imageFolder, imagePath))));
    }

    /**
     * The return value is either a concrete name, referencing a certain sequence,
     * or null when no sequence was selected.
     *
     * @return the currently selected sequence name
     */
    public String getSelectedSequence() {
        return sequenceName;
    }

    /**
     * Sets the currently selected slide sequence
     *
     * @param newSelectedSequence the name of the sequence
     */
    public void setSelectedSequence(String newSelectedSequence) {
        sequenceName = newSelectedSequence;
    }

    /**
     * returns the given url path as a {@link File} object
     *
     * @param filePathUrl the file path as a {@link URL}
     * @return a {@link File}
     */
    private File getAsFile(URL filePathUrl) throws URISyntaxException {

        return Paths.get(filePathUrl.toURI()).toFile();
    }

    /**
     * @return {@link File}
     */
    private File getDefaultPresentationAsFile() throws URISyntaxException {
        return getAsFile(Objects.requireNonNull(getClass().getClassLoader().getResource(String.format("%s/%s/%s", assetsFolder, dataFolder, defaultPresentationPath))));
    }

    /**
     * Initializes the JabberPoint model mapper instance
     * It maps the processing bag objects, loaded from the JabberPoint file structure, to concrete domain objects
     */
    private void initMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true);

        Converter<List<StyleBag>, Map<String, Style>> styleListToMapConverter = context -> {
            Map<String, Style> styleMap = new HashMap<>();
            if (Optional.ofNullable(context.getSource()).isPresent()) {
                for (StyleBag b : context.getSource()) {
                    styleMap.put(b.getName(), modelMapper.map(b, Style.class));
                }
            }
            return styleMap;
        };

        Converter<FontBag, Font> fontConverter = context -> {
            Font resultingFont = null;
            if (Optional.ofNullable(context.getSource()).isPresent()) {
                resultingFont = new Font(context.getSource().getFontFace(), context.getSource().getFontSize());
            }
            return resultingFont;
        };

        Converter<List<PaddingBag>, Map<Direction, Double>> paddingListToMapConverter = context -> {
            Map<Direction, Double> paddingMap = new HashMap<>();
            if (Optional.ofNullable(context.getSource()).isPresent()) {
                for (PaddingBag p : context.getSource()) {
                    paddingMap.put(p.getDirection(), p.getValue());
                }
            }
            return paddingMap;
        };

        PropertyMap<MetaDataBag, MetaData> metaDataMap = new PropertyMap<>() {
            protected void configure() {
                map().setAuthor(source.getAuthor());
                map().setCreationDate(source.getCreationDate());
                map().setSkipTransitions(source.isSkipTransitions());
                map().setShowSlideNumber(source.showSlideNumbers());
                map().setThemeName(source.getTheme());
            }
        };

        PropertyMap<StyleBag, Style> styleMap = new PropertyMap<>() {
            protected void configure() {
                map().setName(source.getName());
                map().setStrokeColor(source.getColor());
                map().setFillColor(source.getBackgroundColor());
                using(fontConverter).map(source.getFont(), destination.getFont());
                using(paddingListToMapConverter).map(source.getPaddingBagList(), destination.getPaddingMap());
            }
        };

        PropertyMap<ThemeBag, Theme> themeMap = new PropertyMap<>() {
            protected void configure() {
                map().setName(source.getName());
                // open issue https://github.com/modelmapper/modelmapper/issues/414
                using(styleListToMapConverter).map(source.getStyles(), destination.getStyleMap());
            }
        };

        PropertyMap<MasterPresentationBag, MasterPresentation> presentationMap = new PropertyMap<>() {
            protected void configure() {
                map(source.getMetadata(), destination.getMetaData());
                map(source.getTheme(), destination.getTheme());
                map(source.getSlideSequence(), destination.getSlideSequence());
            }
        };


        modelMapper.addConverter(fontConverter);
        modelMapper.addConverter(paddingListToMapConverter);
        modelMapper.addConverter(styleListToMapConverter);
        modelMapper.addMappings(metaDataMap);
        modelMapper.addMappings(presentationMap);
        modelMapper.addMappings(styleMap);
        modelMapper.addMappings(themeMap);
        modelMapper.validate();
    }

    /**
     * Checks whether the given file path is absolute or relative.
     * If it is relative, a resource image file path is built using the getResourceImageAsFile method
     *
     * @param imagePath the path to the image
     * @return {@link File}
     */
    public File getImageAsFile(String imagePath) throws URISyntaxException {
        File imageFile = new File(imagePath);
        if (!imageFile.isAbsolute()) {

            imageFile = getResourceImageAsFile(imagePath);
        }

        return imageFile;
    }
}
