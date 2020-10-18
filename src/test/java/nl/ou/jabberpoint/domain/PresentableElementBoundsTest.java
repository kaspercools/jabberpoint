package nl.ou.jabberpoint.domain;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PresentableElementBoundsTest {

    private PresentableElementBounds bounds;

    @BeforeEach
    void setup() {
        bounds = new PresentableElementBounds(203, 10, 200, 400);
    }

    @Test
    void isEmpty() {
        bounds = new PresentableElementBounds(200, 10, 200, 10);
        assertTrue(bounds.isEmpty());
    }

    @Test
    void contains() {
        assertTrue(bounds.contains(new Point2D(203, 10)));

        assertTrue(bounds.contains(new Point2D(240, 10)));
        assertFalse(bounds.contains(new Point2D(203, 411)));

        assertTrue(bounds.contains(new Point2D(203 + 200, 10 + 10)));
    }

    @Test
    void testContains1() {
        assertTrue(bounds.contains(203, 10));

        assertTrue(bounds.contains(240, 10));
        assertFalse(bounds.contains(203, 411));

        assertTrue(bounds.contains(203 + 200, 10 + 10));
    }

    @Test
    void testContains2() {
        assertTrue(bounds.contains(new PresentableElementBounds(204, 240, 20, 20)));
        assertFalse(bounds.contains(new PresentableElementBounds(204, 240, 200, 20)));
    }

    @Test
    void testContains3() {
        assertTrue(bounds.contains(204, 240, 20, 20));
        assertFalse(bounds.contains(204, 240, 200, 20));
    }

    @Test
    void intersects() {
        assertTrue(bounds.intersects(204, 240, 20, 20));
        assertTrue(bounds.intersects(204, 240, 200, 20));
        assertTrue(bounds.intersects(100, 240, 200, 20));
        assertFalse(bounds.intersects(0, 0, 10, 10));
    }

    @Test
    void testIntersects() {
        assertTrue(bounds.intersects(new PresentableElementBounds(204, 240, 20, 20)));
        assertTrue(bounds.intersects(new PresentableElementBounds(204, 240, 200, 20)));
        assertTrue(bounds.intersects(new PresentableElementBounds(100, 240, 200, 20)));
        assertFalse(bounds.intersects(new PresentableElementBounds(0, 0, 10, 10)));
    }
}
