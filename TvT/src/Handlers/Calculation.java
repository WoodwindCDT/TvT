package Handlers;

// Importing static context for this class
import static Assets.Constants.GRAVITY;
import static Assets.Constants.calcTOF_NOHEIGHT;
import static Assets.Constants.calcTOF_WITHHEIGHT;
import static Assets.Constants.calcVel_X;
import static Assets.Constants.calcVel_Y;
import static Assets.Constants.calcYmax_NOHEIGHT;
import static Assets.Constants.calcYmax_WITHHEIGHT;
import static Assets.Constants.calcfPos_X_NOHEIGHT;
import static Assets.Constants.calcfPos_X_WITHHEIGHT;

import Objects.Tank;

// Calculation class uses constant functions to provide feedback to controller/positioncapture
public class Calculation {
    
    private PositionCapture[] tanks;
    private double velocity; // power
    private double angle; // angle provided
    private boolean loaded = false; // loaded is true when the calculation are completed -> should await next user input to fire missle

    public Calculation(PositionCapture[] tanks, double velocity, double angle) {
        System.out.println("New Calculation starting...");
        this.tanks = tanks;
        this.velocity = velocity;
        this.angle = angle;
        checkLaunch();
    };

    // checking if launch conditions are a go!
    // velocity should be greater than 0, angle should be equal or greater than 0 degrees (0 > 90 < 180)
    // tanks should not be null for launch
    private void checkLaunch() {
        if (this.velocity > 0 && this.angle >= 0 && this.angle <= 180 && this.tanks != null) {
            this.loaded = true;
            System.out.println("Arguments passed check");
            return;
        };
        System.out.println("Arguments failed check");
    };

    // Missle loaded! Ready for launch by user
    public boolean readyToFire() {
        return this.loaded;
    };

    public void printLastAction() {
        System.out.println(
            "Last Action Conditions: " + '\n' +
            "---------------------------------------"
        );
        for (PositionCapture t : this.tanks) {
            ((Tank)t).print();
        };
    };

    // prints calculation properties
    public void print() {
        System.out.println(
            "Calculation Results: " + '\n' +
            "---------------------------------------" + '\n' +
            "Initial Velocity (POWER): " + this.velocity + '\n' +
            "Angle: " + this.angle + '\n'
        );
    }
};