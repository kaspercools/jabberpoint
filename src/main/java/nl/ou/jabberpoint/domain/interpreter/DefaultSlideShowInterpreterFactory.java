package nl.ou.jabberpoint.domain.interpreter;

import nl.ou.jabberpoint.domain.SlideShow;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Optional;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class DefaultSlideShowInterpreterFactory implements SlideShowInterpreterFactory {

    public SlideShowInterpreter createInterpreter(SlideShow slideShow) {
        LinkedList<SlideShowExpression> slideShowExpressionLinkedList = new LinkedList<>();
        slideShowExpressionLinkedList.add(new SlideShowExpression("$slide.nr$", () -> String.valueOf(slideShow.getSlideNr())));
        slideShowExpressionLinkedList.add(new SlideShowExpression("$slides.count$", () -> String.valueOf(slideShow.getSlides().size())));
        slideShowExpressionLinkedList.add(new SlideShowExpression("$author$", () -> String.valueOf(slideShow.getMetaData().getAuthor())));
        slideShowExpressionLinkedList.add(new SlideShowExpression("$slide.title$", () -> {
            if (Optional.ofNullable(slideShow.getCurrentSlide()).isPresent()) {
                return String.valueOf(slideShow.getCurrentSlide().getTitle());
            }
            return "";
        }));
        slideShowExpressionLinkedList.add(new SlideShowExpression("$slide.type$", () -> {
            if (Optional.ofNullable(slideShow.getCurrentSlide()).isPresent()) {
                return String.valueOf(slideShow.getCurrentSlide().getType());
            }
            return "";
        }));
        slideShowExpressionLinkedList.add(new SlideShowExpression("$today$", () -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.now().format(formatter);
        }));
        slideShowExpressionLinkedList.add(new SlideShowExpression("$creationDate$", () -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return slideShow.getMetaData().getCreationDate().format(formatter);
        }));

        return new SlideShowInterpreter(slideShowExpressionLinkedList);
    }
}
