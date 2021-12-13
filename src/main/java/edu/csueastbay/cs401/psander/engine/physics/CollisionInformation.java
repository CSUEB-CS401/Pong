package edu.csueastbay.cs401.psander.engine.physics;

import edu.csueastbay.cs401.psander.engine.common.Direction;

/**
 * A record containing information about an object a collider
 * will hit, and which side it will impact on.
 */
public record CollisionInformation(BoxCollider other, Direction location) {
}
