package nl.ou.jabberpoint.domain;

import nl.ou.jabberpoint.domain.Slide.SlideBuilder;
import nl.ou.jabberpoint.domain.factories.DefaultSlideFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("SlideBuilder Test")
class SlideBuilderTest {
    private SlideBuilder fixture;

    @BeforeEach
    void setup() {
        fixture = new SlideBuilder(new DefaultSlideFactory());
    }

    @Test
    void buildSlideWithOneTextEntry() {
        fixture.addText("Dit is een eerste niveau");
        Slide s = fixture.build();

        assertNotNull(s);
        assertEquals(s.getNumberOfSteps(), 1);
    }

    @Test
    void buildSlideWithSlideType() {
        fixture.type(SlideType.TitleSlide);
        Slide s = fixture.build();

        assertNotNull(s);
        assertEquals(s.getType(), SlideType.TitleSlide);
        assertEquals(s.getNumberOfSteps(), 0);
    }

    @Test
    void buildSlideWithTwoSections() {
        fixture.addText("Dit is een eerste niveau").transition().addText("Dit is ook een eerste niveau");
        Slide s = fixture.build();

        assertNotNull(s);
        assertEquals(s.getNumberOfSteps(), 2);
    }
}
