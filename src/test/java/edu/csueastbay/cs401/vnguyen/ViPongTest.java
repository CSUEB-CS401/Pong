package edu.csueastbay.cs401.vnguyen;

import edu.csueastbay.cs401.pong.*;
import edu.csueastbay.cs401.vnguyen.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Color;
import java.awt.*;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ViPongTest {
    ViPong pong;
    int victoryScore = 10;
    double fieldWidth = 500, fieldHeight = 500;


    @BeforeEach
    public void setup() {
        pong = new ViPong(victoryScore, fieldWidth, fieldHeight);
    }

    @Test
    void playMusic() {
        pong.playSound("src/main/java/edu/csueastbay/cs401/vnguyen/start.wav");
    }

    @Test
    void mapRangTest() {
        double angle = pong.mapRange(50, 150, 225, 135, 0);
        //return b1 + ((s - a1)*(b2 - b1))/(a2 - a1);
        assertEquals(270, angle, "Should get");
    }
    @Test
            void TestWall() {
        Puck testPuck = new Puck(500, 500);
        Collision bang = new Collision(
                "Wall",
                "Test Wall",
                true,
                50,
                200,
                50,
                125);
        pong.collisionHandler(testPuck, bang);
    }

    @Test
    void TestPaddle() {
        Puck testPuck = new Puck(500, 500);
        Collision bang = new Collision(
                "Paddle",
                "Test Paddle",
                true,
                50,
                200,
                50,
                125);
        pong.collisionHandler(testPuck, bang);
    }
    @Test
    void TestMovingObject() {
        Puck testPuck = new Puck(500, 500);
        Collision bang = new Collision(
                "Moving Object",
                "Test Moving Obj",
                true,
                50,
                200,
                50,
                125);
        pong.collisionHandler(testPuck, bang);
    }
    @Test
    void TestGoal() {
        Puck testPuck = new Puck(500, 500);
        Collision bang = new Collision(
                "Goal",
                "Test Goal",
                true,
                50,
                200,
                50,
                125);
        pong.collisionHandler(testPuck, bang);
    }

}







