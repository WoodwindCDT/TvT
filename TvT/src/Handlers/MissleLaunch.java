package Handlers;

public class MissleLaunch {
    
    // creating animation based off of calculations
    Calculation c;

    // default constructor
    public MissleLaunch(Calculation c) {
        this.c = c;
        System.out.println("Calculation Complete");
        c.print();
        // TODO: Add point of landing on display somewhere in here and adjust pane health
    }
};