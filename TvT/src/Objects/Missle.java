package Objects;
import Interfaces.MissleInterface;
import Interfaces.ObjectInterface;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Missle implements MissleInterface, ObjectInterface {

    private String name;
    private Color color;
    private double speed;
    private double power;
    private double objPosX;
    private double objPosY;
    private double condition;
    private double resistance;
    private Rectangle body;

    // default constructor
    public Missle() {

    }

    // overloaded constructor for testing or preset
    public Missle(String name, Color color, double speed, double power, double objPosX, double objPosY, double condition, double resistance) {
        this.name = name;
        this.color = color;
        this.speed = speed;
        this.power = power;
        this.objPosX = objPosX;
        this.objPosY = objPosY;
        this.condition = condition;
        this.resistance = resistance;
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
    public String getName() {
        return this.name;
    }
    
    @Override
    public double getResistance() {
        return this.resistance;
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

    @Override
    public void setResistance(double resistance) {
        this.resistance = resistance;
    }

    @Override
    public void setBody(Rectangle x) {
        this.body = x;
    }

    @Override
    public Rectangle getBody() {
        return this.body;
    }
}