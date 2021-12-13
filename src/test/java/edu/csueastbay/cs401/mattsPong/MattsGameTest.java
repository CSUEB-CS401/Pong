package edu.csueastbay.cs401.mattsPong;

import edu.csueastbay.cs401.classic.ClassicPong;
import edu.csueastbay.cs401.pong.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MattsGameTest {

    private MattsGame game;
    private MattsController controller;

    @BeforeEach
    public void setup(){game = new MattsGame(10,1300,860);
    controller = new MattsController();
    }

    @Test
    void shouldHaveOnePuckAtStart(){
        ArrayList<Puckable> pucks = game.getPucks();
        assertEquals(1, pucks.size(), "There should be one puck");
    }

    @Test
    void shouldHaveTwoWalls(){
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
                player_1_paddle = (Paddle)object;
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
                player_2_paddle = (Paddle)object;
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
    void mapRange0to10and30to50() {
        double result = ClassicPong.mapRange(0,10,30,50, 5);
        assertEquals(40, result);

    }

    @Test
    void mapRange100to200and20to50() {
        double result = ClassicPong.mapRange(200,300,20,50, 225);
        assertEquals(27.5, result);
    }

    @Test
    void HittingPlayer1PaddleAddPointToPlayer1(){
        Puck puck = new Puck(500,500);
        puck.setCenterX(100);
        puck.setCenterY(100);
        Collision bang = new Collision(
                "Paddle",
                "Player 1 Paddle",
                true,
                225,
                325,
                50,
                60);
        game.collisionHandler(puck, bang);
        assertEquals(1, game.getPlayerBlockScore(1));
    }

    @Test
    void HittingPlayer2PaddleAddPointToPlayer2(){
        Puck puck = new Puck(500,500);
        puck.setCenterX(100);
        puck.setCenterY(100);
        Collision bang = new Collision(
                "Paddle",
                "Player 2 Paddle",
                true,
                225,
                325,
                50,
                60);
        game.collisionHandler(puck, bang);
        assertEquals(1, game.getPlayerBlockScore(2));
    }

    @Test
    void YellowPowerUpPlayer1(){
        game.addBlockPointsToPlayer(1,20);
        game.keyPressed(KeyCode.W);
        assertEquals(Color.YELLOW, game.getPaddleColor(1));
    }

    @Test
    void YellowPowerUpPlayer2(){
        game.addBlockPointsToPlayer(2,20);
        game.keyPressed(KeyCode.O);
        assertEquals(Color.YELLOW, game.getPaddleColor(2));
    }

    @Test
    void YellowPaddlesBackToNormal(){
        game.addBlockPointsToPlayer(1,20);
        game.keyPressed(KeyCode.W);
        long timeOne = game.getStartTime("playerOneTime");

        game.addBlockPointsToPlayer(2,20);
        game.keyPressed(KeyCode.O);
        long timeTwo = game.getStartTime("playerTwoTime");
        while(game.getTime(timeOne) < 21 && game.getTime(timeTwo) <21){
            game.checkPowerUpDuration();
        }
        Paddle paddle = game.getPaddle(1);
        Paddle paddle2 = game.getPaddle(2);

        assertEquals(Color.RED, game.getPaddleColor(1));
        assertEquals(Color.BLUE, game.getPaddleColor(2));
    }

    @Test
    void GreenPowerUpPlayer1(){
        game.addBlockPointsToPlayer(1,10);
        game.keyPressed(KeyCode.Q);
        Paddle paddle = game.getPaddle(1);
        assertEquals(Color.GREEN, game.getPaddleColor(1));
        assertEquals(200, paddle.getHeight());

    }
    @Test
    void GreenPowerUpPlayer2(){
        game.addBlockPointsToPlayer(2,10);
        game.keyPressed(KeyCode.P);
        Paddle paddle = game.getPaddle(2);
        assertEquals(Color.GREEN, game.getPaddleColor(2));
        assertEquals(200, paddle.getHeight());

    }

    @Test
    void GreenPaddlesBackToNormal(){
        game.addBlockPointsToPlayer(1,10);
        game.keyPressed(KeyCode.Q);
        long timeOne = game.getStartTime("playerOneTime2");

        game.addBlockPointsToPlayer(2,10);
        game.keyPressed(KeyCode.P);
        long timeTwo = game.getStartTime("playerTwoTime2");
        while(game.getTime(timeOne) < 21 && game.getTime(timeTwo) <21){
            game.checkPowerUpDuration();
        }
        Paddle paddle = game.getPaddle(1);
        Paddle paddle2 = game.getPaddle(2);

        assertEquals(Color.RED, game.getPaddleColor(1));
        assertEquals(100, paddle.getHeight());

        assertEquals(Color.BLUE, game.getPaddleColor(2));
        assertEquals(100, paddle2.getHeight());

    }

    @Test
    void BothPaddlePowerUps(){
        game.addBlockPointsToPlayer(1,20);
        game.keyPressed(KeyCode.W);

        game.addBlockPointsToPlayer(2,20);
        game.keyPressed(KeyCode.O);

        game.addBlockPointsToPlayer(1,10);
        game.keyPressed(KeyCode.Q);
        Paddle paddle = game.getPaddle(1);

        game.addBlockPointsToPlayer(2,10);
        game.keyPressed(KeyCode.P);
        Paddle paddle2 = game.getPaddle(2);

        assertEquals(Color.YELLOW, game.getPaddleColor(1));
        assertEquals(200, paddle.getHeight());
        assertEquals(Color.YELLOW, game.getPaddleColor(2));
        assertEquals(200, paddle2.getHeight());
    }

    @Test
    void PuckPoweredUpByPaddle1(){
        game.addBlockPointsToPlayer(1,20);
        game.keyPressed(KeyCode.W);
        Puck puck = new Puck(500,500);
        puck.setCenterX(100);
        puck.setCenterY(100);
        Collision bang = new Collision(
                "Paddle",
                "Player 1 Paddle",
                true,
                225,
                325,
                50,
                60);
        game.collisionHandler(puck, bang);
        assertEquals(Color.YELLOW, puck.getFill());
        assertEquals(8.0,puck.getSpeed());
    }
    @Test
    void PuckPoweredUpByPaddle2(){
        game.addBlockPointsToPlayer(2,20);
        game.keyPressed(KeyCode.O);
        Puck puck = new Puck(500,500);
        puck.setCenterX(100);
        puck.setCenterY(100);
        Collision bang = new Collision(
                "Paddle",
                "Player 2 Paddle",
                true,
                225,
                325,
                50,
                60);
        game.collisionHandler(puck, bang);
        assertEquals(Color.YELLOW, puck.getFill());
        assertEquals(8.0,puck.getSpeed());
    }
    @Test
    void PoweredUpPuckBackToNormal(){
        game.addBlockPointsToPlayer(1,20);
        game.keyPressed(KeyCode.W);
        Puck puck = new Puck(500,500);
        puck.setCenterX(100);
        puck.setCenterY(100);
        Collision bang = new Collision(
                "Paddle",
                "Player 1 Paddle",
                true,
                225,
                325,
                50,
                60);
        game.collisionHandler(puck, bang);
        Collision goal = new Collision("Goal",
                "Player 1 Goal",
                true,
                225,
                325,
                50,
                60);
        game.collisionHandler(puck, goal);

        game.addBlockPointsToPlayer(2,20);
        game.keyPressed(KeyCode.O);
        Puck puck2 = new Puck(500,500);
        puck.setCenterX(100);
        puck.setCenterY(100);
        Collision bang2 = new Collision(
                "Paddle",
                "Player 2 Paddle",
                true,
                225,
                325,
                50,
                60);
        game.collisionHandler(puck2, bang2);
        Collision goal2 = new Collision("Goal",
                "Player 2 Goal",
                true,
                225,
                325,
                50,
                60);
        game.collisionHandler(puck2, goal2);

        assertEquals(Color.WHITE, puck.getFill());
        assertEquals(5.0,puck.getSpeed());
        assertEquals(Color.WHITE, puck2.getFill());
        assertEquals(5.0,puck2.getSpeed());
    }
}
