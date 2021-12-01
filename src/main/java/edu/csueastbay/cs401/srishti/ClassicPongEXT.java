package edu.csueastbay.cs401.srishti;


import edu.csueastbay.cs401.pong.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.io.File;

/**
 * This class extends GameEXT
 * @see edu.csueastbay.cs401.srishti.GameEXT
 * Changing Puck color and increasing the size of Puck
 *
 */
public class ClassicPongEXT extends GameEXT {

	private double fieldHeight;
	private double fieldWidth;

	public ClassicPongEXT(int victoryScore, double fieldWidth, double fieldHeight) {
		//super(victoryScore, fieldWidth, fieldHeight);
		// TODO Auto-generated constructor stub
		super(victoryScore);
		this.fieldWidth = fieldWidth;
		this.fieldHeight = fieldHeight;

		// Puck puck = new Puck(this.fieldWidth, this.fieldHeight);
		PuckEXT puck = new PuckEXT(this.fieldWidth, this.fieldHeight);
		puck.setID("Classic");
		addPuck(puck);

		Wall top = new Wall("Top Wall", 0, 0, this.fieldWidth, 10);
		top.setFill(Color.WHITE);
		addObject(top);

		Wall bottom = new Wall("Bottom Wall", 0, this.fieldHeight - 10, this.fieldWidth, 10);
		bottom.setFill(Color.WHITE);
		addObject(bottom);

		Goal left = new Goal("Player 1 Goal", this.fieldWidth - 10, 10, 10, this.fieldHeight - 20);
		left.setFill(Color.WHITE);
		addObject(left);

		Goal right = new Goal("Player 2 Goal", 0, 10, 10, this.fieldHeight - 20);
		right.setFill(Color.WHITE);
		addObject(right);

		Paddle playerOne = new Paddle("Player 1 Paddle", 30, (this.fieldHeight / 2) - 50, 10, 100, 10,
				this.fieldHeight - 10);
		playerOne.setFill(Color.WHITE);
		addPlayerPaddle(1, playerOne);

		Paddle playerTwo = new Paddle("Player 2 Paddle", this.fieldWidth - 40, (this.fieldHeight / 2) - 50, 10, 100, 10,
				this.fieldHeight - 10);
		playerTwo.setFill(Color.WHITE);
		addPlayerPaddle(2, playerTwo);

	}

	@Override
	public void collisionHandler(Puckable puck, Collision collision) {
//	        System.out.println(puck.getDirection());
		switch (collision.getType()) {
			case "Wall":
				puck.setDirection(0 - puck.getDirection());
				break;
			case "Goal":
				if (collision.getObjectID() == "Player 1 Goal") {
					addPointsToPlayer(1, 1);
					puck.reset();
				} else if (collision.getObjectID() == "Player 2 Goal") {
					addPointsToPlayer(2, 1);
					puck.reset();
				}
				break;
			case "Paddle":
				double puckCenter = ((Puck) puck).getCenterY();
				double angle;
				if (collision.getObjectID() == "Player 1 Paddle") {
					// Added Path for audio file
					String path = "C:\\Users\\14082\\IdeaProjects\\Pong\\src\\main\\resources\\edu\\csueastbay\\cs401\\srishti\\pongMusic\\hitAudio.mp3";
					// Instantiating Media class
					Media media = new Media(new File(path).toURI().toString());
					// Instantiating MediaPlayer class
					MediaPlayer mediaPlayer = new MediaPlayer(media);
					// by setting this property to true, the audio will be played
					mediaPlayer.setAutoPlay(false);
					mediaPlayer.setAutoPlay(true);
					System.out.println(" Player 1 goal ");
					angle = mapRange(collision.getTop(), collision.getBottom(), -45, 45, puckCenter);
				} else {
					String path = "C:\\Users\\14082\\IdeaProjects\\Pong\\src\\main\\resources\\edu\\csueastbay\\cs401\\srishti\\pongMusic\\hitAudio.mp3";
					// Instantiating Media class
					Media media = new Media(new File(path).toURI().toString());
					// Instantiating MediaPlayer class
					MediaPlayer mediaPlayer = new MediaPlayer(media);
					// by setting this property to true, the audio will be played
					mediaPlayer.setAutoPlay(false);
					mediaPlayer.setAutoPlay(true);
					System.out.println(" Player 2 goal ");
					angle = mapRange(collision.getTop(), collision.getBottom(), 225, 135, puckCenter);
				}
				puck.setDirection(angle);

		}
	}

	public static double mapRange(double a1, double a2, double b1, double b2, double s) {
		return b1 + ((s - a1) * (b2 - b1)) / (a2 - a1);
	}

}