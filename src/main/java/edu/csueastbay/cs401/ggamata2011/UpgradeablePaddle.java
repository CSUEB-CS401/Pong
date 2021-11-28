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

  public void SpeedBoost()
  {
    UPGRADE_SPEED += 2;

    if(UPGRADE_SPEED > 14)
    {
      UPGRADE_SPEED = 14;
    }
  }

  public void HeightBoost()
  {
    super.setHeight(12);
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
