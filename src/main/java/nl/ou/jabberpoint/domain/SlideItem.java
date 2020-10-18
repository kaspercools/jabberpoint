package nl.ou.jabberpoint.domain;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Optional;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * {@code SlideItem} is the abstract class definition used in the Composite, Decorator, Template Method and Chain of Responsibility
 * pattern that is used in JabberPoint. It coordinates the drawing of elements in relation to Decorator. It contains
 * template code and provides hooks that can be used by the concrete instances to add their concrete implementions without interferring
 * with the base functionality / responsibility of a {@code SlideItem}.
 */
public abstract class SlideItem implements PresentableElement {
    protected Point location;
    protected Style style;
    protected Effect effect;
    private PresentableElement underlyingContent;
    private int level;

    protected SlideItem() {
        location = null;
    }

    SlideItem(Style style) {
        this.style = style;
    }

    protected SlideItem(PresentableElement underlyingContent) {
        this.underlyingContent = underlyingContent;
        style = new Style();
    }

    protected SlideItem(PresentableElement underlyingContent, Style style) {
        this(underlyingContent);
        this.style = style;
    }

    protected SlideItem(double x, double y, PresentableElement underlyingContent, Style style) {
        this(underlyingContent, style);
    }

    protected SlideItem(double x, double y, Style style) {
        this.location = new Point(x, y);
        this.style = style;
    }

    public abstract double calculateHeight(GraphicsContext graphics, double scale);

    public abstract double calculateWidth(GraphicsContext graphics, double scale);

    public void draw(GraphicsContext graphics, PresentableElementBounds slideShowBounds, double scale, Point drawLocation, MouseEvent mouseEvent) {

        if (Optional.ofNullable(underlyingContent).isPresent()) {
            underlyingContent.draw(graphics, slideShowBounds, scale, drawLocation, mouseEvent);
        }

        if (Optional.ofNullable(style).isPresent()) {
            initContext(graphics, scale);
        }

        onDraw(graphics, slideShowBounds, scale, drawLocation);
        reset(graphics);

        if (Optional.ofNullable(location).isEmpty()) {
            //moves the Y position down os the next element is drawn in the right location
            drawLocation.moveY(calculateHeight(graphics, scale) + style.getSpacing(Direction.Bottom) * scale);
        }
    }
    /**
     * Hook method used by concrete classes
     */
    protected abstract void onDraw(GraphicsContext graphics, PresentableElementBounds slideShowBounds, double scale, Point drawLocation);

    public Bounds getBounds(GraphicsContext graphics, double scale, Point drawLocation) {
        Point currentLocation = drawLocation;
        if (Optional.ofNullable(getLocation()).isPresent()) {
            currentLocation = getLocation();
        }

        double width = calculateWidth(graphics, scale);
        double height = calculateHeight(graphics, scale);

        return new PresentableElementBounds(new Point(getActualX(currentLocation, scale), getActualY(currentLocation, scale) - (height * scale)), width, height);
    }

    private Point getLocation() {
        return location;
    }


    public void setStyle(Style style) {
        this.style = style;
    }

    public void setPosition(Point point) {
        this.location = point;
    }

    @Override
    public String toString() {
        return "Content{" +
                ", style=" + style +
                ", effect=" + effect +
                '}';
    }

    protected double getActualX(Point drawLocation, double scale) {
        Point actualPoint;
        if (Optional.ofNullable(location).isPresent())
            actualPoint = location;
        else
            actualPoint = drawLocation;

        return actualPoint.getX() + style.getSpacing(Direction.Left) * scale;
    }

    protected double getActualY(Point drawLocation, double scale) {
        Point actualPoint;
        if (Optional.ofNullable(location).isPresent())
            actualPoint = location;
        else
            actualPoint = drawLocation;

        return actualPoint.getY() + style.getSpacing(Direction.Top) * scale;
    }

    private void reset(GraphicsContext graphics) {
        graphics.setFill(Color.BLACK);
        graphics.setStroke(Color.BLACK);
    }

    /**
     * Inits the current graphics context, the {@code SlideItem} reads its {@link Style} information and initializes
     * the context based on those values
     *
     * @param graphics the related graphics context
     * @param scale    the scale that needs to be applied in relation to the original font size
     */
    private void initContext(GraphicsContext graphics, double scale) {
        graphics.setFill(style.getStrokeColor());
        graphics.setStroke(style.getStrokeColor());
        graphics.setFont(style.getScaledFont(scale));
    }

    public int getLevel() {
        return level;
    }

    void setLevel(int level) {
        this.level = level;
    }
}
