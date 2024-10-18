package edu.luc.etl.cs313.android.shapes.model;

/**
 * A decorator for specifying the stroke (foreground) color for drawing the
 * shape.
 */
public class StrokeColor implements Shape {

    // TODO entirely your job
    // Initializing

    private final int color;
    private final Shape shape;

    public StrokeColor(final int color, final Shape shape) {
        this.color = color;
        this.shape = shape;
    }

    // Gets the color and shape, no further action
    public int getColor() {
        return color;
    }

    public Shape getShape() {
        return shape;
    }

    @Override
    public <Result> Result accept(Visitor<Result> v) {
        // Passing Visitor instance again
        return v.onStrokeColor(this);
    }
}
