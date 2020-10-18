package nl.ou.jabberpoint.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import nl.ou.jabberpoint.configuration.JabberPointConfiguration;
import nl.ou.jabberpoint.domain.SlideShow;
import nl.ou.jabberpoint.domain.factories.DefaultSlideShowFactory;
import nl.ou.jabberpoint.ui.JabberPointNotification;
import nl.ou.jabberpoint.ui.controllers.contracts.KeyController;
import nl.ou.jabberpoint.ui.controllers.contracts.MouseController;
import nl.ou.jabberpoint.ui.controllers.contracts.SlideController;
import nl.ou.jabberpoint.ui.controls.PresentationCanvas;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * Provides a means of navigating through a {@code SlideShow}
 */
public class SlideShowController implements SlideController, Initializable {
    private final SlideShowMouseController mouseController;
    private final SlideShowKeyController keyController;
    private final DefaultSlideShowFactory slideShowFactory;
    private final Stage primaryStage;
    @FXML
    private PresentationCanvas presentationCanvas;
    private SlideShow slideShow;

    public SlideShowController(Stage primaryStage, SlideShowKeyController keyController, SlideShowMouseController mouseController, DefaultSlideShowFactory slideShowFactory) {
        this.keyController = keyController;
        this.mouseController = mouseController;
        this.slideShowFactory = slideShowFactory;
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mouseController.setPresentationCanvas(presentationCanvas);
    }

    /**
     * Draws the first slide  item om the {@code PresentationCanvas}
     */
    void startPresentation() {
        try {
            this.slideShow.goToSlide(1);
        } catch (IllegalStateException e) {
            JabberPointNotification.slideDoesNotExist(1);
        }

        presentationCanvas.draw(this.slideShow);
    }

    /**
     * Initializes a new {@code SlideShow}, configures the needed listeners and starts
     */
    void initSlideShow() {
        try {
            if (Optional.ofNullable(JabberPointConfiguration.getConfig().getSelectedSequence()).isPresent()) {
                this.slideShow = slideShowFactory.createBySequenceName(JabberPointConfiguration.getConfig().getSelectedSequence());
            } else {
                this.slideShow = slideShowFactory.createDefault();
            }

            keyController.setSlideShow(slideShow);
            mouseController.setSlideShow(slideShow);
            primaryStage.setTitle(slideShow.getTitle());

            this.slideShow.addSlideShowChangedListener(evt -> presentationCanvas.draw(slideShow));

        } catch (IOException | URISyntaxException e) {
            JabberPointNotification.errorLoadingPresentationFile();
        }

    }

    SlideShow getSlideShow() {
        return slideShow;
    }

    void setSlideShow(SlideShow slideShow) {
        this.slideShow = slideShow;
        presentationCanvas.draw(slideShow);
    }

    /**
     * asks the current {@code SlideShow} to navigate to a specific slide
     *
     * @param slideNr the requested slide nr
     */
    public void navigateToSlide(int slideNr) {
        slideShow.goToSlide(slideNr);
    }

    /**
     * asks the current {@code SlideShow} to navigate one step back
     */
    public void previousSlideStep() {
        slideShow.previousStep();
    }

    /**
     * asks the current {@code SlideShow} to navigate one step forward
     */
    public void nextSlideStep() {
        slideShow.nextStep();
    }

    /**
     * asks the current {@code SlideShow} to navigate to the previous slide
     */
    public void previousSlide() {
        slideShow.previousSlide();
    }

    /**
     * asks the current {@code SlideShow} to navigate to the next slide
     */
    public void nextSlide() {
        slideShow.nextSlide();
    }

    public KeyController getKeyController() {
        return this.keyController;
    }

    public MouseController getMouseController() {
        return this.mouseController;
    }
}
