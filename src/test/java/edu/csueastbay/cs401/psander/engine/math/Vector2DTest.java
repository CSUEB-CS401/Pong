package edu.csueastbay.cs401.psander.engine.math;

import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vector2DTest {

    @Test
    public void ConstructorTests() {
        var v1 = new Vector2D();
        assertEquals(0.0, v1.X());
        assertEquals(0.0, v1.Y());

        var v2 = new Vector2D(1.0, 2.0);
        assertEquals(1.0, v2.X());
        assertEquals(2.0, v2.Y());

        var v3 = new Vector2D(v2);
        assertEquals(1.0, v3.X());
        assertEquals(2.0, v3.Y());
    }

    @Test
    public void StaticMathMethods() {
        var v1 = new Vector2D(4.0, 3.0);
        var v2 = new Vector2D(2.0, 1.0);

        Vector2D res;
        res = Vector2D.add(v1, v2);
        assertEquals(4.0, v1.X());
        assertEquals(3.0, v1.Y());
        assertEquals(2.0, v2.X());
        assertEquals(1.0, v2.Y());
        assertEquals(6.0, res.X());
        assertEquals(4.0, res.Y());

        res = Vector2D.subtract(v1, v2);
        assertEquals(4.0, v1.X());
        assertEquals(3.0, v1.Y());
        assertEquals(2.0, v2.X());
        assertEquals(1.0, v2.Y());
        assertEquals(2.0, res.X());
        assertEquals(2.0, res.Y());

        res = Vector2D.scale(v1, 2.0);
        assertEquals(4.0, v1.X());
        assertEquals(3.0, v1.Y());
        assertEquals(8.0, res.X());
        assertEquals(6.0, res.Y());
    }

    @Test
    public void TestGettersAndSetters() {
        var v1 = new Vector2D(1.0, 2.0);

        assertEquals(1.0, v1.X());
        assertEquals(2.0, v1.Y());

        v1.setX(3.0);
        assertEquals(3.0, v1.X());
        assertEquals(2.0, v1.Y());

        v1.setY(4.0);
        assertEquals(3.0, v1.X());
        assertEquals(4.0, v1.Y());

        var v2 = new Vector2D(5.0, 6.0);
        v1.set(v2);
        assertEquals(5.0, v1.X());
        assertEquals(6.0, v1.Y());

        v1.set(7.0, 8.0);
        assertEquals(7.0, v1.X());
        assertEquals(8.0, v1.Y());
    }

    @Test
    public void TestInstanceMathMethods() {
        var v1 = new Vector2D(4.0, 3.0);
        var v2 = new Vector2D(2.0, 1.0);

        v1.add(v2);
        assertEquals(2.0, v2.X());
        assertEquals(1.0, v2.Y());
        assertEquals(6.0, v1.X());
        assertEquals(4.0, v1.Y());

        v1.subtract(v2);
        assertEquals(2.0, v2.X());
        assertEquals(1.0, v2.Y());
        assertEquals(4.0, v1.X());
        assertEquals(3.0, v1.Y());

        var v3 = new Vector2D(3.0, 4.0);
        assertEquals(5.0, v3.length());

        v3.scale(2.0);
        assertEquals(10.0, v3.length());

        v3.scale(0.0, 1.0);
        assertEquals(0.0, v3.X());
        assertEquals(8.0, v3.Y());
        assertEquals(8.0, v3.length());

        v3.normalize();
        assertEquals(1.0, v3.length());

        var v4 = new Vector2D();
        v4.normalize();
        assertEquals(0.0, v4.length());
    }

    @Test
    public void TestToStringOverride() {
        var v1 = new Vector2D();

        assertEquals("(0.0, 0.0)", v1.toString());
    }
}
