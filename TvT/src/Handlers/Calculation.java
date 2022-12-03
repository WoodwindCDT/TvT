package Handlers;

// Importing static context for this class
import static Assets.Constants.calcTOF_NOHEIGHT;
import static Assets.Constants.calcTOF_WITHHEIGHT;
import static Assets.Constants.calcVel_X;
import static Assets.Constants.calcVel_Y;
import static Assets.Constants.calcYmax_NOHEIGHT;
import static Assets.Constants.calcYmax_WITHHEIGHT;
import static Assets.Constants.calcfPos_X_NOHEIGHT;
import static Assets.Constants.calcfPos_X_WITHHEIGHT;
import static Assets.Constants.calc_vyS;
import static Assets.Constants.toRad;

import Interfaces.CalcResultInterface;
import Objects.Tank;

// Calculation class uses constant functions to provide feedback to controller/positioncapture
public class Calculation {

    protected PositionCapture[] tanks;
    private PositionCapture in_Play;
    protected double velocity; // power
    protected double angle; // angle provided
    protected double height;
    private boolean loaded = false; // loaded is true when the calculation are completed

    // final results
    private double vx; // velocity of object
    private double vy; // velocity of object
    private double tof; // time of flight
    private double capH; // max height reached
    private double Xf; // final position x of projectile

    // TODO: Add missle damage
    // TODO: Adjust calculations for other enemy tank via comparing reference of object with second tank
    // result with no height kinematics
    protected class CalcNOHeight implements CalcResultInterface {
        // complete calcs
        public CalcNOHeight() {
            funk_tof();
            funk_cmh();
            funk_fp();
        };

        // Calculating time of flight
        public void funk_tof() {
            setTimeofFlight(calcTOF_NOHEIGHT(getVelocity_Y()));
        };

        // Calculating max height
        public void funk_cmh() {
            double vyS = calc_vyS(getVelocity_Y());
            setMaxHeight(calcYmax_NOHEIGHT(vyS) + in_Play.getObjectCurrentPostionY());
        };

        // Calculating final position
        public void funk_fp() {
            setFinalPosition(calcfPos_X_NOHEIGHT(getVelocity_X(), getVelocity_Y()) + in_Play.getObjectCurrentPostionX());
        };
    }

    // result with height kinematics
    protected class CalcWHeight implements CalcResultInterface {
        private double h = height;
        private double vyS = calc_vyS(getVelocity_Y());

        // complete calcs
        public CalcWHeight() {
            funk_tof();
           funk_cmh();
           funk_fp();
        }

        // Calculating time of flight
        public void funk_tof() {
            setTimeofFlight(calcTOF_WITHHEIGHT(getVelocity_Y(), vyS, h));
        }

        // Calculating max height
        public void funk_cmh() {
            setMaxHeight(calcYmax_WITHHEIGHT(vyS, h) + in_Play.getObjectCurrentPostionY());
        }

        // Calculating final position
        public void funk_fp() {
            setFinalPosition(calcfPos_X_WITHHEIGHT(getVelocity_X(), getVelocity_Y(), vyS, h) + in_Play.getObjectCurrentPostionX());
        }
    }

    public Calculation(PositionCapture in_Play, PositionCapture[] tanks, double velocity, double angle) {
        System.out.println("New Calculation starting...");
        this.tanks = tanks;
        this.in_Play = in_Play;
        this.velocity = velocity;
        this.angle = angle;
        this.height = getTankHeight();
        checkLaunch();
    };

    // checking if launch conditions are a go!
    // velocity should be greater than 0, angle should be equal or greater than 0 degrees (0 > 90 < 180)
    // tanks should not be null for launch
    private void checkLaunch() {
        if (this.velocity > 0 && this.angle >= 0 && this.angle <= 180 && this.tanks != null && this.in_Play != null) {
            load();
            System.out.println("Arguments passed check");
            CalcDetermine();
            return;
        };
        System.out.println("Arguments failed check");
        unload();
    };

    private void CalcDetermine() {
        set_x_y_a(); // set angle, x, y for rest of kinematics
        if (this.height > 0) {
            new CalcWHeight();
        } else {
            new CalcNOHeight();
        }
    }

    private void set_x_y_a() {
        // must set angle to radians
        this.angle = toRad(this.angle); 
        this.setVelocity_X(calcVel_X(this.velocity, this.angle));
        this.setVelocity_Y(calcVel_Y(this.velocity, this.angle));
    };

    // Setting Velocity in Y direction
    private void setVelocity_Y(double v) {
        this.vy = v;
    }

    // Setting Velocity in X direction
    private void setVelocity_X(double v) {
        this.vx = v;
    }

    // Setting Velocity in X direction
    private void setTimeofFlight(double t) {
        this.tof = t;
    }

    private void setFinalPosition(double x) {
        this.Xf = x;
    }

    private void setMaxHeight(double h) {
        this.capH = h;
    }

    // Missle loaded! Ready for launch by user
    public boolean readyToFire() {
        return this.loaded;
    };

    // getters for missle launch
    public double getVelocity_X() {
        return this.vx;
    }

    public double getVelocity_Y() {
        return this.vy;
    }

    public double getTimeofFlight() {
        return this.tof;
    }

    public double getMaxHeight() {
        return this.capH;
    }

    public double getFinalPosition() {
        return this.Xf;
    } 

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
            "Angle (rads): " + this.angle + '\n' +
            "Velocity X: " + getVelocity_X() + '\n' +
            "Velocity Y: " + getVelocity_Y() + '\n' +
            "Time of Flight: " + getTimeofFlight() + '\n' +
            "Max Height Reached: " + getMaxHeight() + '\n' +
            "Final X Position: " + getFinalPosition() + '\n'
        );
    };

    // Representations of calculation being ready, not actual data for interpretation for tank
    protected void load() {
        this.loaded = true;
    };

    protected void unload() {
        this.loaded = false;
    };

    private double getTankHeight() {
        double currH = this.in_Play.getObjectCurrentPostionY();
        return currH - 530; // gets height from starting plane height
    }
};