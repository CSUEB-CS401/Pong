package edu.csueastbay.cs401.pong;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;

    @BeforeEach
    public void setup() {
        game = new TestGame(10);
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

        Paddle player1 = new Paddle("Player 1", 10, 10, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        Paddle player2 = new Paddle("Player 1", 100, 10, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        game.move();
        assertFalse(((TestGame) game).collisionDetected);
        Wall wall = new Wall("Big Wall", 0, 0, 1000, 500);
        game.addObject(wall);
        game.move();
        assertTrue(((TestGame) game).collisionDetected);
    }




    @Test
    void eKeyPressed() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        Paddle player1 = new Paddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        Paddle player2 = new Paddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        game.keyPressed(KeyCode.E);
        game.move();

        assertEquals(95, player1.getY(),
                "Player 1 Paddle should move up when E key is pressed");

    }

    @Test
    void iKeyPressed() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        Paddle player1 = new Paddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        Paddle player2 = new Paddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        game.keyPressed(KeyCode.I);
        game.move();

        assertEquals(95, player2.getY(),
                "Player 2 Paddle should move up when I key is pressed");

    }

    @Test
    void dKeyPressed() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        Paddle player1 = new Paddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        Paddle player2 = new Paddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        game.keyPressed(KeyCode.D);
        game.move();

        assertEquals(105, player1.getY(),
                "Player 1 Paddle should move down when D key is pressed");

    }

    @Test
    void kKeyPressed() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        Paddle player1 = new Paddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        Paddle player2 = new Paddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        game.keyPressed(KeyCode.K);
        game.move();

        assertEquals(105, player2.getY(),
                "Player 2 Paddle should move down when K key is pressed");

    }


    @Test
    void eKeyReleased() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        Paddle player1 = new Paddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        Paddle player2 = new Paddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

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

        Paddle player1 = new Paddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        Paddle player2 = new Paddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

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

        Paddle player1 = new Paddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        Paddle player2 = new Paddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

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

        Paddle player1 = new Paddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        Paddle player2 = new Paddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        game.keyPressed(KeyCode.K);
        game.move();
        game.keyReleased(KeyCode.K);
        game.move();
        game.move();

        assertEquals(105, player2.getY(),
                "Player 2 Paddle should move down when K key is pressed");

    }




    private class TestGame extends Game {

        public boolean collisionDetected = false;

        public TestGame(int victoryScore) {
            super(victoryScore);
        }

        @Override
        public void collisionHandler(Puckable puck, Collision collision) {
            collisionDetected = true;
        }
    }


}