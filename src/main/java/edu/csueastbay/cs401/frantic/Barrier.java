package edu.csueastbay.cs401.frantic;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Random;


public class Barrier extends Rectangle implements Collidable{

    public enum Owner{
        ORPHAN, PLAYER_ONE, PLAYER_TWO
    }

    private final String id;
    private double fieldWidth;
    private double fieldHeight;
    private double offset;
    private double originX;
    private double originY;

    public Barrier(String id, double x, double y, double width, double height, double fieldWidth, double fieldHeight, double offset){
        super(x, y, width, height);
        originX = x;
        originY = y;
        this.fieldWidth = (fieldWidth - width) - offset;
        this.fieldHeight = fieldHeight-offset;
        this.offset = offset;
        this.id = id;
    }

    public void shuffle(Owner side){
        Random random = new Random();
        double x = random.nextDouble()*fieldWidth/2;
        if (side == Owner.PLAYER_ONE) x = x+(fieldWidth/2);
        else x= x+offset;
        double y = random.nextDouble()*fieldHeight;
        this.setX(x);
        this.setY(y);
    }

    public void reset(){
        setX(originX);
        setY(originY);
    }

    @Override
    public Collision getCollision(Shape shape) {
        return new Collision(
                "Barrier",
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
        return id;
    }

    @Override
    public String getType() {
        return "Barrier";
    }
}
