package nl.ou.jabberpoint.ui.controls.dialogs;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nl.ou.jabberpoint.ui.controls.RadioListCell;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class SequenceSelectionDialog {

    /**
     * Loads and configures the fxml dialog window and asks the user for input
     *
     * @param parentStage   the primary stage to which this dialog is linked
     * @param title         the title of the dialog window
     * @param sequenceNames list of available sequence names
     * @return the sequence name
     * @throws IOException when the FXML file cannot be loaded
     */
    public static String display(Stage parentStage, String title, List<String> sequenceNames) throws IOException {
        Stage sequenceSelectionDialog = new Stage();

        sequenceSelectionDialog.initModality(Modality.WINDOW_MODAL);
        sequenceSelectionDialog.initOwner(parentStage);
        sequenceSelectionDialog.setResizable(false);
        sequenceSelectionDialog.setTitle(title);
        FXMLLoader loader = new FXMLLoader(SequenceSelectionDialog.class.getResource("/dialogs/select-sequence.fxml"));

        return getSequenceNameFromUser(sequenceNames, sequenceSelectionDialog, loader);
    }

    /**
     * Builds a windows, opens it and waits for the dialog to be closed before returning the selected sequence name
     *
     * @param sequenceNames           the list of available sequence names
     * @param sequenceSelectionDialog the sequenceSelectionDialog stage object
     * @param loader                  the FXML loader instance
     * @return the selected sequence
     * @throws IOException when the fxml dialog cannot be initialized correctly
     */
    private static String getSequenceNameFromUser(List<String> sequenceNames, Stage sequenceSelectionDialog, FXMLLoader loader) throws IOException {
        StringProperty selectedSequenceNameProperty = new SimpleStringProperty();
        String defaultSequenceName = "Standaard inhoud";

        sequenceNames.add(defaultSequenceName);
        loader.setController(new SequenceSelectionController(sequenceNames, defaultSequenceName, sequenceSelectionDialog, selectedSequenceNameProperty));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        sequenceSelectionDialog.setScene(scene);
        sequenceSelectionDialog.showAndWait();

        if (Optional.ofNullable(selectedSequenceNameProperty.get()).isEmpty() || selectedSequenceNameProperty.get().equals(defaultSequenceName)) {
            return null;
        } else {
            return selectedSequenceNameProperty.get();
        }
    }

    /**
     * Handles the SequenceSelection dialog logic and sets the value of the sequenceNameProperty which is used to return
     * data to the {@code nl.ou.jabberpoint.ui.controllers.JabberPointPresentationController}
     */
    private static class SequenceSelectionController implements Initializable {
        private final String defaultSequenceName;
        private final StringProperty sequenceNameProperty;
        private final Stage currentStage;
        private final ObservableList<String> sequences;
        @FXML
        ListView<String> sequenceListView;
        @FXML
        Button selectButton;

        SequenceSelectionController(List<String> sequenceNames, String defaultSelectedSequence, Stage currentStage, StringProperty sequenceNameProperty) {
            this.sequences = FXCollections.observableList(sequenceNames);
            this.currentStage = currentStage;
            this.sequenceNameProperty = sequenceNameProperty;
            this.defaultSequenceName = defaultSelectedSequence;
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            ToggleGroup radioButtonToggleGroup = new ToggleGroup();
            sequenceListView.setCellFactory(param -> new RadioListCell(radioButtonToggleGroup));
            sequenceListView.setItems(sequences);
            sequenceListView.getSelectionModel().select(defaultSequenceName);
            sequenceListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
                    -> sequenceNameProperty.set(newValue));
            selectButton.setOnAction(this::onSubmit);
        }

        /**
         * Closes the current dialog window
         *
         * @param actionEvent the {@code ActionEvent} from the button that has been clicked
         */
        private void onSubmit(ActionEvent actionEvent) {
            currentStage.close();
        }
    }
}
