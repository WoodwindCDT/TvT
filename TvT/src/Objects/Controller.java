package Objects;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controller {

    private final int TANK_AMOUNT = 2;

    // Properties
    private EnvironmentPane ep; // environment pane to provide values for display
    private Tank tanks[] = new Tank[TANK_AMOUNT]; // We only allow 2 tanks, so preset length to 2, BST should be implemented later
    private boolean turn = true; // player 1 = false, player 2 = true
    private Tank in_Play;
    
    // default constructor
    public Controller(EnvironmentPane ep) {
        System.out.println("Controller Loaded");
        this.ep = ep;
        System.out.println("EnvironmentPane loaded: " + this.ep);
        System.out.println("I have control :) Score: " + this.ep.score);
        this.ep.changeText("Welcome! I can be changed with Controller.");
    }

    public void addTank(Tank t1, Tank t2) {
        this.tanks[0] = t1;
        this.tanks[1] = t2;
    }

    public void changeTankPosition(KeyEvent e) {
        // Player 2 turn
        if (turn == false) this.in_Play = this.tanks[1];
        else this.in_Play = this.tanks[0];
        double currPosX = this.in_Play.getObjectCurrentPosition()[0];
        //Left
        if (e.getCode() == KeyCode.LEFT && currPosX > 0) {
            this.in_Play.setObjectCurrentPosition_X(currPosX - this.in_Play.getSpeed()); // speed will be how "Fast" it will change position
        }
        else if (e.getCode() == KeyCode.RIGHT && currPosX < 670) { // will not allow past right border
            this.in_Play.setObjectCurrentPosition_X(currPosX + this.in_Play.getSpeed());
        }
        this.turn = !this.turn; // used for testing!!! just to observe tanks can both move
    }

    public Tank getInPlay() {
        return this.in_Play;
    }

    public Tank[] provideTanks() {
        return this.tanks;
    }
}