package edu.csueastbay.cs401.psander.engine.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilityTest {
    @Test
    public void TestMapRange() {
        var res = Utility.MapRange(2.0, 0.0, 10.0, 10.0, 30.0);
        assertEquals(14.0, res);
    }
}
