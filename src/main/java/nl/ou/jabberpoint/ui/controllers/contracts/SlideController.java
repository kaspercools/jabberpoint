package nl.ou.jabberpoint.ui.controllers.contracts;

/**
 * The {@code KeyController} defines the methods needed when a domain object supports {@code Slide}
 * navigation
 */
public interface SlideController {
    void navigateToSlide(int slideNr);

    void previousSlideStep();

    void nextSlideStep();

    void previousSlide();

    void nextSlide();
}
