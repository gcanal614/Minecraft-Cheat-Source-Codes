package stellar.skid.events.events;

public class aea implements Event {
    private float c;
    private float b;
    private double e;
    private double d;
    private double g;
    private boolean a;
    private EventState f;

    public aea(double var1, double var3, double var5, float var7, float var8, boolean var9, EventState var10) {
        //int var10000 = apZ.c();
        //int var11 = var10000;
        this.g = var1;
        this.e = var3;
        this.d = var5;
        this.c = var7;
        this.b = var8;
        this.a = var9;
        this.f = var10;


    }

    public aea(EventState var1) {
        this.f = var1;
    }


    public float getYaw() {
        return this.c;
    }


    public float getPitch() {
        return this.b;
    }


    public double getY() {
        return this.e;
    }


    public double getZ() {
        return this.d;
    }


    public double getX() {
        return this.g;
    }


    public boolean isOnGround() {
        return this.a;
    }

    public EventState getState() {
        return this.f;
    }


    public void setYaw(float var1) {
        this.c = var1;
    }


    public void setPitch(float var1) {
        this.b = var1;
    }


    public void setY(double var1) {
        this.e = var1;
    }


    public void setZ(double var1) {
        this.d = var1;
    }

    public void setX(double var1) {
        this.g = var1;
    }


    public void setOnGround(boolean var1) {
        this.a = var1;
    }

    public void setState(EventState var1) {
        this.f = var1;
    }
}
