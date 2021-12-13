package edu.csueastbay.cs401.ggamata2011;

import edu.csueastbay.cs401.pong.Collidable;
import edu.csueastbay.cs401.pong.Collision;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.image.Image;

import java.util.Random;

/**
 * UpGradeHeight Class used to Instantiate a HeightItem on field
 */
public class UpgradeHeight extends Rectangle implements Collidable
{
    private String id;
    private double TopBound;
    private double BottomBound;
    private double LeftBound;
    private double RightBound;

    boolean InPlay = false;

    Image Upgradeimg = new Image(getClass().getResource("heightup.png").toExternalForm());


    private static final int Default_Width = 5;
    private static final int Default_Height = 5;

    /**
     * Default Constructor to instantiate UpgradeHeight object which constructs a Square shape
     * @param ID ID used to identify what the instantiation is
     * @param xcord xcoordinate used for initial spawn location (arbitratry)
     * @param ycord ycoordinate used for initial spawn location (arbitrary)
     * @param width Width of the UpgradeHeight Item
     * @param height The Height of the UpgradeHeight Item
     * @param TopBound The Top bound of the playable area for spawn
     * @param BottomBound The bottom bound of the playable area for spawn
     * @param LeftBound The Left bound of the playable area for spawn
     * @param RightBound The right bound of the playable area for spawn
     */
    public UpgradeHeight(String ID, double xcord, double ycord, double width, double height, double TopBound, double BottomBound
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
        setFill(new ImagePattern(Upgradeimg));
        setVisible(false);
        InPlay = false;
    }

    /**
     * Gets Collision with Shape returns the state of a Collision
     * if it is intersected with UpgradeHeight or not
     * @param shape Used for intersects
     * @return Returns a Collision object
     */
    @Override
    public Collision getCollision(Shape shape) {
        return new Collision(
                "UpgradeHeight",
                this.id,
                this.getLayoutBounds().intersects(shape.getLayoutBounds()),
                this.getLayoutBounds().getMinY(),
                this.getLayoutBounds().getMaxY(),
                this.getLayoutBounds().getMinX(),
                this.getLayoutBounds().getMaxX()
        );
    }

    /**
     * Returns ID of HeightUpgrade object
     * @return an ID
     */
    @Override
    public String getID() {
        return id;
    }

    /**
     * Returns the Type of  HeightUpgrade
     * @return returns a string "HeightUpgrade"
     */
    @Override
    public String getType() {
        return "UpgradeHeight";
    }

    /**
     * Sets InPlay to true
     * Also sets the item to visible on the field
     */
    public void InPlay()
    {
        InPlay = true;
        setVisible(true);
    }

    /**
     * Sets InPlay to false
     * Also sets the item to invisible on the field
     */
    public void OutOfPlay()
    {
        InPlay = false;
        setVisible(false);
    }

    /**
     * Returns the Inplay status of the HeightUpgrade Item
     * @return the Inplay status of the HeightUpgrade Item
     */
    public Boolean PlayState()
    {
        return InPlay;
    }


    /**
     * Generates a Random Number for an UpgradeablePaddle to modify
     * its height
     * @return a Number between 15 and 1
     * @see UpgradeablePaddle
     */
    public double HeightModify()
    {
        Random rand = new Random();
        double HeightModifier= rand.nextInt(15)+1;
        OutOfPlay();
        return HeightModifier;
    }


    /**
     * Reset position on the field of the HeightUpgrade item
     */
    public void ResetPosition()
    {
        Random rand = new Random();
        //Must be placed within bounds of the playfield
        double Xpos = (LeftBound) + ((RightBound)-(LeftBound))*rand.nextDouble();
        double Ypos = (BottomBound) + ((TopBound)-(BottomBound))*rand.nextDouble();

        setX(Xpos);
        setY(Ypos);
    }}
