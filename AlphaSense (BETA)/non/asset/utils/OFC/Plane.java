package non.asset.utils.OFC;

public class Plane {
    private final double x;
    private final double y;

    private final boolean visible;

    public Plane(double x, double y, boolean visible) {
        this.x = x;
        this.y = y;
        this.visible = visible;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }
}