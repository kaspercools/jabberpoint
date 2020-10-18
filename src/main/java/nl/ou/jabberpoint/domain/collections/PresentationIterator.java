package nl.ou.jabberpoint.domain.collections;

import nl.ou.jabberpoint.domain.PresentableElement;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * The {@code PresentationIterator} interface defines what extra functionality a slideshow iterator should
 * incorporate. The {@code PresentationIterator} has been defined <b>public</b> so that custom {@code SlideShow} iterators can implement it
 */
public interface PresentationIterator extends ListIterator<PresentableElement> {
    /***
     * Returns the initial drawable slide state of the next {@code Slide}
     * @throws NoSuchElementException when there is no next slide
     * @return an object of type @{code Drawable}
     */
    PresentableElement nextSlide() throws NoSuchElementException;

    /***
     * Returns the initial drawable slide state of the next {@code Slide}
     * @throws NoSuchElementException when there is no previous slide
     * @return an object of type @{code Drawable}
     */
    PresentableElement previousSlide() throws NoSuchElementException;
}
