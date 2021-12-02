package edu.csueastbay.cs401.vnguyen;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Used play sound depending on the file path
 *
 * @see File
 * @see IOException
 * @see AudioInputStream
 * @see AudioSystem
 * @see Clip
 * @see LineUnavailableException
 * @see UnsupportedAudioFileException
 */

public class Sound {
    /**
     * This is the sound
     */


    Clip clip;
    AudioInputStream audioInputStream;

    /**
     * Constructor
     * @param filePath
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    // constructor to initialize streams and clip
    public Sound(String filePath)
            throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        // create AudioInputStream object
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

        // create clip reference
        clip = AudioSystem.getClip();

        // open audioInputStream to the clip
        clip.open(audioInputStream);
        clip.loop(0);

    }


}
