package org.newdawn.slick.openal;

import ibxm.*;
import java.io.*;
import org.lwjgl.openal.*;
import org.lwjgl.*;
import java.nio.*;

public class MODSound extends AudioImpl
{
    private static OpenALMODPlayer player;
    private Module module;
    private SoundStore store;
    
    public MODSound(final SoundStore store, final InputStream in) throws IOException {
        this.store = store;
        this.index = 0;
        this.module = OpenALMODPlayer.loadModule(in);
    }
    
    @Override
    public void release() {
        this.cleanUpSource();
        super.release();
        this.index = 0;
    }
    
    @Override
    public int playAsMusic(final float pitch, final float gain, final boolean loop) {
        this.cleanUpSource();
        MODSound.player.play(this.module, this.store.getSource(0), loop, SoundStore.get().isMusicOn());
        MODSound.player.setup(pitch, 1.0f);
        this.store.setCurrentMusicVolume(gain);
        this.store.setMOD(this);
        return this.store.getSource(0);
    }
    
    private void cleanUpSource() {
        AL10.alSourceStop(this.store.getSource(0));
        final IntBuffer buffer = BufferUtils.createIntBuffer(1);
        for (int queued = AL10.alGetSourcei(this.store.getSource(0), 4117); queued > 0; --queued) {
            AL10.alSourceUnqueueBuffers(this.store.getSource(0), buffer);
        }
        AL10.alSourcei(this.store.getSource(0), 4105, 0);
    }
    
    public void poll() {
        MODSound.player.update();
    }
    
    @Override
    public int playAsSoundEffect(final float pitch, final float gain, final boolean loop) {
        return -1;
    }
    
    @Override
    public void stop() {
        this.store.setMOD(null);
    }
    
    @Override
    public float getPosition() {
        throw new RuntimeException("Positioning on modules is not currently supported");
    }
    
    @Override
    public boolean setPosition(final float position) {
        throw new RuntimeException("Positioning on modules is not currently supported");
    }
    
    static {
        MODSound.player = new OpenALMODPlayer();
    }
}
