package stellar.skid.events.events;

import net.minecraft.client.entity.EntityPlayerSP;
import stellar.skid.events.EventManager;


public class MotionUpdateEvent2 implements Event {
    private float yaw;
    private float pitch;
    private double posY;
    private double posZ;
    private double posX;
    private boolean onGround;
    private EventState state;
    private static boolean d;



    public MotionUpdateEvent2(EntityPlayerSP var1, EventState var2) {
        aea var4 = new aea(this.posX, this.posY, this.posZ, this.yaw, this.pitch, this.onGround, var2);
        j();
        this.posX = var1.posX;
        this.posY = var1.getEntityBoundingBox().minY;
        this.posZ = var1.posZ;
        this.yaw = var1.rotationYaw;
        this.pitch = var1.rotationPitch;
        this.onGround = var1.onGround;
        this.state = var4.getState();


    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public double getY() {
        return this.posY;
    }

    public double getZ() {
        return this.posZ;
    }

    public double getX() {
        return this.posX;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public EventState getState() {
        return this.state;
    }

    public void setYaw(float var1) {
        this.yaw = var1;
    }

    public void setPitch(float var1) {
        this.pitch = var1;
    }

    public void setY(double var1) {
        this.posY = var1;
    }

    public void setZ(double var1) {
        this.posZ = var1;
    }

    public void setX(double var1) {
        this.posX = var1;
    }

    public void setOnGround(boolean var1) {
        this.onGround = var1;
    }

    public void setState(EventState var1) {
        this.state = var1;
    }

    public static void b(boolean var0) {
        d = var0;
    }

    public static boolean j() {
        return d;
    }

    public static boolean c() {
        boolean var0 = j();
        return true;
    }

    static {
        if(!c()) {
            b(true);
        }

    }
}
