package Handlers;
import java.util.ArrayList;
import Extenders.EnvironmentPane;
import Objects.Missle;
import Objects.Tank;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class Controller {

    private final Missle[] arsenal = new Missle[]{new Missle("m1", Color.RED, 10, 20, 0, 40, 100, 0)};
    private final int TANK_AMOUNT = 2;
    private final double angleMin = 90;
    private final double angleMax = 180;
    
    // Possible key inputs WE want
    ArrayList<KeyCode> keys = new ArrayList<>() {
        {
            add(KeyCode.LEFT); // mvmnt left
            add(KeyCode.RIGHT); // mvmnt right
            add(KeyCode.SPACE); // interface allowing missle launch
            add(KeyCode.A); // interface allowing -1 from angle of shot
            add(KeyCode.D); // interface allowing +1 adds to angle of shot
            add(KeyCode.W); // increases power of shot
            add(KeyCode.S); // decreases power of shot
            add(KeyCode.R); // for restart!
            add(KeyCode.ENTER); // dials in power/angle
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
    private Calculation calc; // player launch calculations
    private boolean gameover = false;
    
    // default constructor
    public Controller(EnvironmentPane ep) {
        System.out.println("Controller Loaded");
        this.ep = ep;
        System.out.println("EnvironmentPane loaded: " + this.ep);
        // this.ep.changeMessage(sendAsMessage, "Welcome! I can be changed with Controller.");
        //Adding tanks to controller
        // Creation of tanks, t2 @ 500 due to visual issues with border ** can't see at max
        addTank();
        setHealth();
    };

    public void addTank() {
        this.tanks[0] = new Tank("t1", Color.RED, 10, 0, 90, 530, 100, this.arsenal);
        this.tanks[1] = new Tank("t2", Color.BLUE, 10, 0, 500, 530, 100, this.arsenal);;
    };

    // Handles input from user during their turn
    public void changeTankPosition(KeyEvent e) {
        // check to see if key is expected
        if (!this.gameover) {
            if (checkKey(e)) {
            setEPText(sendAsMessage, "");
            // Player 2 turn
            if (turn == false) this.in_Play = this.tanks[1];
            else this.in_Play = this.tanks[0];

            // Sending to ep to show player in play
            // setEPText(sendAsPlayer, this.in_Play.getName() + " is in play.");

            double currPosX = this.in_Play.getObjectCurrentPosition()[0];
            double[] launchArgs = this.in_Play.getObjectCurrentLaunchPosition();
            //Left
            if (e.getCode() == KeyCode.LEFT && currPosX > 0) TankKELeft(currPosX); // Tank Key Event Left
            //Right
            if (e.getCode() == KeyCode.RIGHT && currPosX < 670) TankKERight(currPosX); // will not allow past right border
            if (e.getCode() == KeyCode.SPACE) {
                if (!this.missleLaunch) TankKESpace(launchArgs); // should assign calculator for listening to angle and initial velocity
                else {
                    System.err.println("Cannot launch twice in one turn!");
                    System.out.println("Issuer: " + this + " suspect: " + this.in_Play.getName());
                    return;
                };
            };
            // Launches missle
            if (e.getCode() == KeyCode.ENTER) TankKEEnter(); 
            // key events for setting power and angle
            if (e.getCode() == KeyCode.W) TankKEW(launchArgs[0]);
            if (e.getCode() == KeyCode.S) TankKES(launchArgs[0]);
            if (e.getCode() == KeyCode.A) TankKEA(launchArgs[1]);
            if (e.getCode() == KeyCode.D) TankKED(launchArgs[1]);
            // finally
            setTankBodyPosition(); // setting result to actual tank body for user view
            } else {
                System.err.println("Cannot use that key! Try again.");
                return;
            } 
        } else {
            System.out.println("Game Over, Press R to play again!");
            if (e.getCode() == KeyCode.R && this.gameover) {
                System.out.println("Resetting Game!");
                resetGame();
            }
        }
    };

    // Handling calculation calling!
    private void handleMissleLaunch(double v, double a) {
        // setting instance on controller only THIS launch
        // provides tanks, angle and velocity from user as well
        this.calc = new Calculation(this.in_Play, provideTanks(), v, a);
    
        if (this.calc.readyToFire()) {
            System.out.println("Passed launch phase");
            this.missleLaunch = this.calc.readyToFire();
            setEPText(sendAsMessage, "Missle Launch is ready: " + this.calc.readyToFire() + "\n -> commanded by: " + this.in_Play.getName()
            + '\n' +
            "Press ENTER/RETURN Key to fire!"
            );
            return;
        }
        setEPText(sendAsMessage, "Missle not ready for launch: " + "\n -> commanded by: " + this.in_Play.getName());
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
        // setEPText(sendAsMessage, this.in_Play.distanceTraveled());
        this.ep.moveAngleText(this.in_Play.getObjectCurrentPostionX(), this.in_Play.getObjectCurrentPostionY());
        this.ep.movePowerText(this.in_Play.getObjectCurrentPostionX(), this.in_Play.getObjectCurrentPostionY());
        if(turn) this.ep.moveTank1GunX(-(this.in_Play.getSpeed()));
        else this.ep.moveTank2GunX(-(this.in_Play.getSpeed()));
    };

    private void TankKERight(double currPosX) {
        this.in_Play.setObjectCurrentPosition_X(currPosX + this.in_Play.getSpeed());
        // setEPText(sendAsMessage, this.in_Play.distanceTraveled());
        this.ep.moveAngleText(this.in_Play.getObjectCurrentPostionX(), this.in_Play.getObjectCurrentPostionY());
        this.ep.movePowerText(this.in_Play.getObjectCurrentPostionX(), this.in_Play.getObjectCurrentPostionY());
        if(turn) this.ep.moveTank1GunX(this.in_Play.getSpeed());
        else this.ep.moveTank2GunX(this.in_Play.getSpeed());
    };

    // Missle launch start
    private void TankKESpace(double[] largs) {
        // Expecting some angle and power from user
        handleMissleLaunch(largs[0], largs[1]);
    };

    private void TankKEEnter() {
        if (this.missleLaunch && this.calc.readyToFire()) {
            this.ep.removeVisual();
            // should fire missle
            MissleLaunch ml = new MissleLaunch(this.calc, this);
            this.ep.setVisual(ml.getCircle(), ml.getQC());
            this.ep.changeMessage(sendAsMessage, this.calc.getHit() + "\n" + this.calc.printLastAction());
            if (this.calc.getGameOver()) {
                gameOver();
                return;
            } else {
                endTurn();
            }
        };
    };

    // handle temp power state
    private void TankKEW(double pwr) {
        // should reference tank's power cap when the object is created
        if (pwr + 1 <= 100) this.in_Play.setObjectCurrentPower(++pwr);
        this.ep.changePower(this.in_Play.getObjectCurrentPower());;
    };

    private void TankKES(double pwr) {
        if (pwr - 1 >= 0) this.in_Play.setObjectCurrentPower(--pwr);
        this.ep.changePower(this.in_Play.getObjectCurrentPower());;
    };

    // handle angle position
    private void TankKEA(double a) {
        if (a - 1 >= this.angleMin) this.in_Play.setObjectCurrentAngle(--a);
        this.ep.changeAngle(this.in_Play.getObjectControlledAngle());
        if(turn) this.ep.rotateTank1Gun(this.in_Play.getObjectCurrentPostionX(), this.in_Play.getObjectControlledAngle());
        else this.ep.rotateTank2Gun(this.in_Play.getObjectCurrentPostionX(), this.in_Play.getObjectControlledAngle());
    };

    private void TankKED(double a) {
        if (a + 1 <= this.angleMax) this.in_Play.setObjectCurrentAngle(++a);
        this.ep.changeAngle(this.in_Play.getObjectControlledAngle());
        if(turn) this.ep.rotateTank1Gun(this.in_Play.getObjectCurrentPostionX(), this.in_Play.getObjectControlledAngle());
        else this.ep.rotateTank2Gun(this.in_Play.getObjectCurrentPostionX(), this.in_Play.getObjectControlledAngle());
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
        // Sending to ep to show player in play
        // setEPText(sendAsMessage, "Launch Complete");
        // setEPText(sendAsPlayer, this.in_Play.getName() + " ended their turn.");
        this.missleLaunch = false;
        // to reset angle values on change
        this.ep.changeAngle(0);
        this.ep.changePower(0);
        setHealth();   
        this.turn = !this.turn;
        if(this.turn) this.ep.highlightTank1();
        else this.ep.highlightTank2();
    };

    // checks to see if key is acceptable/expected
    private boolean checkKey(KeyEvent e) {
        if (this.keys.contains(e.getCode())) return true;
        return false;
    };

    public void gameOver() {
        this.ep.changeMessage(sendAsMessage, "Loser is: " + ((Tank)this.calc.getLoser()).getName() + "\n" + " Press R to play again!");
        this.gameover = true;
        setHealth();
    };

    private void setHealth() {
        this.ep.changeHealth(this.tanks[0].getCondition(), this.tanks[1].getCondition());
    };

    private void resetGame() {
        this.ep.removeAll();
        resetController();
    };

    private void resetController() {
        this.gameover = false;
        this.tanks = new Tank[TANK_AMOUNT];
        addTank();
        this.ep.setAll();
        setHealth();
        this.missleLaunch = false;
    };
};