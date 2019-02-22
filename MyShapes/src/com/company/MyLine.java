//Mingzhi Xu
package com.company;
import javafx.scene.canvas.GraphicsContext;
import java.awt.Color;

public class MyLine extends MyShape {
    private double x2, y2;
    public MyLine(double x1, double x2, double y1, double y2, Color color) {
        super(x1, y1,color);
        this.x2 = x2;
        this.y2 = y2;
    }
    public double getLength() {
        double xsq = Math.pow(this.x2 - getX(), 2);
        double ysq = Math.pow(this.y2 - getY(), 2);
        double length = Math.sqrt(xsq + ysq);
        return length;
    }
    public double get_xAngle() {
        double xAngle = Math.toDegrees(Math.atan((this.y2 - getY())/(this.x2 - getX())));
        return xAngle;
    }
    @Override
    public String toString() {
        return "The length of this line is " + this.getLength() + " with an angle relate to the x-axis of "
                + this.get_xAngle() + " degrees";
    }
    @Override
    public void draw(GraphicsContext gc){
        gc.setStroke(super.getColor());
        gc.setLineWidth(1);
        gc.strokeLine(super.getX(), super.getY(),this.x2, this.y2);
    }
}