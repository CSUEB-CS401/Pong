package edu.csueastbay.cs401.psander.engine.math;

/**
 * A helper class to contain several useful math functions.
 */
public class Utility {

    /**
     * Maps a value in one range to a value in another range. Values
     * are translated linearly: if the original value is 25% through
     * the old range, then the returned value will be 25% of the way
     * through the new range. (E.g., 2 in [0, 10] would translate to
     * 12 in [10, 20].)
     * @param x         The original value to be mapped.
     * @param start1    The start of the original range.
     * @param end1      The end of the original range.
     * @param start2    The start of the new range.
     * @param end2      The end of the new range.
     * @return          The translated value.
     */
    public static double MapRange(double x, double start1, double end1, double start2, double end2) {
        return (x - start1) / (end1 - start1) * (end2 - start2) + start2;
    }
}
