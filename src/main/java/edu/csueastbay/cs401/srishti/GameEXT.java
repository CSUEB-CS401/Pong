package edu.csueastbay.cs401.srishti;

import edu.csueastbay.cs401.pong.*;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

/**
 * This class changes the control and releases new control for the game
 * @see edu.csueastbay.cs401.pong.Game for older control
 */

/**
 * New control:
 * Player 1: X and Alt for moving the paddle
 * Player 2: Up and Down for moving the paddle

 */

public abstract class GameEXT extends Game {
	private int playerOneScore;
	private Paddle playOnePaddle;
	private int playerTwoScore;
	private Paddle playTwoPaddle;
	private int victoryScore;
	private ArrayList<Collidable> objects;
	private ArrayList<Puckable> pucks;

	public GameEXT(int victoryScore) {
		super(victoryScore);
		this.victoryScore = victoryScore;
		this.objects = new ArrayList<>();
		this.pucks = new ArrayList<>();
		this.playerOneScore = 0;
		this.playerTwoScore = 0;

	}

	public int getPlayerScore(int player) {
		if (player == 1)
			return playerOneScore;
		else if (player == 2)
			return playerTwoScore;
		return 0;
	}

	public void addPointsToPlayer(int player, int value) {
		if (player == 1)
			playerOneScore += value;
		else if (player == 2)
			playerTwoScore += value;
	}

	public void setVictoryScore(int score) {
		victoryScore = score;
	}

	public int getVictoryScore() {
		return victoryScore;
	}

	public int getVictor() {
		int victor = 0;
		if (playerOneScore >= victoryScore)
			victor = 1;
		return victor;
	}

	public void addObject(Collidable object) {
		objects.add(object);
	}

	public ArrayList<Collidable> getObjects() {
		// Shallow copy so the object collection can not be accessed but
		// still breaks encapsulation because the objects in the collection
		// are accessible.
		return (ArrayList<Collidable>) objects.clone();
	}

	public void addPuck(Puckable ball) {
		this.pucks.add(ball);
	}

	public ArrayList<Puckable> getPucks() {
		// Also shallow copy
		return (ArrayList<Puckable>) pucks.clone();
	}

	public void move() {

		playOnePaddle.move();
		playTwoPaddle.move();

		for (Puckable puck : pucks) {
			checkCollision(puck);
			puck.move();
		}
	}

	public void checkCollision(Puckable puck) {
		objects.forEach((object) -> {
			Collision collision = object.getCollision((Shape) puck);
			if (collision.isCollided()) {
				collisionHandler(puck, collision);
			}
		});
	}

	protected void addPlayerPaddle(int player, Paddle paddle) {
		if (player == 1) {
			playOnePaddle = paddle;
			addObject(paddle);
		} else if (player == 2) {
			playTwoPaddle = paddle;
			addObject(paddle);
		}
	}

	public abstract void collisionHandler(Puckable puck, Collision collision);
	
	@Override
	public void keyPressed(KeyCode code) {
		switch (code) {
	/* Key changed as X for Moving UP paddle */
		case X:
			playOnePaddle.moveUp();
			break;
	/* Key changed as ALT for Moving DOWN paddle */
		case ALT:
			playOnePaddle.moveDown();
			break;
	/* Key changed as UP for Moving Up paddle */
		case UP:
			playTwoPaddle.moveUp();
			break;
	/* Key changed as DOWN for Moving DOWN paddle */
		case DOWN:
			playTwoPaddle.moveDown();
			break;
		}
	}

	@Override
	public void keyReleased(KeyCode code) {
		switch (code) {
		case X, ALT:
			playOnePaddle.stop();
			break;
		case UP, DOWN:
			playTwoPaddle.stop();
			break;
		}
	}
}
