package nl.ou.jabberpoint.domain.shape;

import javafx.scene.canvas.GraphicsContext;
import nl.ou.jabberpoint.domain.Point;
import nl.ou.jabberpoint.domain.PresentableElement;
import nl.ou.jabberpoint.domain.PresentableElementBounds;
import nl.ou.jabberpoint.domain.Style;
import org.controlsfx.control.Notifications;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class Rectangle extends Shape {

    public Rectangle(double x, double y, double width, double height, Style style, PresentableElement underlyingContent) {
        super(x, y, width, height, style, underlyingContent);
    }

    @Override
    public void onDraw(GraphicsContext graphics, PresentableElementBounds slideShowBounds, double scale, Point drawLocation) {
        graphics.fillRect(getActualX(drawLocation, scale), getActualY(drawLocation, scale), width * scale, height * scale);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                ", x=" + location.getX() +
                ", y=" + location.getY() +
                ", style=" + style +
                ", effect=" + effect +
                '}';
    }
}
