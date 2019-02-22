//Mingzhi Xu
package com.company;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
    public String getArea() { return "\nLine has no Area."; }
    public String getPerimeter(){ return "\nThe Perimeter is " + this.getLength(); }
    public String toString() { return "\nThe Length is " + this.getLength() + "\nThe Angle relate to x-axis is " + this.get_xAngle() + " degrees"; }
    public void draw(GraphicsContext gc){
        gc.setStroke(super.getColor());
        gc.setLineWidth(1);
        gc.strokeLine(super.getX(), super.getY(),this.x2, this.y2);
    }
    public void moveTo(double x, double y) { super.moveTo(x,y); }
    public double distanceTo(double x, double y) { return super.distanceTo(x,y); }
    public MyRectangle getBoundingBox() {
        MyRectangle bounding = new MyRectangle(super.getX(), super.getY(), this.getLength(), this.getLength(), super.getColor());
        return bounding;
    }
    public boolean doOverlap(MyShape other) {
        return super.getX() + this.getLength() >= other.getX()
                && super.getX() <= other.getX() + other.getBoundingBox().getWidth()
                && super.getY() + this.getLength() >= other.getY()
                && super.getY() <= other.getY() + other.getBoundingBox().getHeight();
    }
}