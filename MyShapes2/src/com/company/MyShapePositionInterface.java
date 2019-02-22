//Mingzhi Xu
package com.company;

public interface MyShapePositionInterface extends MyShapeInterface, MyPositionInterface{
    MyRectangle getBoundingBox();
    boolean doOverlap(MyShape other);
}
