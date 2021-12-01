package edu.csueastbay.cs401.ejamdar;
import edu.csueastbay.cs401.pong.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import java.util.Random;


/**
 * JamdarPong will bring out the features for the Jamdar version of Pong.
 * This class will have the methods needed to play JamdarPong and is a modification of the
 * Pong class.
 * @author Eshaq Jamdar
 * @version 1.0
 * @see JamdarPong
 */
public class JamdarPong extends Game {
    /**
     * Will have the value of the screen's height
     */
    private double fieldHeight;
    /**
     * Will have the value of the screen's width
     */
    private double fieldWidth;
    /**
     * playerOne is a paddle for the left side
     */
    public Paddle playerOne;
    /**
     * playerOne is a paddle for the right side
     */
    public Paddle playerTwo;
    /**
     * game is a instance of the GameController class
     */
    private GameController game = new GameController();

    /**
     * Method addBall() will create a new puck with a width and height member
     * variables that are a part of JamdarPong. The puck is then added to the screen.
     * <p>
     * Method is ran from the Initializer of JamdarPong and will add a random amount of
     * Pucks from 1-5. In addition, paddle colors and their size will be randomized everytime the game
     * is started.
     * @param victoryScore victory score is a score that determines the winner of the game: int
     * @param fieldHeight height represents the height of the game screen
     * @param fieldWidth width represents width of game screen
     * @see JamdarPong
     */
    public JamdarPong(int victoryScore, double fieldWidth, double fieldHeight) {

        super(victoryScore);
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        setVictoryScore(20);

        //random number generation for the balls (Range)
        Random rand = new Random();
        int max = 5;
        int min = 1;

        int numBalls = rand.nextInt(max - min) + min;

        //random generation for width of paddle
        min = 10;
        max = 25;
        int width = rand.nextInt(max - min) + min;

        //random generation of height for paddle
        min = 25;
        max = 100;
        int height = rand.nextInt(max - min) + min;

        //adding balls to the screen
        for(int i = 1 ; i <= numBalls ; i++){
            addBall();
        }

        //rgb and r2g2b2 will be the randomized colors for the paddles
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();

        float r2 = rand.nextFloat();
        float g2 = rand.nextFloat();
        float b2 = rand.nextFloat();

        Wall top = new Wall("Top Wall", 0,0, this.fieldWidth, 10);
        top.setFill(Color.WHITE);
        addObject(top);

        Wall bottom = new Wall("Bottom Wall", 0, this.fieldHeight -10, this.fieldWidth, 10 );
        bottom.setFill(Color.WHITE);
        addObject(bottom);

        Goal left = new Goal("Player 1 Goal", this.fieldWidth -10, 10, 10, this.fieldHeight - 20);
        left.setFill(Color.RED);
        addObject(left);

        Goal right = new Goal("Player 2 Goal", 0, 10, 10, this.fieldHeight - 20);
        right.setFill(Color.BLUE);
        addObject(right);

        playerOne = new Paddle(
                "Player 1 Paddle",
                50,
                (this.fieldHeight/2) - 50,
                width,
                height,
                10,
                this.fieldHeight - 10);
        playerOne.setFill(Color.color(r,g,b));
        addPlayerPaddle(1, playerOne);

        playerTwo = new Paddle(
                "Player 2 Paddle",
                this.fieldWidth - 50,
                (this.fieldHeight/2) - 50,
                width,
                height,
                10,
                this.fieldHeight - 10);
        playerTwo.setFill(Color.color(r2,g2,b2));
        addPlayerPaddle(2, playerTwo);

    }

    /**
     * Method addBall() will create a new puck with a width and height member
     * variables that are a part of JamdarPong. The puck is then added to the screen.
     * <p>
     * Method is ran from the Initializer of JamdarPong and will add a random amount of
     * Pucks from 1-5.
     * @param puck it is of type Puckable and represents one puck
     * @param collision of type Collision that represents Collision handling method retrieval
     * @see JamdarPong
     */
    @Override
    public void collisionHandler(Puckable puck, Collision collision)  {
            switch (collision.getType()) {
                case "Wall":
                    puck.setDirection(0 - puck.getDirection());
                    break;
                case "Goal":
                    if (collision.getObjectID() == "Player 1 Goal") {
                        playSound(1);
                        addPointsToPlayer(1, 1);
                        puck.reset();
                    } else if (collision.getObjectID() == "Player 2 Goal") {
                        playSound(1);
                        addPointsToPlayer(2, 1);
                        puck.reset();
                    }
                    break;
                case "Paddle":
                    playSound(2);
                    double puckCenter = ((Puck) puck).getCenterY();
                    double angle;
                    if (collision.getObjectID() == "Player 1 Paddle") {
                        angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
                    } else {
                        angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
                    }
                    puck.setDirection(angle);

            }
    }
    /**
     * Method mapRange will calculate the angle of the paddle
     * <p>
     * Method will calculate the mapRange of the game screen given the parameters
     * @param a1 represents the top of the map: double
     * @param a2 represents the botton of the map: double
     * @param b1 represents a x coordinate: double
     * @param b2 represents a y coordinate: double
     * @param s representing the center: double
     * @return double representing a range in the map
     * @see JamdarPong
     */
    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }

    /**
     * This method takes in an int representing the sound effect to be
     * played and will use GameController's backgroundMusic method to play
     * the sound.
     * <p>
     * This method is ran any time the puck collides with a wall or any paddle.
     * Sound effect 1 plays the point sound when a point is obtained by either player.
     * Sound effect 2 will play the sound anytime the puck hits any colidable object.
     *
     * @param sound an int representing the sfx to be played
     * @see JamdarPong
     */
    public boolean playSound(int sound)  {
        if(sound == 1) {
            game.backgroundMusic(2);
            return true;
        }else if (sound == 2){
            game.backgroundMusic(3);
            return true;
        }
        return false;
    }

    /**
     * Method addBall() will create a new puck with a width and height member
     * variables that are a part of JamdarPong. The puck is then added to the screen.
     * <p>
     * Method is ran from the Initializer of JamdarPong and will add a random amount of
     * Pucks from 1-5.
     * @see JamdarPong
     */
    public boolean addBall(){
        try {
            Puck puck = new Puck(this.fieldWidth, this.fieldHeight);
            puck.setID("JamdarPuck");
            addPuck(puck);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    /**
     * Method keyPressed(KeyCode) will override the keyPressed method from the Game class
     * and provide control movement on the x and y axis.
     * <p>
     * Method is ran everytime a keystroke is registered and will move a paddle in the appropriate direction
     * for the specific paddle player. WASD is for paddle 1 and IKJL is for the 2nd. W represents up, s represents
     * down, a represents left, and d represents rights. Ditto for IJKL.
     * @param code key stroke representing ASCII value on keyboard
     * @see JamdarPong
     */
    @Override
    public void keyPressed(KeyCode code) {
        switch (code) {
            case W:
                playerOne.moveUp();
                break;
            case S:
                playerOne.moveDown();
                break;
            case A:
                playerOne.moveLeft();
                break;
            case D:
                playerOne.moveRight();
                break;
            case I:
                playerTwo.moveUp();
                break;
            case K:
                playerTwo.moveDown();
                break;
            case J:
                playerTwo.moveLeft();
                break;
            case L:
                playerTwo.moveRight();
                break;
        }
    }
    /**
     * Method move() will be used within collision function and is an overridden method from the paddle class that will
     * allow for movement handling for the paddles of playerOne and Two.
     * <p>
     * Method will call move2(), a method from within Paddle that handles movement for the x and y axis, on both paddle
     * instances and then goes through the list of pucks to check collision and allows the puck to move.
     * @see JamdarPong
     */
    @Override
    public void move() {
        playerOne.move2();
        playerTwo.move2();
        for (Puckable puck : getPucks()) {
            checkCollision(puck);
            puck.move();
        }
    }


}
