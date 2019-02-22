//Mingzhi Xu
package com.company;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MyPolygon extends MyShape{
    private int n;
    private double sideLength;
    public MyPolygon(double x, double y, int n, double sideLength, Color color) {
        super(x,y, color);
        this.n = n;
        this.sideLength = sideLength;
    }
    public String getArea() {
        double getRadians = Math.toRadians(180/this.n);
        return "\nThe Area is " + Math.pow(getSide(),2) * this.n/4 * (Math.tan(getRadians));
    }
    public String getPerimeter() { return "\nThe Perimeter is " + this.n * getSide(); }
    public double getAngle() { return 180*(this.n - 2) / this.n; }
    public double getSide() { return sideLength; }
    public String toString() { return "\nThe Side Length is " + getSide() + "\nThe Interior Angle is " + getAngle() + getArea() + getPerimeter(); }
    public void draw(GraphicsContext gc){
        gc.setFill(super.getColor());
        double[] x_vertices = new double[this.n];
        double[] y_vertices = new double[this.n];
        double angle = (this.n - 1) * getAngle();
        double angle_increment = (2*Math.PI)/this.n;
        int i;
        for (i = 0; i < this.n; i++) {
            x_vertices[i] =  (int)  ((getSide()*Math.cos(angle)) + super.getX());
            y_vertices[i] = (int) ((getSide()*Math.sin(angle)) + super.getY());
            angle += angle_increment;
        }
        gc.strokePolygon(x_vertices, y_vertices, this.n);
        gc.fillPolygon(x_vertices, y_vertices, this.n);
    }
    public void moveTo(double x, double y) { super.moveTo(x,y); }
    public double distanceTo(double x, double y) { return super.distanceTo(x,y); }
    public MyRectangle getBoundingBox() {
        MyRectangle bounding = new MyRectangle(super.getX(), super.getY(), this.getSide(), this.getSide(), super.getColor());
        return bounding;
    }
    public boolean doOverlap(MyShape other) {
        return super.getX() + this.getSide() >= other.getX()
                && super.getX() <= other.getX() + other.getBoundingBox().getWidth()
                && super.getY() + this.getSide() >= other.getY()
                && super.getY() <= other.getY() + other.getBoundingBox().getHeight();
    }
}