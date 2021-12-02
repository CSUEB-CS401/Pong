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
 * Debuff Class used to Instantiate a Debuff on field
 */
public class Debuff extends Rectangle implements Collidable
{
    private String id;
    private double TopBound;
    private double BottomBound;
    private double LeftBound;
    private double RightBound;

    boolean InPlay = false;

    Image Debuffimg = new Image(getClass().getResource("debuff.jpg").toExternalForm());



    private static final int Default_Width = 5;
    private static final int Default_Height = 5;

    /**
     * Default Constructor to Debuff constructs a Square shape
     * @param ID ID used to identify what the instantiation is
     * @param xcord xcoordinate used for initial spawn location (arbitratry)
     * @param ycord ycoordinate used for initial spawn location (arbitrary)
     * @param width Width of the Debuff Item
     * @param height The Height of the Debuff Item
     * @param TopBound The Top bound of the playable area for spawn
     * @param BottomBound The bottom bound of the playable area for spawn
     * @param LeftBound The Left bound of the playable area for spawn
     * @param RightBound The right bound of the playable area for spawn
     */
    public Debuff(String ID, double xcord, double ycord, double width, double height, double TopBound, double BottomBound
            , double LeftBound, double RightBound)
    {
        //Call to Super
        super(xcord, ycord, width, height);
        this.id = id;
        this.TopBound = TopBound;
        this.BottomBound = BottomBound;
        this.LeftBound = LeftBound;
        this.RightBound = RightBound;
        setHeight(height);
        setWidth(width);
        setVisible(false);
        setFill(new ImagePattern(Debuffimg));
        InPlay = false;
    }

    /**
     * Gets Collision with Shape returns the state of a Collision
     * if it is intersected with Debuff or not
     * @param shape Used for intersects
     * @return Returns a Collision object
     */
    @Override
    public Collision getCollision(Shape shape) {
        return new Collision("Debuff",
                this.id,
                this.getLayoutBounds().intersects(shape.getLayoutBounds()),
                this.getLayoutBounds().getMinY(),
                this.getLayoutBounds().getMaxY(),
                this.getLayoutBounds().getMinX(),
                this.getLayoutBounds().getMaxX()
        );
    }

    /**
     * Returns ID of Debuff
     * @return an ID of Debuff Object
     */
    @Override
    public String getID() {
        return id;
    }

    /**
     * Returns the Type of Debuff
     * @return returns a string "Debuff"
     */
    @Override
    public String getType() {
        return "Debuff";
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
     * Returns the Inplay status of the Debuff
     * @return the Inplay status of the Debuff
     */
    public Boolean PlayState()
    {
        return InPlay;
    }


    /**
     * Returns a random integer 1-10 to see which debuff to apply
     * to Upgradeable Paddle
     * @see UpgradeablePaddle
     */
    public int DebuffRandomizer()
    {
        Random rand = new Random();
        int DebuffChoice= rand.nextInt(10)+1;
        return DebuffChoice;
    }

    /**
     * Generates a Random Number between -25 and 5
     * used to apply debuffs to Upgradeable Paddle
     * @return A Random integer between -25 and 5
     * @see UpgradeablePaddle
     */
    public double DebuffHeight()
    {
        Random rand = new Random();
        double HeightDebuff = rand.nextInt(25)+5;
        OutOfPlay();
        HeightDebuff = HeightDebuff*(-1);
        return HeightDebuff;
    }

    /**
     * Generates a Random Number between -2.50 and -0.5
     * used to apply debuffs to Upgradeable Paddle
     * @return A Random integer between -2.50 and -0.5
     * @see UpgradeablePaddle
     */
    public double DebuffSpeed()
    {
        Random rand = new Random();
        double SpeedDebuff = (0.5) + ((2.50)-(0.5))*rand.nextDouble();;
        OutOfPlay();
        SpeedDebuff = SpeedDebuff*(-1);
        return SpeedDebuff;
    }

    /**
     * Reset position on the field of the Debuff item
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
