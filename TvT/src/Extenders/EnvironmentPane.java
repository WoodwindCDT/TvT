package Extenders;
import java.util.ArrayList;

import Handlers.Controller;
import Objects.Tank;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class EnvironmentPane extends Pane {
    // Properties for visual interaction
    private ArrayList<Object> arr = new ArrayList<Object>(); // ArrayList should contain all objects for rendering
    // displayScore will be fed by controller
    // Text displayScore = new Text(50, 50, Integer.toString()); // Will get score from controller
    private Text displayMessage = new Text(150, 120, "");
    private Text displayPlayer = new Text(200, 300, "");
    private Controller controller;
    private Line sLine = new Line(0, 80, 700, 80);
    

    // Objects for which may adjust to inputs
    private Line terrain = new Line(0, 575, 700, 575);
    private Text tank1HPText = new Text(50, 25, "Tank 1 Health: ");
    private Text tank2HPText = new Text(400, 25, "Tank 2 Health: ");
    private Text powerText = new Text(50, 65, "Power: ");
    private Text angleText = new Text(400, 65, "Angle: ");
    private Line tank1Gun;
    private Line tank2Gun;
    private Circle trench;
    private QuadCurve path;

    // Default constructor
    public EnvironmentPane() {
        // Setting Pane preferences
        System.out.println("Environmet Pane loaded");
        this.controller = new Controller(this); // Controller will contain reference to Environment Pane for "Control over properties"

        System.out.println("Controller created and passed ref: " + controller);

        this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, getInsets())));

        setAll();
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
            ((Text)object).setFont(new Font(25)); // setting font size
            ((Text)object).setFill(Color.YELLOW);
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
                t.print();
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
        this.tank2HPText.setText("Tank 2 Health: " + t2HP);
    }

    public void highlightTank1() {
        this.tank1HPText.setFill(Color.GREENYELLOW);
        this.tank2HPText.setFill(Color.DARKVIOLET);
    }

    public void highlightTank2() {
        this.tank2HPText.setFill(Color.GREENYELLOW);
        this.tank1HPText.setFill(Color.DARKVIOLET);
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

    public void setVisual(Circle c, Shape qc) {
        this.trench = c;
        this.path = (QuadCurve)qc;
        this.getChildren().add(this.trench);
        this.getChildren().add(this.path);
    }

    public void graphPlot(Circle c) {
        this.getChildren().add(c);
    }

    public void removeVisual() {
        this.getChildren().remove(this.trench);
        this.getChildren().remove(this.path);
    };

    // Takes the current position of the tank, checks the firing angle, and adjusts the tankGun line
    public void rotateTank1Gun(double x, double angle) {
        if (angle <= 90){
            this.tank1Gun.setEndX(x + 41);
            this.tank1Gun.setEndY(530);
        }
        else if (angle <= 115) {
            this.tank1Gun.setEndX(x + 43);
            this.tank1Gun.setEndY(527);
        }
        else if (angle <= 130) {
            this.tank1Gun.setEndX(x + 41.5);
            this.tank1Gun.setEndY(526);
        }
        else if (angle <= 145) {
            this.tank1Gun.setEndX(x + 40);
            this.tank1Gun.setEndY(525);
        }
        else if (angle <= 160) {
            this.tank1Gun.setEndX(x + 37);
            this.tank1Gun.setEndY(523);
        }
        else if (angle <= 165) {
            this.tank1Gun.setEndX(x + 35);
            this.tank1Gun.setEndY(520);
        }
        else if (angle <= 175) {
            this.tank1Gun.setEndX(x + 32.5);
            this.tank1Gun.setEndY(517);
        }
        else if (angle <= 180) {
            this.tank1Gun.setEndX(x + 30);
            this.tank1Gun.setEndY(515);
        }
    }

    public void rotateTank2Gun(double x, double angle) {
        if (angle <= 90){
            this.tank2Gun.setEndX(x - 11);
            this.tank2Gun.setEndY(530);
        }
        else if (angle <= 115) {
            this.tank2Gun.setEndX(x - 13);
            this.tank2Gun.setEndY(527);
        }
        else if (angle <= 130) {
            this.tank2Gun.setEndX(x - 11.5);
            this.tank2Gun.setEndY(526);
        }
        else if (angle <= 145) {
            this.tank2Gun.setEndX(x - 10);
            this.tank2Gun.setEndY(525);
        }
        else if (angle <= 160) {
            this.tank2Gun.setEndX(x - 7);
            this.tank2Gun.setEndY(523);
        }
        else if (angle <= 165) {
            this.tank2Gun.setEndX(x - 5);
            this.tank2Gun.setEndY(520);
        }
        else if (angle <= 175) {
            this.tank2Gun.setEndX(x - 3);
            this.tank2Gun.setEndY(517);
        }
        else if (angle <= 180) {
            this.tank2Gun.setEndX(x);
            this.tank2Gun.setEndY(515);
        }
    }

    public void removeAll() {
        this.getChildren().clear();
        this.arr.clear();
    };

    public void setAll() {
        // Add tanks to the pane
        this.tank1Gun = new Line(120, 530, 130, 525);
        this.tank2Gun = new Line(500, 530, 490, 525);
        this.tank1Gun.setStroke(Color.WHEAT);
        this.tank2Gun.setStroke(Color.WHEAT);
        this.terrain.setStrokeWidth(70);
        this.terrain.setStroke(Color.DARKGREEN);
        addToArr();
        for (Tank t : this.controller.provideTanks()) {
            this.arr.add(t);
        };
        addToChildren(this.arr);
        highlightTank1();
        stars();
    };

    private void addToArr() {
        this.arr.add(this.displayMessage);
        this.arr.add(this.displayPlayer);
        this.arr.add(this.terrain);
        this.arr.add(this.tank1HPText);
        this.arr.add(this.tank2HPText);
        this.arr.add(powerText);
        this.arr.add(angleText);
        this.arr.add(sLine);
        this.arr.add(tank1Gun);
        this.arr.add(tank2Gun);
    };

    // create stars
    private void stars() {
        for (int i = 0; i < 30; i++) {
            double rndX = 1 + Math.random() * (699 - 1);
            double rndY = 100 + Math.random() * (499 - 100);
            Circle c = new Circle(rndX, rndY, 2, Color.WHITE);
            this.getChildren().add(c);
        }
    }
};