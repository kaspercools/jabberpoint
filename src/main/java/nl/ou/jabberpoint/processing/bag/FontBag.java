package nl.ou.jabberpoint.processing.bag;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import javafx.scene.text.Font;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * JabberPoint bag, used to temporarily store and access the JabberPoint file
 * in a object oriented manner
 */
public class FontBag {
    @JacksonXmlProperty(localName = "fontFace")
    private String fontFace;
    @JacksonXmlProperty(localName = "fontSize")
    private double fontSize;

    public FontBag() {
        this.fontFace = Font.getDefault().getFamily();
        this.fontSize = Font.getDefault().getSize();
    }

    public String getFontFace() {
        return fontFace;
    }

    public void setFontFace(String fontFace) {
        this.fontFace = fontFace;
    }

    public double getFontSize() {
        return fontSize;
    }

    public void setFontSize(double fontSize) {
        this.fontSize = fontSize;
    }
}
