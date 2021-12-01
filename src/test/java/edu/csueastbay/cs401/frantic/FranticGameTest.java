package edu.csueastbay.cs401.frantic;

import edu.csueastbay.cs401.classic.ClassicPong;
import edu.csueastbay.cs401.pong.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FranticGameTest {

    FranticPong game;

    @BeforeEach
    void setup(){
        game = new FranticPong(10,1300,860);
    }
    @Test
    void ShouldHaveOnePuckAtStart () {
        ArrayList<Puckable> pucks = game.getPucks();
        assertEquals(1, pucks.size(), "There should be one puck");
    }

    @Test
    void shouldHaveTwoWalls() {
        ArrayList<Collidable> game_objects = game.getObjects();
        int counter = 0;
        for (Collidable object : game_objects) {
            if (object.getType() == "Wall") {
                counter++;
            }
        }
        assertEquals(2, counter, "Should have two Walls");
    }

    @Test
    void shouldHave6Barriers(){
        ArrayList<Collidable> game_objects = game.getObjects();
        int counter = 0;
        for (Collidable object : game_objects) {
            if (object.getType() == "Barrier") {
                counter++;
            }
        }
        assertEquals(6, counter, "Should have Six Barriers");
    }

    @Test
    void shouldHave1Booster(){
        ArrayList<Collidable> game_objects = game.getObjects();
        int counter = 0;
        for (Collidable object : game_objects) {
            if (object.getType() == "Booster") {
                counter++;
            }
        }
        assertEquals(1, counter, "Should have one Booster");
    }

    @Test
    void shouldHave1Sensor(){
        ArrayList<Collidable> game_objects = game.getObjects();
        int counter = 0;
        for (Collidable object : game_objects) {
            if (object.getType() == "Sensor") {
                counter++;
            }
        }
        assertEquals(1, counter, "Should have one Sensor");
    }

    @Test
    void shouldHave2Gages(){
        ArrayList<Collidable> game_objects = game.getObjects();
        int counter = 0;
        for (Collidable object : game_objects) {
            if (object.getType() == "Gage") {
                counter++;
            }
        }
        assertEquals(2, counter, "Should have two Gages");
    }

    @Test
    void shouldHaveTopWall() {
        ArrayList<Collidable> game_objects = game.getObjects();
        Wall top_wall = null;
        for (Collidable object : game_objects) {
            if (object.getID() == "Top Wall") {
                top_wall = (Wall)object;
            }
        }
        assertNotEquals(null, game_objects);
        assertEquals(0, top_wall.getX());
        assertEquals(0, top_wall.getY());
        assertEquals(1300, top_wall.getWidth());
        assertEquals(10, top_wall.getHeight());
    }

    @Test
    void shouldHaveBottomWall() {
        ArrayList<Collidable> game_objects = game.getObjects();
        Wall top_wall = null;
        for (Collidable object : game_objects) {
            if (object.getID() == "Bottom Wall") {
                top_wall = (Wall)object;
            }
        }
        assertNotEquals(null, game_objects);
        assertEquals(0, top_wall.getX());
        assertEquals(850, top_wall.getY());
        assertEquals(1300, top_wall.getWidth());
        assertEquals(10, top_wall.getHeight());
    }

    @Test
    void shouldHavePlayer1Goal() {
        ArrayList<Collidable> game_objects = game.getObjects();
        Goal player_1_goal = null;
        for (Collidable object : game_objects) {
            if (object.getID() == "Player 1 Goal") {
                player_1_goal = (Goal)object;
            }
        }
        assertNotEquals(null, game_objects);
        assertEquals(1290, player_1_goal.getX());
        assertEquals(10, player_1_goal.getY());
        assertEquals(10, player_1_goal.getWidth());
        assertEquals(840, player_1_goal.getHeight());
    }

    @Test
    void shouldHavePlayer2Goal() {
        ArrayList<Collidable> game_objects = game.getObjects();
        Goal player_1_goal = null;
        for (Collidable object : game_objects) {
            if (object.getID() == "Player 2 Goal") {
                player_1_goal = (Goal)object;
            }
        }
        assertNotEquals(null, game_objects);
        assertEquals(0, player_1_goal.getX());
        assertEquals(10, player_1_goal.getY());
        assertEquals(10, player_1_goal.getWidth());
        assertEquals(840, player_1_goal.getHeight());
    }

    @Test
    void shouldHavePlayer1Paddle() {
        ArrayList<Collidable> game_objects = game.getObjects();
        Paddle player_1_paddle = null;
        for (Collidable object : game_objects) {
            if (object.getID() == "Player 1 Paddle") {
                player_1_paddle = (FranticPaddle)object;
            }
        }
        assertNotEquals(null, game_objects);
        assertEquals(50, player_1_paddle.getX());
        assertEquals(380, player_1_paddle.getY());
        assertEquals(10, player_1_paddle.getWidth());
        assertEquals(100, player_1_paddle.getHeight());
    }

    @Test
    void shouldHavePlayer2Paddle() {
        ArrayList<Collidable> game_objects = game.getObjects();
        Paddle player_2_paddle = null;
        for (Collidable object : game_objects) {
            if (object.getID() == "Player 2 Paddle") {
                player_2_paddle = (FranticPaddle)object;
            }
        }
        assertNotEquals(null, game_objects);
        assertEquals(1250, player_2_paddle.getX());
        assertEquals(380, player_2_paddle.getY());
        assertEquals(10, player_2_paddle.getWidth());
        assertEquals(100, player_2_paddle.getHeight());
    }

    @Test
    void HittingAWallShouldMakePuckReverseDirection() {
        Puck puck = new Puck(500, 500);
        puck.setCenterX(100);
        puck.setCenterY(100);
        puck.setDirection(45);
        Collision bang = new Collision(
                "Wall",
                "Test Wall",
                true,
                0,
                500,
                90,
                110);

        game.collisionHandler(puck, bang);
        assertEquals(-45, puck.getDirection());
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
                110);

        game.collisionHandler(puck, bang);
        assertEquals(1, game.getPlayerScore(1));
    }
    @Test
    void HittingPlayer2GoalAddAPointToPlayer2() {
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
                110);

        game.collisionHandler(puck, bang);
        assertEquals(1, game.getPlayerScore(2));
    }

    @Test
    void hittingBarrierChangesPuckDirection(){
        Puck puck = new Puck(500, 500);
        puck.setCenterX(100);
        puck.setCenterY(100);
        puck.setDirection(45);
        Collision bang = new Collision(
                "Barrier",
                "Test Barrier",
                true,
                0,
                500,
                90,
                110);

        game.collisionHandler(puck, bang);
        assertNotEquals(45, puck.getDirection(),"Puck direction should not remain 45 after hitting barrier");
    }


    @Test
    void hittingBoostShouldBoostPuckSpeed(){
        Puck puck = new Puck(500, 500);
        puck.setCenterX(100);
        puck.setCenterY(100);
        puck.setDirection(45);
        puck.setSpeed(20);
        Collision bang = new Collision(
                "Booster",
                "Test Booster",
                true,
                0,
                500,
                90,
                110);

        game.collisionHandler(puck, bang);
        assertEquals(30, puck.getSpeed(),"Puck speed should be 30 after being boosted");
    }

    @Test
    void mapRange0to10and30to50() {
        double result = ClassicPong.mapRange(0,10,30,50, 5);
        assertEquals(40, result);

    }

    @Test
    void mapRange100to200and20to50() {
        double result = ClassicPong.mapRange(200,300,20,50, 225);
        assertEquals(27.5, result);
    }
}
