/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.api;

import de.liquiddev.ircclient.net.IrcPacket;

public interface IrcPacketListener {
    public void onReceived(IrcPacket var1);

    public void onSend(IrcPacket var1);
}

