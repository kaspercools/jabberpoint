package nl.ou.jabberpoint.domain.builder;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import nl.ou.jabberpoint.domain.Direction;
import nl.ou.jabberpoint.domain.Style;

public interface StyleBuilder {
    StyleBuilder font(Font font);

    StyleBuilder fillColor(Color fillColor);

    StyleBuilder color(Color color);

    StyleBuilder padding(Direction direction, double padding);

    StyleBuilder reset();

    Style build();
}
