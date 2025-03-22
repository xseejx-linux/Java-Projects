package mapcreator;

import java.awt.Color;
import java.awt.Shape;

public class MapObject {
    private Shape shape;
    private Color color;
    private String type;

    public MapObject(Shape shape, Color color, String type) {
        this.shape = shape;
        this.color = color;
        this.type = type;
    }

    public Shape getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public String getType() {
        return type;
    }
}
