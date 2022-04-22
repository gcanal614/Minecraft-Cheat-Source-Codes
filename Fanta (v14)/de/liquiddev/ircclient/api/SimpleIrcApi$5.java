/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.api;

class SimpleIrcApi$5 {
    int t;

    SimpleIrcApi$5() {
    }

    public String toString() {
        byte[] byArray = new byte[1];
        this.t = 483473346;
        byArray[0] = (byte)(this.t >>> 8);
        return new String(byArray);
    }
}

