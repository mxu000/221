//Mingzhi Xu
package com.company;
import javafx.scene.canvas.GraphicsContext;
import java.awt.*;

public class MyPolygon extends MyShape{
    private int n;
    private double sideLength;
    public MyPolygon(double x, double y, int n, double sideLength, Color color) {
        super(x,y, color);
        this.n = n;
        this.sideLength = sideLength;
    }
    public double getArea() {
        double getRadians = Math.toRadians(180/this.n);
        return Math.pow(getSide(),2) * this.n/4 * (Math.tan(getRadians));
    }
    public double getPerimeter() {
        return this.n * getSide();
    }
    public double getAngle() {
        return 180*(this.n - 2)/ this.n;
    }
    public double getSide() {
        return sideLength;
    }
    public String toString() {
        return "This polygon has side length of " + getSide() + " , an interior angle of " + getAngle() +
                " , a perimeter of " + getPerimeter() + " and an area of " + getArea();
    }
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
}