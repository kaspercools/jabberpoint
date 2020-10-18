package nl.ou.jabberpoint.processing.bag;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * JabberPoint bag, used to temporarily store and access the JabberPoint file
 * in a object oriented manner
 */
@JacksonXmlRootElement(localName = "presentation", namespace = "http://jabberpoint.ou.nl")
public class MasterPresentationBag {
    private MetaDataBag metadata;
    private ThemeBag theme;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "sequence")
    private List<SlideSequenceBag> slideSequenceBagList;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "slide")
    private LinkedList<SlideBag> slideList;

    public MasterPresentationBag() {
    }

    public MetaDataBag getMetadata() {
        return metadata;
    }

    public void setMetadata(MetaDataBag metadata) {
        this.metadata = metadata;
    }

    public ThemeBag getTheme() {
        return theme;
    }

    public void setTheme(ThemeBag theme) {
        this.theme = theme;
    }

    public List<SlideSequenceBag> getSlideSequence() {
        return slideSequenceBagList;
    }

    public void setSlideSequence(List<SlideSequenceBag> slideSequenceBagList) {
        this.slideSequenceBagList = slideSequenceBagList;
    }

    public LinkedList<SlideBag> getSlides() {
        if (Optional.ofNullable(slideList).isEmpty() || slideList.size() == 0) {
            throw new IllegalStateException("Je slideshow bevat nog geen slides! gelieve een andere slideshow te openen");
        }
        return slideList;
    }

    public void setSlideList(LinkedList<SlideBag> slideList) {
        this.slideList = slideList;
    }

    public SlideSequenceBag getSlideSequence(String name) {
        return this.slideSequenceBagList.stream().filter(ssb -> ssb.getName().equals(name)).findAny().get();
    }
}
