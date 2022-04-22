/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.api;

import de.liquiddev.ircclient.api.SimpleIrcApi;

class SimpleIrcApi$13 {
    int t;
    final /* synthetic */ SimpleIrcApi this$0;

    SimpleIrcApi$13(SimpleIrcApi simpleIrcApi) {
        this.this$0 = simpleIrcApi;
    }

    public String toString() {
        byte[] byArray = new byte[2];
        this.t = 473489109;
        byArray[0] = (byte)(this.t >>> 16);
        this.t = 2025316673;
        byArray[1] = (byte)(this.t >>> 17);
        return new String(byArray);
    }
}

