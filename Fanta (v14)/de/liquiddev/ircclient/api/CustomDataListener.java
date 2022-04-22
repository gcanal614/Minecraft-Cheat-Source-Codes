/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.api;

import de.liquiddev.ircclient.client.IrcPlayer;

public interface CustomDataListener {
    public void onCustomDataReceived(IrcPlayer var1, String var2, byte[] var3);
}

