package nl.ou.jabberpoint.domain;

import nl.ou.jabberpoint.domain.factories.DefaultSlideFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class SlideShowTest {

    private ArrayList<Slide> lstSlideItems;
    private SlideShow slideShow;
    private DefaultSlideFactory slideItemFactory;

    @BeforeEach
    void setUp() {
        slideItemFactory = new DefaultSlideFactory();
        lstSlideItems = new ArrayList<Slide>();

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
    void next() {
        PresentableElement slide = slideShow.nextStep();
        assertNotNull(slide);
        assertTrue(slide instanceof Slide);
    }

    @Test
    void previous() {
        slideShow.nextSlide();
        slideShow.nextSlide();
        int slideNr = slideShow.getSlideNr();
        int currentStepRef = slideShow.getCurrentSlide().getSlideStepNr();
        slideShow.nextStep();
        assertNotEquals(currentStepRef, slideShow.getCurrentSlide().getSlideStepNr());
        slideShow.previousStep();
        assertEquals(currentStepRef, slideShow.getCurrentSlide().getSlideStepNr());
        assertEquals(slideNr, slideShow.getSlideNr());
        slideShow.previousStep();
        assertEquals(currentStepRef + 1, slideShow.getCurrentSlide().getSlideStepNr());

        PresentableElement slide = slideShow.previousStep();
        slideShow.nextSlide();
        //test go to previous slide via step iterator
        slideShow.previousStep();
        assertEquals(currentStepRef + 1, slideShow.getCurrentSlide().getSlideStepNr());

        assertNotNull(slide);
        assertTrue(slide instanceof SlideItem);
        assertEquals(currentStepRef, slideShow.getSlideNr());
    }

    @Test
    void nextSlide() {
        PresentableElement slide = slideShow.nextSlide();
        assertNotNull(slide);
        assertTrue(slide instanceof Slide);
    }

    @Test
    void goToSpecificSlide() {
        slideShow.goToSlide(2);
        assertEquals(slideShow.getSlideNr(), 2);


        slideShow.goToSlide(5);
        assertEquals(slideShow.getSlideNr(), 5);
    }

    @Test
    void previousSlide() {
        slideShow.nextSlide();
        int slideNr = slideShow.getSlideNr();
        slideShow.nextSlide();
        PresentableElement slide = slideShow.previousSlide();

        assertEquals(slideShow.getSlideNr(), slideNr);
        assertNotNull(slide);
        assertTrue(slide instanceof Slide);
    }
}
