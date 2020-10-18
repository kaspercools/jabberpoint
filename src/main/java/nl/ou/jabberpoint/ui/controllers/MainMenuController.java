package nl.ou.jabberpoint.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.ou.jabberpoint.configuration.JabberPointConfiguration;
import nl.ou.jabberpoint.domain.MasterPresentation;
import nl.ou.jabberpoint.domain.SlideSequence;
import nl.ou.jabberpoint.ui.JabberPointNotification;
import nl.ou.jabberpoint.ui.controls.dialogs.SequenceSelectionDialog;
import nl.ou.jabberpoint.ui.controls.dialogs.SlideSelectionDialog;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * The {@code MainMenuController} class coordinates all actions between the main menu and the
 * {@code JabberPointPresentationController}
 */
class MainMenuController {

    private final Stage primaryStage;
    private JabberPointPresentationController jabberPointPresentationController;

    MainMenuController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    void setPresentationController(JabberPointPresentationController jabberPointPresentationController) {
        this.jabberPointPresentationController = jabberPointPresentationController;
    }

    void openAboutDialog(ActionEvent event) {
        try {
            Stage aboutWindow = new Stage();

            aboutWindow.initModality(Modality.APPLICATION_MODAL);
            aboutWindow.setTitle("Over OU-JabberPoint");

            Parent root = FXMLLoader.load(getClass().getResource("/views/about.fxml"));
            Scene scene = new Scene(root);

            aboutWindow.setScene(scene);

            aboutWindow.showAndWait();
        } catch (IOException e) {
            JabberPointNotification.internalError();
        }
    }

    void openFileDialog(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open JabberPoint File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File presentationFile = fileChooser.showOpenDialog(primaryStage);
        if (Optional.ofNullable(presentationFile).isPresent()) {
            loadPresentationFileAndQuerySequenceName(presentationFile);
        }
    }

    void exitApplication(ActionEvent actionEvent) {
        System.exit(0);
    }

    void previousSlideStep(ActionEvent actionEvent) {
        try {
            jabberPointPresentationController.previousSlideStep();
        } catch (NoSuchElementException e) {
            JabberPointNotification.firstSlideNotification();
        }
    }

    void nextSlideStep(ActionEvent actionEvent) {
        try {
            jabberPointPresentationController.nextSlideStep();
        } catch (NoSuchElementException e) {
            JabberPointNotification.lastSlideNotification();
        }
    }

    void previousSlide(ActionEvent actionEvent) {
        try {
            jabberPointPresentationController.previousSlide();
        } catch (NoSuchElementException e) {
            JabberPointNotification.firstSlideNotification();
        }
    }

    void nextSlide(ActionEvent actionEvent) {
        try {
            jabberPointPresentationController.nextSlide();
        } catch (NoSuchElementException e) {
            JabberPointNotification.lastSlideNotification();
        }
    }

    void openSequenceSelectionDialog(ActionEvent actionEvent) {
        selectNewSequence(jabberPointPresentationController.getPresentation());
    }

    void openMetaDataDialog(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Over uw presentatie");
        alert.setHeaderText(jabberPointPresentationController.getSlideShowtitle());
        alert.setContentText(jabberPointPresentationController.getMetaData().toString());
        alert.showAndWait();
    }

    void openSpecificSlideDialog(ActionEvent actionEvent) {
        Integer slideNr = null;
        try {
            slideNr = SlideSelectionDialog.display(primaryStage, "Navigeer naar slide", jabberPointPresentationController.getSlideShowSlides());
        } catch (IOException e) {
            JabberPointNotification.internalError();
        }
        if (Optional.ofNullable(slideNr).isPresent()) {
            jabberPointPresentationController.navigateToSlide(slideNr);
        }
    }

    private void loadPresentationFileAndQuerySequenceName(File currentPresentationAsFile) {
        MasterPresentation currentMasterPresentation = null;
        JabberPointConfiguration config = JabberPointConfiguration.getConfig();

        try {
            config.setPresentationPath(currentPresentationAsFile.getPath());
            currentMasterPresentation = jabberPointPresentationController.processPresentationFile();
            selectNewSequence(currentMasterPresentation);
        } catch (IllegalStateException e) {
            JabberPointNotification.presenationAlreadyLoaded(e.getMessage());
        }
    }

    private String openSequenceSelectionDialog(List<SlideSequence> slideSequence) {
        String sequenceName = null;
        try {
            sequenceName = SequenceSelectionDialog.display(primaryStage, "Inhoud selectie", slideSequence.stream().map(SlideSequence::getName).collect(Collectors.toList()));
        } catch (IOException e) {
            JabberPointNotification.internalError();
        }
        return sequenceName;
    }

    private void selectNewSequence(MasterPresentation currentMasterPresentation) {
        JabberPointConfiguration config = JabberPointConfiguration.getConfig();
        // open select sequence dialog
        config.setSelectedSequence(openSequenceSelectionDialog(currentMasterPresentation.getSlideSequence()));
        jabberPointPresentationController.loadPresentation(currentMasterPresentation);
    }
}
