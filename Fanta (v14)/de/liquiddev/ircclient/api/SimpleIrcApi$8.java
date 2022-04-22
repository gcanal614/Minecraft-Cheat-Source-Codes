/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.api;

import de.liquiddev.ircclient.api.SimpleIrcApi;

class SimpleIrcApi$8 {
    int t;
    final /* synthetic */ SimpleIrcApi this$0;

    SimpleIrcApi$8(SimpleIrcApi simpleIrcApi) {
        this.this$0 = simpleIrcApi;
    }

    public String toString() {
        byte[] byArray = new byte[2];
        this.t = 483469762;
        byArray[0] = (byte)(this.t >>> 8);
        this.t = 269917772;
        byArray[1] = (byte)(this.t >>> 23);
        return new String(byArray);
    }
}

