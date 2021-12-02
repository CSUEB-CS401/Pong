package edu.csueastbay.cs401.lbernard;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class SuperBonus extends Circle implements Collidable {

    public static final int radius = 20;
    private final double fieldWidth;
    private final double fieldHeight;
    private String id;
    private Color bonusColor;


    public SuperBonus(double fieldWidth, double fieldHeight, String id, double x, double y, Color c) {
        super(x, y, radius, c);

        this.id = id;
        this.bonusColor = c;
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
    }

    public Color GetBonusColor() {
        System.out.println(this.bonusColor.toString()); return this.bonusColor;
    }

    @Override
    public Collision getCollision(Shape shape) {
        return new Collision(
                "SuperBonus",
                this.id,
                this.getLayoutBounds().intersects(shape.getLayoutBounds()),
                this.getLayoutBounds().getMinY(),
                this.getLayoutBounds().getMaxY(),
                this.getLayoutBounds().getMinX(),
                this.getLayoutBounds().getMaxX()
        );
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public String getType() {
        return "SuperBonus";
    }
}