package edu.csueastbay.cs401.psander.engine.physics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpanTest {

    @Test
    public void testStaticMethods() {
        assertDoesNotThrow( () -> {
            var zero = Span.Zero;
        });

        assertDoesNotThrow( () -> {
            var inf = Span.Infinite;
        });
    }

    @Test
    public void TestConstructor() {
        var span1 = Span.getSpan(2.0, 3.0);
        assertEquals(1.0, span1.Duration);
        assertEquals(2.0, span1.Start);
        assertEquals(3.0, span1.End);

        var span2 = Span.getSpan(3.0, 2.0);
        assertEquals(1.0, span2.Duration);
        assertEquals(2.0, span2.Start);
        assertEquals(3.0, span2.End);
    }

    @Test
    public void TestIntersection() {
        var span1 = Span.getSpan(2.0, 6.0);
        var span2 = Span.getSpan(4.0, 8.0);
        var span3 = Span.getSpan(20.0, 40.0);

        assertEquals(Span.Zero, Span.intersection(span1, Span.Zero));
        assertEquals(Span.Zero, Span.intersection(Span.Zero, span2));
        assertEquals(span2, Span.intersection(Span.Infinite, span2));
        assertEquals(span1, Span.intersection(span1, Span.Infinite));

        var res1 = Span.intersection(span1, span2);
        assertEquals(2.0, res1.Duration);
        assertEquals(4.0, res1.Start);
        assertEquals(6.0, res1.End);

        var res2 = Span.intersection(span2, span1);
        assertEquals(2.0, res1.Duration);
        assertEquals(4.0, res1.Start);
        assertEquals(6.0, res1.End);

        assertEquals(Span.Zero, Span.intersection(span1, span3));
    }

    @Test
    public void TestStringOverride() {
        var str = Span.Zero.toString();
        System.out.println(str);
        assertEquals("0.0[NaN, NaN]", str);
    }
}
