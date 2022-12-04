package Handlers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MissleLaunch {
    
    // creating animation based off of calculations
    Calculation c;
    Controller ctrl;
    Circle circle;

    // default constructor
    public MissleLaunch(Calculation c, Controller ctrl) {
        this.c = c;
        this.ctrl = ctrl;
        System.out.println("Calculation Complete");
        c.print();
        displayHit();
    };
    
    // display missle hit!
    private void displayHit() {
        Color clr;

        if (this.ctrl.getInPlay() == this.ctrl.provideTanks()[1]) {
            clr = Color.BLUE;
        } else {
            clr = Color.RED;
        }

        Circle c = new Circle(25);
        c.setCenterX(this.c.getFinalPosition());
        c.setCenterY(540);
        c.setStroke(clr);
        c.setStrokeWidth(3);
        c.setFill(Color.TRANSPARENT);
        this.circle = c;

        if (this.c.getGameOver()) {
            System.out.println("Game Over!");
            this.ctrl.gameOver();
        };
    };

    public Circle getCircle() {
        return this.circle;
    };
};