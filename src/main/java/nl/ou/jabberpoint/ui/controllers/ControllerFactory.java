package nl.ou.jabberpoint.ui.controllers;

import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.util.Callback;
import nl.ou.jabberpoint.domain.factories.DefaultMasterPresentationFactory;
import nl.ou.jabberpoint.domain.factories.DefaultSlideShowFactory;
import nl.ou.jabberpoint.ui.behavior.KeyComboBehavior;
import nl.ou.jabberpoint.ui.controllers.contracts.KeyController;
import nl.ou.jabberpoint.ui.controllers.contracts.MouseController;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * The {@code ControllerFactory} is used to create custom controller classes, providing the ability to
 * manage and configure the controllers used by the {@code JabberPoint} application
 */
public class ControllerFactory implements Callback<Class<?>, Object> {
    private final Map<Type, Object> factoryList;
    private final Stage primaryStage;
    private Map<Type, Object> controllerList;

    public ControllerFactory(Map<Type, Object> factoryList, Stage primaryStage) {
        this.factoryList = factoryList;
        this.primaryStage = primaryStage;
    }

    /**
     * Retrieves the correct controller instance based on the given type and returns it.
     *
     * @param type the class type requested by the fxml window container
     * @return the controller instance
     */
    @Override
    public Object call(Class<?> type) {
        if (Optional.ofNullable(controllerList).isEmpty()) {
            initControllers();
        }

        if (type == JabberPointPresentationController.class) {
            return controllerList.get(JabberPointPresentationController.class);
        } else if (type == SlideShowController.class) {
            return controllerList.get(SlideShowController.class);
        } else {
            // default behavior for controllerFactory:
            try {
                return type.getConstructor().newInstance();
            } catch (Exception exc) {
                exc.printStackTrace();
                throw new RuntimeException(exc);
            }
        }
    }

    /**
     * Coordinates the creation of all controller instances
     * and populates the controllerList Map
     */
    private void initControllers() {
        controllerList = new HashMap<>();
        MainMenuController menuController = createMenuController();
        controllerList.put(MainMenuController.class, menuController);

        KeyController mainKeyController = createMainKeyController();
        controllerList.put(MainKeyController.class, mainKeyController);

        MouseController slideShowMouseController = createSlideShowMouseController();
        controllerList.put(SlideShowMouseController.class, slideShowMouseController);

        JabberPointPresentationController presentationController = createPresentationController();
        controllerList.put(JabberPointPresentationController.class, presentationController);

        KeyController slideShowKeyController = createSlideShowKeyController();
        controllerList.put(SlideShowKeyController.class, slideShowKeyController);

        SlideShowController slideShowController = createSlideShowController();
        controllerList.put(SlideShowController.class, slideShowController);

        primaryStage.setOnShown((value) -> {
            primaryStage.getScene().setOnKeyPressed(event -> {
                presentationController.getKeyController().keyPressed(event);
                slideShowController.getKeyController().keyPressed(event);
            });

            primaryStage.getScene().setOnMouseClicked(event -> {
                slideShowController.getMouseController().mouseClicked(event);
            });
        });

    }

    /**
     * Creates an instance the {@code SlideShowMouseController}
     *
     * @return the controller instance
     */
    private SlideShowMouseController createSlideShowMouseController() {
        return new SlideShowMouseController();
    }

    /**
     * Creates an instance of the {@code SlideShowKeyController}
     *
     * @return the controller instance
     */
    private SlideShowKeyController createSlideShowKeyController() {

        JabberPointPresentationController presentationController = (JabberPointPresentationController) controllerList.get(JabberPointPresentationController.class);

        List<KeyComboBehavior> keyComboBehaviorList = new ArrayList<>();
        KeyComboBehavior nextSlideKeyCombo = new KeyComboBehavior(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN), presentationController::nextSlideStep);
        KeyComboBehavior previousSlideStepKeyCombo = new KeyComboBehavior(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN), presentationController::previousSlideStep);
        keyComboBehaviorList.add(nextSlideKeyCombo);
        keyComboBehaviorList.add(previousSlideStepKeyCombo);

        return new SlideShowKeyController(keyComboBehaviorList);
    }

    /**
     * Creates an instance of the {@code SlideShowController}
     *
     * @return the controller instance
     */
    private SlideShowController createSlideShowController() {
        return new SlideShowController(primaryStage, (SlideShowKeyController) controllerList.get(SlideShowKeyController.class), (SlideShowMouseController) controllerList.get(SlideShowMouseController.class), (DefaultSlideShowFactory) factoryList.get(DefaultSlideShowFactory.class));
    }

    /**
     * Creates an instance of the {@code JabberPointPresentationController}
     *
     * @return the controller instance
     */
    private JabberPointPresentationController createPresentationController() {
        MainMenuController menuController = (MainMenuController) controllerList.get(MainMenuController.class);
        MainKeyController keyController = (MainKeyController) controllerList.get(MainKeyController.class);

        JabberPointPresentationController presentationController = new JabberPointPresentationController((DefaultMasterPresentationFactory) factoryList.get(DefaultMasterPresentationFactory.class));
        //link menu controller to the main presentation controller
        presentationController.setMenuController(menuController);
        menuController.setPresentationController(presentationController);

        //link key controller to the main presentation controller
        presentationController.setKeyController(keyController);
        //menuController.setPresentationController(presentationController);

        return presentationController;
    }

    /**
     * Creates an instance of the {@code MainMenuController}
     *
     * @return the controller instance
     */
    private MainMenuController createMenuController() {

        return new MainMenuController(primaryStage);
    }

    /**
     * Creates an instance of the {@code MainKeyController}
     *
     * @return the controller instance
     */
    private KeyController createMainKeyController() {
        MainMenuController menuController = (MainMenuController) controllerList.get(MainMenuController.class);
        List<KeyComboBehavior> keyComboBehaviorList = new ArrayList<>();

        KeyComboBehavior openSlideShowKeyCombo = new KeyComboBehavior(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN), () -> menuController.openFileDialog(new ActionEvent()));
        KeyComboBehavior openMetaDataKeyCombo = new KeyComboBehavior(new KeyCodeCombination(KeyCode.M, KeyCombination.CONTROL_DOWN), () -> menuController.openMetaDataDialog(new ActionEvent()));
        KeyComboBehavior openAboutKeyCombo = new KeyComboBehavior(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN), () -> menuController.openAboutDialog(new ActionEvent()));
        KeyComboBehavior exitApplicationKeyCombo = new KeyComboBehavior(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN), () -> System.exit(0));
        KeyComboBehavior sequenceSelectionDialogKeyCombo = new KeyComboBehavior(new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN), () -> menuController.openSequenceSelectionDialog(new ActionEvent()));
        KeyComboBehavior specificSlideKeyCombo = new KeyComboBehavior(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN), () -> menuController.openSpecificSlideDialog(new ActionEvent()));

        keyComboBehaviorList.add(openSlideShowKeyCombo);
        keyComboBehaviorList.add(specificSlideKeyCombo);
        keyComboBehaviorList.add(openMetaDataKeyCombo);
        keyComboBehaviorList.add(openAboutKeyCombo);
        keyComboBehaviorList.add(exitApplicationKeyCombo);
        keyComboBehaviorList.add(sequenceSelectionDialogKeyCombo);

        return new MainKeyController(keyComboBehaviorList);
    }

}

