package edu.csueastbay.cs401.felixchoypong;
import javafx.scene.media.AudioClip;

/**
 * Sounds class, used to play sound effects
 */
public class Sounds {
    private AudioClip paddleOneSound;
    private AudioClip paddleTwoSound;
    private AudioClip goalSound;
    private AudioClip wallSound;
    private AudioClip CelebrationSound;
    private AudioClip powerUpSound;
    private AudioClip deniedSound;

    /**
     * Constructor for sounds class
     */
    public Sounds() {
        paddleOneSound = new AudioClip(this.getClass().getResource("Player1paddlesoundeffect.wav").toExternalForm());
        paddleTwoSound = new AudioClip(this.getClass().getResource("Player2paddlesoundeffect.wav").toExternalForm());
        goalSound = new AudioClip(this.getClass().getResource("Ponggoalsoundeffect.wav").toExternalForm());
        wallSound = new AudioClip(this.getClass().getResource("pongwallsoundeffect.wav").toExternalForm());
        CelebrationSound = new AudioClip(this.getClass().getResource("celebration.wav").toExternalForm());
        powerUpSound = new AudioClip(this.getClass().getResource("powerupsound.wav").toExternalForm());
        deniedSound = new AudioClip(this.getClass().getResource("deniedSound.wav").toExternalForm());

        paddleOneSound.setVolume(0.3);
        paddleOneSound.setCycleCount(1);

        paddleTwoSound.setVolume(0.3);
        paddleTwoSound.setCycleCount(1);

        wallSound.setVolume(0.3);
        wallSound.setCycleCount(1);

        goalSound.setVolume(0.1);
        goalSound.setCycleCount(1);

        CelebrationSound.setVolume(0.05);
        CelebrationSound.setCycleCount(1);

        powerUpSound.setVolume(0.2);
        powerUpSound.setCycleCount(1);

        deniedSound.setVolume(0.85);
        deniedSound.setCycleCount(1);
    }

    /**
     * Plays a sound when the puck collides with player one's paddle
     */
    public void playPaddleOneSound(){
        paddleOneSound.play();
    }

    /**
     * Plays a sound when the puck collides with player two's paddle
     */
    public void playPaddleTwoSound(){
        paddleTwoSound.play();
    }

    /**
     * Plays a sound when the puck hits a wall
     */
    public void playWallSound(){
        wallSound.play();
    }

    /**
     * Plays a sound when the puck hits a player's goal
     */
    public void playGoalSound(){
        goalSound.play();
    }

    /**
     * PLays a sound when a player wins
     */
    public void playCelebrationSound(){
        CelebrationSound.play();
    }

    /**
     * Plays a sound when the play successfully activates their power up using their power up key
     */
    public void playPowerUpSound(){
        powerUpSound.play();
    }

    /**
     * Plays a sound when a player tries to activate their power up but does not meet the requirements to do so
     */
    public void playDeniedSound(){
        deniedSound.play();
    }

    /**
     * Stops playing all sounds (except for the denied and power up sound)
     */
    public void stopPlayingSound(){
        paddleOneSound.stop();
        paddleTwoSound.stop();
        wallSound.stop();
        goalSound.stop();
        CelebrationSound.stop();
    }

    /**
     * Stops playing the denied sound
     */
    public void stopPlayingDeniedSound(){
        deniedSound.stop();

    }

    /**
     * Stops playing the power up sound
     */
    public void stopPlayingPowerUpSound(){
        powerUpSound.stop();
    }

}
