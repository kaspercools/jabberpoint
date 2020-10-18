package nl.ou.jabberpoint.domain;

import java.util.HashMap;

/**
 * @author Kasper Cools (coolskasper@gmail.com
 * Theme is a data carrier, it is used to retrieve style information when building a {@link SlideShow}.
 */
public class Theme {
    private String name;
    private HashMap<String, Style> styleMap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Style> getStyleMap() {
        return styleMap;
    }

    public void setStyleMap(HashMap<String, Style> styleMap) {
        this.styleMap = styleMap;
    }

    public Style getStyleByName(String name) {
        return styleMap.get(name);
    }

}
