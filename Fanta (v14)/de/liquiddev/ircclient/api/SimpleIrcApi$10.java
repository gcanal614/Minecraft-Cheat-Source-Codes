/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.api;

import de.liquiddev.ircclient.api.SimpleIrcApi;

class SimpleIrcApi$10 {
    int t;
    final /* synthetic */ SimpleIrcApi this$0;

    SimpleIrcApi$10(SimpleIrcApi simpleIrcApi) {
        this.this$0 = simpleIrcApi;
    }

    public String toString() {
        byte[] byArray = new byte[3];
        this.t = 483473602;
        byArray[0] = (byte)(this.t >>> 8);
        this.t = -577331636;
        byArray[1] = (byte)(this.t >>> 23);
        this.t = 135004107;
        byArray[2] = (byte)(this.t >>> 22);
        return new String(byArray);
    }
}

