package Handlers;
import java.util.ArrayList;

import Extenders.EnvironmentPane;
import Objects.Tank;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controller {

    private final int TANK_AMOUNT = 2;
    // Possible key inputs WE want
    ArrayList<KeyCode> keys = new ArrayList<>() {
        {
            add (KeyCode.LEFT);
            add (KeyCode.RIGHT);
            add (KeyCode.SPACE);
        };
    };

    // Properties
    private char sendAsMessage = 'm';
    private char sendAsPlayer = 'p';
    private EnvironmentPane ep; // environment pane to provide values for display
    private Tank tanks[] = new Tank[TANK_AMOUNT]; // We only allow 2 tanks, so preset length to 2, BST should be implemented later
    private boolean turn = true; // player 1 = true, player 2 = false
    private boolean missleLaunch = false;
    private Tank in_Play; // player 1 is first
    
    // default constructor
    public Controller(EnvironmentPane ep) {
        System.out.println("Controller Loaded");
        this.ep = ep;
        System.out.println("EnvironmentPane loaded: " + this.ep);
        this.ep.changeMessage(sendAsMessage, "Welcome! I can be changed with Controller.");
    };

    public void addTank(Tank t1, Tank t2) {
        this.tanks[0] = t1;
        this.tanks[1] = t2;
    };

    // Handles input from user during their turn
    public void changeTankPosition(KeyEvent e) {
        // check to see if key is expected
        if (this.keys.contains(e.getCode())) {

            // Player 2 turn
            if (turn == false) this.in_Play = this.tanks[1];
            else this.in_Play = this.tanks[0];

            double currPosX = this.in_Play.getObjectCurrentPosition()[0];
            //Left
            if (e.getCode() == KeyCode.LEFT && currPosX > 0) TankKELeft(currPosX); // Tank Key Event Left
            //Right
            if (e.getCode() == KeyCode.RIGHT && currPosX < 670) TankKERight(currPosX); // will not allow past right border
            if (e.getCode() == KeyCode.SPACE) {
                if (!this.missleLaunch) TankKESpace(); // should assign calculator for listening to angle and initial velocity
                else {
                    System.err.println("Cannot launch twice in one turn!");
                    System.out.println("Issuer: " + this + " suspect: " + this.in_Play.getName());
                    return;
                };
            };
            // finally
            // Sending to ep to show player in play
            // setEPText(sendAsPlayer, this.in_Play.getName() + " is in play.");
            setTankBodyPosition(); // setting result to actual tank body for user view
        } else {
            System.err.println("Cannot use that key! Try again.");
            return;
        };
    };

    private void handleMissleLaunch() {
        setEPText(sendAsMessage, "Missle Launch is ready: " + this.missleLaunch + "\n -> commanded by: " + this.in_Play.getName());
        System.out.println("omg we're about to die: Launch complete");
        this.missleLaunch = !this.missleLaunch;
        endTurn();
    };

    // Setting final position of x,y to tank body
    private void setTankBodyPosition() {
        double[] pos = this.in_Play.getObjectCurrentPosition();
        this.in_Play.getBody().setX(pos[0]);
        this.in_Play.getBody().setY(pos[1]);
    };

    // Tank position
    private void TankKELeft(double currPosX) {
        this.in_Play.setObjectCurrentPosition_X(currPosX - this.in_Play.getSpeed()); // speed will be how "Fast" it will change position
        setEPText(sendAsMessage, this.in_Play.distanceTraveled());
        endTurn(); // used for testing!!! just to observe tanks can both move
    };

    private void TankKERight(double currPosX) {
        this.in_Play.setObjectCurrentPosition_X(currPosX + this.in_Play.getSpeed());
        setEPText(sendAsMessage, this.in_Play.distanceTraveled());
        endTurn(); // used for testing!!! just to observe tanks can both move
    };

    // Missle launch start
    private void TankKESpace() {
        this.missleLaunch = true;
        handleMissleLaunch();
        // tank turn now determined POST launch
    };

    // Sets environment pane text
    // type equivalent to message or player, allowing reuse or method for different types of messages
    private void setEPText(char type, String msg) {
        this.ep.changeMessage(type, msg);
    };

    // can provide what tank is in play to other classes
    public Tank getInPlay() {
        return this.in_Play;
    };
    
    // Provides all tanks to caller
    public Tank[] provideTanks() {
        return this.tanks;
    };

    private void endTurn() {
        this.turn = !this.turn;
    };
}