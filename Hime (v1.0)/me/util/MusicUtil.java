package me.util;

import me.Hime;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class MusicUtil {



 public static synchronized void playSound(final String url) {
  new Thread(new Runnable() {
   // The wrapper thread is unnecessary, unless it blocks on the
   // Clip finishing; see comments.
   public void run() {
    try {
     Clip clip = AudioSystem.getClip();
     AudioInputStream inputStream = AudioSystem.getAudioInputStream(
     Hime.class.getResourceAsStream("/assets/minecraft/client/Songs/" + url));
     clip.open(inputStream);
     clip.start();
    } catch (Exception e) {
     System.err.println(e.getMessage());
    }
   }
  }).start();
 }

 }

 



