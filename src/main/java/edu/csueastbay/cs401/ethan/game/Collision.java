package edu.csueastbay.cs401.ethan.game;

import javafx.scene.shape.Shape;

/**
 * Record of a collision, containing the other {@link Collidable} and the {@link Shape intersection}
 * @param <T> The specific type of the other Collidable
 */
public record Collision<T extends Collidable>(T other, Shape intersection) {
}
