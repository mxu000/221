//Mingzhi Xu
package com.company;

public interface MyPositionInterface {
    double[][] getPoint();
    void moveTo(double x, double y);
    double distanceTo(double x, double y);
}
