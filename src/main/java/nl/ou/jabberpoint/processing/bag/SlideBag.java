package nl.ou.jabberpoint.processing.bag;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import nl.ou.jabberpoint.domain.SlideType;

import java.util.LinkedList;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * JabberPoint bag, used to temporarily store and access the JabberPoint file
 * in a object oriented manner
 */
public class SlideBag {
    @JacksonXmlProperty(isAttribute = true)
    private int id;
    @JacksonXmlProperty(isAttribute = true)
    private String effect;
    private String title;
    @JacksonXmlProperty(isAttribute = true)
    private SlideType type;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "item")
    private LinkedList<ItemBag> itemList;

    public SlideBag() {
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SlideType getType() {
        return type;
    }

    public void setType(SlideType type) {
        this.type = type;
    }

    public LinkedList<ItemBag> getItems() {
        return itemList;
    }

    public void setItems(LinkedList<ItemBag> items) {
        this.itemList = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean hasItemsOfKind(String kind) {
        return itemList.stream().filter(item -> item.getKind().equals(kind)).findAny().isPresent();
    }
}
