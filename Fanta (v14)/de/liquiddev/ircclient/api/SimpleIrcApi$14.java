/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.api;

import de.liquiddev.ircclient.api.SimpleIrcApi;

class SimpleIrcApi$14 {
    int t;
    final /* synthetic */ SimpleIrcApi this$0;

    SimpleIrcApi$14(SimpleIrcApi simpleIrcApi) {
        this.this$0 = simpleIrcApi;
    }

    public String toString() {
        byte[] byArray = new byte[6];
        this.t = 473161429;
        byArray[0] = (byte)(this.t >>> 16);
        this.t = 2023350593;
        byArray[1] = (byte)(this.t >>> 17);
        this.t = -1152794927;
        byArray[2] = (byte)(this.t >>> 10);
        this.t = 2036477036;
        byArray[3] = (byte)(this.t >>> 5);
        this.t = 1393223022;
        byArray[4] = (byte)(this.t >>> 19);
        this.t = -1540985607;
        byArray[5] = (byte)(this.t >>> 8);
        return new String(byArray);
    }
}

