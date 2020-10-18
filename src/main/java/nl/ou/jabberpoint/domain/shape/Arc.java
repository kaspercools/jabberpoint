package nl.ou.jabberpoint.domain.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;
import nl.ou.jabberpoint.domain.Point;
import nl.ou.jabberpoint.domain.PresentableElement;
import nl.ou.jabberpoint.domain.PresentableElementBounds;
import nl.ou.jabberpoint.domain.Style;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class Arc extends Shape {

    private final double startAngle;
    private final double extent;
    private final ArcType closure;

    public Arc(double x, double y, double width, double height, double startAngle, double extent, ArcType closure, Style style, PresentableElement underlyingContent) {
        super(x, y, width, height, style, underlyingContent);
        this.startAngle = startAngle;
        this.extent = extent;
        this.closure = closure;
    }

    @Override
    public void onDraw(GraphicsContext graphics, PresentableElementBounds slideShowBounds, double scale, Point drawLocation) {
        graphics.fillArc(location.getX(), location.getY(), calculateWidth(graphics, scale), calculateHeight(graphics, scale), startAngle, extent, closure);
    }

    @Override
    public String toString() {
        return "Arc{" +
                "startangle=" + startAngle +
                ", extent=" + extent +
                ", closure=" + closure +
                ", x=" + location.getX() +
                ", y=" + location.getY() +
                ", style=" + style +
                ", effect=" + effect +
                '}';
    }
}
