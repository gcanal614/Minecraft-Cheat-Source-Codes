package me.injusttice.neutron.utils.player;

public class Rotation {

    float yaw;
    float pitch;
    
    public Rotation(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
}
