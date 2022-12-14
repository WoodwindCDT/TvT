package Handlers;
import Objects.Missle;
import Objects.Tank;

// An external function allowing controller to set the position for capture outside of referencing the object directly
public class PositionCapture {

    private Object myObject; // stores reference to object can be Missle, Tank
    private double controlled_pos_x;
    private double controlled_pos_y;
    private double controlled_angle;
    private double controlled_power;
    private double distance_traveled;
    
    public PositionCapture() {};

    public void setObjectforCapture(Object o) {
        this.myObject = o;

        // getting last position or default position of object exists
        if (o instanceof Tank) {
            this.controlled_pos_x = ((Tank)o).getObjectX();
            this.controlled_pos_y = ((Tank)o).getObjectY();
            this.controlled_angle = ((Tank)o).getLauncherAngle();
            this.controlled_power = 0;
        };
        if (o instanceof Missle) {
            this.controlled_pos_x = ((Missle)o).getObjectX();
            this.controlled_pos_y = ((Missle)o).getObjectY();
        };
    };

    // x 0, y 1;
    public double[] getObjectCurrentPosition() {
        return new double[]{this.controlled_pos_x, this.controlled_pos_y}; // returns double array contains x[0],y[1] coord of object
    };

    public double getObjectCurrentPostionX() {
        return this.controlled_pos_x;
    }

    public double getObjectCurrentPostionY() {
        return this.controlled_pos_y;
    }

    // power 0, angle 1;
    public double[] getObjectCurrentLaunchPosition() {
        return new double[]{this.controlled_power, this.controlled_angle}; // returns double array contains pwr[0], angle[1]
    };

    public void setObjectCurrentPosition_X(double x) {
        setDistanceTraveled(x);
        this.controlled_pos_x = x;
    };

    public void setObjectCurrentPosition_Y(double y) {
        this.controlled_pos_y = y;
    };

    public void setObjectCurrentAngle(double a) {
        this.controlled_angle = a;
        System.out.println("Angle: " + this.controlled_angle);
    };

    public double getObjectControlledAngle() {
        return this.controlled_angle;
    }

    public void setObjectCurrentPower(double p) {
        this.controlled_power = p;
        System.out.println("PWR: " + this.controlled_power);
    };

    public double getObjectCurrentPower() {
        return this.controlled_power;
    }

    public String distanceTraveled() {
        double tempDT = this.distance_traveled;
        if (tempDT > 0) {
            this.distance_traveled = 0; // reset distance for next check
            if (this.myObject instanceof Tank) {
                return ((Tank)this.myObject).getName() + " moved " + (tempDT);
            };
            if (this.myObject instanceof Missle) {
                return ((Missle)this.myObject).getName() + " moved " + (tempDT);
            };
        };
        return "No Movement Detected";
    };

    public void setDistanceTraveled(double position) {
        // set distance traveled
        this.distance_traveled =  Math.abs(position - this.controlled_pos_x);
    };

    public void resetObject_PWR() {
        this.setObjectCurrentPower(0);
    };

    public void resetObject_ANGLE() {
        this.setObjectCurrentAngle(90);
    };
};