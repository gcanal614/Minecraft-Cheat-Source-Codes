/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.net.packet;

import de.liquiddev.ircclient.client.ClientType;
import de.liquiddev.ircclient.client.SkidIrc;
import de.liquiddev.ircclient.net.IrcPacket;
import de.liquiddev.ircclient.net.packet.Irc00Login$LoginState;
import de.liquiddev.ircclient.net.packet.Irc09JoinServer;
import de.liquiddev.ircclient.net.packet.Irc16SetExtra;
import de.liquiddev.ircclient.net.packet._ircIllIlIlIII;
import de.liquiddev.ircclient.net.packet._ircIllIllIIlI;
import de.liquiddev.ircclient.net.packet._irclllllIIlII;

public class Irc00Login
implements IrcPacket {
    public int protocolVersion;
    public Irc00Login$LoginState state;
    public String message;
    public ClientType clientType;
    public String token;
    public String minecraftName;
    public String clientVersion;
    public String ircVersion;
    public boolean supportsExtensions;

    /*
     * WARNING - void declaration
     */
    @Override
    public void read(de.liquiddev.ircclient.net._ircIllIllIIlI connection) {
        this.protocolVersion = connection._ircIllIllIIlI();
        this.state = Irc00Login$LoginState._ircIllIllIIlI(connection._ircIllIllIIlI());
        int n = 60;
        Object object = connection;
        if (((String)(object = ((de.liquiddev.ircclient.net._ircIllIllIIlI)object)._ircIllIllIIlI())).length() > 60) {
            object = ((String)object).substring(0, 60);
        }
        this.message = object;
        if (this.state == Irc00Login$LoginState.LOGIN) {
            void var1_1;
            this.clientType = ClientType.getById(connection._ircIllIllIIlI());
            this.token = connection._ircIllIllIIlI();
            this.minecraftName = connection._ircIllIllIIlI();
            this.clientVersion = connection._ircIllIllIIlI();
            this.ircVersion = connection._ircIllIllIIlI();
            this.supportsExtensions = var1_1._irclllllIIlII();
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void write(de.liquiddev.ircclient.net._ircIllIllIIlI connection) {
        connection._ircIllIllIIlI(this.protocolVersion);
        connection._ircIllIllIIlI(this.state.getId());
        connection._irclllllIIlII(this.message);
        if (this.state == Irc00Login$LoginState.LOGIN) {
            void var1_1;
            connection._ircIllIllIIlI(this.clientType.getId());
            connection._irclllllIIlII(this.token);
            connection._irclllllIIlII(this.minecraftName);
            connection._irclllllIIlII(this.clientVersion);
            connection._irclllllIIlII(this.ircVersion);
            var1_1._ircIllIllIIlI(this.supportsExtensions);
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void handle(de.liquiddev.ircclient.net._ircIllIllIIlI connection) {
        if (this.state == Irc00Login$LoginState.SUCCESS) {
            IrcPacket ircPacket;
            SkidIrc skidIrc = (SkidIrc)connection._ircIllIllIIlI();
            skidIrc.updateNickname(this.message);
            if (connection._ircIllIllIIlI().getMcServerIp() != null && connection._ircIllIllIIlI().getMcServerIp().length() > 0) {
                ircPacket = new Irc09JoinServer();
                new Irc09JoinServer().server = connection._ircIllIllIIlI().getMcServerIp();
                skidIrc.sendPacket(ircPacket);
            }
            if (connection._ircIllIllIIlI().getExtra() != null) {
                ircPacket = new Irc16SetExtra();
                new Irc16SetExtra().extra = connection._ircIllIllIIlI().getExtra();
                skidIrc.sendPacket(ircPacket);
                return;
            }
        } else {
            if (this.state == Irc00Login$LoginState.FAIL) {
                void var1_1;
                connection._ircIllIllIIlI().getApiManager().onChatMessage(String.valueOf(new _ircIllIllIIlI(this).toString()) + "\u00a7" + new _irclllllIIlII(this).toString() + this.message);
                var1_1._ircIllIllIIlI(this.message);
                return;
            }
            throw new IllegalStateException(String.valueOf(new _ircIllIlIlIII(this).toString()) + (Object)((Object)this.state));
        }
    }
}

