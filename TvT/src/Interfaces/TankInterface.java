package Interfaces;

import Objects.Missle;

public interface TankInterface {
    // methods Tank should use
    // setter
    public void setMissle(Missle[] missle); // setting missle of tank
    public void setLauncherAngle(double a);
    // getter
    public Missle getMissle(int i);
    public double getLauncherAngle();
};