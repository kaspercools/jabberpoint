package nl.ou.jabberpoint.domain.visualization;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import nl.ou.jabberpoint.domain.*;
import nl.ou.jabberpoint.domain.interpreter.SlideShowInterpreter;

/**
 * @author Kasper Cools (coolskasper@gmail.com
 */
public class HeaderVisualizationBehavior extends VisualizationBehavior {

    private final SlideShowInterpreter slideShowInterpreter;
    private final String headerText;

    public HeaderVisualizationBehavior(String headerText, SlideShowInterpreter interpreter) {
        super(new TextItem());
        slideShowInterpreter = interpreter;
        this.headerText = headerText;
    }

    @Override
    public void draw(GraphicsContext graphics, PresentableElementBounds slideShowBounds, double scale, Point drawLocation, MouseEvent mouseEvent) {
        TextItem currentTextItem = (TextItem) visualization;

        currentTextItem.setText(slideShowInterpreter.interpret(headerText));
        applyHeaderStyle(graphics);

        visualization.setPosition(new Point(slideShowBounds.getMaxX(), slideShowBounds.getMinY()));

        super.draw(graphics, slideShowBounds, scale, drawLocation, mouseEvent);
    }

    private void applyHeaderStyle(GraphicsContext graphics) {
        Style.ItemStyleBuilder styleBuilder = new Style.ItemStyleBuilder();
        Style headerStyle = styleBuilder.font(new Font("Verdana", 14)).build();

        visualization.setStyle(headerStyle);
        headerStyle.setPadding(Direction.Left, -visualization.calculateWidth(graphics, 1) - 25);
        headerStyle.setPadding(Direction.Top, visualization.calculateHeight(graphics, 1));
    }
}
