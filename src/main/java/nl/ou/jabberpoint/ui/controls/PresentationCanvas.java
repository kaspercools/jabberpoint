package nl.ou.jabberpoint.ui.controls;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import nl.ou.jabberpoint.domain.Point;
import nl.ou.jabberpoint.domain.PresentableElement;
import nl.ou.jabberpoint.domain.PresentableElementBounds;

import java.util.Optional;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class PresentationCanvas extends Pane {
    private final static int WIDTH = 1200;
    private final static int HEIGHT = 800;
    private final Canvas canvas;
    private final GraphicsContext graphics;
    private PresentableElement currentContent;
    private MouseEvent currentMouseEvent;

    /**
     * Basic constructor for {@code PresentationCanvas} initializes the canvas, and adds listeners to width and heightproperties
     * Everytime one of these (the width or the height property) changes, the canvas redraws the current {@code PresentableElement}
     */
    public PresentationCanvas() {
        canvas = new Canvas(getWidth(), getHeight());
        getChildren().add(canvas);
        graphics = canvas.getGraphicsContext2D();

        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());

        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }

    /**
     * Clears the canvas and draws the current {@code PresentableElement} onto the canvas.
     */
    private void draw() {
        graphics.clearRect(0, 0, getWidth(), getHeight());
        double scale = getScale();
        if (Optional.ofNullable(currentContent).isPresent()) {
            currentContent.draw(graphics, getSlideShowBounds(), scale, new Point(20 * scale, 50 * scale), currentMouseEvent);
        }
    }

    /**
     * Creates bounds based on the current canvas width and height properties
     *
     * @return the {@code PresentationCanvas} Bounds
     */
    private PresentableElementBounds getSlideShowBounds() {
        return new PresentableElementBounds(0, 0, this.widthProperty().getValue(), this.heightProperty().getValue());
    }

    /**
     * @param newDrawableContent The new {@code PresentableElement} to be drawn onto the {@code PresentationCanvas}
     * @throws IllegalStateException when the {@code PresentableElement} is null
     */
    public void draw(PresentableElement newDrawableContent) throws IllegalStateException {
        if (Optional.ofNullable(currentMouseEvent).isEmpty()) {
            this.currentContent = newDrawableContent;

            this.draw();
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * Calculates the scale based on the width and height property of the {@code PresentationCanvas}
     *
     * @return
     */
    private double getScale() {
        return Math.min(this.widthProperty().getValue() / ((double) WIDTH), this.heightProperty().getValue() / ((double) HEIGHT));
    }

    /**
     * Determines whether or not the current canvas is resizable.
     * The {@code PresentationCanvas} is always resizable.
     *
     * @return always true
     */
    @Override
    public boolean isResizable() {
        return true;
    }

    /**
     * Defines whether the {@code PresentationCanvas} is busy or not.
     *
     * @return true if a mouse event is being processed, false if the default draw cycle is activated
     */
    public boolean isBusy() {
        return Optional.ofNullable(currentMouseEvent).isPresent();
    }
}
