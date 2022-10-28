package Objects;
import Extenders.PositionCapture;
import Interfaces.ObjectInterface;
import Interfaces.TankInterface;
import javafx.scene.paint.Color;

public class Tank extends PositionCapture implements TankInterface, ObjectInterface {

    // properties
    private String name;
    private Color color;
    private double speed;
    private double power;
    private double objPosX; // starting position at any time .. if scene were to change or terrain, the position of tank would be preserved, and controller would not require to hold this
    private double objPosY; // starting position at any time
    private double condition; // should be 100 "default" but can change if user may set something, or the
    private Missle missle;

    // default constructor
    public Tank() {
        super();
        super.setObjectforCapture(this); // setting reference to object after object properties complete
    }

    // FOR TESTING ONLY, this overloaded constructor likely wont exist in the future unless we use preset models
    // Overloaded constructor
    public Tank(String name, Color color, double speed, double power, double objPosX, double objPosY, double condition, Missle missle) {
        super();
        this.name = name;
        this.color = color;
        this.speed = speed;
        this.power = power;
        this.objPosX = objPosX;
        this.objPosY = objPosY;
        this.condition = condition;
        this.missle = missle;
        super.setObjectforCapture(this);
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public double getSpeed() {
        return this.speed;
    }

    @Override
    public double getPower() {
        return this.power;
    }

    @Override
    public double getObjectX() {
        return this.objPosX;
    }

    @Override
    public double getObjectY() {
        return this.objPosY;
    }

    @Override
    public double getCondition() {
        return this.condition;
    }

    @Override
    public Missle getMissle() {
        return this.missle;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setMissle(Missle missle) {
        this.missle = missle;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void setPower(double power) {
        this.power = power;
    }

    @Override
    public void setObjectX(double pos_x) {
        this.objPosX = pos_x;
    }

    @Override
    public void setObjectY(double pos_y) {
        this.objPosY = pos_y;
    }

    @Override
    public void setCondition(double condition) {
        this.condition = condition;
    }
}