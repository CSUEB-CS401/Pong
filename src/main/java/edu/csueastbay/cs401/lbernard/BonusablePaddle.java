package edu.csueastbay.cs401.lbernard;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import edu.csueastbay.cs401.pong.Paddle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class BonusablePaddle extends Rectangle implements Collidable {

    public static final double STARTING_SPEED = 5.0;
    private String id;
    private double speed;
    private double topBound;
    private double bottomBound;

    enum Direction {UP, Down, STILL}
    private BonusablePaddle.Direction moving;

    private boolean sizeBonus;
    private boolean sizeMalus;
    private boolean moveBonus;
    private boolean lastHit;
    private double starting_height;

    public BonusablePaddle(String id, double x, double y, double width, double height, double topBound, double bottomBound) {
        super(x, y, width, height);
        this.id = id;
        this.topBound = topBound;
        this.bottomBound = bottomBound;
        moving = BonusablePaddle.Direction.STILL;
        speed = STARTING_SPEED;

        this.sizeBonus = false;
        this.sizeMalus = false;
        this.lastHit = false;
        this.moveBonus = false;
        this.starting_height = height;
    }

    public void setSizeBonus() {
        if(!this.sizeBonus) {
            if(!this.sizeMalus) {
                this.sizeBonus = true;
            }
            else {
                this.sizeMalus = false;
            }
            setHeight(getHeight() * 2);
            if(getY() + 2 * getHeight() > 850) {
                setY(getY() - (getY() + 2*getHeight() - 850));
            }
        }

    }

    public void setSizeMalus() {
        if(!this.sizeMalus) {
            if(!this.sizeBonus) {
                this.sizeMalus = true;
            }
            else {
                this.sizeBonus = false;
            }
            setHeight(getHeight() / 2);
        }
    }

    public void setMoveBonus(boolean b) {
        if(this.moveBonus != b) {
            this.moveBonus = b;
        }
    }

    public void setLastHit(boolean b) {
        this.lastHit = b;
    }

    public boolean getLastHit() {
        return this.lastHit;
    }

    public void resetPaddle() {
        setLastHit(false);
        setMoveBonus(false);
        setHeight(this.starting_height);
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
        if (moving == BonusablePaddle.Direction.UP) {
            setY(getY() - speed);
        } else if (moving == BonusablePaddle.Direction.Down) {
            setY(getY() + speed) ;
        }


        if (getY() < topBound && moveBonus) setY(bottomBound - getHeight());
        else if (getY() < topBound && !moveBonus) setY(topBound);
        double floor = bottomBound - getHeight();
        if (getY() > floor && moveBonus) setY(topBound);
        else if(getY() > floor && !moveBonus) setY(floor);

    }

    public void stop() {
        moving = BonusablePaddle.Direction.STILL;
    }

    public void moveUp() {
        moving = BonusablePaddle.Direction.UP;
    }

    public void moveDown() {
        moving = BonusablePaddle.Direction.Down;
    }
}
