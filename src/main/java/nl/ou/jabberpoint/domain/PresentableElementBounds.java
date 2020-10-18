package nl.ou.jabberpoint.domain;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class PresentableElementBounds extends Bounds {

    /**
     * Creates a new instance of {@code SlideItemBounds} class.
     *
     * @param x      the starting position of the {@code SlideItemBounds} on the x axis
     * @param y      the starting position of the {@code SlideItemBounds} on the y axis
     * @param width  the width of the {@code SlideItemBounds}
     * @param height the height of the {@code SlideItemBounds}
     */
    public PresentableElementBounds(double x, double y, double width, double height) {
        super(x, y, 0, width, height, 0);
    }

    public PresentableElementBounds(Point startingPoint, double calculateWidth, double calculateHeight) {
        this(startingPoint.getX(), startingPoint.getY(), calculateWidth, calculateHeight);
    }

    @Override
    public boolean isEmpty() {
        return getMinX() == getWidth() && getMinY() == getHeight();
    }

    @Override
    public boolean contains(Point2D p) {
        return (p.getX() >= getMinX() && p.getX() <= getMaxX()) && (p.getY() >= getMinY() && p.getY() <= getMaxY());
    }

    @Override
    public boolean contains(Point3D p) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(double x, double y) {
        return contains(new Point2D(x, y));
    }

    @Override
    public boolean contains(double x, double y, double z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Bounds boundingBox) {
        return boundingBox.getMinX() >= getMinX() && boundingBox.getMinY() >= getMinY() && boundingBox.getMaxX() <= getMaxX() && boundingBox.getMaxY() <= getMaxY();
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {
        return contains(new PresentableElementBounds(x, y, w, h));
    }

    @Override
    public boolean contains(double x, double y, double z, double w, double h, double d) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean intersects(Bounds boundingBox) {
        return !(getMinX() > boundingBox.getMaxX() ||
                getMaxX() < boundingBox.getMinX() ||
                getMinY() > boundingBox.getMaxY() ||
                getMaxY() < boundingBox.getMinY());
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return intersects(new PresentableElementBounds(x, y, w, h));

    }

    @Override
    public boolean intersects(double x, double y, double z, double w, double h, double d) {
        throw new UnsupportedOperationException();
    }
}
