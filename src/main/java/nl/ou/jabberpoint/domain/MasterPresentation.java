package nl.ou.jabberpoint.domain;

import java.util.List;

/**
 * @author Kasper Cools
 * - Wijziging extra feature uitbreidings opdracht
 */
public class MasterPresentation {
    private MetaData metaData;
    private Theme theme;
    private List<SlideSequence> slideSequenceList;

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public List<SlideSequence> getSlideSequence() {
        return slideSequenceList;
    }

    public void setSlideSequence(List<SlideSequence> slideSequenceList) {
        this.slideSequenceList = slideSequenceList;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return "Presentation{" +
                ", metaData=" + metaData +
                '}';
    }
}
