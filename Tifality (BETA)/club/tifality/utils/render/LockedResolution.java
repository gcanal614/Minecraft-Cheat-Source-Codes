/*
 * Decompiled with CFR 0.152.
 */
package club.tifality.utils.render;

public final class LockedResolution {
    private final int width;
    private final int height;

    public LockedResolution(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}

