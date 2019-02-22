//Mingzhi Xu
package com.company;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane pane = new StackPane();
        Scene scene = new Scene(pane, 800, 500);
        Canvas canvas = new Canvas(800,500);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        /*gc.setStroke(Color.BLACK);//drawing for fun
        gc.setLineWidth(1);
        scene.setOnMousePressed(e -> {
                gc.beginPath();
                gc.lineTo(e.getSceneX(), e.getSceneY());
                gc.stroke();
        });
        scene.setOnMouseDragged(e-> {
        gc.lineTo(e.getSceneX(), e.getSceneY());
        gc.stroke();
         });*/
        MyShape shape1 = new MyShape(800, 500,  Color.black);
        shape1.draw(gc);
        MyCircle circle1 = new MyCircle(150, 0, 500, Color.white);
        circle1.draw(gc);
        MyPolygon polygon1 = new MyPolygon(400,250, 5, 250, Color.black);
        polygon1.draw(gc);
        MyCircle circle2 = new MyCircle(200, 50, 400, Color.red);
        circle2.draw(gc);
        MyPolygon polygon2 = new MyPolygon(400,250, 5, 200, Color.black);
        polygon2.draw(gc);
        MyCircle circle3 = new MyCircle(250, 100, 300, Color.white);
        circle3.draw(gc);
        MyPolygon polygon3 = new MyPolygon(400,250, 5, 150, Color.black);
        polygon3.draw(gc);
        MyLine line1 = new MyLine(0, 800, 0, 500, Color.green);
        line1.draw(gc);
        MyLine line2 = new MyLine(800, 0, 0, 500, Color.green);
        line2.draw(gc);

        pane.getChildren().add(canvas);
        primaryStage.setTitle("MyShape");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}