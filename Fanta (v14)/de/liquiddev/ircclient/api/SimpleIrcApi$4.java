/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.api;

class SimpleIrcApi$4 {
    int t;

    SimpleIrcApi$4() {
    }

    public String toString() {
        byte[] byArray = new byte[3];
        this.t = 483473602;
        byArray[0] = (byte)(this.t >>> 8);
        this.t = 781622860;
        byArray[1] = (byte)(this.t >>> 23);
        this.t = 135004107;
        byArray[2] = (byte)(this.t >>> 22);
        return new String(byArray);
    }
}

