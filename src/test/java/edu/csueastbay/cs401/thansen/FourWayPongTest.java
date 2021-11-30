package edu.csueastbay.cs401.thansen;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import edu.csueastbay.cs401.pong.Puck;
import edu.csueastbay.cs401.pong.Puckable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FourWayPongTest {

    FourWayPong game;

    @BeforeEach
    void setUp() {
        game = new FourWayPong(10, 1300, 1300);
    }

    @Test
    void shouldHaveOnePuckAtStart() {
        ArrayList<Puckable> pucks = game.getPucks();
        assertEquals(1, pucks.size(), "There should be one puck");
    }

    @Test
    void shouldHaveTwelveWalls() {
        ArrayList<Collidable> game_objects = game.getObjects();
        int counter = 0;
        for (Collidable object : game_objects) {
            if (object.getType() == "Wall") {
                counter++;
            }
        }
        assertEquals(12, counter, "Should have twelve Walls");
    }

    @Test
    void shouldHaveFourGoals() {
        ArrayList<Collidable> game_objects = game.getObjects();
        int counter = 0;
        for (Collidable object : game_objects) {
            if (object.getType() == "Goal") {
                counter++;
            }
        }
        assertEquals(4, counter, "Should have four Goals");
    }

    @Test
    void shouldHaveTwoVerticalPaddles() {
        ArrayList<Collidable> game_objects = game.getObjects();
        int counter = 0;
        for (Collidable object : game_objects) {
            if (object.getType() == "Paddle") {
                counter++;
            }
        }
        assertEquals(2, counter, "Should have two vertical Paddles");
    }

    @Test
    void shouldHaveTwoHorizontalPaddles() {
        ArrayList<Collidable> game_objects = game.getObjects();
        int counter = 0;
        for (Collidable object : game_objects) {
            if (object.getType() == "HorizontalPaddle") {
                counter++;
            }
        }
        assertEquals(2, counter, "Should have two HorizontalPaddles");
    }

    @Test
    void hittingAHorizontalWallShouldMakePuckReverseVerticalDirection() {
        Puck puck = new Puck(500, 500);
        puck.setCenterX(100);
        puck.setCenterY(100);
        puck.setDirection(45);
        Collision bang = new Collision(
                "Wall",
                "Top Left Wall",
                true,
                0,
                500,
                90,
                110
        );

        game.collisionHandler(puck, bang);
        assertEquals(-45, puck.getDirection());
    }

    @Test
    void hittingAVerticalWallShouldMakePuckReverseVerticalDirection() {
        Puck puck = new Puck(500, 500);
        puck.setCenterX(100);
        puck.setCenterY(100);
        puck.setDirection(45);
        Collision bang = new Collision(
                "Wall",
                "Left Top Wall",
                true,
                0,
                500,
                90,
                110
        );

        game.collisionHandler(puck, bang);
        assertEquals(135, puck.getDirection());
    }

    @Test
    void hittingPlayer1GoalAddAPointToPlayer1() {
        Puck puck = new Puck(500, 500);
        puck.setCenterX(100);
        puck.setCenterY(100);
        Collision bang = new Collision(
                "Goal",
                "Player 1 Goal",
                true,
                0,
                500,
                90,
                110
        );

        game.collisionHandler(puck, bang);
        assertEquals(1, game.getPlayerScore(1));
    }

    @Test
    void hittingPlayer2GoalAddAPointToPlayer2() {
        Puck puck = new Puck(500, 500);
        puck.setCenterX(100);
        puck.setCenterY(100);
        Collision bang = new Collision(
                "Goal",
                "Player 2 Goal",
                true,
                0,
                500,
                90,
                110
        );

        game.collisionHandler(puck, bang);
        assertEquals(1, game.getPlayerScore(2));
    }

    @Test
    void hittingPlayer3GoalAddAPointToPlayer3() {
        Puck puck = new Puck(500, 500);
        puck.setCenterX(100);
        puck.setCenterY(100);
        Collision bang = new Collision(
                "Goal",
                "Player 3 Goal",
                true,
                0,
                500,
                90,
                110
        );

        game.collisionHandler(puck, bang);
        assertEquals(1, game.getPlayerScore(3));
    }

    @Test
    void hittingPlayer4GoalAddAPointToPlayer4() {
        Puck puck = new Puck(500, 500);
        puck.setCenterX(100);
        puck.setCenterY(100);
        Collision bang = new Collision(
                "Goal",
                "Player 4 Goal",
                true,
                0,
                500,
                90,
                110
        );

        game.collisionHandler(puck, bang);
        assertEquals(1, game.getPlayerScore(4));
    }

    @Test
    void shouldHaveNoDeadPlayersAtStart() {
        for (int player = 1; player < FourWayPong.NUM_PLAYERS; ++player) {
            shouldBeAlive(player);
        }
    }

    @Test
    void shouldHaveNoVictorAtStart() {
        shouldHaveNoVictor();
    }

    @Test
    void shouldEliminatePlayerAfterAccumulatingPoints() {
        game.addPointsToPlayer(1, game.getLoseScore());
        shouldBeDead(1);
    }

    @Test
    void shouldStillHaveNoVictorAfterPlayerEliminated() {
        game.addPointsToPlayer(1, game.getLoseScore());
        shouldBeDead(1);
        shouldHaveNoVictor();
    }

    @Test
    void shouldHaveVictorAfterEliminatingAllButOnePlayer() {
        game.addPointsToPlayer(1, game.getLoseScore());
        shouldBeDead(1);
        shouldHaveNoVictor();
        game.addPointsToPlayer(3, game.getLoseScore());
        shouldBeDead(3);
        shouldHaveNoVictor();
        game.addPointsToPlayer(4, game.getLoseScore());
        shouldBeDead(4);
        shouldHaveVictor(2);
    }

    private void shouldBeAlive(int player) {
        assertEquals(
                0, game.getPlayerScore(player),
                "Player " + player + " should have a score of 0."
        );
        assertEquals(
                0, game.playerScoreProperty(player).get(),
                "Player " + player + " should have a score of 0 via property."
        );
        assertTrue(game.isPlayerAlive(player), "Player " + player + " should still be alive.");
        assertTrue(game.playerAlive(player).get(), "Player " + player + " should still be alive via binding.");
        assertFalse(game.isPlayerDead(player), "Player " + player + " should not be dead.");
        assertFalse(game.playerDead(player).get(), "Player " + player + " should not be dead via binding.");
    }

    private void shouldBeDead(int player) {
        assertTrue(
                game.getPlayerScore(player) >= game.getLoseScore(),
                "Player " + player + " should have more than " + game.getLoseScore() + " points."
        );
        assertTrue(
                game.playerScoreProperty(player).get() >= game.getLoseScore(),
                "Player " + player + " should have more than " + game.getLoseScore() + " points via property."
        );
        assertFalse(game.isPlayerAlive(player), "Player " + player + " should not be alive.");
        assertFalse(game.playerAlive(player).get(), "Player " + player + " should not be alive via binding.");
        assertTrue(game.isPlayerDead(player), "Player " + player + " should be dead.");
        assertTrue(game.playerDead(player).get(), "Player " + player + " should be dead via binding.");
    }

    private void shouldHaveNoVictor() {
        assertEquals(0, game.getVictor(), "Should have no victor");
        assertEquals(0, game.victorProperty().get(), "Should have no victor via binding");
        assertFalse(game.isGameOver(), "Should not be game over");
        assertFalse(game.gameOver().get(), "Should not be game over via binding");
        assertTrue(game.isStillPlaying(), "Should still be playing");
        assertTrue(game.stillPlaying().get(), "Should still be playing via binding");
    }

    private void shouldHaveVictor(int player) {
        assertEquals(player, game.getVictor(), "Should have " + player + " as victor");
        assertEquals(player, game.victorProperty().get(), "Should have " + player + " as victor via binding");
        assertTrue(game.isGameOver(), "Should be game over");
        assertTrue(game.gameOver().get(), "Should be game over via binding");
        assertFalse(game.isStillPlaying(), "Should not be still playing");
        assertFalse(game.stillPlaying().get(), "Should not be still playing via binding");
    }
}
