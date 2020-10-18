package nl.ou.jabberpoint.domain.media;

import javafx.scene.canvas.GraphicsContext;
import nl.ou.jabberpoint.domain.*;
import nl.ou.jabberpoint.domain.media.behavior.ExecutableMedia;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class Media extends SlideItem implements PresentableElement {

    private PresentableElement visualization;
    private ExecutableMedia mediaExecution;

    public Media(PresentableElement visualizationBehavior, ExecutableMedia executionBehavior, PresentableElement underlyingContent) {
        super(underlyingContent);
        visualization = visualizationBehavior;
        mediaExecution = executionBehavior;
    }

    public Media(PresentableElement visualizationBehavior, ExecutableMedia executionBehavior, Style style, PresentableElement underlyingContent) {
        super(underlyingContent, style);
    }

    @Override
    public double calculateHeight(GraphicsContext graphics, double scale) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double calculateWidth(GraphicsContext graphics, double scale) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void onDraw(GraphicsContext graphics, PresentableElementBounds slideShowBounds, double scale, Point drawLocation) {
        visualization.draw(graphics, slideShowBounds, scale, new Point(0, 0), null);
    }

}
