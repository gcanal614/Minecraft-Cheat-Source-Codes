package org.newdawn.slick.openal;

public interface Audio
{
    void stop();
    
    int getBufferID();
    
    boolean isPlaying();
    
    boolean isPaused();
    
    int playAsSoundEffect(final float p0, final float p1, final boolean p2);
    
    int playAsSoundEffect(final float p0, final float p1, final boolean p2, final float p3, final float p4, final float p5);
    
    int playAsMusic(final float p0, final float p1, final boolean p2);
    
    boolean setPosition(final float p0);
    
    float getPosition();
    
    void release();
}
