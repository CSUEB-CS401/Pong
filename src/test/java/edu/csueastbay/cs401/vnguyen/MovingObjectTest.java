package edu.csueastbay.cs401.vnguyen;

import edu.csueastbay.cs401.vnguyen.MovingObject;
import javafx.scene.shape.Rectangle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovingObjectTest {

    @Test
    void TestID()
    {
        MovingObject getMoveObj;
        MovingObject moveObj = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        assertEquals("myObject",moveObj.getID(),"Should get ID myObject");

    }
    @Test
    void TestType()
    {
        MovingObject getMoveObj;
        MovingObject moveObj = new MovingObject("myObject", 10, 100, 10, 100, 0, 500);
        assertEquals("Moving Object",moveObj.getType(),"Should get Type Moving Object");

    }
    @Test
    void TestWhenReachTheEdge()
    {
        MovingObject moveObj = new MovingObject("myObject", 490, 100, 10, 100, 0, 500);
        moveObj.move();
        moveObj.move();
        if (moveObj.getSpeed()==5.0)
            assertEquals(500,moveObj.getX(),"Should get 500 if move towards in positive direction");


    }



}
