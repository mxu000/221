//Mingzhi Xu
package com.company;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MyOval extends MyShape{
    private double radiusW, radiusH;
    public MyOval(double x, double y, double w, double h, Color color) {
        super(x,y,color);
        this.radiusW = w;
        this.radiusH = h;
    }
    public double getRadiusW() { return this.radiusW; }
    public double getRadiusH() { return this.radiusH; }
    public String getArea() { return "\nThe Area is " + ((Math.PI) * this.getRadiusH() * this.getRadiusW()); }
    public String getPerimeter() { return "\nThe Perimeter is " +
            ((Math.PI) * (3 * (this.getRadiusW() + this.getRadiusH()) - Math.sqrt((3 * this.getRadiusH() + this.getRadiusW())
                    * (this.getRadiusH() + 3 * this.getRadiusW()))));
    }//Ramanujan Formula
    public String toString() { return "\nThe RadiusW is " + this.getRadiusW() + "\nThe RadiusH is " + this.getRadiusH() + this.getArea() + this.getPerimeter(); }
    public void draw(GraphicsContext gc) {
        gc.setFill(super.getColor());
        gc.strokeOval(super.getX(), super.getY(),getRadiusW(), getRadiusH());
        gc.fillOval(super.getX(), super.getY(),getRadiusW(), getRadiusH());
    }
    public void moveTo(double x, double y) { super.moveTo(x,y); }
    public double distanceTo(double x, double y) { return super.distanceTo(x,y); }
    public MyRectangle getBoundingBox() {
        MyRectangle bounding = new MyRectangle(super.getX(), super.getY(), this.getRadiusH(), this.getRadiusW(), super.getColor());
        return bounding;
    }
    public boolean doOverlap(MyShape other) {
        return super.getX() + this.getRadiusW() >= other.getX()
                && super.getX() <= other.getX() + other.getBoundingBox().getWidth()
                && super.getY() + this.getRadiusH() >= other.getY()
                && super.getY() <= other.getY() + other.getBoundingBox().getHeight();
    }
}
