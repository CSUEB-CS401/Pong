package edu.csueastbay.cs401.ethan.game;

import javafx.scene.shape.Shape;

public record Collision<T extends Collidable>(T other, Shape intersection) {
}
