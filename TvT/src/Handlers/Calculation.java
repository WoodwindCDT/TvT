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
import static Assets.Constants.toPos;

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
    private boolean hit = false;
    private boolean gameover = false;

    // final results
    private double vx; // velocity of object
    private double vy; // velocity of object
    private double tof; // time of flight
    private double capH; // max height reached
    private double Xf; // final position x of projectile
    private double damage;
    private PositionCapture loser;
    
    protected class HitOrMiss {
        double radius = 25;

        public HitOrMiss() {
            double t1 = tanks[0].getObjectCurrentPostionX();
            double t2 = tanks[1].getObjectCurrentPostionX();;
            determineHit(t1, t2);
        };
        
        // algo for determining hit on tank
        private void determineHit(double t1, double t2) {
            double d1; // tank 1 pos
            double d2; // tank 2 pos
            // accomidate for tank2 or tank 1, looking at reference
            if (checkInPlay()) {
                d1 = toPos(t1 - Xf);
                d2 = toPos(Xf - t2);
            } else {
                d1 = toPos(Xf - t1);
                d2 = toPos(Xf - t2);
            }

            // radius of blast is standard set at 25!
            if (d1 <= radius || d2 <= radius) {
                hit = true;
                System.out.println("Got a hit!");
                if (d1 <= radius) {
                    determineDamage(tanks[0]);
                } else {
                    determineDamage(tanks[1]);
                }

            } else {
                System.out.println("No Hit!");
                hit = false;
                return;
            }

            System.out.println("Distance of tank 1: " + t1 + " Distance of tank 2: " + t2);
            System.out.println("Distance from first tank: " + d1 + " Distance from second tank: " + d2);
        };

        // tank in radius loses health according to enemy missle damage capability!
        private void determineDamage(PositionCapture t) {
            Tank tank = ((Tank)t);

            double health = tank.getCondition();
            double res = health - getDamage(tank);
            
            if (res <= 0) {
                System.out.println("Tank: " + tank.getName() + " is disabled!");
                loser = tank;
                gameover = true;
            }

            damage = res;
            tank.setCondition(res);
            System.out.println("Tank: " + tank.getName() + " has: " + tank.getCondition() + " health left!");
        };

        private double getDamage(Tank t) {
            return t.getMissle(0).getPower();
        };
    };

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
            if (checkInPlay()) {
                setFinalPosition(toPos(calcfPos_X_NOHEIGHT(getVelocity_X(), getVelocity_Y()) - in_Play.getObjectCurrentPostionX()));
                return;
            }
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
            if (checkInPlay()) {
                setFinalPosition(toPos(calcfPos_X_WITHHEIGHT(getVelocity_X(), getVelocity_Y(), vyS, h) - in_Play.getObjectCurrentPostionX()));
                return;
            }
            setFinalPosition(calcfPos_X_NOHEIGHT(getVelocity_X(), getVelocity_Y()) + in_Play.getObjectCurrentPostionX());
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
        // Determine hit/damage
        new HitOrMiss();
    };

    private void set_x_y_a() {
        // must set angle to radians
        this.angle = toRad(this.angle); 
        this.setVelocity_X(calcVel_X(this.velocity, this.angle));
        this.setVelocity_Y(calcVel_Y(this.velocity, this.angle));
    };

    // Setting Velocity in Y direction
    private void setVelocity_Y(double v) {
        this.vy = v;
    };

    // Setting Velocity in X direction
    private void setVelocity_X(double v) {
        this.vx = v;
    };

    // Setting Velocity in X direction
    private void setTimeofFlight(double t) {
        this.tof = t;
    };

    private void setFinalPosition(double x) {
        this.Xf = x;
    };

    private void setMaxHeight(double h) {
        this.capH = h;
    };

    // Missle loaded! Ready for launch by user
    public boolean readyToFire() {
        return this.loaded;
    };

    public double getHeight() {
        return this.height;
    }

    // getters for missle launch
    public double getVelocity_X() {
        return this.vx;
    };

    public double getVelocity_Y() {
        return this.vy;
    };

    public double getTimeofFlight() {
        return this.tof;
    };

    public double getMaxHeight() {
        return this.capH;
    };

    public double getFinalPosition() {
        return this.Xf;
    };

    public String getHit() {
        return this.hit ? "Got a HIT!" : "No HIT!";
    };

    public boolean getGameOver() {
        return this.gameover;
    };

    public double getDamage() {
        return this.damage;
    };

    public PositionCapture getLoser() {
        return this.loser;
    };

    public String printLastAction() {
        String str = "Last Action Conditions: " + '\n' +
        "---------------------------------------" + "\n"; 
        str += print();
        return str;
    };

    // prints calculation properties
    public String print() {
        return(
            "Calculation Results: " + '\n' +
            "---------------------------------------" + '\n' +
            "Initial Velocity: " + this.velocity + " m/s" + '\n' +
            "Angle (rads): " + this.angle + '\n' +
            "Velocity X: " + toPos(getVelocity_X()) + " m/s" + '\n' +
            "Velocity Y: " + getVelocity_Y() + " m/s" + '\n' +
            "Time of Flight: " + getTimeofFlight() + " s" + '\n' +
            "Max Height Reached: " + getMaxHeight() + " m" + '\n' +
            "Final X Position: " + getFinalPosition() + " m" + '\n'
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

    private boolean checkInPlay() {
        return this.in_Play == tanks[1];
    }
};