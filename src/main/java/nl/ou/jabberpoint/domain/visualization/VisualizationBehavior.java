package nl.ou.jabberpoint.domain.visualization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import nl.ou.jabberpoint.domain.Point;
import nl.ou.jabberpoint.domain.PresentableElement;
import nl.ou.jabberpoint.domain.PresentableElementBounds;
import nl.ou.jabberpoint.domain.SlideItem;

import java.util.Optional;

/**
 * @author Kasper Cools (coolskasper@gmail.com
 */
public abstract class VisualizationBehavior implements PresentableElement {
    protected final SlideItem visualization;
    private Point drawLocation;

    VisualizationBehavior(SlideItem visualization) {
        this.visualization = visualization;
    }

    @Override
    public double calculateHeight(GraphicsContext graphics, double scale) {
        return visualization.getBounds(graphics, scale, drawLocation).getWidth() * scale;
    }

    @Override
    public double calculateWidth(GraphicsContext graphics, double scale) {
        return visualization.getBounds(graphics, scale, drawLocation).getWidth() * scale;
    }

    @Override
    public void draw(GraphicsContext graphics, PresentableElementBounds slideShowBounds, double scale, Point drawLocation, MouseEvent mouseEvent) {
        this.drawLocation = drawLocation;
        if (Optional.ofNullable(visualization).isPresent()) {
            visualization.draw(graphics, slideShowBounds, scale, drawLocation, mouseEvent);
        }
    }
}
