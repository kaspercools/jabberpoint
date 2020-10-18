package nl.ou.jabberpoint.domain.factories;

import javafx.scene.shape.ArcType;
import nl.ou.jabberpoint.configuration.JabberPointConfiguration;
import nl.ou.jabberpoint.domain.*;
import nl.ou.jabberpoint.domain.factories.contracts.SlideItemFactory;
import nl.ou.jabberpoint.domain.shape.Arc;
import nl.ou.jabberpoint.domain.shape.Line;
import nl.ou.jabberpoint.domain.shape.Oval;
import nl.ou.jabberpoint.domain.shape.Rectangle;
import nl.ou.jabberpoint.processing.bag.*;

import java.io.File;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class DefaultSlideFactory implements SlideItemFactory {

    public DefaultSlideFactory() {

    }

    public SlideItem createArc(Point location, double width, double height, double startAngle, double extent, ArcType closure, Style style, SlideItem currentSlideItem) {
        return new Arc(location.getX(), location.getY(), width, height, startAngle, extent, closure, style, currentSlideItem);
    }

    public SlideItem createImage(String imagePath, SlideItem currentSlideItem) throws URISyntaxException {
        File imageFile = JabberPointConfiguration.getConfig().getImageAsFile(imagePath);
        if (!imageFile.exists()) {
            throw new IllegalStateException("Image does not exist!");
        }
        return new ImageItem(imageFile, currentSlideItem);
    }

    public SlideItem createLine(Point from, Point to, Style style, SlideItem currentSlideItem) {
        return new Line(from.getX(), from.getX(), to.getX(), to.getY(), style, currentSlideItem);
    }

    public SlideItem createOval(Point location, double width, double height, Style style, SlideItem currentSlideItem) {

        return new Oval(location.getX(), location.getY(), width, height, style, currentSlideItem);
    }

    public SlideItem createRectangle(Point location, double width, double height, Style style, SlideItem currentSlideItem) {
        return new Rectangle(location.getX(), location.getY(), width, height, style, currentSlideItem);
    }

    public SlideItem createTextItem(String text, SlideItem currentSlideItem) {
        return new TextItem(text, currentSlideItem);
    }

    /**
     * @param theme             the related theme
     * @param metaData          the metadata for this presentation
     * @param slideBagItemsList the collection of slides that should be included within the presentation
     * @param slideBuilder
     * @return
     */
    private Slide buildSlide(ThemeBag theme, MetaData metaData, List<ItemBag> slideBagItemsList, Slide.SlideBuilder slideBuilder) throws URISyntaxException {

        for (int i = 0; i < slideBagItemsList.size(); i++) {
            ItemBag item = slideBagItemsList.get(i);

            if (Optional.ofNullable(item.getValue()).isPresent()) {
                buildSlideItem(item, theme, slideBuilder);
            }
            if (!metaData.isSkipTransitions()) {
                slideBuilder.transition();
            }
        }

        return slideBuilder.build();
    }

    /**
     * Builds a slide item based on the data found in the item bag
     *
     * @param item         the structural representation of the item
     * @param theme        the related theme
     * @param slideBuilder the {@code slideBuilder} used to build the current set of slides
     */
    private void buildSlideItem(ItemBag item, ThemeBag theme, Slide.SlideBuilder slideBuilder) throws URISyntaxException {
        Optional<StyleBag> currentItemStyleBag;

        // This switch statement will be populated with extra types, for now we only provided the ability
        // to add images and text types
        switch (item.getKind()) {
            case "image":
                slideBuilder.addImage(item.getValue());
                break;
            case "text":
                if (!slideBuilder.hasTitle()) {
                    slideBuilder.title(item.getValue());
                }
            default:
                slideBuilder.addText(item.getValue());
                break;
        }

        slideBuilder.level(item.getLevel());

        currentItemStyleBag = theme.getStyleByName(item.getStyle());
        Style currentStyle;
        // if no style applied to the item itself, get the theme style
        if (currentItemStyleBag.isEmpty()) {
            //check if the item has a level definition, get the related theme style
            currentItemStyleBag = theme.getStyleByLevel(item.getLevel());
            //if we still don't have any style definition for the current item, create a default style
            if (currentItemStyleBag.isPresent()) {
                currentStyle = buildStyle(currentItemStyleBag.get());
            } else currentStyle = new Style.ItemStyleBuilder().build();
        } else {
            currentStyle = buildStyle(currentItemStyleBag.get());
        }
        slideBuilder.style(currentStyle);
    }

    private Style buildStyle(StyleBag currentStyleBag) {
        Style.ItemStyleBuilder styleBuilder = new Style.ItemStyleBuilder();
        styleBuilder.color(currentStyleBag.getColor())
                .font(currentStyleBag.getFxFont())
                .fillColor(currentStyleBag.getBackgroundColor());

        for (PaddingBag p : currentStyleBag.getPaddingBagList()) {
            styleBuilder.padding(p.getDirection(), p.getValue());
        }

        return styleBuilder.build();
    }

    LinkedList<Slide> buildSlides(List<SlideBag> slideBagList, Map<Integer, List<Integer>> slideStepSKipList, ThemeBag theme, MetaData metaData, Slide.SlideBuilder slideBuilder) throws URISyntaxException {
        LinkedList<Slide> slideList = new LinkedList<>();
        for (int i = 0; i < slideBagList.size(); i++) {
            SlideBag slideBag = slideBagList.get(i);
            //check if the type is defined, if not we do not know what to do with this item and need to inform
            //the user. This is a blocking operation
            if (Optional.ofNullable(slideBag.getType()).isEmpty()) {
                throw new IllegalStateException("Slide type kan niet worden gevonden");
            }
            List<Integer> currentSlideSkipList = slideStepSKipList.get(i);
            List<ItemBag> currentSlideBagItemList = filterSlideBagsBySkipList(slideBag.getItems(), currentSlideSkipList);
            //build single slide, pass SlideBuilder so that the individual steps can easily be added

            slideBuilder.id(slideBag.getId());
            slideBuilder.type(slideBag.getType());
            slideList.add(buildSlide(theme, metaData, currentSlideBagItemList, slideBuilder));

            slideBuilder.reset();
        }
        return slideList;
    }

    private List<ItemBag> filterSlideBagsBySkipList(LinkedList<ItemBag> itemList, List<Integer> skippedSlideItemsList) {
        List<ItemBag> itemBagResultList = new LinkedList<>();
        for (int i = 0; i < itemList.size(); i++) {
            ItemBag item = itemList.get(i);

            if (Optional.ofNullable(item.getValue()).isEmpty() || (Optional.ofNullable(skippedSlideItemsList).isPresent() && skippedSlideItemsList.contains(i + 1))) {
                continue;
            }

            itemBagResultList.add(item);
        }
        return itemBagResultList;
    }
}
