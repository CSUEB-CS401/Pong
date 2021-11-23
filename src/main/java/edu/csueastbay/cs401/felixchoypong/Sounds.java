package edu.csueastbay.cs401.felixchoypong;
import javafx.scene.media.AudioClip;

import java.io.File;

public class Sounds {
    private AudioClip paddleOneSound;
    private AudioClip paddleTwoSound;
    private AudioClip goalSound;
    private AudioClip wallSound;


    public Sounds() {
        paddleOneSound = new AudioClip(this.getClass().getResource("Player1paddlesoundeffect.wav").toExternalForm());
        paddleTwoSound = new AudioClip(this.getClass().getResource("Player2paddlesoundeffect.wav").toExternalForm());
        goalSound = new AudioClip(this.getClass().getResource("Ponggoalsoundeffect.wav").toExternalForm());
        wallSound = new AudioClip(this.getClass().getResource("pongwallsoundeffect.wav").toExternalForm());

        paddleOneSound.setVolume(0.3);
        paddleOneSound.setCycleCount(1);

        paddleTwoSound.setVolume(0.3);
        paddleTwoSound.setCycleCount(1);

        wallSound.setVolume(0.3);
        wallSound.setCycleCount(1);

        goalSound.setVolume(0.1);
        goalSound.setCycleCount(1);
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

    public void stopPlayingSound(){
        paddleOneSound.stop();
        paddleTwoSound.stop();
        wallSound.stop();
        goalSound.stop();
    }

}
