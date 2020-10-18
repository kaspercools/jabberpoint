package nl.ou.jabberpoint.domain;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * The {@code TextItem} class is used to draw a piece of text on a canvas
 */
public class TextItem extends SlideItem {
    private String text;

    public TextItem() {
    }

    public TextItem(String textContent) {
        super(new Style());
        this.text = textContent;
    }

    public TextItem(String text, Point drawLocation) {
        this(drawLocation, text, null);
    }

    public TextItem(String textContent, SlideItem currentSlideItem) {
        super(currentSlideItem, new Style());
        this.text = textContent;
    }

    public TextItem(Point point, String text, SlideItem currentSlideItem) {
        this(text, currentSlideItem);
        this.location = point;
    }

    public TextItem(Point point) {
        this.location = point;
    }

    public static TextItem copy(TextItem slideShowFooterElement) throws CloneNotSupportedException {
        return (TextItem) slideShowFooterElement.clone();
    }

    @Override
    public double calculateHeight(GraphicsContext graphics, double scale) {
        Bounds b = computeTextWidth(scale);
        double height = b.getHeight();
        return (height < 0) ? 40 * scale : height;
    }

    @Override
    public double calculateWidth(GraphicsContext graphics, double scale) {
        Bounds b = computeTextWidth(scale);

        return b.getWidth();
    }

    @Override
    public void onDraw(GraphicsContext graphicsContext, PresentableElementBounds slideShowBounds, double scale, Point drawLocation) {

        graphicsContext.fillText(text, getActualX(drawLocation, scale), getActualY(drawLocation, scale));
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Text{" +
                "text='" + text + '\'' +
                ", style=" + style +
                ", effect=" + effect +
                '}';
    }

    private Bounds computeTextWidth(double scale) {

        Text helper = new Text();
        helper.setFont(style.getScaledFont(scale));
        helper.setText(text);
        // getting the text's real preferred width.
        helper.setWrappingWidth(0);
        helper.setLineSpacing(0);

        double height = helper.getBoundsInLocal().getHeight() + style.getSpacing(Direction.Top) + style.getSpacing(Direction.Bottom);
        double width = helper.getBoundsInLocal().getWidth() + style.getSpacing(Direction.Left) + style.getSpacing(Direction.Right);

        return new PresentableElementBounds(new Point(0, 0), width, height);
    }
}
