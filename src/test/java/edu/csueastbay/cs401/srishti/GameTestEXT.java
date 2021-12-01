package edu.csueastbay.cs401.srishti;

import edu.csueastbay.cs401.pong.Collision;
import edu.csueastbay.cs401.pong.Paddle;
import edu.csueastbay.cs401.pong.Puck;
import edu.csueastbay.cs401.pong.Puckable;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTestEXT {

    private TestGameEXT game;

    @BeforeEach
    public void setup() {
        game = new TestGameEXT(10);
    }

    @Test
    void xKeyPressed() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        Paddle player1 = new Paddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        Paddle player2 = new Paddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        game.keyPressed(KeyCode.X);
        game.move();

        assertEquals(95, player1.getY(), "Player 1 Paddle should move up when X key is pressed");

    }

    @Test
    void upKeyPressed() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        Paddle player1 = new Paddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        Paddle player2 = new Paddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        game.keyPressed(KeyCode.UP);
        game.move();

        assertEquals(95, player2.getY(), "Player 2 Paddle should move up when UP key is pressed");

    }

    @Test
    void altKeyPressed() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        Paddle player1 = new Paddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        Paddle player2 = new Paddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        game.keyPressed(KeyCode.ALT);
        game.move();

        assertEquals(105, player1.getY(), "Player 1 Paddle should move down when ALT key is pressed");

    }

    @Test
    void downKeyPressed() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        Paddle player1 = new Paddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        Paddle player2 = new Paddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        game.keyPressed(KeyCode.DOWN);
        game.move();

        assertEquals(105, player2.getY(), "Player 2 Paddle should move down when DOWN key is pressed");

    }

    @Test
    void xKeyReleased() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        Paddle player1 = new Paddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        Paddle player2 = new Paddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        game.keyPressed(KeyCode.X);
        game.move();
        game.keyReleased(KeyCode.X);
        game.move();
        game.move();

        assertEquals(95, player1.getY(), "Player 1 Paddle should move up when X key is pressed");

    }

    @Test
    void upKeyReleased() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        Paddle player1 = new Paddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        Paddle player2 = new Paddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        game.keyPressed(KeyCode.UP);
        game.move();
        game.keyReleased(KeyCode.UP);
        game.move();
        game.move();

        assertEquals(95, player2.getY(), "Player 2 Paddle should move up when UP key is pressed");

    }

    @Test
    void altKeyReleased() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        Paddle player1 = new Paddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        Paddle player2 = new Paddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        game.keyPressed(KeyCode.ALT);
        game.move();
        game.keyReleased(KeyCode.ALT);
        game.move();
        game.move();
        assertEquals(105, player1.getY(), "Player 1 Paddle should move down when ALT key is pressed");

    }

    @Test
    void downKeyReleased() {
        Puck puck = new Puck(1000, 500);
        game.addPuck(puck);

        Paddle player1 = new Paddle("Player 1", 10, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(1, player1);
        Paddle player2 = new Paddle("Player 1", 100, 100, 10, 100, 0, 500);
        game.addPlayerPaddle(2, player2);

        game.keyPressed(KeyCode.DOWN);
        game.move();
        game.keyReleased(KeyCode.DOWN);
        game.move();
        game.move();

        assertEquals(105, player2.getY(), "Player 2 Paddle should move down when Down key is pressed");

    }

    private class TestGameEXT extends GameEXT {

        public boolean collisionDetected = false;

        public TestGameEXT(int victoryScore) {
            super(victoryScore);
        }

        @Override
        public void collisionHandler(Puckable puck, Collision collision) {
            collisionDetected = true;
        }
    }

}