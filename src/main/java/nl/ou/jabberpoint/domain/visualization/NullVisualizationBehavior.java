package nl.ou.jabberpoint.domain.visualization;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import nl.ou.jabberpoint.domain.Point;
import nl.ou.jabberpoint.domain.PresentableElement;
import nl.ou.jabberpoint.domain.PresentableElementBounds;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class NullVisualizationBehavior implements PresentableElement {
    @Override
    public double calculateHeight(GraphicsContext graphics, double scale) {
        return 0;
    }

    @Override
    public double calculateWidth(GraphicsContext graphics, double scale) {
        return 0;
    }

    @Override
    public void draw(GraphicsContext graphics, PresentableElementBounds slideShowBounds, double scale, Point drawLocation, MouseEvent mouseEvent) {
        throw new UnsupportedOperationException();
    }

}
