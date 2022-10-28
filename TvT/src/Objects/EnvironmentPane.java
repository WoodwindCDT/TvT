package Objects;
import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class EnvironmentPane extends Pane {

    // Page properties
    int bound_top = 0;
    int bound_bot = 600;
    int bound_left = 0;
    int bound_right = 700;

    // Properties for visual interaction
    ArrayList<Object> arr = new ArrayList<Object>(); // ArrayList should contain all objects for rendering
    int score = 0; // for testing
    Text displayScore = new Text(50, 50, Integer.toString(this.score)); // Will get score from controller
    Button addBtn = new Button("Add to Count");
    Controller controller;

    // Objects for which may adjust to inputs
    Line terrain = new Line(0, 50, 700, 50);

    // Default constructor
    public EnvironmentPane() {
        // Setting Pane preferences


        System.out.println("Environmet Pane loaded");
        this.controller = new Controller(this); // Controller will contain reference to Environment Pane for "Control over properties"

        //Adding tanks to controller
         // Creation of tanks, t2 @ 500 due to visual issues with border ** can't see at max
        this.controller.addTank(new Tank("t1", Color.RED, 10, 20, 0, 30, 100, new Missle("m1", Color.RED, 10, 20, 0, 40, 100, 0)), (new Tank("t2", Color.BLUE, 10, 20, 500, 30, 100, new Missle("m2", Color.RED, 10, 20, 0, 40, 100, 0))));

        System.out.println("Controller created and passed ref: " + controller);
        this.arr.add(this.displayScore);
        this.arr.add(this.addBtn);
        this.arr.add(this.terrain);

        // Add tanks to the pane
        for (Tank t : controller.provideTanks()) {
            this.arr.add(t);
        }
        
        addToChildren(this.arr);
    }

    // Display Text to pane via Text object creation and pass to pane children
    public void changeScore(int num) {
        this.score = num;
        this.displayScore.setText(Integer.toString(score));
    }

    public void addToChildren(ArrayList<Object> arr) {
        for (Object object : arr) {
            if (object instanceof Text) {
            ((Text)object).setFont(new Font(30)); // setting font size
            ((Text)object).setFill(Color.AQUA);
            this.getChildren().add(((Text)object));
            }
            if (object instanceof Button) {
                ((Button)object).setOnMouseClicked(e -> {
                    changeScore(++this.score);
                    this.requestFocus(); // fixes issue with button taking focus, focus is reset back to entire pane
                });
                this.getChildren().add((Button)object);
            }
            if (object instanceof Line) {
                this.getChildren().add((Line)object);
            }
            if (object instanceof Tank) {
                Tank t = (Tank)object;
                double[] pos = t.getObjectCurrentPosition();
                Rectangle rec = new Rectangle(pos[0], pos[1], 30, 20); // 30 and 40 are chosen JUST to display
                rec.setFill(t.getColor());
                this.setOnKeyPressed(e -> {
                    this.controller.changeTankPosition(e); // TODO: issue with only one rectangle moving at a time, added boolean to change turn, but still not working
                    rec.setX(t.getObjectCurrentPosition()[0]);
                });
                this.getChildren().add(rec);
            }
        }
    }
}