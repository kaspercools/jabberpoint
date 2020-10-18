package nl.ou.jabberpoint.ui.controllers;

import javafx.scene.input.KeyEvent;
import nl.ou.jabberpoint.ui.behavior.KeyComboBehavior;
import nl.ou.jabberpoint.ui.controllers.contracts.KeyController;

import java.util.List;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 */
class MainKeyController implements KeyController {
    private final List<KeyComboBehavior> keyCombinationBehaviorList;

    MainKeyController(List<KeyComboBehavior> keyComboBehaviorList) {
        this.keyCombinationBehaviorList = keyComboBehaviorList;
    }

    /**
     * Processes the given KeyEvent
     *
     * @param keyEvent the linked KeyEvent
     */
    public void keyPressed(KeyEvent keyEvent) {
        processComboKeyPressed(keyEvent);
    }

    /**
     * Processes the given KeyEvent and check if it machtes any of the {@code KeyComboBehavior} instances stored in the
     * {@code keyCombinationBehaviorList}
     *
     * @param keyEvent the linked KeyEvent
     * @return whether or not the command has been processed
     */
    private boolean processComboKeyPressed(KeyEvent keyEvent) {
        boolean hasBeenExecuted = false;
        for (KeyComboBehavior keyComboBehavior : keyCombinationBehaviorList) {
            if (keyComboBehavior.getKeyCombination().match(keyEvent)) {
                keyComboBehavior.executeKeyCommand();
                hasBeenExecuted = true;
            }
        }
        return hasBeenExecuted;
    }
}
