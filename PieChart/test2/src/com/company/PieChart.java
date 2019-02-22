//Mingzhi Xu
package com.company;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class PieChart {
    private int n;
    private double[] freq;
    private char[] Letters;
    public PieChart(int n, double[] freq, char[] Letters) {
        this.n = n;
        this.freq = freq;
        this.Letters = Letters;
    }
    public void draw(GraphicsContext gc){
        int i;
        //Starting Angle Positions
        double[] angP = new double[26];
        double total = 0;
        for(i = 0; i < 26; i++){
            angP[i] = total;
            total += this.freq[i] * 360;
        }
        //Draws Pie Chart
        if(this.n !=26) {
            //All Other Events
            double pchart;
            gc.setFill(Color.color(Math.random(), Math.random(), Math.random()));
            if(this.n == 0){
                pchart = 0;
                gc.fillArc(100, 100, 300, 300, angP[n], 360 - pchart, ArcType.ROUND);
            }
            else {
                pchart = (((Math.round(angP[n] * 10000d) / 10000d)) / 360);
                gc.fillArc(100, 100, 300, 300, angP[n], 374.3 - angP[n], ArcType.ROUND);
            }
            gc.fillText("All Other Events: " + Math.round((1 - pchart) * 10000d) / 10000d, 200, 500);
            gc.fillRect(175, 490, 10, 10);
        }
        //Input n Events
        for(i = 1; i < this.n + 1; i++){
            gc.setFill(Color.color(Math.random(), Math.random(), Math.random()));
            gc.fillArc(100, 100, 300, 300, angP[i - 1], this.freq[i - 1] * 360 + 0.55, ArcType.ROUND);
            gc.fillText("" + this.Letters[i - 1] + ": " + Math.round(this.freq[i - 1] * 10000d) / 10000d, 500, 50 + ((i-1) * 20));
            gc.fillRect(475, ((i) * 20) + 20, 10, 10);
        }
    }
}