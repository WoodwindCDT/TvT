package Handlers;

public class MissleLaunch {
    
    // creating animation based off of calculations
    Calculation c;

    // default constructor
    public MissleLaunch(Calculation c) {
        System.out.println("Got a calculation!");
        this.c = c;
        c.print();
    }
};