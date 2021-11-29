package edu.csueastbay.cs401.thansen;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import edu.csueastbay.cs401.pong.Puck;
import edu.csueastbay.cs401.pong.Puckable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FourWayPongTest {

    FourWayPong game;

    @BeforeEach
    void setUp() {
        game = new FourWayPong(10, 1300, 860);
    }

    @Test
    void ShouldHaveOnePuckAtStart() {
        ArrayList<Puckable> pucks = game.getPucks();
        assertEquals(1, pucks.size(), "There should be one puck");
    }

    @Test
    void shouldHaveEightWalls() {
        ArrayList<Collidable> game_objects = game.getObjects();
        int counter = 0;
        for (Collidable object : game_objects) {
            if (object.getType() == "Wall") {
                counter++;
            }
        }
        assertEquals(8, counter, "Should have eight Walls");
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
    void HittingAHorizontalWallShouldMakePuckReverseVerticalDirection() {
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
    void HittingAVerticalWallShouldMakePuckReverseVerticalDirection() {
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
    void HittingPlayer1GoalAddAPointToPlayer1() {
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
}
