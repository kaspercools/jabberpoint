package nl.ou.jabberpoint.processing.bag;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * JabberPoint bag, used to temporarily store and access the JabberPoint file
 * in a object oriented manner
 */
public class ItemBag {
    @JacksonXmlProperty(isAttribute = true)
    private String kind;

    @JacksonXmlProperty(isAttribute = true)
    private String effect;

    @JacksonXmlProperty(isAttribute = true)
    private String style;

    @JacksonXmlProperty(isAttribute = true)
    private double x;

    @JacksonXmlProperty(isAttribute = true)
    private double y;

    @JacksonXmlProperty(isAttribute = true)
    private int level;

    @JacksonXmlText
    private String value;

    public ItemBag() {
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "ItemBag{" +
                "kind='" + kind + '\'' +
                ", effect='" + effect + '\'' +
                ", style='" + style + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", value='" + value + '\'' +
                '}';
    }
}
