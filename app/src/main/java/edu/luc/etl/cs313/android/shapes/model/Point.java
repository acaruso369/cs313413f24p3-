package edu.luc.etl.cs313.android.shapes.model;

/**
 * A point, implemented as a location without a shape.
 */
public class Point extends Location {

    // TODO your job
    // HINT: use a circle with radius 0 as the shape!

    public Point(final int x, final int y) {
        // Circle with radius 0, uses the Location of (x,y)
        super(x, y, new Circle(0));
        // Guards
        assert x >= 0;
        assert y >= 0;
    }
}
