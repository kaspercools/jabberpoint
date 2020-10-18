package nl.ou.jabberpoint.processing.bag;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * JabberPoint bag, used to temporarily store and access the JabberPoint file
 * in a object oriented manner
 */
public class StyleBag {
    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlProperty(localName = "level")
    private int level;

    private String color;

    private String backgroundColor;

    private FontBag font;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "spacing")
    private List<PaddingBag> paddingBagList;

    public StyleBag() {
        paddingBagList = new ArrayList<>();
        font = new FontBag();
        color = backgroundColor = "#000000";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return Color.web(color);
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Color getBackgroundColor() {
        return Color.web(backgroundColor);
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public FontBag getFont() {
        return font;
    }

    public void setFont(FontBag font) {
        this.font = font;
    }

    public List<PaddingBag> getPaddingBagList() {
        return paddingBagList;
    }

    public void setPaddingBagList(List<PaddingBag> paddingBagList) {
        this.paddingBagList = paddingBagList;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Font getFxFont() {
        return new Font(this.getFont().getFontFace(), this.getFont().getFontSize());
    }
}


