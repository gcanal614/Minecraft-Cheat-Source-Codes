/*
 * Decompiled with CFR 0.152.
 */
package de.fanta.utils;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundUtil {
    private static Clip clip;
    public static final URL toggleOnSound;
    public static final URL toggleOffSound;

    static {
        toggleOnSound = SoundUtil.class.getClassLoader().getResource("assets/minecraft/Fanta/sounds/toggleSound.wav");
        toggleOffSound = SoundUtil.class.getClassLoader().getResource("assets/minecraft/Fanta/sounds/toggleSound2.wav");
    }

    public static void play(URL filePath) {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(filePath));
            FloatControl floatControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            floatControl.setValue(6.0206f);
            clip.start();
        }
        catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
}

