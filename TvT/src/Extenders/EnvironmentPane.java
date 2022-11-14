package Extenders;
import java.util.ArrayList;

import Handlers.Controller;
import Objects.Tank;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class EnvironmentPane extends Pane {
    // Properties for visual interaction
    ArrayList<Object> arr = new ArrayList<Object>(); // ArrayList should contain all objects for rendering
    // displayScore will be fed by controller
    // Text displayScore = new Text(50, 50, Integer.toString()); // Will get score from controller
    Text displayMessage = new Text(100, 200, "");
    Text displayPlayer = new Text(200, 300, "");
    Button addBtn = new Button("Add to Count");
    Controller controller;

    // Objects for which may adjust to inputs
    Line terrain = new Line(0, 50, 700, 50);

    // Default constructor
    public EnvironmentPane() {
        // Setting Pane preferences
        System.out.println("Environmet Pane loaded");
        this.controller = new Controller(this); // Controller will contain reference to Environment Pane for "Control over properties"

        System.out.println("Controller created and passed ref: " + controller);
        this.arr.add(this.displayMessage);
        this.arr.add(this.displayPlayer);
        this.arr.add(this.addBtn);
        this.arr.add(this.terrain);

        // Add tanks to the pane
        for (Tank t : controller.provideTanks()) {
            this.arr.add(t);
        };
        
        addToChildren(this.arr);
    };

    // Display Text to pane via Text object creation and pass to pane children
    // public void changeScore(int num) {
    //     this.score = num;
    //     this.displayScore.setText(Integer.toString(score));
    // }

    // Display Text to pane via Text object creation and pass to pane children
    public void changeMessage(char type, String msg) {
        if (type == 'm') this.displayMessage.setText(msg);
        if (type == 'p') this.displayPlayer.setText(msg);
    };

    // Adding children to pane, and adding event listener to tank objects
    public void addToChildren(ArrayList<Object> arr) {
        for (Object object : arr) {
            if (object instanceof Text) {
            ((Text)object).setFont(new Font(30)); // setting font size
            ((Text)object).setFill(Color.AQUA);
            this.getChildren().add(((Text)object));
            };
            if (object instanceof Line) {
                this.getChildren().add((Line)object);
            };
            if (object instanceof Tank) {
                Tank t = (Tank)object;
                // Likely reduce this to a single function
                double[] pos = t.getObjectCurrentPosition();
                // setting body of tank ? should be done during object creation "testing purposes"
                // Rectangle set at Tanks position in position capture
                Rectangle rec = new Rectangle(pos[0], pos[1], 30, 20); // 30 and 20 are chosen JUST to display
                rec.setFill(t.getColor());
                t.setBody(rec);
                this.setOnKeyPressed(e -> {
                    this.controller.changeTankPosition(e); // controller changes the tanks position with position capture
                });
                this.getChildren().add(t.getBody());
            };
        };
    };
};