package nl.ou.jabberpoint.ui.behavior;

import javafx.scene.input.KeyCombination;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * Defines a {@link KeyCombination} and a {@link KeyCommand} to be executed when that particular
 * keycombination is pressed.
 */
public class KeyComboBehavior {
    private final KeyCombination keyCombination;
    private final KeyCommand keyCommand;

    /**
     * Constructs the {@code KeyComboBehavior} class
     *
     * @param keyCombination The key combination associated with the command
     * @param keyCommand     The command to be executed when the keycombo is pressed
     */
    public KeyComboBehavior(KeyCombination keyCombination, KeyCommand keyCommand) {
        this.keyCombination = keyCombination;
        this.keyCommand = keyCommand;
    }

    /**
     * executes the linked command
     */
    public void executeKeyCommand() {
        keyCommand.onKeyPressed();
    }

    /**
     * Returns the linked {@link KeyCombination}
     *
     * @return the KeyCombination
     */
    public KeyCombination getKeyCombination() {
        return keyCombination;
    }
}
