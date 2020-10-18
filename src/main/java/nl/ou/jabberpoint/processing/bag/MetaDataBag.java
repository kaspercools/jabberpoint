package nl.ou.jabberpoint.processing.bag;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * JabberPoint bag, used to temporarily store and access the JabberPoint file
 * in a object oriented manner
 */
@JacksonXmlRootElement(localName = "metadata")
public class MetaDataBag {
    private String author;
    @JacksonXmlProperty(localName = "creation-date")
    private String creationDate;
    @JacksonXmlProperty(localName = "show-footer")
    private boolean showFooter;
    @JacksonXmlProperty(localName = "show-header")
    private boolean showHeader;
    @JacksonXmlProperty(localName = "theme")
    private String theme;
    @JacksonXmlProperty(localName = "skip-transitions")
    private boolean skipTransitions;

    public MetaDataBag() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getCreationDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE;

        return LocalDate.parse(creationDate, dateFormatter);
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isShowHeader() {
        return showHeader;
    }

    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
    }

    public boolean showSlideNumbers() {
        return this.showFooter;
    }

    public boolean isShowFooter() {
        return showFooter;
    }

    public void setShowFooter(boolean slideNumbers) {
        this.showFooter = slideNumbers;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public boolean isSkipTransitions() {
        return skipTransitions;
    }

    public void setSkipTransitions(boolean skipTransitions) {
        this.skipTransitions = skipTransitions;
    }
}
