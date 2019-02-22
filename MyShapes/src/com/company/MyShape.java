//Mingzhi Xu
package com.company;
import javafx.scene.canvas.GraphicsContext;
import java.awt.*;

public class MyShape {
    private double x, y;
    private Color color;
    public MyShape(double x, double y, Color color){
        this.x = x;
        this.y = y;
        this.color = color;
    }
    public double getX(){ return this.x; }
    public double getY(){ return this.y; }
    public javafx.scene.paint.Color getColor(){
        java.awt.Color awtColor = this.color;
        int r = awtColor.getRed();
        int g = awtColor.getGreen();
        int b = awtColor.getBlue();
        int a = awtColor.getAlpha();
        double opacity = a / 255.0 ;
        javafx.scene.paint.Color fxColor = javafx.scene.paint.Color.rgb(r, g, b, opacity);
        return fxColor;
    }
    public void setX(double x){ this.x = x; }
    public void setY(double y){ this.y = y; }
    public void setColor(Color color){ this.color = color; }
    public void shiftXY(double x, double y){
        this.x += x;
        this.y += y;
    }
    public String toString(){
        return "The coordinate of X and Y is (" + getX() + "," + getY() + ") and the color is " + getColor();
    }
    public void draw(GraphicsContext gc){
        gc.setFill(getColor());
        gc.fillRect(0, 0, this.x, this.y);
    }
}