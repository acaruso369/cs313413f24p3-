package edu.luc.etl.cs313.android.shapes.model;

/**
 * A visitor to compute the number of basic shapes in a (possibly complex)
 * shape.
 */
public class Count implements Visitor<Integer> {

    // TODO entirely your job

    @Override
    public Integer onPolygon(final Polygon p) {
        // Basic shape returns 1
        return 1;
    }

    @Override
    public Integer onCircle(final Circle c) {
        // Basic shape returns 1
        return 1;
    }

    @Override
    public Integer onGroup(final Group g) {
        // Counts all shapes in the group
        int total = 0;
        for (Shape s : g.getShapes()) {
            total += s.accept(this);
        }
        return total;
    }

    @Override
    public Integer onRectangle(final Rectangle q) {
        // Basic shape, returns 1
        return 1;
    }

    @Override
    public Integer onOutline(final Outline o) {
        // Outline does not impact count
        return o.getShape().accept(this);
    }

    @Override
    public Integer onFill(final Fill c) {
        // Fill does not impact count
        return c.getShape().accept(this);
    }

    @Override
    public Integer onLocation(final Location l) {
        // Location does not impact count
        return l.getShape().accept(this);
    }

    @Override
    public Integer onStrokeColor(final StrokeColor c) {
        // Stroke does not impact count
        return c.getShape().accept(this);
    }
}
