/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.api;

import de.liquiddev.ircclient.api.SimpleIrcApi;

class SimpleIrcApi$15 {
    int t;
    final /* synthetic */ SimpleIrcApi this$0;

    SimpleIrcApi$15(SimpleIrcApi simpleIrcApi) {
        this.this$0 = simpleIrcApi;
    }

    public String toString() {
        byte[] byArray = new byte[3];
        this.t = 473489109;
        byArray[0] = (byte)(this.t >>> 16);
        this.t = 2025578817;
        byArray[1] = (byte)(this.t >>> 17);
        this.t = -1152875823;
        byArray[2] = (byte)(this.t >>> 10);
        return new String(byArray);
    }
}

