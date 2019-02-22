//Mingzhi Xu
package com.company;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class MyShape implements MyShapePositionInterface {
    private double x, y;
    private Color color;
    public MyShape(double x, double y, Color color){
        this.x = x;
        this.y = y;
        this.color = color;
    }
    public abstract String getArea();
    public abstract String getPerimeter();
    public abstract MyRectangle getBoundingBox();
    public abstract boolean doOverlap(MyShape other);
    public abstract void draw(GraphicsContext gc);
    public double getX(){ return this.x; }
    public double getY(){ return this.y; }
    public Color getColor(){ return this.color; }
    public void setX(double x){ this.x = x; }
    public void setY(double y){ this.y = y; }
    public void setColor(Color color){ this.color = color; }
    public double[][] getPoint() {
        double[][] xy = new double[2][1];
        xy[0][0] = this.getX();
        xy[1][0] = this.getY();
        return xy;
    }
    public void moveTo(double x, double y){
        this.x += x;
        this.y += y;
    }
    public double distanceTo(double x2, double y2) { return Math.hypot(getX() - x2, getX() - y2); }
    public String toString(){ return "\nThe coordinate of X and Y is (" + getX() + "," + getY() + ") \nThe Color is" + getColor(); }
}
