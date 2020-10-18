package nl.ou.jabberpoint.ui.controls;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.WeakChangeListener;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.util.Optional;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * bron: StackOverflow
 * adds a radiobuton to a listCell making it possible to select the elements inside a listview through a radiobutton
 * selection
 */
public class RadioListCell extends ListCell<String> {

    private final ChangeListener<Boolean> radioListener = (src, ov, nv) -> radioChanged(nv);
    private final RadioButton radioButton;
    private ToggleGroup group;

    public RadioListCell(ToggleGroup toggleGroup) {
        radioButton = new RadioButton();
        WeakChangeListener<Boolean> weakRadioListener = new WeakChangeListener<>(radioListener);
        radioButton.selectedProperty().addListener(weakRadioListener);
        radioButton.setFocusTraversable(false);
        // let it span the complete width of the list
        // needed in fx8 to update selection state
        radioButton.setMaxWidth(Double.MAX_VALUE);
        group = toggleGroup;
    }

    private void radioChanged(boolean selected) {
        if (selected && Optional.ofNullable(getListView()).isPresent() && !isEmpty() && getIndex() >= 0) {
            getListView().getSelectionModel().select(getIndex());
        }
    }

    @Override
    public void updateItem(String obj, boolean empty) {
        super.updateItem(obj, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
            radioButton.setToggleGroup(null);
        } else {
            radioButton.setText(obj);
            radioButton.setToggleGroup(group);
            radioButton.setSelected(isSelected());
            setGraphic(radioButton);
        }
    }
}