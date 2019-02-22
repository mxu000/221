//Mingzhi Xu
package com.company;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane pane = new StackPane();
        Scene scene = new Scene(pane, 800, 500);
        Canvas canvas = new Canvas(800,500);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        MyRectangle rect1 = new MyRectangle(100, 60, 380, 600, Color.BLACK);
        rect1.draw(gc);
        MyOval oval1 = new MyOval(100,60,600,380,Color.WHITE);
        oval1.draw(gc);
        MyRectangle rect2 = new MyRectangle(185, 115, 269, 426, Color.RED);
        rect2.draw(gc);
        MyOval oval2 = new MyOval(185,115,426,269,Color.BLACK);
        oval2.draw(gc);
        MyRectangle rect3 = new MyRectangle(248, 155, 190, 302, Color.WHITE);
        rect3.draw(gc);
        MyOval oval3 = new MyOval(248,155,302,190,Color.RED);
        oval3.draw(gc);
        MyLine line1 = new MyLine(0, 800, 0, 500, Color.GREEN);
        line1.draw(gc);

        pane.getChildren().add(canvas);
        primaryStage.setTitle("MyShape");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}