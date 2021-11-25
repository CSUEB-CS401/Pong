package edu.csueastbay.cs401.felixchoypong;
import javafx.scene.media.AudioClip;

import java.io.File;

public class Sounds {
    private AudioClip paddleOneSound;
    private AudioClip paddleTwoSound;
    private AudioClip goalSound;
    private AudioClip wallSound;
    private AudioClip CelebrationSound;
    private AudioClip powerUpSound;
    private AudioClip deniedSound;


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

    public void playPaddleOneSound(){
        paddleOneSound.play();
    }

    public void playPaddleTwoSound(){
        paddleTwoSound.play();
    }

    public void playWallSound(){
        wallSound.play();
    }

    public void playGoalSound(){
        goalSound.play();
    }

    public void playCelebrationSound(){
        CelebrationSound.play();
    }

    public void playPowerUpSound(){
        powerUpSound.play();
    }

    public void playDeniedSound(){
        deniedSound.play();
    }

    public void stopPlayingSound(){
        paddleOneSound.stop();
        paddleTwoSound.stop();
        wallSound.stop();
        goalSound.stop();
        CelebrationSound.stop();
    }

    public void stopPlayingDeniedSound(){
        deniedSound.stop();

    }

    public void stopPlayingPowerUpSound(){
        powerUpSound.stop();
    }

}
