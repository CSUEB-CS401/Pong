package edu.csueastbay.cs401.ggamata2011;

import edu.csueastbay.cs401.pong.Paddle;

public class UpgradeablePaddle extends Paddle
{
  public final double START_SPEED = 5.0;
  public  double UPGRADE_SPEED = 0;
  public  double UPGRADE_HEIGHT = 0;

  private double topBound;
  private double bottomBound;

  enum Direction {UP, Down, STILL}
  private Direction newmove;

  public UpgradeablePaddle(String id, double x, double y, double width, double height, double topBound, double bottomBound)
  {
      super(id,x,y,width,height,topBound,bottomBound);
      this.topBound = topBound;
      this.bottomBound = bottomBound;
  }

  public void ModifySpeed(double boost)
  {
    UPGRADE_SPEED += boost;

    if(UPGRADE_SPEED > 12)
    {
      UPGRADE_SPEED = 12;
    }
    else if(UPGRADE_SPEED < (-5))
    {
      UPGRADE_SPEED = -5;
    }
  }

  public void ModifyHeight(double boost)
  {
    UPGRADE_HEIGHT += boost;

    if(UPGRADE_HEIGHT > 120)
    {
      UPGRADE_HEIGHT = 120;
    }
    else if(UPGRADE_HEIGHT < (-50))
    {
      UPGRADE_HEIGHT = -50;
    }
    super.setHeight(getHeight()+UPGRADE_HEIGHT);
  }

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

  @Override
  public void stop() {
    newmove = Direction.STILL;
  }

  @Override
  public void moveUp() {
    newmove = Direction.UP;
  }

  @Override
  public void moveDown() {
    newmove = Direction.Down;
  }


}
