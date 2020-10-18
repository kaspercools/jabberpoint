package nl.ou.jabberpoint.domain.factories;

import nl.ou.jabberpoint.configuration.JabberPointConfiguration;
import nl.ou.jabberpoint.domain.MetaData;
import nl.ou.jabberpoint.domain.PresentableElement;
import nl.ou.jabberpoint.domain.Slide;
import nl.ou.jabberpoint.domain.SlideShow;
import nl.ou.jabberpoint.domain.factories.contracts.SlideShowFactory;
import nl.ou.jabberpoint.domain.interpreter.DefaultSlideShowInterpreterFactory;
import nl.ou.jabberpoint.domain.interpreter.SlideShowInterpreter;
import nl.ou.jabberpoint.domain.visualization.BackdropVisualizationBehavior;
import nl.ou.jabberpoint.domain.visualization.FooterVisualizationBehavior;
import nl.ou.jabberpoint.domain.visualization.HeaderVisualizationBehavior;
import nl.ou.jabberpoint.domain.visualization.NullVisualizationBehavior;
import nl.ou.jabberpoint.processing.bag.*;
import nl.ou.jabberpoint.processing.factories.DefaultFileParserFactory;
import nl.ou.jabberpoint.processing.parsers.JabberPointFileParser;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.IntStream;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class DefaultSlideShowFactory implements SlideShowFactory {
    private final DefaultFileParserFactory fileParserFactory;
    private final DefaultSlideFactory slideItemFactory;
    private final DefaultSlideShowInterpreterFactory slideShowInterpreterFactory;

    public DefaultSlideShowFactory(DefaultSlideShowInterpreterFactory slideShowInterpreterFactory, DefaultSlideFactory slideFactory, DefaultFileParserFactory fileParserFactory) {
        this.slideItemFactory = slideFactory;
        this.slideShowInterpreterFactory = slideShowInterpreterFactory;
        this.fileParserFactory = fileParserFactory;
    }

    public SlideShow createDefault() throws IOException, URISyntaxException {
        MasterPresentationBag masterPresentation = loadPresentation();

        SlideShowBag slideShowBag = new SlideShowBag(masterPresentation.getMetadata(), masterPresentation.getSlides());
        return this.buildSlideShowByStructure(masterPresentation.getTheme(), slideShowBag, Collections.EMPTY_MAP);
    }

    public SlideShow createBySequenceName(String name) throws IOException, URISyntaxException {
        //OU: Feature 6 extra method
        MasterPresentationBag masterPresentation = loadPresentation();
        SlideShowBag slideShowBag;
        SlideSequenceBag slideSequenceBag = masterPresentation.getSlideSequence(name);
        LinkedList<SlideBag> slideBagLinkedList;
        LinkedList<SequenceSlideDetailBag> slideBags;
        Map<Integer, List<Integer>> slideStepSkipListMap;

        if (Optional.ofNullable(slideSequenceBag).isEmpty()) {
            throw new NoSuchElementException("De geselecteerde inhoud kan niet gevonden worden");
        }

        if (Optional.ofNullable(masterPresentation.getSlides()).isEmpty() || masterPresentation.getSlides().size() == 0) {
            throw new IllegalStateException("Je presentatie bevat nog geen slides! gelieve een andere slideshow te openen");
        }

        slideBags = slideSequenceBag.getSlides();
        slideBagLinkedList = buildSlideList(slideBags, masterPresentation.getSlides());

        slideStepSkipListMap = createSlideSkipMapForSequence(slideBags);

        slideShowBag = new SlideShowBag(masterPresentation.getMetadata(), slideBagLinkedList);

        return this.buildSlideShowByStructure(masterPresentation.getTheme(), slideShowBag, slideStepSkipListMap);
    }

    private Map<Integer, List<Integer>> createSlideSkipMapForSequence(LinkedList<SequenceSlideDetailBag> slideBags) {
        //OU: Feature 6 extra method
        Map<Integer, List<Integer>> slideStepSkipListMap = new HashMap<>();

        for (int i = 0; i < slideBags.size(); i++) {
            slideStepSkipListMap.put(i, slideBags.get(i).getSkippedSlideSteps());
        }

        return slideStepSkipListMap;
    }

    private LinkedList<SlideBag> buildSlideList(LinkedList<SequenceSlideDetailBag> slideBags, LinkedList<SlideBag> allSlidesList) {
        //OU: Feature 6 extra method
        if (Optional.ofNullable(slideBags).isEmpty()) {
            throw new NoSuchElementException("De sequence bevat nog geen slides! gelieve een andere inhoud te sekecteren");
        }
        LinkedList<SlideBag> slideLinkedListslideBagLinkedList = new LinkedList<>();
        IntStream.range(0, slideBags.size()).forEach(indx -> {
            slideLinkedListslideBagLinkedList.add(allSlidesList.stream().filter(generalSlide -> generalSlide.getId() == slideBags.get(indx).getId()).findFirst().get());
        });
        return slideLinkedListslideBagLinkedList;
    }

    /**
     * Builds the new {@code SlideShow} instance based on the currently selected presentation file
     *
     * @param theme            the theme information defined in the JabberPoint file
     * @param slideShowBag     the parsed slideshow structure information from the JabberPointFile
     * @param slideStepSkipMap a map of references to the slide steps the need to be skipped
     * @return a {@code SlideShow} class
     */
    private SlideShow buildSlideShowByStructure(ThemeBag theme, SlideShowBag slideShowBag, Map<Integer, List<Integer>> slideStepSkipMap) throws URISyntaxException {
        MetaData metaData = JabberPointConfiguration.getConfig().mapper().map(slideShowBag.getMetadata(), MetaData.class);
        LinkedList<Slide> slideList;
        Slide.SlideBuilder slideBuilder = new Slide.SlideBuilder(slideItemFactory);

        slideList = slideItemFactory.buildSlides(slideShowBag.getSlides(), slideStepSkipMap, theme, metaData, slideBuilder);

        SlideShow slideShow = new SlideShow(slideList);

        slideShow.setMetaData(metaData);
        // find the first text element on the first slide
        String slideShowTitle = getSlideShowTitle(slideShowBag.getSlides().get(0), slideStepSkipMap);

        slideShow.setTitle(slideShowTitle);
        PresentableElement footerVisualisation = createFooter(slideShowBag.getMetadata().isShowFooter(), theme.getFooter(), slideShowInterpreterFactory.createInterpreter(slideShow));
        PresentableElement headerVisualisation = createHeader(slideShowBag.getMetadata().isShowHeader(), theme.getHeader(), slideShowInterpreterFactory.createInterpreter(slideShow));
        PresentableElement backdropVisualisation = createBackdrop(theme.getBackdrop());

        slideShow.setFooter(footerVisualisation);
        slideShow.setHeader(headerVisualisation);
        slideShow.setBackdrop(backdropVisualisation);

        return slideShow;
    }

    private String getSlideShowTitle(SlideBag slideBag, Map<Integer, List<Integer>> slideStepSKipMap) {
        List<Integer> slideStepSKipList = slideStepSKipMap.get(0);
        List<ItemBag> slideItemBagList = slideBag.getItems();
        if (Optional.ofNullable(slideStepSKipList).isEmpty() && slideBag.hasItemsOfKind("text")) {
            return slideBag.getItems().stream().filter(slideItem -> slideItem.getKind().equals("text")).findFirst().get().getValue();
        }

        for (int i = 0; i < slideItemBagList.size(); i++) {

            ItemBag item = slideItemBagList.get(i);

            if (Optional.ofNullable(item.getValue()).isPresent() && (Optional.ofNullable(slideStepSKipList).isPresent() && !slideStepSKipList.contains(i + 1))) {
                return item.getValue();
            }
        }

        return "Naamloze slideshow (er werd geen titel gevonden)";
    }

    private PresentableElement createBackdrop(String backdropImagePath) throws URISyntaxException {
        PresentableElement backdrop = new NullVisualizationBehavior();
        if (Optional.ofNullable(backdrop).isPresent()) {
            File imageFile = JabberPointConfiguration.getConfig().getImageAsFile(backdropImagePath);
            if (imageFile.exists()) {
                backdrop = new BackdropVisualizationBehavior(imageFile);
            }
        }
        return backdrop;
    }

    private PresentableElement createHeader(boolean showHeader, String header, SlideShowInterpreter interpreter) {
        PresentableElement footer = new NullVisualizationBehavior();

        // add visualization behavior
        if (showHeader) {
            footer = new HeaderVisualizationBehavior(header, interpreter);
        }

        return footer;
    }

    private PresentableElement createFooter(boolean showFooter, String footerText, SlideShowInterpreter interpreter) {
        //defaults to nothing
        PresentableElement footer = new NullVisualizationBehavior();

        // add visualization behavior
        if (showFooter) {
            footer = new FooterVisualizationBehavior(footerText, interpreter);
        }

        return footer;
    }

    private MasterPresentationBag loadPresentation() throws IOException, URISyntaxException {
        JabberPointFileParser parser = fileParserFactory.createParser(JabberPointConfiguration.getConfig().getCurrentPresentationAsFile());
        return parser.parseDocument();
    }
}
