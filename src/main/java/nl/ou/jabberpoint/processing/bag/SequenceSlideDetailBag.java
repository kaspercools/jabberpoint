package nl.ou.jabberpoint.processing.bag;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * JabberPoint bag, used to temporarily store and access the JabberPoint file
 * in a object oriented manner
 */
public class SequenceSlideDetailBag {
    @JacksonXmlProperty(localName = "id")
    private int id;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "skip")
    private List<Integer> skipList;

    public SequenceSlideDetailBag() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List getSkippedSlideSteps() {
        return skipList;
    }

    public List<Integer> getSkipList() {
        return skipList;
    }

    public void setSkipList(List<Integer> skipList) {
        this.skipList = skipList;
    }
}
