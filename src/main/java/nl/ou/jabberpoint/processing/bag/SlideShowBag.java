package nl.ou.jabberpoint.processing.bag;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * JabberPoint bag, used to temporarily store and access the JabberPoint file
 * in a object oriented manner
 */
public class SlideShowBag {
    private MetaDataBag metadata;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "slide")
    private List<SlideBag> slideList;

    public SlideShowBag() {
    }

    public SlideShowBag(MetaDataBag metadata, List<SlideBag> slideList) {
        this.metadata = metadata;
        this.slideList = slideList;
    }

    public MetaDataBag getMetadata() {
        return metadata;
    }

    public void setMetadata(MetaDataBag metadata) {
        this.metadata = metadata;
    }

    public List<SlideBag> getSlides() {
        return slideList;
    }

    public void setSlides(List<SlideBag> slides) {
        this.slideList = slides;
    }
}
