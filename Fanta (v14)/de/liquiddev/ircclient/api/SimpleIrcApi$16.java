/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.api;

import de.liquiddev.ircclient.api.SimpleIrcApi;

class SimpleIrcApi$16 {
    int t;
    final /* synthetic */ SimpleIrcApi this$0;

    SimpleIrcApi$16(SimpleIrcApi simpleIrcApi) {
        this.this$0 = simpleIrcApi;
    }

    public String toString() {
        byte[] byArray = new byte[2];
        this.t = 473423573;
        byArray[0] = (byte)(this.t >>> 16);
        this.t = 2018632001;
        byArray[1] = (byte)(this.t >>> 17);
        return new String(byArray);
    }
}

