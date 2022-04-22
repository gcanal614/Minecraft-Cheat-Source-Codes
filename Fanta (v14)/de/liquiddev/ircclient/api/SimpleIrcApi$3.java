/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.api;

class SimpleIrcApi$3 {
    int t;

    SimpleIrcApi$3() {
    }

    public String toString() {
        byte[] byArray = new byte[4];
        this.t = 483484098;
        byArray[0] = (byte)(this.t >>> 8);
        this.t = 613850700;
        byArray[1] = (byte)(this.t >>> 23);
        this.t = 344719307;
        byArray[2] = (byte)(this.t >>> 22);
        this.t = -1320964066;
        byArray[3] = (byte)(this.t >>> 16);
        return new String(byArray);
    }
}

