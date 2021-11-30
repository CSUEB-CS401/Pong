package edu.csueastbay.cs401.classic;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.paint.Color;

public class ClassicPong extends Game {

    // Class Constants

    private final int PADDLE_HEIGHT = 150;

    // Class Variables

    private double fieldHeight;
    private double fieldWidth;
    private int paddleHeight;
    private Paddle playerOne;
    private Paddle playerTwo;

    public ClassicPong(int victoryScore, double fieldWidth, double fieldHeight) {
        super(victoryScore);

        paddleHeight = PADDLE_HEIGHT;

        setupPong( "Classic", fieldWidth, fieldHeight);

        System.out.println( "\n...ClassicPong constructor....." );
    }

    public ClassicPong( String id, int victoryScore, double fieldWidth, double fieldHeight) {
        super(victoryScore);

        paddleHeight = PADDLE_HEIGHT;

        setupPong( id, fieldWidth, fieldHeight);
    }

    public int getHeight()
    {
        return this.paddleHeight;
    }

    public void setHeigth( int paddleHeight )
    {
        this.paddleHeight = paddleHeight;
    }

    public void reducePaddle()
    {
        this.paddleHeight = (int) ( 0.75 * this.paddleHeight );  // 25% reduction
    }

    private void setupPong( String id, double fieldWidth, double fieldHeight )
    {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;

        ColoredPuck puck = new ColoredPuck(this.fieldWidth, this.fieldHeight);
        puck.setID(id);
        addPuck(puck);

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
                10,
                this.paddleHeight,
                10,
                this.fieldHeight - 10);
        playerOne.setFill(Color.RED);
        addPlayerPaddle(1, playerOne);

        playerTwo = new Paddle(
                "Player 2 Paddle",
                this.fieldWidth - 50,
                (this.fieldHeight/2) - 50,
                10,
                this.paddleHeight,
                10,
                this.fieldHeight - 10);
        playerTwo.setFill(Color.BLUE);
        addPlayerPaddle(2, playerTwo);
    }

    @Override
    public void collisionHandler(Puckable puck, Collision collision) {
//        System.out.println(puck.getDirection());
        switch(collision.getType()) {
            case "Wall":
                puck.setDirection(0 - puck.getDirection());
                break;
            case "Goal":
                if (collision.getObjectID() == "Player 1 Goal") {
                    addPointsToPlayer(1, 1);
                    playerOne.reset();

                    puck.reset();
                } else if (collision.getObjectID() == "Player 2 Goal") {
                    addPointsToPlayer(2, 1);
                    playerTwo.reset();
                    puck.reset();
                }
                break;
            case "Paddle":
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

    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }
}