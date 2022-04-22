/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.api;

import de.liquiddev.ircclient.api.IrcApiManager;
import de.liquiddev.ircclient.client.ClientType;
import de.liquiddev.ircclient.client.IrcPlayer;
import de.liquiddev.ircclient.client.IrcRank;

public interface IrcClient {
    public IrcApiManager getApiManager();

    public void connect(String var1);

    public void disconnect();

    public void sendCustomData(String var1, byte[] var2);

    public void sendCustomData(String var1, byte[] var2, IrcPlayer var3);

    public void setIngameName(String var1);

    public void sendChatMessage(String var1);

    public void sendLocalChatMessage(String var1);

    public void sendWhisperMessage(String var1, String var2);

    public void executeCommand(String var1);

    public void setMcServerIp(String var1);

    public void leaveMcServer(String var1);

    public String getUuid();

    public ClientType getType();

    public String getIngameName();

    public String getMcServerIp();

    public IrcRank getRank();

    public boolean isForcedDisconnect();

    public void setExtra(String var1);

    public String getExtra();

    public String getIrcVersion();

    public String getClientVersion();

    public int getProtocolVersion();

    public String getNickname();

    public void setCape(String var1);
}

