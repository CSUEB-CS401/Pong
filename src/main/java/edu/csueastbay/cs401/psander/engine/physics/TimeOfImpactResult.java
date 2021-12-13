package edu.csueastbay.cs401.psander.engine.physics;

import edu.csueastbay.cs401.psander.engine.common.Direction;

record TimeOfImpactResult(Span duration, BoxCollider c1, Direction side1,
                                 BoxCollider c2, Direction side2) {
}
