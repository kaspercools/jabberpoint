package nl.ou.jabberpoint.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class MetaData {
    private String author;
    private boolean showSlideNumber;
    private LocalDate creationDate;
    private String themeName;
    private boolean skipTransitions;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isShowFooter() {
        return showSlideNumber;
    }

    public void setShowSlideNumber(boolean showSlideNumber) {
        this.showSlideNumber = showSlideNumber;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("Uw presentatie werd aangemaakt :"));
        builder.append("\n");
        builder.append(String.format("\t- op: %s", getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        builder.append("\n");
        builder.append(String.format("\t- door: %s", getAuthor()));
        builder.append("\n");
        builder.append(String.format("Gebruikt thema: %s", themeName));

        return builder.toString();
    }

    public boolean isSkipTransitions() {
        return this.skipTransitions;
    }

    public void setSkipTransitions(boolean skipTransitions) {
        this.skipTransitions = skipTransitions;
    }
}
