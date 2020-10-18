package nl.ou.jabberpoint.domain.visualization;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import nl.ou.jabberpoint.domain.ImageItem;
import nl.ou.jabberpoint.domain.Point;
import nl.ou.jabberpoint.domain.PresentableElementBounds;

import java.io.File;

/**
 * @author Kasper Cools (coolskasper@gmail.com
 */
public class BackdropVisualizationBehavior extends VisualizationBehavior {
    public BackdropVisualizationBehavior(File imageFile) {
        super(new ImageItem(imageFile));
    }

    @Override
    public void draw(GraphicsContext graphics, PresentableElementBounds slideShowBounds, double scale, Point drawLocation, MouseEvent mouseEvent) {
        ImageItem image = (ImageItem) visualization;
        image.setPreferredSize(slideShowBounds.getWidth(), slideShowBounds.getHeight());
        visualization.setPosition(new Point(0, 0));
        super.draw(graphics, slideShowBounds, 1, drawLocation, mouseEvent);
    }
}
