package nl.ou.jabberpoint.domain;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * The {@code PresentableElement} interface is the abstraction used for all drawable content items
 */
public interface PresentableElement {
    double calculateHeight(GraphicsContext graphics, double scale);

    double calculateWidth(GraphicsContext graphics, double scale);

    void draw(GraphicsContext graphics, PresentableElementBounds slideShowBounds, double scale, Point drawLocation, MouseEvent mouseEvent);
}
