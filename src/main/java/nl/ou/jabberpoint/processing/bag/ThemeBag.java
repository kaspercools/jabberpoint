package nl.ou.jabberpoint.processing.bag;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;
import java.util.Optional;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * JabberPoint bag, used to temporarily store and access the JabberPoint file
 * in a object oriented manner
 */
public class ThemeBag {

    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "style")
    private List<StyleBag> styleList;

    private String footer;

    private String header;

    private String backdrop;

    public ThemeBag() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public List<StyleBag> getStyles() {
        return styleList;
    }

    public void setStyles(List<StyleBag> styles) {
        this.styleList = styles;
    }

    public Optional<StyleBag> getStyleByName(String styleName) {

        return styleList.stream().filter(s -> s.getName().equals(styleName)).findAny();

    }

    public Optional<StyleBag> getStyleByLevel(int level) {
        Optional<StyleBag> styleResult = styleList.stream().filter((s -> s.getLevel() == level)).findAny();

        return styleResult;
    }
}
