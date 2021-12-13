package edu.csueastbay.cs401.StarWarsPong;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.paint.Color;


public class PongWars extends Game{
    private double fieldHeight;
    private double fieldWidth;

    //My variables
    public int player_1_counter;
    public int player_2_counter;
    public int jediScore;
    public int sithScore;
    public int continousPuckHit = 0;
    public double increase_puck_speed;
    public String goal = "Goal!!";
    public String clear = " ";
    public String winner = "WINNER";
    public String message;
    public String message2;

    public PongWars(int victoryScore, double fieldWidth, double fieldHeight) {
        super(victoryScore);

        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;

        Puck puck = new Puck(this.fieldWidth, this.fieldHeight);
        puck.setID("Classic");
        addPuck(puck);

        // Power Up Objects here !
        // Point Zones
        PowerPointsGain LG1 = new PowerPointsGain(this.fieldWidth, this.fieldHeight);
        LG1.setId("Life Left");
        LG1.setCenterX(325);
        LG1.setCenterY(215);
        LG1.setRadius(5);
        LG1.setFill(Color.PINK);
        addObject(LG1);

        PowerPointsGain LG2 = new PowerPointsGain(this.fieldWidth, this.fieldHeight);
        LG2.setId("Life Right");
        LG2.setCenterX(975);
        LG2.setCenterY(645);
        LG2.setRadius(5);
        LG2.setFill(Color.PINK);
        addObject(LG2);

        PowerPointsDrain DG1 = new PowerPointsDrain(this.fieldWidth,this.fieldHeight);
        DG1.setId("Drain Left");
        DG1.setCenterX(325);
        DG1.setCenterY(645);
        DG1.setRadius(5);
        DG1.setFill(Color.TURQUOISE);
        addObject(DG1);

        PowerPointsDrain DG2 = new PowerPointsDrain(this.fieldWidth,this.fieldHeight);
        DG2.setId("Drain Right");
        DG2.setCenterX(975);
        DG2.setCenterY(215);
        DG2.setRadius(5);
        DG2.setFill(Color.TURQUOISE);
        addObject(DG2);

        //Puck Speed Zones
        PowerSpeedIncrease SI1 = new PowerSpeedIncrease(this.fieldWidth,this.fieldHeight);
        SI1.setId("Increase left");
        SI1.setCenterX(432);
        SI1.setCenterY(300);
        SI1.setRadius(5);
        SI1.setFill(Color.YELLOW);
        addObject(SI1);

        PowerSpeedIncrease SI2 = new PowerSpeedIncrease(this.fieldWidth,this.fieldHeight);
        SI2.setId("Increase Right");
        SI2.setCenterX(1115);
        SI2.setCenterY(711);
        SI2.setRadius(5);
        SI2.setFill(Color.YELLOW);
        addObject(SI2);

        PowerSpeedDecrease SD1 = new PowerSpeedDecrease(this.fieldWidth,this.fieldHeight);
        SD1.setId("Decrease left");
        SD1.setCenterX(222);
        SD1.setCenterY(700);
        SD1.setRadius(5);
        SD1.setFill(Color.FUCHSIA);
        addObject(SD1);

        PowerSpeedDecrease SD2 = new PowerSpeedDecrease(this.fieldWidth, this.fieldHeight);
        SD2.setId("Decrease right");
        SD2.setCenterX(555);
        SD2.setCenterY(102);
        SD2.setRadius(5);
        SD2.setFill(Color.FUCHSIA);
        addObject(SD2);

        Wall top = new Wall("Top Wall", 0,0, this.fieldWidth, 10);
        top.setFill(Color.WHITE);
        addObject(top);

        Wall bottom = new Wall("Bottom Wall", 0, this.fieldHeight -10, this.fieldWidth, 10 );
        bottom.setFill(Color.WHITE);
        addObject(bottom);

        Goal left = new Goal("Player 1 Goal", this.fieldWidth -10, 10, 10, this.fieldHeight - 20);
        left.setFill(Color.FIREBRICK);
        addObject(left);

        Goal right = new Goal("Player 2 Goal", 0, 10, 10, this.fieldHeight - 20);
        right.setFill(Color.ROYALBLUE);
        addObject(right);

        Paddle playerOne = new Paddle(
                "Player 1 Paddle",
                50,
                (this.fieldHeight/2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerOne.setFill(Color.ROYALBLUE);
        addPlayerPaddle(1, playerOne);

        Paddle playerTwo = new Paddle(
                "Player 2 Paddle",
                this.fieldWidth - 50,
                (this.fieldHeight/2) - 50,
                10,
                100,
                10,
                this.fieldHeight - 10);
        playerTwo.setFill(Color.FIREBRICK);
        addPlayerPaddle(2, playerTwo);

        // Welcome message
        setMessage("May the force");
        setMessage2("be with you!");
        // Setting victory score
        setVictoryScore(50);
        this.player_1_counter = 0;
        this.player_2_counter = 0;
        this.jediScore = getPlayerScore(1);
        this.sithScore = getPlayerScore(2);
    }

    public int getPlayerStreak(int player)
    {
        if (player == 1) return player_1_counter;
        else if (player == 2) return player_2_counter;
        return 0;
    }

    public String getMessage(){return message;}
    public String getMessage2(){return message2;}
    public void setMessage(String message){this.message = message;}
    public void setMessage2(String message){this.message2 = message;}


    @Override
    public void collisionHandler(Puckable puck, Collision collision) {
//        System.out.println(puck.getDirection());
        switch(collision.getType()) {
            case "SpeedDecrease":
                increase_puck_speed = puck.getSpeed();
                increase_puck_speed -= .02;
                puck.setSpeed(increase_puck_speed);
            case "SpeedIncrease":
                increase_puck_speed = puck.getSpeed();
                increase_puck_speed += .02;
                puck.setSpeed(increase_puck_speed);
            case "PointsDrain":
                addPointsToPlayer(1,-1);
                addPointsToPlayer(2,-1);
            case "PointsGain":
                addPointsToPlayer(1,1);
                addPointsToPlayer(2,1);
                if(jediScore == getVictoryScore())
                {
                    if(sithScore > getVictoryScore())
                        break;
                    setMessage(winner);
                }
                if(sithScore == getVictoryScore())
                {   if(jediScore > getVictoryScore())
                        break;
                    setMessage2(winner);
                }

            case "Wall":
                puck.setDirection(0 - puck.getDirection());
                break;
            case "Goal":
                if (collision.getObjectID() == "Player 1 Goal") {
                    // displaying player 1 goal
                    setMessage(goal);
                    setMessage2(clear);

                    // display streak here
                    player_1_counter +=1;
                    if (player_1_counter != 0)
                        player_2_counter = 0;
                    // display Victor here
                    setMessage2("");
                    addPointsToPlayer(1, 1);
                    if(sithScore == getVictoryScore())
                    {
                        if(jediScore > getVictoryScore())
                            break;
                        setMessage2(winner);
                    }
                    continousPuckHit = 0;
                    puck.reset();
                } else if (collision.getObjectID() == "Player 2 Goal") {
                    //displaying player 2 goal
                    setMessage(clear);
                    setMessage2(goal);

                    //display streak here
                    player_2_counter +=1;
                    if(player_2_counter != 0)
                        player_1_counter = 0;

                    addPointsToPlayer(2, 1);

                    // if jedi scores to victory score
                    if(jediScore == getVictoryScore())
                    {
                        if(sithScore> getVictoryScore())
                            break;
                        setMessage(winner);
                    }
                    continousPuckHit = 0;
                    puck.reset();
                }
                break;
            case "Paddle":
                double puckCenter = ((Puck) puck).getCenterY();
                double angle;
                if (collision.getObjectID() == "Player 1 Paddle") {
                    angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
                    // turns off goal message after 3 continuous hits
                    if(continousPuckHit == 3)
                    {
                        setMessage(clear);
                        setMessage2(clear);
                    }
                    //add speed here
                    increase_puck_speed = puck.getSpeed();
                    increase_puck_speed += .15;
                    puck.setSpeed(increase_puck_speed);
                    continousPuckHit+=1;

                } else {
                    angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
                    // turns off goal message after 3 continuous hits
                    if(continousPuckHit == 3)
                    {
                        setMessage(clear);
                        setMessage2(clear);
                    }
                    //add puck speed here
                    increase_puck_speed = puck.getSpeed();
                    increase_puck_speed += .25;
                    puck.setSpeed(increase_puck_speed);
                    continousPuckHit+=1;
                }
                puck.setDirection(angle);

        }
    }

    public static double mapRange(double a1, double a2, double b1, double b2, double s) {
        return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
    }
}


