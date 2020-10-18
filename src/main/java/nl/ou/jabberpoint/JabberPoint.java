package nl.ou.jabberpoint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.ou.jabberpoint.configuration.JabberPointConfiguration;
import nl.ou.jabberpoint.domain.factories.DefaultMasterPresentationFactory;
import nl.ou.jabberpoint.domain.factories.DefaultSlideFactory;
import nl.ou.jabberpoint.domain.factories.DefaultSlideShowFactory;
import nl.ou.jabberpoint.domain.interpreter.DefaultSlideShowInterpreterFactory;
import nl.ou.jabberpoint.processing.factories.DefaultFileParserFactory;
import nl.ou.jabberpoint.ui.controllers.ControllerFactory;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * {@code JabberPoint} is the main entrypoint of the application
 * Here we initialize our main window, create factories necessary throughout the application
 * and link the custom Abstract Factory that is used to create our controller classes
 */
public class JabberPoint extends Application {

    public JabberPoint() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start function is called as soon as the JavaFX application launches. It configures the primary stage by
     * creating loading and creating the main scene and sets the default {@link ControllerFactory}
     *
     * @param primaryStage the primary Stage linked to the JavaFX app
     * @throws Exception when the main fxml file cannot be loaded
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        JabberPointConfiguration jabberPointConfig = JabberPointConfiguration.getConfig();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main-window.fxml"));
        loader.setControllerFactory(new ControllerFactory(createFactories(), primaryStage));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.setMinHeight(820);
        primaryStage.setMinWidth(1200);

        primaryStage.show();
    }

    /**
     * Initializes the factories used by {@code JabberPoint}, these are passed to the {@link ControllerFactory}
     *
     * @return
     */
    private HashMap<Type, Object> createFactories() {
        //CREATE FACTORIES
        DefaultFileParserFactory fileParserFactory = new DefaultFileParserFactory();
        DefaultSlideFactory slideFactory = new DefaultSlideFactory();
        DefaultSlideShowInterpreterFactory slideShowInterpreterFactory = new DefaultSlideShowInterpreterFactory();
        DefaultSlideShowFactory slideShowFactory = new DefaultSlideShowFactory(slideShowInterpreterFactory, slideFactory, fileParserFactory);
        DefaultMasterPresentationFactory masterPresentationFactory = new DefaultMasterPresentationFactory(fileParserFactory);

        HashMap<Type, Object> factoryList = new HashMap<>();
        factoryList.put(DefaultSlideFactory.class, slideFactory);
        factoryList.put(DefaultSlideShowFactory.class, slideShowFactory);
        factoryList.put(DefaultSlideShowInterpreterFactory.class, slideShowInterpreterFactory);
        factoryList.put(DefaultMasterPresentationFactory.class, masterPresentationFactory);
        return factoryList;
    }
}
