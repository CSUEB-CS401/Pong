package edu.csueastbay.cs401.pong;

import javafx.scene.shape.Shape;

public interface Collidable {
    public Collision getCollision(Shape shape);
    public String getID();
    public String getType();

}
