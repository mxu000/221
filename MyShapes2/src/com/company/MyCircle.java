//Mingzhi Xu
package com.company;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MyCircle extends MyOval {
    private double radius;
    public MyCircle(double x, double y, double radius, Color color) {
        super(x,y,radius,radius,color);
        this.radius = radius;
    }
    public double getRadius() { return radius; }
    public String getArea() { return "\nThe Area is " + (Math.PI)* Math.pow(getRadius(),2); }
    public String getPerimeter() { return "\nThe Perimeter is " + (Math.PI)*(2*getRadius()); }
    public String toString() { return "\nThe Radius is " + this.getRadius() + this.getArea() + this.getPerimeter(); }
    public void draw(GraphicsContext gc) {
        gc.setFill(super.getColor());
        gc.strokeOval(super.getX(), super.getY(),getRadius(), getRadius());
        gc.fillOval(super.getX(), super.getY(),getRadius(), getRadius());
    }
    public void moveTo(double x, double y) { super.moveTo(x,y); }
    public double distanceTo(double x, double y) { return super.distanceTo(x,y); }
    public MyRectangle getBoundingBox() {
        MyRectangle bounding = new MyRectangle(super.getX(), super.getY(), this.getRadius(), this.getRadius(), super.getColor());
        return bounding;
    }
    public boolean doOverlap(MyShape other) {
        return super.getX() + this.getRadius() >= other.getX()
                && super.getX() <= other.getX() + other.getBoundingBox().getWidth()
                && super.getY() + this.getRadius() >= other.getY()
                && super.getY() <= other.getY() + other.getBoundingBox().getHeight();
    }
}