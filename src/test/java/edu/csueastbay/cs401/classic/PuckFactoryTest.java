package edu.csueastbay.cs401.classic;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PuckFactoryTest {
    PuckFactory testPuckFactory;

    @BeforeEach
    void setup() {
        testPuckFactory = new PuckFactory(500, 500);
    }

    @Test
    void createPuck() {

    }
}