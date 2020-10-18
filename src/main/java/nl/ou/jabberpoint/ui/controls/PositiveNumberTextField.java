package nl.ou.jabberpoint.ui.controls;

import javafx.scene.control.TextField;

import java.util.Optional;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * A custom {@code TextField} that ensures that any input given must be of a numeric value.
 * negative numbers are not included in the regex
 */
public class PositiveNumberTextField extends TextField {
    @Override
    public void replaceText(int start, int end, String text) {
        if (validate(text)) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (validate(text)) {
            super.replaceSelection(text);
        }
    }

    private boolean validate(String text) {
        return text.matches("[0-9]*");
    }

    /**
     * The current {@code TextField} value is returned as Integer value. If the field doesn't contain any value, null is returned
     *
     * @return a positive number or null
     */
    public Integer getNumberValue() {
        Integer result = null;
        if (Optional.ofNullable(getText()).isPresent() && !getText().isEmpty()) {
            result = Integer.parseInt(getText());
        }
        return result;
    }
}
