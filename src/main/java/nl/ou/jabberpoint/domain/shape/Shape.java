package nl.ou.jabberpoint.domain.shape;

import javafx.scene.canvas.GraphicsContext;
import nl.ou.jabberpoint.domain.Direction;
import nl.ou.jabberpoint.domain.PresentableElement;
import nl.ou.jabberpoint.domain.SlideItem;
import nl.ou.jabberpoint.domain.Style;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public abstract class Shape extends SlideItem {
    final double width;
    final double height;

    public Shape(double x, double y, double width, double height, Style style, PresentableElement underlyingContent) {
        super(x, y, underlyingContent, style);
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateHeight(GraphicsContext graphics, double scale) {
        return (height + style.getSpacing(Direction.Top) + style.getSpacing(Direction.Bottom)) * scale;
    }

    @Override
    public double calculateWidth(GraphicsContext graphics, double scale) {
        return (width + style.getSpacing(Direction.Left) + style.getSpacing(Direction.Right)) * scale;
    }
}
