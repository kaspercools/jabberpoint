package nl.ou.jabberpoint.domain;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import nl.ou.jabberpoint.domain.builder.StyleBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class Style {
    private String name;
    private Color strokeColor;
    private Color fillColor;
    private Font font;
    private Map<Direction, Double> paddingMap;

    public Style() {
        this.font = new Font(Font.getDefault().getFamily(), 32);
        this.paddingMap = new HashMap<>();
        paddingMap.put(Direction.Top, 0D);
        paddingMap.put(Direction.Bottom, 0D);
        paddingMap.put(Direction.Left, 0D);
        paddingMap.put(Direction.Right, 0D);
    }

    public Style(Color color) {
        this();
        this.strokeColor = color;
    }

    public Style(String name, Font font, Color strokeColor, Color fillColor, Map<Direction, Double> padding) {
        this.name = name;
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
        this.font = font;
        this.paddingMap = padding;
    }

    private Style(ItemStyleBuilder builder) {
        this.paddingMap = builder.paddingMap;
        this.strokeColor = builder.color;
        this.font = builder.font;
        this.fillColor = builder.fillColor;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public String toString() {
        return "Style{" +
                "color=" + strokeColor +
                "font=" + font +
                '}';
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Map<Direction, Double> getPaddingMap() {
        return paddingMap;
    }

    public void setPaddingMap(Map<Direction, Double> paddingMap) {
        this.paddingMap = paddingMap;
    }

    public double getSpacing(Direction direction) {
        return paddingMap.get(direction);
    }

    public Font getScaledFont(double scale) {
        return new Font(font.getName(), font.getSize() * scale);
    }

    public void setPadding(Direction direction, double distance) {
        this.paddingMap.put(direction, distance);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class ItemStyleBuilder implements StyleBuilder {
        private Color color;
        private Font font;
        private Map<Direction, Double> paddingMap;
        private Color fillColor;

        public ItemStyleBuilder() {
            init();
        }

        public StyleBuilder font(Font font) {
            this.font = font;
            return this;
        }

        public StyleBuilder fillColor(Color fillColor) {
            this.fillColor = fillColor;
            return this;
        }

        public StyleBuilder color(Color color) {
            this.color = color;
            return this;
        }

        public StyleBuilder padding(Direction direction, double padding) {
            this.paddingMap.put(direction, padding);
            return this;
        }

        public StyleBuilder reset() {
            init();
            return this;
        }

        public Style build() {
            return new Style(this);
        }

        private void init() {
            this.color = Color.BLACK;
            this.font = new Font(Font.getDefault().getFamily(), 42);
            paddingMap = new HashMap<>();

            paddingMap.put(Direction.Top, 0D);
            paddingMap.put(Direction.Right, 0D);
            paddingMap.put(Direction.Bottom, 0D);
            paddingMap.put(Direction.Left, 0D);
        }
    }
}
