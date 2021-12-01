package edu.csueastbay.cs401.ttruong;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * initializes paddles and objects for the pong game
 * with addition of individually contributed circular bouncers that
 * increases/decreases the speed of the puck. PuckFactory() switches
 * what type of puck is selected everytime there is a goal. The speed buffs/debuffs
 * are 'camouflaged' to make it harder to see/unexpected for players who can't tell.
 */
public class ClassicPong extends Game {

    private double fieldHeight;
    private double fieldWidth;
    private PuckFactory puckFactory;
    private AnchorPane field;
    private AIPaddle botPaddle;


    /**
     * Constructor for ClassicPong
     * @param victoryScore - stores points
     * @param fieldWidth - width of the screen
     * @param fieldHeight - height of the screen
     * @param field - entire field
     */
    public ClassicPong(int victoryScore, double fieldWidth, double fieldHeight, AnchorPane field) {
        super(victoryScore);

        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.field = field;
        this.puckFactory = new PuckFactory(fieldWidth, fieldHeight);

        //use puckFactory
        Puckable puck = puckFactory.createPuck();
        puck.setID("Classic");
        addPuck(puck);

        //create objects for boost and slow when collided with puck
        SpeedBoost zoom1 = new SpeedBoost(this.fieldWidth, this.fieldHeight);
        zoom1.setId("Boost Top Left");
        zoom1.setCenterX(200);
        zoom1.setCenterY(300);
        zoom1.setRadius(10.0);
        zoom1.setFill(Color.LIGHTBLUE);
        addObject(zoom1);

        SpeedBoost zoom2 = new SpeedBoost(this.fieldWidth, this.fieldHeight);
        zoom2.setId("Boost Top Right");
        zoom2.setCenterX(1000);
        zoom2.setCenterY(300);
        zoom2.setRadius(10.0);
        zoom2.setFill(Color.LIGHTBLUE);
        addObject(zoom2);

        SpeedDebuff slow1 = new SpeedDebuff(this.fieldWidth, this.fieldHeight);
        slow1.setId("Debuff Bottom Right");
        slow1.setCenterX(1000);
        slow1.setCenterY(600);
        slow1.setRadius(10.0);
        slow1.setFill(Color.LIGHTBLUE);
        addObject(slow1);

        SpeedDebuff slow2 = new SpeedDebuff(this.fieldWidth, this.fieldHeight);
        slow2.setId("Debuff Bottom Left");
        slow2.setCenterX(200);
        slow2.setCenterY(600);
        slow2.setRadius(10.0);
        slow2.setFill(Color.LIGHTBLUE);
        addObject(slow2);

        Wall top = new Wall("Top Wall", 0, 0, this.fieldWidth, 10);
        top.setFill(Color.MIDNIGHTBLUE);
        addObject(top);

        Wall bottom = new Wall("Bottom Wall", 0, this.fieldHeight - 10, this.fieldWidth, 10);
        bottom.setFill(Color.LIGHTBLUE);
        addObject(bottom);

        Goal left = new Goal("Player 1 Goal", this.fieldWidth - 10, 10, 10, this.fieldHeight - 20);
        left.setFill(Color.DEEPSKYBLUE);
        addObject(left);

        Goal right = new Goal("Player 2 Goal", 0, 10, 10, this.fieldHeight - 20);
        right.setFill(Color.DEEPSKYBLUE);
        addObject(right);

        Paddle playerOne = new Paddle(
                "Player 1 Paddle",
                50,
                (this.fieldHeight / 2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerOne.setFill(Color.AZURE);
        addPlayerPaddle(1, playerOne);

        AIPaddle playerTwo = new AIPaddle(
                "Player 2 Paddle",
                this.fieldWidth - 50,
                (this.fieldHeight / 2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10,
                puck);
        playerTwo.setFill(Color.AZURE);
        addPlayerPaddle(2, playerTwo);
        botPaddle = playerTwo;
    }

    public AIPaddle getBotPaddle() {
        return botPaddle;
    }

    @Override
    public void collisionHandler(Puckable puck, Collision collision) {
        switch (collision.getType()) {
            case "SpeedBuff": //increase buff speed when collided
                puck.setSpeed(10.0);
                break;
            case "SpeedDebuff": //slow down puck when collided
                puck.setSpeed(2.0);
                break;
            case "Wall":
                puck.setDirection(0 - puck.getDirection());
                break;
            case "Goal":
                if (collision.getObjectID() == "Player 1 Goal") {
                    addPointsToPlayer(1, 1);
                    newPuck();
                } else if (collision.getObjectID() == "Player 2 Goal") {
                    addPointsToPlayer(2, 1);
                    newPuck();
                }
                break;
            case "Paddle":
                double puckCenter = puck.getCenterY();
                double angle;
                if (collision.getObjectID() == "Player 1 Paddle") {
                    angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
                } else {
                    angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
                }
                puck.setSpeed(7.0); //increase speed to increase difficulty
                puck.setDirection(angle);
                break;
        }
    }

    public void newPuck() {
        ArrayList<Puckable> pucks = getPucks();
        pucks.forEach((old_puck) -> {
            field.getChildren().remove((Node) old_puck);
        });
        clearPucks();

        Puckable puck = puckFactory.createPuck();
        puck.setID("Random");
        addPuck(puck);
        field.getChildren().add((Node)puck);

        botPaddle.setPuck(puck);
    }

    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1) * (b2 - b1)) / (a2 - a1);
    }
}
