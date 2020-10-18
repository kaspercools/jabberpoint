package nl.ou.jabberpoint.domain;

import javafx.scene.canvas.GraphicsContext;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class Table extends SlideItem {
    public Table(PresentableElement underlyingContent) {
        super(underlyingContent, null);
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
    public void onDraw(GraphicsContext graphicsContext, PresentableElementBounds slideShowBounds, double scale, Point drawLocation) {
        throw new UnsupportedOperationException();
    }

}
