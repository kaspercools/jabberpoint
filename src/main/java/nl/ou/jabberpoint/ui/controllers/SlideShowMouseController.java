package nl.ou.jabberpoint.ui.controllers;

import javafx.scene.input.MouseEvent;
import nl.ou.jabberpoint.domain.SlideShow;
import nl.ou.jabberpoint.ui.JabberPointNotification;
import nl.ou.jabberpoint.ui.controllers.contracts.MouseController;
import nl.ou.jabberpoint.ui.controls.PresentationCanvas;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * The {@code SlideShowMouseController} handles all mouse actions that need to be handled by a SlideShow
 * If a mouse click is caught the {@code MouseController} checks whether or not an actions should be performed on/delegated to
 * the linked {@code slideShow} object
 */
public class SlideShowMouseController implements MouseController {
    private PresentationCanvas presentationCanvas;
    private SlideShow slideShow;

    SlideShowMouseController() {

    }

    void setSlideShow(SlideShow slideShow) {
        this.slideShow = slideShow;
    }

    /**
     * Handles a mouse click event
     *
     * @param mouseEvent the MouseEvent that needs to be processed
     */
    public void mouseClicked(MouseEvent mouseEvent) {
        if (Optional.ofNullable(slideShow).isEmpty()) {
            return;
        }
        switch (mouseEvent.getButton()) {
            case PRIMARY:
                try {
                    if (!presentationCanvas.isBusy()) {
                        slideShow.nextStep();
                    }
                } catch (NoSuchElementException e) {
                    JabberPointNotification.lastSlideNotification();
                }
                break;
            case SECONDARY:
                try {
                    if (!presentationCanvas.isBusy()) {
                        slideShow.previousStep();
                    }
                } catch (NoSuchElementException e) {
                    JabberPointNotification.firstSlideNotification();
                }
                break;
            default:
                break;
        }
    }

    void setPresentationCanvas(PresentationCanvas presentationCanvas) {
        this.presentationCanvas = presentationCanvas;
    }
}
