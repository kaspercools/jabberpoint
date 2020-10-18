package nl.ou.jabberpoint.domain;

import nl.ou.jabberpoint.domain.factories.DefaultSlideFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
class SlideTest {

    Slide.SlideBuilder builder;
    private Slide slide;

    @BeforeEach
    void setup() {
        builder = new Slide.SlideBuilder(new DefaultSlideFactory());
        builder.type(SlideType.OrdinarySlide)
                .addText("text 1")
                .transition()
                .addText("text 2");

        slide = builder.build();
    }

    @Test
    void next() {
        PresentableElement slideItemStep = slide.nextStep();
        assertNotNull(slideItemStep);
        assertTrue(slideItemStep instanceof SlideItem);
    }

    @Test
    void previousStartingFromSlideOne() {
        slide.nextStep();
        slide.nextStep();
        PresentableElement slideItemStep = slide.previousStep();
        assertNotNull(slideItemStep);
        assertTrue(slideItemStep instanceof SlideItem);
    }

    @Test
    void previousThrowsNoSuchElementTestAtBeginningOfSequence() {
        assertThrows(NoSuchElementException.class, () -> slide.previousStep());
    }

    @Test
    void getNumberOfSteps() {
        assertEquals(slide.getNumberOfSteps(), 2);
    }
}
