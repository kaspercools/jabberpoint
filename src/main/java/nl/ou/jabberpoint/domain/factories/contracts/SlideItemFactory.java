package nl.ou.jabberpoint.domain.factories.contracts;

import javafx.scene.shape.ArcType;
import nl.ou.jabberpoint.domain.Point;
import nl.ou.jabberpoint.domain.SlideItem;
import nl.ou.jabberpoint.domain.Style;

import java.net.URISyntaxException;

public interface SlideItemFactory {
    SlideItem createArc(Point location, double width, double height, double startAngle, double extent, ArcType closure, Style style, SlideItem currentSlideItem);

    SlideItem createImage(String imagePath, SlideItem currentSlideItem) throws URISyntaxException;

    SlideItem createLine(Point from, Point to, Style style, SlideItem currentSlideItem);

    SlideItem createOval(Point location, double width, double height, Style style, SlideItem currentSlideItem);

    SlideItem createRectangle(Point location, double width, double height, Style style, SlideItem currentSlideItem);

    SlideItem createTextItem(String text, SlideItem currentSlideItem);
}
