package edu.csueastbay.cs401.srishti;

import edu.csueastbay.cs401.pong.Puck;
import javafx.scene.paint.Color;

import java.util.Random;


/**
*This class extends Puck class
*
*/

public class PuckEXT extends Puck {

	public static final double STARTING_SPEED = 5.0;
	/* Increased the Radius size to 15 */
	public static final int STARTING_RADIOUS = 15;
	private final double fieldWidth;
	private final double fieldHeight;
	private String id;
	private Double speed;
	private Double direction;

	public PuckEXT(double fieldWidth, double fieldHeight) {
		super(fieldHeight, fieldHeight);
		this.fieldWidth = fieldWidth;
		this.fieldHeight = fieldHeight;
		reset();

	}

	@Override
	public void reset() {
		Random random = new Random();
		setCenterX(fieldWidth / 2);
		setCenterY(fieldHeight / 2);
		setRadius(STARTING_RADIOUS);
		/* Changing the color from white to DEEPSKYBLUE */
		setFill(Color.DEEPSKYBLUE);
		speed = STARTING_SPEED;
		if (random.nextInt(2) == 0) {
			direction = (random.nextDouble() * 90) - 45;
		} else {
			direction = (random.nextDouble() * 90) + 115;
		}

	}

	@Override
	public String getID() {
		return id;
	}

	@Override
	public void setID(String id) {
		this.id = id;
	}

	@Override
	public double getSpeed() {
		return speed;
	}

	@Override
	public double getDirection() {
		return direction;
	}

	@Override
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	@Override
	public void setDirection(double angle) {
		this.direction = angle;
	}

	@Override
	public void move() {
		double deltaX = speed * Math.cos(Math.toRadians(direction));
		double deltaY = speed * Math.sin(Math.toRadians(direction));
		setCenterX(getCenterX() + deltaX);
		setCenterY(getCenterY() + deltaY);
	}
	


}
