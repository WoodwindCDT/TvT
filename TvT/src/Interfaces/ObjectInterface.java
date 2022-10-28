package Interfaces;
import javafx.scene.paint.Color;

public interface ObjectInterface {
    // methods every controller object should use
    // setter
    public void setColor(Color color);
    public void setName(String name);
    public void setSpeed(double speed);
    public void setPower(double power); // Power could be universal, missle has x power for damage, and tank has x power for initial velocity of missle
    public void setObjectX(double pos_x); // tracked by controller but should still belong to an object
    public void setObjectY(double pos_y);
    public void setCondition(double condition); // Condition of item should be greater than zero to be functional
    // getter
    public Color getColor();
    public String getName();
    public double getSpeed();
    public double getPower();
    public double getObjectX();
    public double getObjectY();
    public double getCondition(); 
}