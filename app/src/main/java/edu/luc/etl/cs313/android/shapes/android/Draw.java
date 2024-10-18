package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import edu.luc.etl.cs313.android.shapes.model.*;

/**
 * A Visitor for drawing a shape to an Android canvas.
 */
public class Draw implements Visitor<Void> {

    // TODO entirely your job (except onCircle)

    private final Canvas canvas;

    private final Paint paint;

    public Draw(final Canvas canvas, final Paint paint) {
        // Initializing canvas and paint
        this.canvas = canvas; // FIXME
        this.paint = paint; // FIXME
        paint.setStyle(Style.STROKE);
    }

    @Override
    public Void onCircle(final Circle c) {
        // This draws a circle at coords (0,0)
        canvas.drawCircle(0, 0, c.getRadius(), paint);
        canvas.translate(-200,-100);
        return null;
    }

    @Override
    public Void onStrokeColor(final StrokeColor c) {
        // Get the first original color and style
        int firstColor = paint.getColor();
        Style firstStyle = paint.getStyle();
        // Stroke color
        paint.setColor(c.getColor());
        // This draws the shape with the new color
        c.getShape().accept(this);
        // Set back to originals
        paint.setColor(firstColor);
        paint.setStyle(Style.FILL_AND_STROKE);
        return null;
    }

    @Override
    public Void onFill(final Fill f) {
        // Get original paint style
        Style firstStyle = paint.getStyle();
        paint.setStyle(Style.FILL);
        // This will draw a filled shape
        f.getShape().accept(this);
        // Reset again
        paint.setStyle(firstStyle);
        return null;
    }

    @Override
    public Void onGroup(final Group g) {
        // This will iterate for all shapes in one group
        for (Shape s : g.getShapes()){
            // for each shape
            s.accept(this);
        }
        return null;
    }

    @Override
    public Void onLocation(final Location l) {
        canvas.save();
        // Moves the canvas to a new location
        canvas.translate(l.getX(), l.getY());
        // Draw the shape at the new location
        l.getShape().accept(this);
        // Reset again
        canvas.restore();
        return null;
    }

    @Override
    public Void onRectangle(final Rectangle r) {
        // Draws a rectangle at (0,0) with the given dimensions
        paint.setStyle(Style.FILL_AND_STROKE);
        canvas.drawRect(0, 0, r.getWidth(), r.getHeight(), paint);
        canvas.translate(-400,-300);
        canvas.translate(-70,-30);
        return null;
    }

    @Override
    public Void onOutline(Outline o) {
        Style firstStyle = paint.getStyle();
        paint.setStyle(Style.STROKE);
        // Draws the outline only
        o.getShape().accept(this);
        // Reset
        paint.setStyle(firstStyle);
        return null;
    }

    @Override
    public Void onPolygon(final Polygon s) {
        final float[] pts = new float[4 * s.getPoints().size()];
        // This iteration calculates all vertices
        for (int i = 0; i < s.getPoints().size(); i++) {
            Point p1 = s.getPoints().get(i);
            Point p2 = s.getPoints().get((i + 1) % s.getPoints().size());
            pts[4 * i] = p1.getX();
            pts[4 * i + 1] = p1.getY();
            pts[4 * i + 2] = p2.getX();
            pts[4 * i + 3] = p2.getY();
        }
        // This will draw all points and lines
        canvas.drawLines(pts, paint);
        return null;
    }
}
