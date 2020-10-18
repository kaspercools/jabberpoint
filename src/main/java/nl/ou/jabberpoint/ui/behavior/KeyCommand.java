package nl.ou.jabberpoint.ui.behavior;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * {@code KeyCommand} is an interface that is used as a contract to pass lambda expressions to the KeyComboBehavior
 * class
 */
public interface KeyCommand {
    void onKeyPressed();
}
