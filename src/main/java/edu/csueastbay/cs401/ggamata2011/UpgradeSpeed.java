package edu.csueastbay.cs401.ggamata2011;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Random;


public class UpgradeSpeed extends Rectangle implements Collidable
{
    private String id;
    private double TopBound;
    private double BottomBound;
    private double LeftBound;
    private double RightBound;

    boolean InPlay = false;



    private static final int Default_Width = 5;
    private static final int Default_Height = 5;

    public UpgradeSpeed(String ID, double xcord, double ycord, double width, double height, double TopBound, double BottomBound
                       , double LeftBound, double RightBound)
    {
        super(xcord, ycord, width, height);
        this.id = id;
        this.TopBound = TopBound;
        this.BottomBound = BottomBound;
        this.LeftBound = LeftBound;
        this.RightBound = RightBound;
        setHeight(height);
        setWidth(width);
        setFill(Color.GREEN);
        InPlay = false;
        setVisible(false);
    }

    @Override
    public Collision getCollision(Shape shape) {
        return new Collision(
                "UpgradeSpeed",
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
        return "UpgradeSpeed";
    }

    public void InPlay()
    {
       InPlay = true;
       setVisible(true);
    }

    public void OutOfPlay()
    {
        InPlay = false;
        setVisible(false);
    }

    public Boolean PlayState()
    {
        return InPlay;
    }


    /**
     * Returns a random speed modifier for the player that has collided with it
     * @return
     */
    public double SpeedModify()
    {
        Random rand = new Random();
        double speedmodifier= rand.nextInt(2)+1;
        OutOfPlay();
        return speedmodifier;
    }
    /**
     * Reset position on the field of the upgrade puck
     */
    public void ResetPosition()
    {
        Random rand = new Random();
        //Must be placed within bounds of the playfield
        double Xpos = (LeftBound) + ((RightBound)-(LeftBound))*rand.nextDouble();
        double Ypos = (BottomBound) + ((TopBound)-(BottomBound))*rand.nextDouble();

        setX(Xpos);
        setY(Ypos);
    }



}
