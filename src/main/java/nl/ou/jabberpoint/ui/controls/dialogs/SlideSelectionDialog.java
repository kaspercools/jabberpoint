package nl.ou.jabberpoint.ui.controls.dialogs;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import nl.ou.jabberpoint.domain.Slide;
import nl.ou.jabberpoint.ui.JabberPointNotification;
import nl.ou.jabberpoint.ui.controls.PositiveNumberTextField;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
public class SlideSelectionDialog {
    /**
     * Loads and configures the fxml dialog window and asks the user for input
     *
     * @param parentStage the primary stage to which this dialog is linked
     * @param title       the title of the dialog window
     * @param slideList   the collection of slides used as reference by the dialog window
     * @return the chosen slide nr
     * @throws IOException when the fxml dialog window cannot be loaded
     */
    public static Integer display(Stage parentStage, String title, List<Slide> slideList) throws IOException {
        Stage slideSelectionDialog = new Stage();

        slideSelectionDialog.initModality(Modality.WINDOW_MODAL);
        slideSelectionDialog.initOwner(parentStage);
        slideSelectionDialog.setResizable(false);
        slideSelectionDialog.setTitle(title);
        FXMLLoader loader = new FXMLLoader(SequenceSelectionDialog.class.getResource("/dialogs/slide-selection.fxml"));

        return getSlideNumberFromUser(slideSelectionDialog, loader, slideList);
    }

    /**
     * Configures, shows the dialog window to the user and waits for input
     *
     * @param slideSelectionDialog the slideSelectionDialog stage object
     * @param loader               the FXML loader instance
     * @param slideList            list of available slides
     * @return the selected slide nr
     * @throws IOException when the fxml cannot be initialized correctly
     */
    private static Integer getSlideNumberFromUser(Stage slideSelectionDialog, FXMLLoader loader, List<Slide> slideList) throws IOException {
        IntegerProperty slideNumberProperty = new SimpleIntegerProperty();
        SimpleObjectProperty<SlideSelectionType> slideSelectionTypeProperty = new SimpleObjectProperty<>();

        loader.setController(new SlideSelectionDialogController(slideSelectionTypeProperty, slideNumberProperty, slideList, slideSelectionDialog));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        slideSelectionDialog.setScene(scene);
        slideSelectionDialog.showAndWait();

        return (slideNumberProperty.get() == 0) ? null : slideNumberProperty.get();
    }

    /**
     * @author Kasper Cools (coolskasper@gmail.com)
     */
    enum SlideSelectionType {
        SlideSequenceNr,
        Slide
    }

    /**
     * @author Kasper Cools (coolskasper@gmail.com)
     */
    private static class SlideSelectionDialogController implements Initializable {
        private final List<Slide> slideList;
        private final IntegerProperty slideNumberProperty;
        private final SimpleObjectProperty<SlideSelectionType> slideSelectionTypeProperty;
        private final Stage slideSelectionDialog;

        @FXML
        private ToggleGroup inputTypeSelectionGroup;
        @FXML
        private ComboBox<Slide> titleComboBox;
        @FXML
        private PositiveNumberTextField numberTextfield;
        @FXML
        private Button selectButton;

        SlideSelectionDialogController(SimpleObjectProperty<SlideSelectionType> slideSelectionTypeProperty, IntegerProperty slideNumberProperty, List<Slide> slideList, Stage slideSelectionDialog) {
            this.slideList = slideList;
            this.slideNumberProperty = slideNumberProperty;
            this.slideSelectionTypeProperty = slideSelectionTypeProperty;
            this.slideSelectionDialog = slideSelectionDialog;
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            selectButton.setOnAction(this::onGoToSlideAction);
            titleComboBox.setItems(FXCollections.observableArrayList(slideList));
            titleComboBox.setConverter(new StringConverter<>() {
                @Override
                public String toString(Slide object) {
                    if (Optional.ofNullable(object).isPresent()) {
                        return object.getTitle();
                    }
                    return null;
                }

                @Override
                public Slide fromString(String string) {
                    return null;
                }
            });

            inputTypeSelectionGroup.selectedToggleProperty().addListener(this::onRadioButtonSelectionChanged);
            slideSelectionTypeProperty.set(SlideSelectionType.SlideSequenceNr);
        }

        /**
         * Checks whether the value of a radiobutton has changed, and changes the currently selected type based on the
         * textual value of the selected radiobutton
         *
         * @param observableValue The {@code ObservableValue} which value changed
         * @param oldToggle       the old {@code Toggle} value
         * @param newToggle       the new {@code Toggle} value
         */
        private void onRadioButtonSelectionChanged(ObservableValue<? extends Toggle> observableValue, Toggle oldToggle, Toggle newToggle) {
            RadioButton selectedRadioButton = (RadioButton) inputTypeSelectionGroup.getSelectedToggle();
            String titleOrNumberValue = selectedRadioButton.getText();

            if (titleOrNumberValue.equals("titel")) {
                slideSelectionTypeProperty.set(SlideSelectionType.Slide);
                numberTextfield.setVisible(false);
                titleComboBox.setVisible(true);
            } else {
                slideSelectionTypeProperty.set(SlideSelectionType.SlideSequenceNr);
                numberTextfield.setVisible(true);
                titleComboBox.setVisible(false);
            }
        }

        /**
         * Gets the current slide nr and passes it to the selectedSlideNr property
         *
         * @param actionEvent the {@code ActionEvent} from the button that has been clicked
         */
        private void onGoToSlideAction(ActionEvent actionEvent) {
            Integer selectedSlideNr = null;
            if (slideSelectionTypeProperty.get().equals(SlideSelectionType.SlideSequenceNr)) {
                selectedSlideNr = numberTextfield.getNumberValue();
            } else {
                Slide selectedSlide = titleComboBox.getSelectionModel().getSelectedItem();

                //loop through LinkdList of slides
                for (int i = 0; i < slideList.size(); i++) {
                    Slide slide = slideList.get(i);
                    // check equality based on the instance itself!
                    if (slide == selectedSlide) {
                        //set slide nr
                        selectedSlideNr = i + 1;
                        break;
                    }
                }
            }

            Optional<Integer> selectedSlideNrOptional = Optional.ofNullable(selectedSlideNr);

            if (selectedSlideNrOptional.isPresent() && (selectedSlideNr > slideList.size() || selectedSlideNr <= 0)) {
                JabberPointNotification.selectedSlideOutOfBounds(slideNumberProperty, slideList.size());
                return;
            } else if (selectedSlideNrOptional.isEmpty()) {
                JabberPointNotification.noSelectedSlide();
                return;
            }

            slideNumberProperty.set(selectedSlideNr);
            this.slideSelectionDialog.close();
        }
    }
}
