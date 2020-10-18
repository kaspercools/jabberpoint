package nl.ou.jabberpoint.domain;

import javafx.scene.canvas.GraphicsContext;

import java.util.LinkedList;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class ItemList extends SlideItem {
    public LinkedList<SlideItem> slideItemList;
    private ListType type;

    public ItemList(PresentableElement underlyingContent) {
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
    public void onDraw(GraphicsContext graphics, PresentableElementBounds slideShowBounds, double scale, Point drawLocation) {
        throw new UnsupportedOperationException();
    }

}
