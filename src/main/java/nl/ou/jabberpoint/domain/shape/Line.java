package nl.ou.jabberpoint.domain.shape;

import javafx.scene.canvas.GraphicsContext;
import nl.ou.jabberpoint.domain.Point;
import nl.ou.jabberpoint.domain.PresentableElement;
import nl.ou.jabberpoint.domain.PresentableElementBounds;
import nl.ou.jabberpoint.domain.Style;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class Line extends Shape {

    private final Point destinationPoint;

    public Line(double fromX, double fromY, double toX, double toY, Style style) {
        super(fromX, fromY, 1, 1, style, null);
        this.destinationPoint = new Point(toX, toY);
    }

    public Line(double fromX, double fromY, double toX, double toY, Style style, PresentableElement underlyingContent) {
        super(fromX, fromY, 1, 1, style, underlyingContent);

        this.destinationPoint = new Point(toX, toY);
    }

    @Override
    public double calculateHeight(GraphicsContext graphics, double scale) {
        return 0;
    }

    @Override
    public double calculateWidth(GraphicsContext graphics, double scale) {
        return 0;
    }

    @Override
    public void onDraw(GraphicsContext graphics, PresentableElementBounds slideShowBounds, double scale, Point drawLocation) {
        graphics.strokeLine(location.getX(), location.getY(), destinationPoint.getX(), destinationPoint.getY());
    }
}
