package mapcreator;

import javax.swing.JComponent;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class createMap extends JComponent {
    public String[] map;
    public Point size;
    public Color colorMap;
    public int COL;
    public int ROW;
    public double span_x;
    public double span_y;

    // List of all shapes we build from the map
    private ArrayList<MapObject> mapObjects = new ArrayList<>();

    public createMap(Object... params) {
        for (Object object : params) {
            if (object instanceof String[]) {
                this.map = (String[]) object;
            } else if (object instanceof Point) {
                this.size = (Point) object;
            } else if (object instanceof Color) {
                this.colorMap = (Color) object;
            }
        }

        if (this.map != null && size != null) {
            COL = this.map[0].length();
            ROW = this.map.length;
            span_x = (double) size.x / COL;
            span_y = (double) size.y / ROW;

            // Build all shapes now so we can reuse them in paintComponent
            buildMapShapes();
        }
    }

    private void buildMapShapes() {
        mapObjects.clear();
        // The thickness of the lines
        double thickness = Math.min(span_x, span_y) * 0.2;

        for (int row = 0; row < ROW; row++) {
            String line = map[row];
            for (int col = 0; col < line.length(); col++) {
                char c = line.charAt(col);
                double x = col * span_x;
                double y = row * span_y;

                // Center of this cell
                double centerX = x + span_x / 2.0;
                double centerY = y + span_y / 2.0;

                switch (c) {
                    case '=': {
                        // Full cell rectangle
                        Rectangle2D.Double wallRect = new Rectangle2D.Double(x, y, span_x, span_y);
                        mapObjects.add(new MapObject(wallRect, Color.WHITE, "OUTER_WALL"));
                        break;
                    }
                    case '─':
                    case '│':
                    case '┌':
                    case '┐':
                    case '└':
                    case '┘':
                    case '┬':
                    case '┴':
                    case '├':
                    case '┤':
                    case '┼': {
                        // We'll figure out which directions to draw
                        boolean up = false, down = false, left = false, right = false;
                        switch (c) {
                            case '─': left = true; right = true; break;
                            case '│': up = true; down = true; break;
                            case '┌': right = true; down = true; break;
                            case '┐': left = true; down = true; break;
                            case '└': right = true; up = true; break;
                            case '┘': left = true; up = true; break;
                            case '┬': left = true; right = true; down = true; break;
                            case '┴': left = true; right = true; up = true; break;
                            case '├': right = true; up = true; down = true; break;
                            case '┤': left = true; up = true; down = true; break;
                            case '┼': left = true; right = true; up = true; down = true; break;
                        }

                        // For each direction, build a rectangle
                        if (left) {
                            double lineWidth = centerX - x;
                            Rectangle2D.Double leftLine = new Rectangle2D.Double(
                                centerX - lineWidth, 
                                centerY - thickness / 2.0,
                                lineWidth,
                                thickness
                            );
                            mapObjects.add(new MapObject(leftLine, Color.WHITE, "BOX_DRAWING_LINE"));
                        }
                        if (right) {
                            double lineWidth = (x + span_x) - centerX;
                            Rectangle2D.Double rightLine = new Rectangle2D.Double(
                                centerX,
                                centerY - thickness / 2.0,
                                lineWidth,
                                thickness
                            );
                            mapObjects.add(new MapObject(rightLine, Color.WHITE, "BOX_DRAWING_LINE"));
                        }
                        if (up) {
                            double lineHeight = centerY - y;
                            Rectangle2D.Double upLine = new Rectangle2D.Double(
                                centerX - thickness / 2.0,
                                centerY - lineHeight,
                                thickness,
                                lineHeight
                            );
                            mapObjects.add(new MapObject(upLine, Color.WHITE, "BOX_DRAWING_LINE"));
                        }
                        if (down) {
                            double lineHeight = (y + span_y) - centerY;
                            Rectangle2D.Double downLine = new Rectangle2D.Double(
                                centerX - thickness / 2.0,
                                centerY,
                                thickness,
                                lineHeight
                            );
                            mapObjects.add(new MapObject(downLine, Color.WHITE, "BOX_DRAWING_LINE"));
                        }
                        break;
                    }
                    
                    default:
                        break;
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Smooth edges
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw background
        Rectangle2D.Double background = new Rectangle2D.Double(0, 0, size.x - 40, size.y);
        g2d.setColor((colorMap == null) ? new Color(100, 149, 237) : colorMap);
        g2d.fill(background);

        // Now paint each stored MapObject
        for (MapObject obj : mapObjects) {
            g2d.setColor(obj.getColor());
            g2d.fill(obj.getShape());
        }
    }
    public ArrayList<MapObject> getMapObjects() {
        return mapObjects;
    }
}
