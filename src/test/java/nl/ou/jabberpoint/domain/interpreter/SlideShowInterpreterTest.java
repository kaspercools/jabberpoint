package nl.ou.jabberpoint.domain.interpreter;

import nl.ou.jabberpoint.domain.Slide;
import nl.ou.jabberpoint.domain.SlideShow;
import nl.ou.jabberpoint.domain.SlideType;
import nl.ou.jabberpoint.domain.factories.DefaultSlideFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Kasper Cools (coolskasper@gmail.com
 */
class SlideShowInterpreterTest {

    private DefaultSlideFactory slideItemFactory;
    private SlideShow slideShow;

    @BeforeEach
    void setup() {
        slideItemFactory = new DefaultSlideFactory();
        ArrayList<Slide> lstSlideItems = new ArrayList<Slide>();

        Slide.SlideBuilder builder = new Slide.SlideBuilder(new DefaultSlideFactory());
        Collection<Slide> slides = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                builder.type((j % 2 == 0) ? SlideType.TitleSlide : SlideType.OrdinarySlide)
                        .addText("test " + j + " text " + i)
                        .transition()
                        .addText("test " + j + " text " + i);

                lstSlideItems.add(builder.build());
                builder.reset();
            }
        }

        LinkedList<Slide> linkedList = new LinkedList<>();
        linkedList.addAll(lstSlideItems);
        slideShow = new SlideShow(linkedList);
    }

    @Test
    void interpretSlideNumber() {
        LinkedList<SlideShowExpression> expressionList = new LinkedList<>();
        expressionList.add(new SlideShowExpression("$slidenr$", () -> String.valueOf(12)));


        SlideShowInterpreter interpreter = initInterpreter(expressionList);

        String demoText = "Lorem ipsum $slidenr$ dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
        String demoTextResult = "Lorem ipsum 12 dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

        String actualResult = interpreter.interpret(demoText);

        assertEquals(actualResult, demoTextResult);

    }

    private SlideShowInterpreter initInterpreter(LinkedList<SlideShowExpression> expressionList) {
        return new SlideShowInterpreter(expressionList);
    }
}
