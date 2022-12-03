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
    Line sLine = new Line(0, 80, 700, 80);
    

    // Objects for which may adjust to inputs
    Line terrain = new Line(0, 550, 700, 550);
    Text tank1HPText = new Text(50, 25, "Tank 1 Health: ");
    Text tank2HPText = new Text(400, 25, "Tank 2 Health: ");
    Text powerText = new Text(50, 65, "Power: ");
    Text angleText = new Text(400, 65, "Angle: ");
    Line tank1Gun = new Line(120, 530, 130, 525);
    Line tank2Gun = new Line(500, 530, 490, 525);

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
        this.arr.add(this.tank1HPText);
        this.arr.add(this.tank2HPText);
        this.arr.add(powerText);
        this.arr.add(angleText);
        this.arr.add(sLine);
        this.arr.add(tank1Gun);
        this.arr.add(tank2Gun);

        // Add tanks to the pane
        for (Tank t : controller.provideTanks()) {
            this.arr.add(t);
        };
        
        addToChildren(this.arr);
        highlightTank1();
    };

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

    public void changeAngle(double angle) {
        this.angleText.setText("Angle: " + angle);
    }

    public void changePower(double power) {
        this.powerText.setText("Power: " + power);
    }

    public void changeHealth(double t1HP, double t2HP) {
        this.tank1HPText.setText("Tank 1 Health: " + t1HP);
        this.tank1HPText.setText("Tank 2 Health: " + t2HP);
    }

    public void highlightTank1() {
        this.tank1HPText.setFill(Color.GREENYELLOW);
        this.tank2HPText.setFill(Color.GRAY);
    }

    public void highlightTank2() {
        this.tank2HPText.setFill(Color.GREENYELLOW);
        this.tank1HPText.setFill(Color.GRAY);
    }

    public void moveAngleText(double x, double y) {
        this.angleText.setX(x - 25);
        this.angleText.setY(y - 20);
    }

    public void movePowerText(double x, double y) {
        this.powerText.setX(x - 25);
        this.powerText.setY(y - 50);
    }

    // Takes the speed of the tank and moves the tank, needs a negative if moving left 
    public void moveTank1GunX(double speed) {
        this.tank1Gun.setStartX(this.tank1Gun.getStartX() + speed);
        this.tank1Gun.setEndX(this.tank1Gun.getEndX() + speed);
    };

    public void moveTank2GunX(double speed) {
        this.tank2Gun.setStartX(this.tank2Gun.getStartX() + speed);
        this.tank2Gun.setEndX(this.tank2Gun.getEndX() + speed);
    };

    // Takes the current position of the tank, checks the firing angle, and adjusts the tankGun line
    public void rotateTank1Gun(double x, double angle) {
        if (angle <= 10){
            this.tank1Gun.setEndX(x + 45);
            this.tank1Gun.setEndY(530);
        }
        else if (angle <= 25) {
            this.tank1Gun.setEndX(x + 43);
            this.tank1Gun.setEndY(527);
        }
        else if (angle <= 40) {
            this.tank1Gun.setEndX(x + 41.5);
            this.tank1Gun.setEndY(526);
        }
        else if (angle <= 50) {
            this.tank1Gun.setEndX(x + 40);
            this.tank1Gun.setEndY(525);
        }
        else if (angle <= 60) {
            this.tank1Gun.setEndX(x + 37);
            this.tank1Gun.setEndY(523);
        }
        else if (angle <= 70) {
            this.tank1Gun.setEndX(x + 35);
            this.tank1Gun.setEndY(520);
        }
        else if (angle <= 80) {
            this.tank1Gun.setEndX(x + 32.5);
            this.tank1Gun.setEndY(517);
        }
        else if (angle <= 90) {
            this.tank1Gun.setEndX(x + 30);
            this.tank1Gun.setEndY(515);
        }
    }

    public void rotateTank2Gun(double x, double angle) {
        if (angle <= 10){
            this.tank2Gun.setEndX(x - 15);
            this.tank2Gun.setEndY(530);
        }
        else if (angle <= 25) {
            this.tank2Gun.setEndX(x - 13);
            this.tank2Gun.setEndY(527);
        }
        else if (angle <= 40) {
            this.tank2Gun.setEndX(x - 11.5);
            this.tank2Gun.setEndY(526);
        }
        else if (angle <= 50) {
            this.tank2Gun.setEndX(x - 10);
            this.tank2Gun.setEndY(525);
        }
        else if (angle <= 60) {
            this.tank2Gun.setEndX(x - 7);
            this.tank2Gun.setEndY(523);
        }
        else if (angle <= 70) {
            this.tank2Gun.setEndX(x - 5);
            this.tank2Gun.setEndY(520);
        }
        else if (angle <= 80) {
            this.tank2Gun.setEndX(x - 3);
            this.tank2Gun.setEndY(517);
        }
        else if (angle <= 90) {
            this.tank2Gun.setEndX(x);
            this.tank2Gun.setEndY(515);
        }
    }
};