package edu.csueastbay.cs401.frantic;
import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Circle;
import edu.csueastbay.cs401.pong.Collidable;
import java.util.Random;


//The booster is a slow moving large area that multiplies speed of pucks by 1.5
//it spawns in a random corner and moves inwards until it will move out of the game space
//and randomly jumps back to a corner

/**
 * Collidable Circle that can multiply values by a fixed amount.
 * Can Spawn in any of the 4 corners and moves inwards.
 * @see FranticPong
 * @see edu.csueastbay.cs401.pong.Collidable
 */
public class Booster extends Circle implements Collidable {

    public static final double STARTING_SPEED = 1;
    public static final int STARTING_RADIUS = 100;
    public static final double BOOST_VALUE = 1.5;
    public static final double MAX_ANGLE = 80;
    public static final double MIN_ANGLE = 5;
    public static final double OFFSET = 10;
    private final double fieldWidth;
    private final double fieldHeight;
    private boolean isBoosted;
    private String id;
    private Double speed;
    private Double direction;

    /**
     * Constructor
     * @param id
     * @param fieldWidth width of game field
     * @param fieldHeight height of game field
     */
    public Booster(String id, double fieldWidth, double fieldHeight) {
        super();
        this.id=id;
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        isBoosted=false;
        setRadius(STARTING_RADIUS);
        setFill(Color.DARKORANGE);
        setOpacity(0.2);
        reset();
    }

    /**
     * Creates collision
     * @see edu.csueastbay.cs401.pong.Collidable
     * @see edu.csueastbay.cs401.pong.Collision
     * @param shape
     * @return collision
     */
    @Override
    public Collision getCollision(Shape shape) {
        return new Collision(
                "Booster",
                this.id,
                this.getLayoutBounds().intersects(shape.getLayoutBounds()),
                this.getLayoutBounds().getMinY(),
                this.getLayoutBounds().getMaxY(),
                this.getLayoutBounds().getMinX(),
                this.getLayoutBounds().getMaxX()
        );
    }

    /**
     *  get ID
     * @return ID
     */
    @Override
    public String getID() {
        return id;
    }

    /**
     * get type
     * @return type "Booster"
     */
    @Override
    public String getType() {
        return "Booster";
    }


    //this function handles the initial corner as well as direction of the booster

    /**
     * resets position and orientation to new random values within fixed parameters
     */
    public void reset() {
        Random random = new Random();
        speed = STARTING_SPEED;
        int corner = random.nextInt(4);
        double position = STARTING_RADIUS + OFFSET;

        //determines starting position in one of 4 corners
        switch(corner){
            case 0:
                setCenterX(position);
                setCenterY(position);
                break;
            case 1:
                setCenterX(fieldWidth-position);
                setCenterY(position);
                break;
            case 2:
                setCenterX(fieldWidth-position);
                setCenterY(fieldHeight-position);
                break;
            case 3:
                setCenterX(position);
                setCenterY(fieldHeight-position);
                break;
        }
        //determines the angle
        direction = (random.nextDouble() * MAX_ANGLE) + MIN_ANGLE + (90 * corner);
    }

    //stolen from puck

    /**
     * calculates trajectory for Booster
     */
    public void move() {
        double deltaX = speed * Math.cos(Math.toRadians(direction));
        double deltaY = speed * Math.sin(Math.toRadians(direction));
        setCenterX(getCenterX() + deltaX);
        setCenterY(getCenterY() + deltaY);
        if (this.getCenterX()<STARTING_RADIUS || this.getCenterX()>(fieldWidth-STARTING_RADIUS)) reset();
        if (this.getCenterY()<STARTING_RADIUS || this.getCenterY()>(fieldHeight-STARTING_RADIUS)) reset();
    }

    //a boolean value determines whether a boost has already occurred, no stacking

    /**
     *  get boolean value for if boosted
     * @return boolean
     */
    public boolean getIsBoosted(){ return isBoosted;}

    /**
     * set if boosted
     * @param boost boolean if boosted
     */
    public void setIsBoosted(boolean boost) {isBoosted=boost;}

    /**
     * multiplies value
     * @param val initial value
     * @return increased value
     */
    public double boost(double val){return val * BOOST_VALUE;}
}
