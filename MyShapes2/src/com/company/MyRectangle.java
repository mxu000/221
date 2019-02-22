//Mingzhi Xu
package com.company;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MyRectangle extends MyShape{
    private double height, width;
    public MyRectangle(double x, double y, double h, double w, Color color) {
        super(x,y, color);
        this.height = h;
        this.width = w;
    }
    public double getHeight(){ return this.height; }
    public double getWidth(){ return this.width; }
    public String getArea() { return "\nThe Area is " + (this.getHeight() * this.getWidth());}
    public String getPerimeter() { return "\nThe Perimeter is " + 2 * (this.getHeight() + this.getWidth()); }
    public void draw(GraphicsContext gc) {
        gc.setFill(super.getColor());
        gc.strokeRect(super.getX(), super.getY(), this.getWidth(), this.getHeight());
        gc.fillRect(super.getX(), super.getY(), this.getWidth(), this.getHeight());
    }
    public MyRectangle getBoundingBox() {
        MyRectangle bounding = new MyRectangle(super.getX(), super.getY(), this.getHeight(), this.getWidth(), super.getColor());
        return bounding;
    }
    public boolean doOverlap(MyShape other) {
        return super.getX() + this.getWidth() >= other.getX()
                && super.getX() <= other.getX() + other.getBoundingBox().getWidth()
                && super.getY() + this.getHeight() >= other.getY()
                && super.getY() <= other.getY() + other.getBoundingBox().getHeight();
    }
}
