package Extenders;
import Objects.Missle;
import Objects.Tank;

// An external function allowing controller to set the position for capture outside of referencing the object directly
public class PositionCapture {

    Object myObject; // stores reference to object can be Missle, Tank
    double controlled_pos_x;
    double controlled_pos_y;
    
    public PositionCapture() {}

    public void setObjectforCapture(Object o) {
        this.myObject = o;

        // getting last position or default position of object exists
        if (o instanceof Tank) {
            this.controlled_pos_x = ((Tank)o).getObjectX();
            this.controlled_pos_y = ((Tank)o).getObjectY();
        }
        if (o instanceof Missle) {
            this.controlled_pos_x = ((Missle)o).getObjectX();
            this.controlled_pos_y = ((Missle)o).getObjectY();
        }
    }

    public double[] getObjectCurrentPosition() {
        return new double[]{this.controlled_pos_x, this.controlled_pos_y}; // returns double array contains x,y coord of object
    }

    public void setObjectCurrentPosition_X(double x) {
        this.controlled_pos_x = x;
    }

    public void setObjectCurrentPosition_Y(double y) {
        this.controlled_pos_y = y;
    }
}