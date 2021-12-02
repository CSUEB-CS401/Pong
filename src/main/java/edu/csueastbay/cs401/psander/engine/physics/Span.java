package edu.csueastbay.cs401.psander.engine.physics;

class Span {

    static final Span Zero = new Span(0.0, Double.NaN, Double.NaN);

    static final Span Infinite = new Span(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

    final double Duration;
    final double Start;
    final double End;

    private Span(double duration, double start, double end) {
        Duration = duration;
        Start = start;
        End = end;
    }

    static Span getSpan(double start, double end) {
        if (start < end)
            return new Span(end - start, start, end);
        else
            return new Span(start - end, end, start);
    }

    static Span intersection(Span first, Span second)
    {
        if (first == Zero || second == Zero)
            return Zero;
        else if (first == Infinite)
            return second;
        else if (second == Infinite)
            return first;
        else {
            Span earlier, later;
            if (first.Start < second.Start) {
                earlier = first;
                later = second;
            }
            else {
                earlier = second;
                later = first;
            }

            if (earlier.End > later.Start)
                return getSpan(later.Start, Math.min(earlier.End, later.End));
            else
                return Zero;
        }
    }

    @Override
    public String toString() {
        return this.Duration + "[" + this.Start + ", " + this.End + "]";
    }
}
