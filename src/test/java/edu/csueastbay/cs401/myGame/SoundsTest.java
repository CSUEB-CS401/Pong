package edu.csueastbay.cs401.myGame;

import javafx.scene.media.AudioClip;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SoundsTest {

    private AudioClip newAudio;

    @Test
    void setUp(){
        newAudio = null;
        assertEquals(null, newAudio); //should be true.

        newAudio = new AudioClip("");
        assertEquals(null, newAudio); //should be false
    }

    @Test
    void playerOnePaddleSoundNotPlaying() {
        newAudio = new AudioClip("");
        assertEquals(false, newAudio.isPlaying()); //not playing, should be false.
    }

    @Test
    void playerOnePaddleSoundPlaying(){
        newAudio.play();
        assertEquals(true, newAudio.isPlaying()); //should be true
    }

    @Test
    void playerOneTestVolume(){
        newAudio.setVolume(0.3);
        assertEquals(newAudio.getVolume(), 0.3); //should be true
    }

    @Test
    void playPlayerTwoPaddleSound() {
    }

    @Test
    void playWallCollisionSound() {
    }

    @Test
    void playGoalSoundEffect() {
    }
}