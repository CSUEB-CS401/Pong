package edu.csueastbay.cs401.ejamdar;


import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import edu.csueastbay.cs401.pong.Paddle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class JamdarPaddle extends Paddle implements Collidable {
    public static final double STARTING_SPEED = 5.0;
    private String id;
    private double speed;
    private double topBound;
    private double bottomBound;

    enum Direction {UP, Down, STILL, RIGHT, LEFT}
    private Direction moving;


    public JamdarPaddle(String id, double x, double y, double width, double height, double topBound, double bottomBound) {
        super(id, x, y, width, height, topBound, bottomBound);
        this.id = id;
        this.topBound = topBound;
        this.bottomBound = bottomBound;
        moving = Direction.STILL;
        speed = STARTING_SPEED;
    }

    @Override
    public Collision getCollision(Shape shape) {
        return new Collision(
                "Paddle",
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
        return "Paddle";
    }

    public void move() {
        if (moving == Direction.UP) {
            setY(getY() - speed);
        } else if (moving == Direction.Down) {
            setY(getY() + speed);
        }
        if (getY() < topBound) setY(topBound);
        double floor = bottomBound - getHeight();
        if (getY() > floor) setY(floor);

    }

    public void move2() {
        if (moving == Direction.UP) {
            setY(getY() - speed);
        } else if (moving == Direction.Down) {
            setY(getY() + speed) ;
        } else if (moving == Direction.LEFT){
            setX(getX()- speed);
        } else if (moving == Direction.RIGHT){
            setX(getX()+ speed);
        }


        if (getY() < topBound) setY(topBound);
        double floor = bottomBound - getHeight();
        if (getY() > floor) setY(floor);




    }

    public void stop() {
        moving = Direction.STILL;
    }

    public void moveUp() {
        moving = Direction.UP;
    }

    public void moveDown() {
        moving = Direction.Down;
    }

    public void moveLeft() {
        moving = Direction.LEFT;
    }

    public void moveRight() {
        moving = Direction.RIGHT;
    }

}
