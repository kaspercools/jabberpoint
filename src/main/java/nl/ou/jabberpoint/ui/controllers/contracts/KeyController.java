package nl.ou.jabberpoint.ui.controllers.contracts;

import javafx.scene.input.KeyEvent;

/**
 * The {@code KeyController} defines the methods needed when a domain object supports Key events
 */
public interface KeyController {
    void keyPressed(KeyEvent keyEvent);
}
