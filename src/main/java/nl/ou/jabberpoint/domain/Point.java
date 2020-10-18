package nl.ou.jabberpoint.domain;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public final class Point implements PresentableElement {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double calculateHeight(GraphicsContext graphics, double scale) {
        return 1;
    }

    @Override
    public double calculateWidth(GraphicsContext graphics, double scale) {
        return 1;
    }

    @Override
    public void draw(GraphicsContext graphics, PresentableElementBounds slideShowBounds, double scale, Point drawLocation, MouseEvent mouseEvent) {
        graphics.setFont(new Font(11 * scale));
        graphics.setFill(Color.RED);
        graphics.fillText(toString(), drawLocation.getX(), drawLocation.getY() + 10);
    }

    public void moveX(double xMovement) {
        this.x += xMovement;
    }

    public void moveY(double yMovement) {
        this.y += yMovement;
    }

    public double getX() {
        return x;
    }

    public void setX(double newX) {
        this.x = newX;
    }

    public double getY() {
        return y;
    }

    public void setY(double newY) {
        this.y = newY;
    }

    @Override
    public String toString() {
        return "(" + x +
                ", " + y +
                ')';
    }
}

