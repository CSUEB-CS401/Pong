package edu.csueastbay.cs401.ggamata2011;

import edu.csueastbay.cs401.pong.Paddle;

/**
 * Upgradeable Paddle that is extended from the original Paddle
 * Allows for adjustment of Height and Speed
 */
public class UpgradeablePaddle extends Paddle
{
  //Initialization of Starting SPeed
  private double START_SPEED = 5.0;
  //Default Modifiers to SPeed and Height declared here
  private  double UPGRADE_SPEED = 0;
  private  double UPGRADE_HEIGHT = 0;

  //bounds used to limit paddle movement
  private double topBound;
  private double bottomBound;

  enum Direction {UP, Down, STILL}
  private Direction newmove;

  /**
   * Default Constructor of Upgradeable Paddle
   * @param id sets ID of Paddle
   * @param x sets default x coordinate of Paddle on field
   * @param y sets default y coordinate of Paddle on field
   * @param width sets width of paddle
   * @param height sets height of paddle
   * @param topBound sets the Topbound in which the paddle can travel
   * @param bottomBound sets the BottomBound in which the paddle can travel
   */
  public UpgradeablePaddle(String id, double x, double y, double width, double height, double topBound, double bottomBound)
  {
      super(id,x,y,width,height,topBound,bottomBound);
      this.topBound = topBound;
      this.bottomBound = bottomBound;
  }

  /**
   *Function takes a double generated from UpgradeSpeed to modify
   * its own Speed
   * @param boost added to upgrade speed to modify a paddles speed
   * @see UpgradeSpeed
   */
  public void ModifySpeed(double boost)
  {
    UPGRADE_SPEED = UPGRADE_SPEED + boost;

    if(UPGRADE_SPEED >= 10)
    {
      UPGRADE_SPEED = 10;
    }
    else if(UPGRADE_SPEED <= (-3.5))
    {
      UPGRADE_SPEED = -3.5;
    }


  }

  /**
   * Returns the current speed modifier that gets added to starting speed
   * for testing purposes only
   * @return a double value UPGRADE_SPEED
   */
  public double GetSpeedModifier()
  {
    return UPGRADE_SPEED;
  }

  /**
   * Returns the current height modifier that gets added to starting speed
   * for testing purposes only
   * @return a double value UPGRADE_HEIGHT
   */
  public double GetHeightModifier()
  {
    return UPGRADE_SPEED;
  }



  /**
   *Function takes a double generated from UpgradeHeight to modify
   * its own Speed
   * @param boost added to upgrade speed to modify a paddles speed
   * @see UpgradeHeight
   */
  public void ModifyHeight(double boost)
  {
    UPGRADE_HEIGHT = UPGRADE_HEIGHT + boost;

    if(UPGRADE_HEIGHT >= 140)
    {
      UPGRADE_HEIGHT = 140;
    }
    else if(UPGRADE_HEIGHT <= (-40))
    {
      UPGRADE_HEIGHT = -40;
    }
    super.setHeight(getHeight()+UPGRADE_HEIGHT);
  }

  /**
   * Used to move the paddle, Start Speed is Modified by
   * Upgrade speed
   */
  @Override
  public void move()
  {
    if (newmove == Direction.UP) {
      setY(getY() - (START_SPEED+UPGRADE_SPEED));
    } else if (newmove == Direction.Down) {
      setY(getY() + (START_SPEED+UPGRADE_SPEED)) ;
    }

    if (getY() < topBound) setY(topBound);
    double floor = bottomBound - getHeight();
    if (getY() > floor) setY(floor);

  }

  /**
   * Used to Stop Paddle
   */
  @Override
  public void stop() {
    newmove = Direction.STILL;
  }

  /**
   * Used to Set Paddle Direction to up
   */
  @Override
  public void moveUp() {
    newmove = Direction.UP;
  }

  /**
   *Used to Set Paddle Direction to down
   */
  @Override
  public void moveDown() {
    newmove = Direction.Down;
  }


}
