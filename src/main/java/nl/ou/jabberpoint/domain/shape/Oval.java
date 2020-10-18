package nl.ou.jabberpoint.domain.shape;

import javafx.scene.canvas.GraphicsContext;
import nl.ou.jabberpoint.domain.Point;
import nl.ou.jabberpoint.domain.PresentableElement;
import nl.ou.jabberpoint.domain.PresentableElementBounds;
import nl.ou.jabberpoint.domain.Style;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class Oval extends Shape {

    public Oval(double x, double y, double width, double height, Style style, PresentableElement underlyingContent) {
        super(x, y, width, height, style, underlyingContent);
    }

    @Override
    public void onDraw(GraphicsContext graphics, PresentableElementBounds slideShowBounds, double scale, Point drawLocation) {
        graphics.fillOval(location.getX(), location.getY(), calculateWidth(graphics, scale), calculateHeight(graphics, scale));
    }

    @Override
    public String toString() {
        return "Oval{" +
                ", x=" + location.getX() +
                ", y=" + location.getY() +
                ", style=" + style +
                ", effect=" + effect +
                '}';
    }
}
