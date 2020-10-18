package nl.ou.jabberpoint.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import nl.ou.jabberpoint.configuration.JabberPointConfiguration;
import nl.ou.jabberpoint.domain.MasterPresentation;
import nl.ou.jabberpoint.domain.MetaData;
import nl.ou.jabberpoint.domain.Slide;
import nl.ou.jabberpoint.domain.SlideShow;
import nl.ou.jabberpoint.domain.factories.DefaultMasterPresentationFactory;
import nl.ou.jabberpoint.ui.JabberPointNotification;
import nl.ou.jabberpoint.ui.controllers.contracts.KeyController;
import nl.ou.jabberpoint.ui.controllers.contracts.SlideController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * The {@code JabberPointPresentationController} is the heart of the UI package. This is where the main user interactions happen
 * The controller is responsible for propagating the slideshow commands to the {@code SlideShowController} instance and has
 * a reference to its own mouse and key controlller instances.
 */
public class JabberPointPresentationController implements SlideController, Initializable {
    private final DefaultMasterPresentationFactory masterPresentationFactory;
    @FXML
    private SlideShowController slideShowController;
    private MainKeyController keyController;
    private MainMenuController menuController;
    private MasterPresentation currentMasterPresentation;
    @FXML
    private MenuItem openFileMenuItem;
    @FXML
    private MenuItem aboutMenuItem;
    @FXML
    private MenuItem nextSlideMenuItem;
    @FXML
    private MenuItem nextSlideStepMenuItem;
    @FXML
    private MenuItem previousSlideMenuItem;
    @FXML
    private MenuItem previousSlideStepMenuItem;
    @FXML
    private MenuItem goToSpecificSlideMenuItem;
    @FXML
    private MenuItem selectSequenceMenuItem;
    @FXML
    private MenuItem aboutThisPresentationMenuItem;
    @FXML
    private MenuItem exitApplicationMenuItem;

    JabberPointPresentationController(DefaultMasterPresentationFactory masterPresentationFactory) {
        this.masterPresentationFactory = masterPresentationFactory;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindMenuItems();

        processPresentationFile();
        menuController.openSequenceSelectionDialog(new ActionEvent());
    }

    /**
     * Sets the new presentation file and delegates the initialisation of the new {@code SlideShow} class to the
     * {@code SlideShowController}
     *
     * @param masterPresentation the new MasterPresentation object
     */
    void loadPresentation(MasterPresentation masterPresentation) {
        currentMasterPresentation = masterPresentation;
        JabberPointConfiguration config = JabberPointConfiguration.getConfig();
        String previousSequence = config.getSelectedSequence();
        SlideShow previousSlideshow = slideShowController.getSlideShow();

        try {
            slideShowController.initSlideShow();
            slideShowController.startPresentation();
            previousSlideshow = null;
        } catch (IllegalStateException e) {
            JabberPointNotification.noSlidesInSlideShow(e.getMessage());
        } catch (NoSuchElementException e) {
            JabberPointNotification.noSlidesInSequence(e.getMessage());
        } finally {
            if (Optional.ofNullable(previousSlideshow).isPresent()) {
                config.setSelectedSequence(previousSequence);
                slideShowController.setSlideShow(previousSlideshow);
            }
        }
    }

    MasterPresentation getPresentation() {
        return this.currentMasterPresentation;
    }

    /**
     * Delegates the navigation request to the slieShowController instance
     *
     * @param slideNr the requested slide nr
     */
    public void navigateToSlide(int slideNr) {
        slideShowController.navigateToSlide(slideNr);
    }

    /**
     * Delegates the navigation request to the slieShowController instance
     */
    @Override
    public void previousSlideStep() {
        slideShowController.previousSlideStep();
    }

    /**
     * Delegates the navigation request to the slieShowController instance
     */
    @Override
    public void nextSlideStep() {
        slideShowController.nextSlideStep();
    }

    /**
     * Delegates the navigation request to the slieShowController instance
     */
    @Override
    public void previousSlide() {
        slideShowController.previousSlide();
    }

    /**
     * Delegates the navigation request to the slieShowController instance
     */
    @Override
    public void nextSlide() {
        slideShowController.nextSlide();
    }

    String getSlideShowtitle() {
        return slideShowController.getSlideShow().getTitle();
    }

    MetaData getMetaData() {
        return currentMasterPresentation.getMetaData();
    }

    List<Slide> getSlideShowSlides() {
        return slideShowController.getSlideShow().getSlides();
    }

    void setMenuController(MainMenuController menuController) {
        this.menuController = menuController;
    }

    /**
     * Configures the actions for our menu items by passing in the accompanying methods defined in the menuController
     */
    private void bindMenuItems() {
        nextSlideMenuItem.setOnAction(menuController::nextSlide);
        previousSlideMenuItem.setOnAction(menuController::previousSlide);
        nextSlideStepMenuItem.setOnAction(menuController::nextSlideStep);
        previousSlideStepMenuItem.setOnAction(menuController::previousSlideStep);
        openFileMenuItem.setOnAction(menuController::openFileDialog);
        aboutMenuItem.setOnAction(menuController::openAboutDialog);
        exitApplicationMenuItem.setOnAction(menuController::exitApplication);
        selectSequenceMenuItem.setOnAction(menuController::openSequenceSelectionDialog);
        goToSpecificSlideMenuItem.setOnAction(menuController::openSpecificSlideDialog);
        aboutThisPresentationMenuItem.setOnAction(menuController::openMetaDataDialog);
    }

    /**
     * Prcesses the given presentation file, stored in the {@code JabberPointConfiguration} class and creates a MasterPresentation object from the given source
     *
     * @return the {@code MasterPresentation} instance
     */
    MasterPresentation processPresentationFile() {
        JabberPointConfiguration config = JabberPointConfiguration.getConfig();
        try {
            currentMasterPresentation = masterPresentationFactory.createFromFile(config.getCurrentPresentationAsFile());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            JabberPointNotification.errorLoadingPresentationFile();
        }

        return currentMasterPresentation;
    }

    public KeyController getKeyController() {
        return this.keyController;
    }

    public void setKeyController(MainKeyController keyController) {
        this.keyController = keyController;
    }

}
