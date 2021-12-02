package edu.csueastbay.cs401.psander.engine.audio;

import edu.csueastbay.cs401.psander.PongWare;
import edu.csueastbay.cs401.psander.engine.math.Utility;
import edu.csueastbay.cs401.psander.engine.math.Vector2D;
import javafx.scene.media.AudioClip;

import java.util.Hashtable;
import java.util.Map;


/**
 * Audio manager for the engine that is
 * responsible for playing sounds.
 */
public class AudioManager {
    private static AudioManager _instance;

    private final Map<String, AudioClip> _soundEffects = new Hashtable<String, AudioClip>();

    private double _soundEffectVolume = 1.0;

    private AudioManager() {}

    private static AudioManager getInstance() {
        if (_instance == null)
            _instance = new AudioManager();

        return _instance;
    }

    /**
     * Reinitializes the AudioManager. Necessary
     * since it is implemented as a singleton.
     */
    public static void init() {
        getInstance()._soundEffects.clear();
    }

    /**
     * Registers a sound effect so that it can be played later.
     * @param name     The name or handle to associate with the effect.
     * @param location The location on disk the file is located.
     */
    public static void registerSoundEffect(String name, String location) {
        getInstance()._soundEffects.put(name, new AudioClip(location));
    }

    /**
     * Plays the sound effect by the specified name.
     * @param name The name of the sound effect to play.
     */
    public static void playSoundEffect(String name) {
        var instance = getInstance();
        if (!instance._soundEffects.containsKey(name)) return;

        instance._soundEffects.get(name).play(instance._soundEffectVolume);
    }

    /**
     * Plays the sound effect by the specified name and
     * at the specified position.
     * @param name     The name of the sound effect to play.
     * @param location The location to play the sound effect at.
     */
    public static void playSoundEffect(String name, Vector2D location) {
        var instance = getInstance();
        if (!instance._soundEffects.containsKey(name)) return;

        var balance = Utility.MapRange(location.X(), 0, PongWare.getFieldWidth(),
                -1, 1);

        instance._soundEffects.get(name).play(instance._soundEffectVolume, balance, 1.0, 0.0, 0);
    }
}
