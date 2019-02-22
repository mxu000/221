//Mingzhi Xu
package com.company;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class HistogramLetters extends Application{
    //Check if user input is int
    private boolean isInt(String line) {
        try{
            int num = Integer.parseInt(line);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }

    @Override public void start(Stage primaryStage) {
        //Read textfile
        Scanner scan = null;
        try { scan = new Scanner(new File("D:/Pokemon/Other_Shit/CCNY/FALL2018/CSC221/Assignments/PieChart/test2/src/com/company/Emma.txt")); }
        catch (FileNotFoundException e) {
            e.printStackTrace(); //No file found
        }
        //Count Letters
        char[] Letters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        int[] Lettercount = new int[26];
        int i, j;
        char[] current;
        String readline;
        int total = 0;
        while(scan.hasNextLine()) {
            readline = scan.nextLine();
            current = readline.toCharArray();
            for(i = 0; i < current.length; i++) {
                for(j = 0; j < 26; j++) {
                    if (current[i] == Letters[j]) {
                        Lettercount[j]++;
                        total++;
                        break;
                    }
                }
            }
        }
        double []freq = new double[26];
        for(i = 0; i < 26; i++){
            freq[i]=(Lettercount[i] /  (double) total);//the count of letters divided by frequency
        }
        //Sort Letters and Frequency in order
        for (i = 0; i < freq.length; i++) {
            int m = i;
            for (j = i; j < freq.length; j++) {
                if (freq[j] > freq[m]) {
                    m = j;
                }
            }
            //sort frequency using a temp variable
            double temp1;
            temp1 = freq[i];
            freq[i] = freq[m];
            freq[m] = temp1;
            //sort the letters with a temp variable
            char temp2;
            temp2 = Letters[i];
            Letters[i] = Letters[m];
            Letters[m] = temp2;
        }
        //Build Stage
        Scene scene1, scene2;
        Stage s = primaryStage;
        s.setTitle("Pie Chart");
        TextField input = new TextField();
        Label label = new Label();
        Label label2 = new Label();
        label.setText(null);
        label2.setText("Enter a number n (0 - 26): ");

        //Layout for PieChart
        StackPane layout2 = new StackPane();
        scene2 = new Scene(layout2, 800, 600);
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        layout2.getChildren().add(canvas);

        Button button = new Button("Display Pie Chart");
        button.setOnAction(e -> {
            if(isInt(input.getText()) == true) {
                int num = Integer.parseInt(input.getText());
                if (num > 26 || num < 0) {
                    label.setText("There are only 26 letters in the alphabet");
                } else {
                    label.setText("Displaying PieChart");
                    PieChart pc = new PieChart(num, freq, Letters);
                    s.setScene(scene2);
                    pc.draw(gc);
                }
            }
            else label.setText("Error Not A Number");
        });
        //Layout to input n
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20,15,0,15));
        layout.getChildren().addAll(label2, input, button, label);
        scene1 = new Scene(layout, 300, 200);

        s.setScene(scene1);
        s.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}