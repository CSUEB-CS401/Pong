package edu.csueastbay.cs401.vnguyen;

import org.junit.jupiter.api.Test;
import edu.csueastbay.cs401.vnguyen.MyPaddle;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
class SoundTest {
    @Test
    void getInputStreamTest() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        String filePath = "src/main/java/edu/csueastbay/cs401/vnguyen/bounce.wav";

        try {
            Sound audioPlayer = new Sound(filePath);

        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();

        }



    }
}
