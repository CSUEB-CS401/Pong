package edu.csueastbay.cs401.frantic;

import edu.csueastbay.cs401.pong.Paddle;


//Frantic Paddles have 2 features over vanilla paddles
//They add bonus speed to the puck based on how much they have moved
//If not moving they will double the pucks base movement
//If they move too much there will be no bonus built up and the puck
//will travel at ordinary speeds. The rate of decay is passed to the paddle
//in addition to the regular parameters
//The paddles can additionally get smaller dynamically during the game
//initially shrinking very quickly but slowing down over a volley
//they can also be reset back to their original size
/**
 * Paddle with bonus hit speed and shrinkable. Hit bonus is determined by recent movement, more movement less bonus.
 * @see FranticPong
 * @see edu.csueastbay.cs401.pong.Paddle
 */
public class FranticPaddle extends Paddle {
    public static final double STARTING_SPEED = 10.0;
    public static final double STARTING_BONUS = 0.02;
    public static final double MAXIMUM_BONUS = 5;
    private double shrinkFactor;
    private double defaultHeight;
    private double topBound;
    private double bottomBound;
    enum Direction {UP, Down, STILL}
    private FranticPaddle.Direction moving;
    private double speed;
    private double bonus;
    private double staleBonus;
    private double totalBonus;

    /**
     * constructor
     * @param id
     * @param x x coordinate
     * @param y y coordinate
     * @param width
     * @param height
     * @param topBound upper bound for y coordinate
     * @param bottomBound lower bound for y coordinate
     * @param staleing speed of bonus loss on movement
     */
    public FranticPaddle(String id, double x, double y, double width, double height, double topBound, double bottomBound, double staleing) {
        super(id, x, y, width, height, topBound, bottomBound);
        this.topBound=topBound;
        this.bottomBound=bottomBound;
        shrinkFactor = 10;
        defaultHeight = height;
        staleBonus = staleing;
        bonus=STARTING_BONUS;
        totalBonus=0;
        speed = STARTING_SPEED;
    }

    //The building and decaying of boost is called by the move function

    /**
     * Moves the paddles and increases/decreases bonus
     */
    @Override
    public void move() {
        if (moving == Direction.UP) {
            setY(getY() - speed);
            bonusStaling();
        } else if (moving == Direction.Down) {
            setY(getY() + speed) ;
            bonusStaling();
        }
        else if (moving == Direction.STILL){
            bonusBuild();
        }
        if (getY() < topBound) setY(topBound);
        double floor = bottomBound - getHeight();
        if (getY() > floor) setY(floor);

    }

    /**
     * halts movement
     */
    @Override
    public void stop() {
        moving = Direction.STILL;
    }

    /**
     * sets direction of movement to up
     */
    @Override
    public void moveUp() {
        moving = Direction.UP;
    }

    /**
     * sets direction of movement to down
     */
    @Override
    public void moveDown() {
        moving = Direction.Down;
    }


    //handles the shrinking of paddles

    /**
     * decreases size of paddle
     */
    public void shrink(){
        if (this.getHeight() < 20) return;
        this.setHeight(this.getHeight()-shrinkFactor);
        if (shrinkFactor>2) shrinkFactor--;
    }

    //returns paddle to original size and resets initial size loss

    /**
     * returns paddle to original size
     */
    public void resetHeight(){
        this.setHeight(defaultHeight);
        shrinkFactor = 10;
    }

    /**
     * removes bonus
     */
    public void resetBonus(){totalBonus=0;}

    /**
     * gets the next size loss of paddle
     * @return double shrinkFactor
     */
    public double getShrinkFactor(){return shrinkFactor;}

    /**
     * gets the current total bonus
     * @return double bonus
     */
    public double getTotalBonus(){return totalBonus;}

    //lowers bonus hit speed

    /**
     * lowers bonus
     */
    private void bonusStaling(){
        if (bonus>0){
            totalBonus= totalBonus-staleBonus;
        }
        if (totalBonus<0){totalBonus=0;}
        return;
    }

    //raises bonus hit speed

    /**
     * raises bonus
     */
    private void bonusBuild(){
        totalBonus= totalBonus+bonus;
        if (totalBonus>MAXIMUM_BONUS){totalBonus=MAXIMUM_BONUS;}
        return;
    }
}