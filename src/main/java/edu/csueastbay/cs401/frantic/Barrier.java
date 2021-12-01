package edu.csueastbay.cs401.frantic;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.util.Random;


//The barrier class are intended as smaller walls peppered into the game space
//They can be shuffled onto either side of the playing field and reset back to initial positions

/**
 * Walls that move around into specific areas of the Game. Collidable
 * @see FranticPong
 * @see edu.csueastbay.cs401.pong.Collidable
 */
public class Barrier extends Rectangle implements Collidable{

    //enum determines who hit the ball last and where the barriers should go
    public enum Owner{
        ORPHAN, PLAYER_ONE, PLAYER_TWO
    }

    private final String id;
    private double fieldWidth;
    private double fieldHeight;
    private double offset;
    private double originX;
    private double originY;

    /**
     * Constructor
     * @param id
     * @param x the x coordinate
     * @param y the y coordinate
     * @param width
     * @param height
     * @param fieldWidth width of the game field
     * @param fieldHeight height of the game field
     * @param offset minimum distance from walls
     */
    public Barrier(String id, double x, double y, double width, double height, double fieldWidth, double fieldHeight, double offset){
        super(x, y, width, height);
        originX = x;
        originY = y;
        this.fieldWidth = (fieldWidth - width) - offset;
        this.fieldHeight = fieldHeight-offset;
        this.offset = offset;
        this.id = id;
    }

    //uses field width and height, as well as an enum to place the barrier on the side of the
    //last player to hit the puck
    /**
     * Changes coordinates based on which side the barrier should move to
     * @param side
     */
    public void shuffle(Owner side){
        Random random = new Random();
        double x = random.nextDouble()*fieldWidth/2;
        if (side == Owner.PLAYER_TWO) x = x+(fieldWidth/2);
        else x= x+offset;
        double y = random.nextDouble()*(fieldHeight-offset);
        y+=offset;
        this.setX(x);
        this.setY(y);
    }

    //resets the barrier to its initial position

    /**
     * resets barrier position
     */
    public void reset(){
        setX(originX);
        setY(originY);
    }

    /**
     *  Collidable behavior
     *  @see edu.csueastbay.cs401.pong.Collidable
     *  @see edu.csueastbay.cs401.pong.Collision
     * @param shape
     * @return collision
     */
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

    /**
     * get ID
     * @return ID
     */
    @Override
    public String getID() {
        return id;
    }

    /**
     * get type
     * @return type
     */
    @Override
    public String getType() {
        return "Barrier";
    }
}
