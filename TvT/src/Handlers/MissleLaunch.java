package Handlers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.QuadCurve;

// Draws path of projectile
public class MissleLaunch {
    
    // creating animation based off of calculations
    Calculation c;
    Controller ctrl;
    Circle circle;
    QuadCurve qc;

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
        createCircle();
        createCurve();
    };

    public Circle getCircle() {
        return this.circle;
    };

    public QuadCurve getQC() {
        return this.qc;
    };
    
    private void createCircle() {
        Color clr;

        if (this.ctrl.getInPlay() == this.ctrl.provideTanks()[1]) {
            clr = Color.BLUE;
        } else {
            clr = Color.RED;
        };

        this.circle = new Circle(20);
        this.circle.setCenterX(this.c.getFinalPosition());
        this.circle.setCenterY(540);
        this.circle.setStroke(clr);
        this.circle.setStrokeWidth(3);
        this.circle.setFill(Color.TRANSPARENT);
    };

    private void createCurve() {
        PositionCapture t = this.ctrl.getInPlay();
        this.qc = new QuadCurve();
        this.qc.setStartX(t.getObjectCurrentPostionX());
        this.qc.setStartY(t.getObjectCurrentPostionY());
        this.qc.setEndX(this.c.getFinalPosition());
        this.qc.setEndY(t.getObjectCurrentPostionY());
        this.qc.setFill(Color.TRANSPARENT);
        this.qc.setStroke(Color.BLACK);
    };
};