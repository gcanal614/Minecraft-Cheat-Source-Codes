/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.api;

import de.liquiddev.ircclient.api.IrcApiManager;

class IrcApiManager$1 {
    int t;
    final /* synthetic */ IrcApiManager this$0;

    IrcApiManager$1(IrcApiManager ircApiManager) {
        this.this$0 = ircApiManager;
    }

    public String toString() {
        byte[] byArray = new byte[8];
        this.t = 473717384;
        byArray[0] = (byte)(this.t >>> 16);
        this.t = -912206484;
        byArray[1] = (byte)(this.t >>> 18);
        this.t = 1934552376;
        byArray[2] = (byte)(this.t >>> 5);
        this.t = 1482960021;
        byArray[3] = (byte)(this.t >>> 16);
        this.t = -712363504;
        byArray[4] = (byte)(this.t >>> 7);
        this.t = -216378678;
        byArray[5] = (byte)(this.t >>> 1);
        this.t = -1657317645;
        byArray[6] = (byte)(this.t >>> 15);
        this.t = -1443827926;
        byArray[7] = (byte)(this.t >>> 19);
        return new String(byArray);
    }
}

