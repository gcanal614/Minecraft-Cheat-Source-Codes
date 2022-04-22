/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.client;

import de.liquiddev.ircclient._ircIllIllIIlI;
import de.liquiddev.ircclient.api.IrcApiManager;
import de.liquiddev.ircclient.api.IrcClient;
import de.liquiddev.ircclient.client.ClientType;
import de.liquiddev.ircclient.client.IrcPlayer;
import de.liquiddev.ircclient.client.IrcRank;
import de.liquiddev.ircclient.client._ircIIIIIlIlII;
import de.liquiddev.ircclient.client._ircIIIlllllll;
import de.liquiddev.ircclient.client._ircIIlllIIIlI;
import de.liquiddev.ircclient.client._ircIllIlIIllI;
import de.liquiddev.ircclient.client._ircIllIlllIlI;
import de.liquiddev.ircclient.client._ircIlllIIlIIl;
import de.liquiddev.ircclient.client._ircllIlIIlIIl;
import de.liquiddev.ircclient.client._irclllIlllllI;
import de.liquiddev.ircclient.client._ircllllllIIIl;
import de.liquiddev.ircclient.net.IrcPacket;
import de.liquiddev.ircclient.net._ircIIIIlIlIlI;
import de.liquiddev.ircclient.net._ircIllIIIlIlI;
import de.liquiddev.ircclient.net.packet.Irc03Chat;
import de.liquiddev.ircclient.net.packet.Irc06ChangeIngameName;
import de.liquiddev.ircclient.net.packet.Irc07ClientCommand;
import de.liquiddev.ircclient.net.packet.Irc08Whisper;
import de.liquiddev.ircclient.net.packet.Irc09JoinServer;
import de.liquiddev.ircclient.net.packet.Irc10LeaveServer;
import de.liquiddev.ircclient.net.packet.Irc12LocalChat;
import de.liquiddev.ircclient.net.packet.Irc16SetExtra;
import de.liquiddev.ircclient.net.packet.Irc17CustomData;
import de.liquiddev.ircclient.util.IrcServers;
import java.io.IOException;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SkidIrc
implements IrcClient {
    private boolean _ircIllIllIIlI;
    private _ircIllIIIlIlI _ircIllIllIIlI;
    private IrcApiManager _ircIllIllIIlI;
    private int _ircIllIllIIlI;
    private String _ircIllIllIIlI;
    private _ircIIIIlIlIlI _ircIllIllIIlI;
    private String _irclllllIIlII;
    private Thread _ircIllIllIIlI;
    private ClientType _ircIllIllIIlI;
    private SSLContext _ircIllIllIIlI;
    private String _ircIllIlIlIII;
    private String _irclIIlIlllll;
    private String _irclllIIlIlIl;
    private IrcRank _ircIllIllIIlI;
    private boolean _irclllllIIlII = false;
    private String _ircIIlIIIlIlI;
    private String _ircIIIlIlllII;

    /*
     * WARNING - void declaration
     */
    public SkidIrc(SSLContext sslContext, List serversIps, int port, String uuid, ClientType clientType, String loginToken, String ingameName, String clientVersion) {
        void var1_1;
        void var3_3;
        void var2_2;
        this._ircIllIllIIlI = IrcServers.getOnlineServer((List)var2_2, port);
        this._ircIllIllIIlI = var3_3;
        this._irclllllIIlII = uuid;
        this._ircIllIllIIlI = clientType;
        this._ircIllIllIIlI = var1_1;
        this._ircIllIlIlIII = ingameName;
        this._ircIIIlIlllII = clientVersion;
        this._ircIllIllIIlI = false;
        this._ircIllIllIIlI = new _ircIllIIIlIlI();
        this._ircIllIllIIlI = new IrcApiManager();
        this.connect(loginToken);
    }

    /*
     * WARNING - void declaration
     */
    public SkidIrc(SSLContext sslContext, String serversIp, int port, String uuid, ClientType clientType, String loginToken, String ingameName, String clientVersion) {
        void var1_1;
        void var3_3;
        void var2_2;
        this._ircIllIllIIlI = var2_2;
        this._ircIllIllIIlI = var3_3;
        this._irclllllIIlII = uuid;
        this._ircIllIllIIlI = clientType;
        this._ircIllIllIIlI = var1_1;
        this._ircIllIlIlIII = ingameName;
        this._ircIIIlIlllII = clientVersion;
        this._ircIllIllIIlI = false;
        this._ircIllIllIIlI = new _ircIllIIIlIlI();
        this._ircIllIllIIlI = new IrcApiManager();
        this.connect(loginToken);
    }

    public _ircIllIIIlIlI getPacketManager() {
        return this._ircIllIllIIlI;
    }

    @Override
    public IrcApiManager getApiManager() {
        return this._ircIllIllIIlI;
    }

    public Thread getNetworkThread() {
        return this._ircIllIllIIlI;
    }

    @Override
    public void connect(String loginToken) {
        while (!this._ircIllIllIIlI) {
            try {
                SSLSocket sSLSocket = (SSLSocket)this._ircIllIllIIlI.getSocketFactory().createSocket(this._ircIllIllIIlI, this._ircIllIllIIlI);
                this._ircIllIllIIlI = new _ircIIIIlIlIlI(this, sSLSocket, (String)loginToken);
                this._ircIllIllIIlI = true;
                this._ircIllIllIIlI = new Thread(this._ircIllIllIIlI);
                this._ircIllIllIIlI.setName(new _ircIllIlllIlI(this).toString());
                this._ircIllIllIIlI.start();
                this._ircIllIllIIlI._ircIllIllIIlI();
                this._ircIllIllIIlI._ircIllIlIlIII();
            }
            catch (IOException iOException) {
                this._ircIllIllIIlI.onChatMessage(String.valueOf(new _irclllIlllllI(this).toString()) + 10 + new _ircIIIlllllll(this).toString());
                iOException.printStackTrace();
                try {
                    Thread.sleep(10000L);
                }
                catch (InterruptedException interruptedException) {
                    loginToken = interruptedException;
                    interruptedException.printStackTrace();
                    return;
                }
            }
        }
    }

    @Override
    public void disconnect() {
        this._irclllllIIlII = true;
        this._ircIllIllIIlI._ircIllIllIIlI(new _ircIIIIIlIlII(this).toString());
    }

    /*
     * WARNING - void declaration
     */
    public void sendPacket(IrcPacket packet) {
        if (this._ircIllIllIIlI) {
            void var1_1;
            this._ircIllIllIIlI._ircIllIllIIlI((IrcPacket)var1_1);
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void sendCustomData(String tag, byte[] data) {
        void var2_2;
        void var1_1;
        Irc17CustomData irc17CustomData = new Irc17CustomData();
        new Irc17CustomData().target = new _ircllllllIIIl(this).toString();
        irc17CustomData.tag = var1_1;
        irc17CustomData.data = var2_2;
        this.sendPacket(irc17CustomData);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void sendCustomData(String tag, byte[] data, IrcPlayer target) {
        void var2_2;
        void var1_1;
        void var3_3;
        Irc17CustomData irc17CustomData = new Irc17CustomData();
        new Irc17CustomData().target = var3_3.getIrcNick();
        irc17CustomData.tag = var1_1;
        irc17CustomData.data = var2_2;
        this.sendPacket(irc17CustomData);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setIngameName(String name) {
        void var1_1;
        if (name.equals(this._ircIllIlIlIII)) {
            return;
        }
        this._ircIllIlIlIII = name;
        Irc06ChangeIngameName irc06ChangeIngameName = new Irc06ChangeIngameName();
        new Irc06ChangeIngameName().name = var1_1;
        this.sendPacket(irc06ChangeIngameName);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void sendChatMessage(String message) {
        void var1_1;
        Irc03Chat irc03Chat = new Irc03Chat();
        new Irc03Chat().message = var1_1;
        this.sendPacket(irc03Chat);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void sendLocalChatMessage(String message) {
        void var1_1;
        Irc12LocalChat irc12LocalChat = new Irc12LocalChat();
        new Irc12LocalChat().message = var1_1;
        this.sendPacket(irc12LocalChat);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void sendWhisperMessage(String targetPlayer, String message) {
        void var2_2;
        void var1_1;
        Irc08Whisper irc08Whisper = new Irc08Whisper();
        new Irc08Whisper().player = var1_1;
        irc08Whisper.message = var2_2;
        this.sendPacket(irc08Whisper);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void executeCommand(String command) {
        void var1_1;
        if (command.toLowerCase().startsWith(new _ircllIlIIlIIl(this).toString())) {
            command = command.substring(5);
        }
        Irc07ClientCommand irc07ClientCommand = new Irc07ClientCommand();
        new Irc07ClientCommand().command = var1_1;
        this.sendPacket(irc07ClientCommand);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setMcServerIp(String ip) {
        void var1_1;
        if (ip == null || ip.length() == 0) {
            this.leaveMcServer(new _ircIIlllIIIlI(this).toString());
            return;
        }
        if (ip.equals(this._irclllIIlIlIl)) {
            return;
        }
        this._irclllIIlIlIl = ip;
        Irc09JoinServer irc09JoinServer = new Irc09JoinServer();
        new Irc09JoinServer().server = var1_1;
        this.sendPacket(irc09JoinServer);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void leaveMcServer(String reason) {
        void var1_1;
        if (this._irclllIIlIlIl == null) {
            return;
        }
        this._irclllIIlIlIl = null;
        Irc10LeaveServer irc10LeaveServer = new Irc10LeaveServer();
        new Irc10LeaveServer().reason = var1_1;
        this.sendPacket(irc10LeaveServer);
    }

    /*
     * WARNING - void declaration
     */
    public void updateRank(IrcRank rank) {
        void var1_1;
        this._ircIllIllIIlI = var1_1;
    }

    /*
     * WARNING - void declaration
     */
    public void updateNickname(String nick) {
        void var1_1;
        this._irclIIlIlllll = var1_1;
    }

    public String getHost() {
        return this._ircIllIllIIlI;
    }

    public int getServerPort() {
        return this._ircIllIllIIlI;
    }

    @Override
    public String getUuid() {
        return this._irclllllIIlII;
    }

    @Override
    public ClientType getType() {
        return this._ircIllIllIIlI;
    }

    public SSLContext getSSLContext() {
        return this._ircIllIllIIlI;
    }

    @Override
    public String getIngameName() {
        return this._ircIllIlIlIII;
    }

    @Override
    public String getMcServerIp() {
        return this._irclllIIlIlIl;
    }

    @Override
    public IrcRank getRank() {
        return this._ircIllIllIIlI;
    }

    @Override
    public boolean isForcedDisconnect() {
        return this._irclllllIIlII;
    }

    @Override
    public void setExtra(String extra) {
        Irc16SetExtra irc16SetExtra;
        if (extra.equals(this._ircIIlIIIlIlI)) {
            return;
        }
        this._ircIIlIIIlIlI = irc16SetExtra;
        irc16SetExtra = new Irc16SetExtra();
        new Irc16SetExtra().extra = this._ircIIlIIIlIlI;
        this.sendPacket(irc16SetExtra);
    }

    @Override
    public String getExtra() {
        return this._ircIIlIIIlIlI;
    }

    @Override
    public String getIrcVersion() {
        return de.liquiddev.ircclient._ircIllIllIIlI._ircIllIllIIlI;
    }

    @Override
    public String getClientVersion() {
        return this._ircIIIlIlllII;
    }

    @Override
    public int getProtocolVersion() {
        return 11;
    }

    @Override
    public String getNickname() {
        if (this._irclIIlIlllll == null) {
            throw new IllegalStateException(new _ircIllIlIIllI(this).toString());
        }
        return this._irclIIlIlllll;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void setCape(String name) {
        void var1_1;
        this.sendCustomData(new _ircIlllIIlIIl(this).toString(), var1_1.getBytes());
    }
}

