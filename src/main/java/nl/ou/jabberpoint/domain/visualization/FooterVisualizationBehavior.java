package nl.ou.jabberpoint.domain.visualization;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import nl.ou.jabberpoint.domain.*;
import nl.ou.jabberpoint.domain.interpreter.SlideShowInterpreter;
import org.controlsfx.control.Notifications;

/**
 * @author Kasper Cools (coolskasper@gmail.com
 */
public class FooterVisualizationBehavior extends VisualizationBehavior {

    private final SlideShowInterpreter slideShowInterpreter;
    private final String footerText;

    public FooterVisualizationBehavior(String footerText, SlideShowInterpreter interpreter) {
        super(new TextItem());
        slideShowInterpreter = interpreter;
        this.footerText = footerText;
    }

    @Override
    public void draw(GraphicsContext graphics, PresentableElementBounds slideShowBounds, double scale, Point drawLocation, MouseEvent mouseEvent) {
        TextItem currentTextItem = (TextItem) visualization;

        currentTextItem.setText(slideShowInterpreter.interpret(footerText));
        applyFooterStyle(graphics);

        currentTextItem.setPosition(new Point(slideShowBounds.getMinX(), slideShowBounds.getMaxY()));

        super.draw(graphics, slideShowBounds, scale, drawLocation, mouseEvent);


    }

    private void applyFooterStyle(GraphicsContext graphics) {
        Style.ItemStyleBuilder styleBuilder = new Style.ItemStyleBuilder();
        Style headerStyle = styleBuilder.font(new Font("Verdana", 14)).build();
        visualization.setStyle(headerStyle);

        headerStyle.setPadding(Direction.Left, 10);
        headerStyle.setPadding(Direction.Top, -visualization.calculateHeight(graphics, 1));
    }
}
