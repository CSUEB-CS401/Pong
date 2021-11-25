package edu.csueastbay.cs401.felixchoypong;
import edu.csueastbay.cs401.pong.*;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
public class MyPong extends MyGame {

    private double fieldHeight;
    private double fieldWidth;
    private Sounds soundClip;

    @FXML
    Label playerOneVictoryText;
    @FXML
    Label playerTwoVictoryText;

    private Timeline timeLine;

    public MyPong(int victoryScore, double fieldWidth, double fieldHeight, Label playerOneVictoryText, Label playerTwoVictoryText, AnchorPane fieldPane) {
        super(10); ///first to 10 points wins, can be changed.
        soundClip = new Sounds();
        soundClip.stopPlayingSound(); //prevents sound from carrying over to next game
        soundClip.stopPlayingPowerUpSound();
        soundClip.stopPlayingDeniedSound();
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.playerOneVictoryText = playerOneVictoryText;
        this.playerTwoVictoryText = playerTwoVictoryText;

        Puck puck = new Puck(this.fieldWidth, this.fieldHeight);
        puck.setID("Classic");
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

        Paddle playerOne = new Paddle(
                "Player 1 Paddle",
                50,
                (this.fieldHeight/2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerOne.setFill(Color.RED);
        addPlayerPaddle(1, playerOne);

        Paddle playerTwo = new Paddle(
                "Player 2 Paddle",
                this.fieldWidth - 50,
                (this.fieldHeight/2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerTwo.setFill(Color.BLUE);
        addPlayerPaddle(2, playerTwo);

    }

    @Override
    public void collisionHandler(Puckable puck, Collision collision) {
        switch(collision.getType()) {
            case "Wall":
                puck.setDirection(0 - puck.getDirection());
                //play sound
                soundClip.stopPlayingSound(); //prevent overlapping
                soundClip.playWallSound();
                break;
            case "Goal":
                if (collision.getObjectID() == "Player 1 Goal") {
                    //play sound
                    soundClip.stopPlayingSound();
                    //reset status of power up for player 1.
                    //change speed of puck back to normal
                    if(super.getPlayerOnePowerUp()){
                        addPointsToPlayer(1, 2); //get two points for scoring a goal on opposing player who activated power up mode
                    }
                    else {
                        addPointsToPlayer(1, 1);
                    }
                    puck.setSpeed(5.5);//change speed of puck back to normal
                    //reset status of power up regardless of who made the goal
                    super.setPlayerOnePowerUp(false);
                    super.setPlayerTwoPowerUp(false);

                    /** If Player One Wins **/
                    if(super.getVictor() == 1){
                        //display player one victory text, play a cheer, and end the game.
                        playerOneVictoryText.setText("Player One Wins");
                        soundClip.playCelebrationSound();
                        timeLine.stop();
                    }
                    else {
                        soundClip.playGoalSound();
                        puck.reset();
                        }
                    }
                else if (collision.getObjectID() == "Player 2 Goal") {
                    soundClip.stopPlayingSound();
                    if(super.getPlayerTwoPowerUp()){
                        addPointsToPlayer(2, 2); //get two points for scoring a goal on opposing player who activated power up mode
                    }
                    else {
                        addPointsToPlayer(2, 1);
                    }
                    puck.setSpeed(5.5);//change speed of puck back to normal
                    //reset status of power up regardless of who made the goal
                    super.setPlayerOnePowerUp(false);
                    super.setPlayerTwoPowerUp(false);
                    /** If Player Two Wins **/
                    if(super.getVictor() == 2){
                        //display player two victory text, play a cheer, and end the game.
                        playerTwoVictoryText.setText("Player Two Wins");
                        soundClip.playCelebrationSound();
                        timeLine.stop();
                    }
                    else {
                        soundClip.playGoalSound();
                        puck.reset();
                    }

                }
                break;
            case "Paddle":
                double puckCenter = ((Puck) puck).getCenterY();
                double angle;
                if (collision.getObjectID() == "Player 1 Paddle") {
                    angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
                    //play sound
                    soundClip.stopPlayingSound();
                    soundClip.playPaddleOneSound();

                } else {
                    angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
                    //play sound
                    soundClip.stopPlayingSound();
                    soundClip.playPaddleTwoSound();
                }
                puck.setDirection(angle);

        }
    }

    /**Used to end the game using the timeline.stop() function**/
    public void setTimeLine(Timeline timeline){
        this.timeLine = timeline;
    }

    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }

}
