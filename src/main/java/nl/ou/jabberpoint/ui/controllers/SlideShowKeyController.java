package nl.ou.jabberpoint.ui.controllers;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import nl.ou.jabberpoint.domain.SlideShow;
import nl.ou.jabberpoint.ui.JabberPointNotification;
import nl.ou.jabberpoint.ui.behavior.KeyComboBehavior;
import nl.ou.jabberpoint.ui.controllers.contracts.KeyController;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * The {@code SlideShowKeyController} processes all keyevents related to {@code SlideShow} actions
 * and determines whether something has to happen or not
 */
class SlideShowKeyController implements KeyController {
    private final List<KeyComboBehavior> keyCombinationBehaviorList;
    private SlideShow slideShow;

    SlideShowKeyController(List<KeyComboBehavior> keyComboBehaviorList) {
        this.keyCombinationBehaviorList = keyComboBehaviorList;
    }

    void setSlideShow(SlideShow slideShow) {
        this.slideShow = slideShow;
    }

    /**
     * Processes the Key pressed Action on a slideshow
     *
     * @param keyEvent the linked {@code KeyEvent}
     */
    public void keyPressed(KeyEvent keyEvent) {
        if (Optional.ofNullable(slideShow).isEmpty()) {
            return;
        }

        List<KeyCode> nextSlideKeyList =
                Arrays.asList(KeyCode.DOWN,
                        KeyCode.PAGE_DOWN,
                        KeyCode.ENTER);
        List<KeyCode> previousSlideKeyList =
                Arrays.asList(KeyCode.PAGE_UP, KeyCode.UP);
        List<KeyCode> nextSlideStepKeyList = Arrays.asList(KeyCode.RIGHT, KeyCode.SPACE);
        List<KeyCode> previousSlideStepKeyList = Arrays.asList(KeyCode.LEFT, KeyCode.BACK_SPACE);

        if (processComboKeyPressed(keyEvent)) {
            return;
        }

        if (nextSlideKeyList.contains(keyEvent.getCode())) {
            try {
                slideShow.nextSlide();
            } catch (NoSuchElementException e) {
                JabberPointNotification.lastSlideNotification();
            }
        }

        if (previousSlideKeyList.contains(keyEvent.getCode())) {
            try {
                slideShow.previousSlide();
            } catch (NoSuchElementException e) {
                JabberPointNotification.firstSlideNotification();
            }
        }

        if (nextSlideStepKeyList.contains(keyEvent.getCode())) {
            try {
                slideShow.nextStep();
            } catch (NoSuchElementException e) {
                JabberPointNotification.lastSlideNotification();
            }
        }

        if (previousSlideStepKeyList.contains(keyEvent.getCode())) {
            try {
                slideShow.previousStep();
            } catch (NoSuchElementException e) {
                JabberPointNotification.firstSlideNotification();
            }
        }
    }

    /**
     * Processes the given KeyEvent and checks if it matches any of the keyCombinations defined in {@code keyCombinationBehaviorList}
     *
     * @param keyEvent the linked {@code KeyEvent}
     * @return whether or not the keyevent has been processed
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
