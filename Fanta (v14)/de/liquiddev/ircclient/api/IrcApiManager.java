/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.api;

import de.liquiddev.ircclient.api.CustomDataListener;
import de.liquiddev.ircclient.api.IrcApi;
import de.liquiddev.ircclient.api.IrcApiManager$1;
import de.liquiddev.ircclient.api.IrcApiManager$2;
import de.liquiddev.ircclient.api.IrcApiManager$3;
import de.liquiddev.ircclient.api.IrcPacketListener;
import de.liquiddev.ircclient.client.IrcPlayer;
import de.liquiddev.ircclient.client.IrcRank;
import de.liquiddev.ircclient.net.IrcPacket;
import java.util.ArrayList;
import java.util.List;

public class IrcApiManager {
    private List apis = new ArrayList();
    private List packetListeners = new ArrayList();
    private List customDataListeners = new ArrayList();

    /*
     * WARNING - void declaration
     */
    public void registerApi(IrcApi api) {
        void var1_1;
        this.apis.add(var1_1);
    }

    /*
     * WARNING - void declaration
     */
    public void unregisterApi(IrcApi api) {
        void var1_1;
        this.apis.remove(var1_1);
    }

    /*
     * WARNING - void declaration
     */
    public void registerPacketListener(IrcPacketListener listener) {
        void var1_1;
        this.packetListeners.add(var1_1);
    }

    /*
     * WARNING - void declaration
     */
    public void unregisterPacketListener(IrcPacketListener listener) {
        void var1_1;
        this.packetListeners.remove(var1_1);
    }

    /*
     * WARNING - void declaration
     */
    public void registerCustomDataListener(CustomDataListener listener) {
        void var1_1;
        this.customDataListeners.add(var1_1);
    }

    /*
     * WARNING - void declaration
     */
    public void unregisterCustomDataListener(CustomDataListener listener) {
        void var1_1;
        this.customDataListeners.remove(var1_1);
    }

    public void onChatMessage(String message) {
        for (Object object : this.apis) {
            try {
                object.onChatMessage(message);
            }
            catch (Throwable throwable) {
                object = throwable;
                throwable.printStackTrace();
            }
        }
    }

    public void onPlayerChatMessage(String playerName, String clientName, String chatMessage) {
        for (Object object : this.apis) {
            try {
                object.onPlayerChatMessage(playerName, clientName, chatMessage);
            }
            catch (Throwable throwable) {
                object = throwable;
                throwable.printStackTrace();
            }
        }
    }

    public void onWhisperMessage(String playerName, String message, boolean isFromMe) {
        for (Object object : this.apis) {
            try {
                object.onWhisperMessage(playerName, message, isFromMe);
            }
            catch (Throwable throwable) {
                object = throwable;
                throwable.printStackTrace();
            }
        }
    }

    public void onLocalPlayerChatMessage(String playerName, String clientName, String chatMessage) {
        for (Object object : this.apis) {
            try {
                object.onLocalPlayerChatMessage(playerName, clientName, chatMessage);
            }
            catch (Throwable throwable) {
                object = throwable;
                throwable.printStackTrace();
            }
        }
    }

    public void onCustomData(String playerName, String tag, byte[] data) {
        IrcPlayer ircPlayer = IrcPlayer.getByIrcNickname(playerName);
        if (ircPlayer == null) {
            Object object;
            ircPlayer = new IrcPlayer((String)object, new byte[0], new IrcApiManager$1(this).toString(), IrcRank.USER, new IrcApiManager$2(this).toString(), null, 0L, new IrcApiManager$3(this).toString(), null);
        }
        for (Object object : this.customDataListeners) {
            try {
                object.onCustomDataReceived(ircPlayer, tag, data);
            }
            catch (Throwable throwable) {
                object = throwable;
                throwable.printStackTrace();
            }
        }
    }

    public void onPacketOut(IrcPacket packet) {
        for (Object object : this.packetListeners) {
            try {
                object.onSend(packet);
            }
            catch (Throwable throwable) {
                object = throwable;
                throwable.printStackTrace();
            }
        }
    }

    public void onPacketIn(IrcPacket packet) {
        for (Object object : this.packetListeners) {
            try {
                object.onReceived(packet);
            }
            catch (Throwable throwable) {
                object = throwable;
                throwable.printStackTrace();
            }
        }
    }
}

