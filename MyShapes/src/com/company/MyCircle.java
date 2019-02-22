//Mingzhi Xu
package com.company;
import javafx.scene.canvas.GraphicsContext;
import java.awt.Color;

public class MyCircle extends MyShape {
    private double radius;
    public MyCircle(double x, double y, double radius, Color color) {
        super(x,y,color);
        this.radius = radius;
    }
    public double getRadius() {
        return radius;
    }
    public double getArea() {
        return (Math.PI)* Math.pow(getRadius(),2);
    }
    public double getPerimeter() {
        return (Math.PI)*(2*getRadius());
    }
    @Override
    public String toString() {
        return "The circle has a radius of " + this.getRadius() + " with a perimeter of " + this.getPerimeter() +
                " and an area of " + this.getArea();
    }
    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(super.getColor());
        gc.strokeOval(super.getX(), super.getY(),getRadius(), getRadius());
        gc.fillOval(super.getX(), super.getY(),getRadius(), getRadius());
    }
}