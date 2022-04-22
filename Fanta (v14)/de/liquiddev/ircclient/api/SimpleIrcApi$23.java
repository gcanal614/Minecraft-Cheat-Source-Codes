/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.api;

import de.liquiddev.ircclient.api.SimpleIrcApi;

class SimpleIrcApi$23 {
    int t;
    final /* synthetic */ SimpleIrcApi this$0;

    SimpleIrcApi$23(SimpleIrcApi simpleIrcApi) {
        this.this$0 = simpleIrcApi;
    }

    public String toString() {
        byte[] byArray = new byte[5];
        this.t = 476176085;
        byArray[0] = (byte)(this.t >>> 16);
        this.t = 2029248833;
        byArray[1] = (byte)(this.t >>> 17);
        this.t = -1152794927;
        byArray[2] = (byte)(this.t >>> 10);
        this.t = 2036477612;
        byArray[3] = (byte)(this.t >>> 5);
        this.t = 1359144302;
        byArray[4] = (byte)(this.t >>> 19);
        return new String(byArray);
    }
}

