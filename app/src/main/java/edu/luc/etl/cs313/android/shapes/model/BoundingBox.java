package edu.luc.etl.cs313.android.shapes.model;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

    // TODO entirely your job (except onCircle)

    @Override
    public Location onCircle(final Circle c) {
        final int radius = c.getRadius();
        return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
    }

    @Override
    public Location onFill(final Fill f) {
        // Filled shape is the bounding box; no further action needed
        return f.getShape().accept(this);
    }

    @Override
    public Location onGroup(final Group g) {
        // These variables calculate the bounding box
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

        // Over all shapes + update the coords
        for (Shape shape : g.getShapes()) {
            Location loc = shape.accept(this);
            int shapeX = loc.getX();
            int shapeY = loc.getY();
            int shapeWidth = ((Rectangle) loc.getShape()).getWidth();
            int shapeHeight = ((Rectangle) loc.getShape()).getHeight();

            minX = Math.min(minX, shapeX);
            minY = Math.min(minY, shapeY);
            maxX = Math.max(maxX, shapeX + shapeWidth);
            maxY = Math.max(maxY, shapeY + shapeHeight);
        }
        // New rectangle
        return new Location(minX, minY, new Rectangle(maxX - minX, maxY - minY));
    }

    @Override
    public Location onLocation(final Location l) {
        Location innerBox = l.getShape().accept(this);
        // Applying the translation to the bounding box
        return new Location(l.getX() + innerBox.getX(), l.getY() + innerBox.getY(), innerBox.getShape());
    }

    @Override
    public Location onRectangle(final Rectangle r) {
        // Rectangle itself at (0,0); no action needed
        return new Location(0,0, r);
    }

    @Override
    public Location onStrokeColor(final StrokeColor c) {
        // Same; no action needed
        return c.getShape().accept(this);
    }

    @Override
    public Location onOutline(final Outline o) {
        // Same; no action needed
        return o.getShape().accept(this);
    }

    @Override
    public Location onPolygon(final Polygon s) {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        // Similar to onGroup, but iterates over the points of the polygon
        for (Point p : s.getPoints()) {
            int x = p.getX();
            int y = p.getY();
            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
        }
        return new Location(minX, minY, new Rectangle(maxX - minX, maxY - minY));
    }
}
