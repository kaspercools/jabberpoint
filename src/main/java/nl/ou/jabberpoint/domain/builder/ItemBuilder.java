package nl.ou.jabberpoint.domain.builder;

import javafx.scene.shape.ArcType;
import nl.ou.jabberpoint.domain.Point;
import nl.ou.jabberpoint.domain.SlideType;
import nl.ou.jabberpoint.domain.Style;

import java.net.URISyntaxException;

public interface ItemBuilder {
    ItemBuilder addArc(double x, double y, double width, double height, double startAngle, double extent, ArcType closure, Style style);

    ItemBuilder type(SlideType type);

    ItemBuilder transition();

    ItemBuilder title(String slideTitle);

    ItemBuilder style(Style style);

    void reset();

    ItemBuilder position(Point location);

    void level(int level);

    ItemBuilder addText(String text);

    ItemBuilder id(int id);

    ItemBuilder addSquare(double x, double y, double width, Style style);

    ItemBuilder addRectangle(double x, double y, double width, double height, Style style);

    ItemBuilder addOval(double x, double y, double width, double height, Style style);

    ItemBuilder addLine(double fromX, double fromY, double toX, double toY, Style style);

    ItemBuilder addImage(String imagePath) throws URISyntaxException;

    ItemBuilder addCircle(double x, double y, double radius, Style color);
}