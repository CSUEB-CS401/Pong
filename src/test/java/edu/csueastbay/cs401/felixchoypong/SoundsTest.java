package edu.csueastbay.cs401.felixchoypong;

import javafx.scene.media.AudioClip;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test coverage for sounds class
 */
class SoundsTest {

    AudioClip newAudio;

    @Test
    void setUp(){
        newAudio = null;
        assertEquals(null, newAudio); //should be true.
        newAudio = new AudioClip(this.getClass().getResource("celebration.wav").toExternalForm());
    }

    @Test
    void playerOnePaddleSoundNotPlaying() {
        newAudio = new AudioClip(this.getClass().getResource("celebration.wav").toExternalForm());
        assertEquals(false, newAudio.isPlaying()); //not playing, should be false.
    }

    @Test
    void playerOnePaddleSoundTest(){
        newAudio = new AudioClip(this.getClass().getResource("Player1paddlesoundeffect.wav").toExternalForm());
        newAudio.play();
        assertEquals(true, newAudio.isPlaying()); //should be true
        newAudio.stop();
        assertEquals(false, newAudio.isPlaying()); //should be false
    }

    @Test
    void TestVolume(){
        newAudio = new AudioClip(this.getClass().getResource("Player1paddlesoundeffect.wav").toExternalForm());
        newAudio.setVolume(0.3);
        assertEquals(newAudio.getVolume(), 0.3); //should be true
    }

    @Test
    void playPlayerTwoPaddleSoundTest() {
        newAudio = new AudioClip(this.getClass().getResource("Player2paddlesoundeffect.wav").toExternalForm());
        newAudio.play();
        assertEquals(true, newAudio.isPlaying()); //should be true
        newAudio.stop();
        assertEquals(false, newAudio.isPlaying()); //should be false
    }

    @Test
    void playWallCollisionSoundTest() {
        newAudio = new AudioClip(this.getClass().getResource("pongwallsoundeffect.wav").toExternalForm());
        newAudio.play();
        assertEquals(true, newAudio.isPlaying()); //should be true
        newAudio.stop();
        assertEquals(false, newAudio.isPlaying()); //should be false
    }

    @Test
    void playGoalSoundEffectTest() {
        newAudio = new AudioClip(this.getClass().getResource("ponggoalsoundeffect.wav").toExternalForm());
        newAudio.play();
        assertEquals(true, newAudio.isPlaying()); //should be true
        newAudio.stop();
        assertEquals(false, newAudio.isPlaying()); //should be false
    }
}