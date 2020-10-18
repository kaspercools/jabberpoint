package nl.ou.jabberpoint.processing.bag;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.LinkedList;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * JabberPoint bag, used to temporarily store and access the JabberPoint file
 * in a object oriented manner
 */
public class SlideSequenceBag {
    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "slide")
    private LinkedList<SequenceSlideDetailBag> sequenceSlideList;

    public SlideSequenceBag() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<SequenceSlideDetailBag> getSlides() {
        return sequenceSlideList;
    }

    public void setSlides(LinkedList<SequenceSlideDetailBag> slideSequence) {
        this.sequenceSlideList = slideSequence;
    }
}
