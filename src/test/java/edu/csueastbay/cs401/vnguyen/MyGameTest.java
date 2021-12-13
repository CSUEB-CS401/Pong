package edu.csueastbay.cs401.vnguyen;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MyGameTest {

    private MyGame game;

    @BeforeEach
    public void setup() {
        game = new MyTestGame(10);
    }

    @Test
    public void playerOneStartingScore() {
        assertEquals(0, game.getPlayerScore(1),
                "Play One should have a score of Zero at the start.");
    }


    @Test
    public void AddingPointToPlayerOne() {
        game.addPointsToPlayer(1, 1);
        assertEquals(1, game.getPlayerScore(1),
                "When player 1 scores their score should increment");
    }

    @Test
    public void playerTwoStartingScore() {
        assertEquals(0, game.getPlayerScore(2),
                "Play 2 should have a score of Zero at the start.");
    }

    @Test
    public void AddingPointToPlayerTwo() {
        game.addPointsToPlayer(2,1);
        assertEquals(1, game.getPlayerScore(2),
                "When player 2 scores their score should increment");
    }

    @Test
    public void setAndGetVictoryScore() {
        game.setVictoryScore(10);
        assertEquals(10, game.getVictoryScore(),
                "When the victory score is set to 10 the victory score should be ten");
    }

    @Test
    public void noWinnerAtStart() {
        assertEquals(0, game.getVictor(),
                "There should be no winner at the start of the game.");
    }

    @Test
    public void playerOneWins() {
        game.addPointsToPlayer(1,10);
        assertEquals(1, game.getVictor(),
                "Player one should be the winner after getting enough points");
    }

    @Test
    public void addObject() {
        Wall wall = new Wall("Test Wall", 0,0,680,10);
        game.addObject(wall);
        ArrayList<Collidable> objects = game.getObjects();
        assertEquals("Test Wall", objects.get(0).getID(),
                "Should be able to add a object to the wall");
    }

    @Test
    public void addPuck() {
        Puck ball = new Puck(1000, 500);
        ball.setID("Test Ball");
        game.addPuck(ball);
        ArrayList<Puckable> pucks = game.getPucks();
        assertEquals("Test Ball", pucks.get(0).getID());
    }



    @Test
    void checkCollision() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        MyPaddle player1 = new MyPaddle("Player 1", 10, 10, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        MyPaddle player2 = new MyPaddle("Player 1", 100, 10, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        MovingObject moveObj1 = new MovingObject("myObject", 490, 100, 10, 100, 0, 500);
        game.addMovingObject(1,moveObj1);
        MovingObject moveObj2 = new MovingObject("myObject", 490, 100, 10, 100, 0, 500);
        game.addMovingObject(2,moveObj2);

        game.move();
        assertFalse(((MyTestGame)game).collisionDetected);
        Wall wall = new Wall("Big Wall", 0, 0, 1000, 500);
        game.addObject(wall);
        game.move();
        assertTrue(((MyTestGame)game).collisionDetected);
    }




    @Test
    void eKeyPressed() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        MyPaddle player1 = new MyPaddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        MyPaddle player2 = new MyPaddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        MovingObject moveObj1 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        MovingObject moveObj2 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        game.addMovingObject(1,moveObj1);
        game.addMovingObject(2,moveObj2);

        game.keyPressed(KeyCode.E);
        game.move();

        assertEquals(95, player1.getY(),
                "Player 1 Paddle should move up when E key is pressed");

    }

    @Test
    void iKeyPressed() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        MyPaddle player1 = new MyPaddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        MyPaddle player2 = new MyPaddle("Player 2", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        MovingObject moveObj1 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        MovingObject moveObj2 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        game.addMovingObject(1,moveObj1);
        game.addMovingObject(2,moveObj2);

        game.keyPressed(KeyCode.I);
        game.move();

        assertEquals(95, player2.getY(),
                "Player 2 Paddle should move up when I key is pressed");

    }

    @Test
    void dKeyPressed() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        MyPaddle player1 = new MyPaddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        MyPaddle player2 = new MyPaddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        MovingObject moveObj1 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        MovingObject moveObj2 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        game.addMovingObject(1,moveObj1);
        game.addMovingObject(2,moveObj2);

        game.keyPressed(KeyCode.D);
        game.move();

        assertEquals(105, player1.getY(),
                "Player 1 Paddle should move down when D key is pressed");

    }

    @Test
    void kKeyPressed() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        MyPaddle player1 = new MyPaddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        MyPaddle player2 = new MyPaddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        MovingObject moveObj1 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        MovingObject moveObj2 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        game.addMovingObject(1,moveObj1);
        game.addMovingObject(2,moveObj2);

        game.keyPressed(KeyCode.K);
        game.move();

        assertEquals(105, player2.getY(),
                "Player 2 Paddle should move down when K key is pressed");

    }
    @Test
    void sKeyPressed() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        MyPaddle player1 = new MyPaddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        MyPaddle player2 = new MyPaddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        MovingObject moveObj1 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        MovingObject moveObj2 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        game.addMovingObject(1, moveObj1);
        game.addMovingObject(2, moveObj2);

        game.keyPressed(KeyCode.S);
        game.move();

        assertTrue(player1.getSpeedUpCollidedObj(),
                "Return true: Player 1 Paddle is ready to speed up the puck ");
    }
    @Test
    void lKeyPressed() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        MyPaddle player1 = new MyPaddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        MyPaddle player2 = new MyPaddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        MovingObject moveObj1 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        MovingObject moveObj2 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        game.addMovingObject(1, moveObj1);
        game.addMovingObject(2, moveObj2);

        game.keyPressed(KeyCode.L);
        game.move();

        assertTrue(player2.getSpeedUpCollidedObj(),
                "Return true: Player 2 Paddle is ready to speed up the puck ");

    }

            @Test
    void eKeyReleased() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        MyPaddle player1 = new MyPaddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        MyPaddle player2 = new MyPaddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        MovingObject moveObj1 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        MovingObject moveObj2 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        game.addMovingObject(1,moveObj1);
        game.addMovingObject(2,moveObj2);

        game.keyPressed(KeyCode.E);
        game.move();
        game.keyReleased(KeyCode.E);
        game.move();
        game.move();

        assertEquals(95, player1.getY(),
                "Player 1 Paddle should move up when E key is pressed");

    }

    @Test
    void iKeyReleased() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        MyPaddle player1 = new MyPaddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        MyPaddle player2 = new MyPaddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        MovingObject moveObj1 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        MovingObject moveObj2 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        game.addMovingObject(1,moveObj1);
        game.addMovingObject(2,moveObj2);

        game.keyPressed(KeyCode.I);
        game.move();
        game.keyReleased(KeyCode.I);
        game.move();
        game.move();

        assertEquals(95, player2.getY(),
                "Player 2 Paddle should move up when I key is pressed");

    }

    @Test
    void dKeyReleased() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        MyPaddle player1 = new MyPaddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        MyPaddle player2 = new MyPaddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        MovingObject moveObj1 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        MovingObject moveObj2 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        game.addMovingObject(1,moveObj1);
        game.addMovingObject(2,moveObj2);

        game.keyPressed(KeyCode.D);
        game.move();
        game.keyReleased(KeyCode.D);
        game.move();
        game.move();
        assertEquals(105, player1.getY(),
                "Player 1 Paddle should move down when D key is pressed");

    }

    @Test
    void kKeyReleased() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        MyPaddle player1 = new MyPaddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        MyPaddle player2 = new MyPaddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        MovingObject moveObj1 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        MovingObject moveObj2 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        game.addMovingObject(1,moveObj1);
        game.addMovingObject(2,moveObj2);

        game.keyPressed(KeyCode.K);
        game.move();
        game.keyReleased(KeyCode.K);
        game.move();
        game.move();

        assertEquals(105, player2.getY(),
                "Player 2 Paddle should move down when K key is pressed");

    }
    @Test
    void sKeyReleased() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        MyPaddle player1 = new MyPaddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        MyPaddle player2 = new MyPaddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        MovingObject moveObj1 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        MovingObject moveObj2 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        game.addMovingObject(1, moveObj1);
        game.addMovingObject(2, moveObj2);

        game.keyReleased(KeyCode.S);
        game.move();

        assertFalse(player1.getSpeedUpCollidedObj(),
                "Return false: Player 1 Paddle cannot speed up the puck ");
    }
        @Test
    void lKeyReleased() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        MyPaddle player1 = new MyPaddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        MyPaddle player2 = new MyPaddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        MovingObject moveObj1 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        MovingObject moveObj2 = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        game.addMovingObject(1, moveObj1);
        game.addMovingObject(2, moveObj2);

        game.keyReleased(KeyCode.L);
        game.move();

        assertFalse(player2.getSpeedUpCollidedObj(),
                "Return false: Player 2 Paddle cannot speed up the puck ");
    }

    private class MyTestGame extends MyGame {

        public boolean collisionDetected = false;

        public MyTestGame(int victoryScore) {
            super(victoryScore);
        }

        @Override
        public void collisionHandler(Puckable puck, Collision collision) {
            collisionDetected = true;
        }
    }


}