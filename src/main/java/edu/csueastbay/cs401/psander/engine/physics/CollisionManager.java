package edu.csueastbay.cs401.psander.engine.physics;

import edu.csueastbay.cs401.psander.engine.common.Direction;
import edu.csueastbay.cs401.psander.engine.math.Vector2D;
import edu.csueastbay.cs401.psander.engine.gameObjects.GameObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

/**
 * <p>Calculates and dispatches collisions during a frame's update step.</p>
 *
 * <p>The game uses an a priori or continuous algorithm to detect the
 * earliest oncoming impact, advance the simulation to that time frame,
 * resolve it, and then find the new next impact.</p>
 */
public class CollisionManager {

    /**
     * Updates the state of all the scene's colliders
     * during a frame's update step.
     * @param delta The amount of time since the last frame, in seconds.
     * @param gameObjects The list of game objects to process.
     */
    public void update(double delta, List<GameObject> gameObjects)
    {
        // First phase, get all the colliders and chuck them into buckets.
        var staticColliders = new ArrayList<BoxCollider>();
        var dynamicColliders = new ArrayList<BoxCollider>();
        var kinematicColliders = new ArrayList<BoxCollider>();

        for (var go : gameObjects) {
            var c = go.getComponent(BoxCollider.class);
            if (c == null) continue;

            switch (c.getMode()) {
                case STATIC -> staticColliders.add(c);
                case DYNAMIC -> dynamicColliders.add(c);
                case KINEMATIC -> kinematicColliders.add(c);
            }
        }

        // Calculates the delta in position and rewinds
        // the collider's position, so it can be run through
        // the simulation.
        kinematicColliders.forEach( kc -> {
            var curr = kc.getOwner().Transform().Position();
            var prev = kc.getPreviousPosition();
            var diff = Vector2D.subtract(curr, prev);
            diff.scale(1/delta);

            kc.setVelocity(diff);
            kc.getOwner().Transform().Position().set(prev);
        });

        dynamicColliders.addAll(kinematicColliders);
        var remainingTime = delta;
        while(remainingTime > 0.0)
            remainingTime = performSimulationStep(remainingTime, dynamicColliders, staticColliders);

        kinematicColliders.forEach( kc -> kc.resetPreviousPosition());
    }

    private double performSimulationStep(double remainingTime, List<BoxCollider> dynamicColliders, List<BoxCollider> staticColliders) {
        var impacts = new ArrayList<TimeOfImpactResult>();

        for(var i = 0; i < dynamicColliders.size(); i ++) {
            var c1 = dynamicColliders.get(i);
            for(var j = i + 1; j < dynamicColliders.size(); j++ ) {
                var c2 = dynamicColliders.get(j);
                impacts.add(calculateTimeOfImpact(c1, c2));
            }

            for(var k = 0; k < staticColliders.size(); k++) {
                var c3 = staticColliders.get(k);
                impacts.add(calculateTimeOfImpact(c1, c3));
            }
        }

        // Now to filter out all of the results we don't need.
        var eligible = impacts.stream().filter( (i) ->
                i.duration() != Span.Zero &&
                i.duration() != Span.Infinite &&
                i.duration().Start >= 0.0 &&
                i.duration().Start <= remainingTime &&
                i.duration().End >= 0.0)
                .sorted(Comparator.comparingDouble(i -> i.duration().Start))
                .toList();

        // We have our preliminary list of candidates.
        if (eligible.size() == 0) { // List is empty
            dynamicColliders.forEach( c -> c.advance(remainingTime));
            return 0.0;
        }

        var firstTOI = eligible.get(0).duration().Start;
        eligible = eligible.stream().filter( i -> i.duration().Start == firstTOI ).toList();

        // If we have multiple results here, it means that they're all happening simultaneously.
        // So we'll need to group them together.
        var collisionDict = new Hashtable<BoxCollider, ArrayList<CollisionInformation>>();
        for(var i : eligible) {
            var c1 = i.c1();
            var c2 = i.c2();

            if (!collisionDict.contains(c1)) collisionDict.put(c1, new ArrayList<>());
            if (!collisionDict.contains(c2)) collisionDict.put(c2, new ArrayList<>());

            collisionDict.get(c1).add(new CollisionInformation(c2, i.side1()));
            collisionDict.get(c2).add(new CollisionInformation(c1, i.side2()));
        }

        for(var c : dynamicColliders)
            c.advance(firstTOI);

        collisionDict.forEach( (c, lst) -> {
            var listener = c.getOwner().getComponent(CollisionListener.class);
            if (listener == null) return;

            listener.processCollisions(lst);
        });

        return remainingTime - firstTOI;
    }

    private TimeOfImpactResult calculateTimeOfImpact(BoxCollider c1, BoxCollider c2) {
        // First we need to get the spans of overlap for both of the axes
        var c1Pos = c1.getOwner().Transform().Position();
        var c2Pos = c2.getOwner().Transform().Position();

        var horizontalSpan = calculateAxisOverlapDuration(
                c1Pos.X(), c1.getWidth(), c1.getVelocity().X(),
                c2Pos.X(), c2.getWidth(), c2.getVelocity().X());
        var verticalSpan = calculateAxisOverlapDuration(
                c1Pos.Y(), c1.getHeight(), c1.getVelocity().Y(),
                c2Pos.Y(), c2.getHeight(), c2.getVelocity().Y());


        // If both boxes are overlapping on both axes at the same time,
        // then they're going to intersect.
        var duration = Span.intersection(horizontalSpan, verticalSpan);

        Direction impact1, impact2;
        Direction[] impacts = new Direction[] { Direction.NONE, Direction.NONE};

        if (duration == Span.Zero ||    // No intersection
            duration == Span.Infinite) { // Overlapping indefinitely
            impact1 = Direction.NONE;
            impact2 = Direction.NONE;
        } else if (horizontalSpan.Start < verticalSpan.Start) { // This is a top-to-bottom collision
            if (c1Pos.Y() < c2Pos.Y()) { // c1 on top, c2 on bottom
                impact1 = Direction.BOTTOM;
                impact2 = Direction.TOP;
            } else { // c1 on bottom, c2 on top
                impact1 = Direction.TOP;
                impact2 = Direction.BOTTOM;
            }
        } else if (verticalSpan.Start < horizontalSpan.Start) { // This is a side-to-side collision
            if (c1Pos.X() < c2Pos.X()) { // c1 on left, c2 on right
                impact1 = Direction.RIGHT;
                impact2 = Direction.LEFT;
            } else { // c1 on righjt, c2 on left
                impact1 = Direction.LEFT;
                impact2 = Direction.RIGHT;
            }
        } else { // If both axes start intersecting at the same time, this is a corner collision.
            if (c1Pos.X() < c2Pos.X()) { // c1 on left, c2 on right
                if (c1Pos.Y() < c2Pos.Y()) { // c1 on top, c2 on bottom
                    impact1 = Direction.BOTTOM_RIGHT;
                    impact2 = Direction.TOP_LEFT;
                } else { // c1 on bottom, c2 on top
                    impact1 = Direction.TOP_RIGHT;
                    impact2 = Direction.BOTTOM_LEFT;
                }
            }
            else { // c1 on right, c2 on left
                if (c1Pos.Y() < c2Pos.Y()) { // c1 on top, c2 on bottom
                    impact1 = Direction.BOTTOM_LEFT;
                    impact2 = Direction.TOP_RIGHT;
                } else { // c1 on bottom, c2 on top
                    impact1 = Direction.TOP_LEFT;
                    impact2 = Direction.BOTTOM_RIGHT;
                }
            }
        }

        return new TimeOfImpactResult(duration, c1, impact1, c2, impact2);
    }

    // Calculates how long two line segments moving colinearly
    // will overlap for.
    private Span calculateAxisOverlapDuration(double coord1, double dim1, double velocity1,
                                              double coord2, double dim2, double velocity2) {
        var inner = calculateSingleCoordinateTOI(coord1, velocity1, coord2 + dim2, velocity2);
        var outer = calculateSingleCoordinateTOI(coord1 + dim1, velocity1, coord2, velocity2);

        if (inner == Double.POSITIVE_INFINITY || outer == Double.POSITIVE_INFINITY) {
            // They have the same velocity in this axis. Either they will overlap forever
            // or never overlap, depending on if they were already in overlap or not.

            var smallerBound = Math.min(coord1, coord2);
            var largerBound = Math.max(coord1 + dim1, coord2 + dim2);

            if ((largerBound - smallerBound) < (dim1 + dim2) ) // overlap
                return Span.Infinite;
            else    // Else no overlap
                return Span.Zero;
        } else
            return Span.getSpan(Math.min(inner, outer), Math.max(inner, outer));
    }


    // For 2 points moving colinearly, calculates the time of impact.
    // If they never impact, returns +infinity.
    private double calculateSingleCoordinateTOI(double coord1, double velocity1,
                                                double coord2, double velocity2) {
        var denominator = velocity1 - velocity2;
        if (denominator == 0.0) return Double.POSITIVE_INFINITY;

        return -(coord1 - coord2) / denominator;
    }
}