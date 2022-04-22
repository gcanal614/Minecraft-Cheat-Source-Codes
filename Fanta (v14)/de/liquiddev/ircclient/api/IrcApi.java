/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.api;

public interface IrcApi {
    public void onChatMessage(String var1);

    public void onPlayerChatMessage(String var1, String var2, String var3);

    public void onLocalPlayerChatMessage(String var1, String var2, String var3);

    public void onWhisperMessage(String var1, String var2, boolean var3);
}

