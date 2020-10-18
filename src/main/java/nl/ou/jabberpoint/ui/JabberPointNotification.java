package nl.ou.jabberpoint.ui;

import javafx.beans.property.IntegerProperty;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * This class contains all notification used throughout the application to prompt info
 * every method references at least one rule as defined in the common language for OU-JabberPoint
 */
public class JabberPointNotification {

    public static void firstSlideNotification() {
        Notifications.create().darkStyle().title("JabberPoint notificatie").text("Dit is je eerste slide.").hideAfter(Duration.seconds(5)).showInformation();
    }

    public static void lastSlideNotification() {
        Notifications.create().darkStyle().title("JabberPoint notificatie").text("Er zijn geen slides meer, dit was de laatste.").hideAfter(Duration.seconds(5)).showInformation();
    }

    public static void slideDoesNotExist(int slideRef) {
        Notifications.create().darkStyle().title("JabberPoint notificatie").text(String.format("Slide nr %s bestaat niet.")).hideAfter(Duration.seconds(5)).showInformation();
    }

    public static void errorLoadingPresentationFile() {
        Notifications.create().darkStyle().title("JabberPoint notificatie").text("De presentatie kon niet worden ingeladen.").hideAfter(Duration.seconds(5)).showError();
    }

    public static void mediaNotPlayableNotification() {
        Notifications.create().darkStyle().title("JabberPoint notificatie").text("Het opgegeven mediabestand kan niet worden afgespeeld.").hideAfter(Duration.seconds(5)).showError();
    }

    public static void noSlidesInSlideShow(String message) {
        Notifications.create().darkStyle().title("JabberPoint notificatie").text(message).hideAfter(Duration.seconds(5)).showError();
    }

    public static void noSlidesInSequence(String message) {
        Notifications.create().darkStyle().title("JabberPoint notificatie").text(message).hideAfter(Duration.seconds(5)).showError();
    }

    public static void presenationAlreadyLoaded(String message) {
        Notifications.create().darkStyle().title("JabberPoint notificatie").text(message).hideAfter(Duration.seconds(5)).showInformation();
    }

    public static void internalError() {
        Notifications.create().darkStyle().title("JabberPoint notificatie").text("Woeps, daar liep het even mis! \n Neem contact op met de beheerder van deze toepassing.").hideAfter(Duration.seconds(5)).showError();
    }

    public static void selectedSlideOutOfBounds(IntegerProperty slideNumber, int size) {
        Notifications.create().darkStyle().title("JabberPoint notificatie").text(String.format("Deze presentatie bevat %s slides, het door u opgegeven nummer bestaat niet (%s)", size, slideNumber)).hideAfter(Duration.seconds(5)).showError();
    }

    public static void noSelectedSlide() {
        Notifications.create().darkStyle().title("JabberPoint notificatie").text(String.format("U dient een slide te selecteren of een nr in te geven alvoren verder te gaan.")).hideAfter(Duration.seconds(5)).showError();
    }
}
