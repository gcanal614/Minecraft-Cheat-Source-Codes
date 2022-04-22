/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.api;

class SimpleIrcApi$2 {
    int t;

    SimpleIrcApi$2() {
    }

    public String toString() {
        byte[] byArray = new byte[2];
        this.t = 483473602;
        byArray[0] = (byte)(this.t >>> 8);
        this.t = 764845644;
        byArray[1] = (byte)(this.t >>> 23);
        return new String(byArray);
    }
}

