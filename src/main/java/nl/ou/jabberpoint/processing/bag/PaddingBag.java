package nl.ou.jabberpoint.processing.bag;

import nl.ou.jabberpoint.domain.Direction;

/**
 * @author Kasper Cools (coolskasper@gmail.com)
 * JabberPoint bag, used to temporarily store and access the JabberPoint file
 * in a object oriented manner
 */
public class PaddingBag {
    private Direction direction;
    private double value;

    public PaddingBag() {
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
