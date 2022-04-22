/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.net;

import de.liquiddev.ircclient.api.IrcClient;
import de.liquiddev.ircclient.client.SkidIrc;
import de.liquiddev.ircclient.net.IrcPacket;
import de.liquiddev.ircclient.net._ircIIIlIIIIll;
import de.liquiddev.ircclient.net._ircIlIIIIlIlI;
import de.liquiddev.ircclient.net._ircIllIIlIlll;
import de.liquiddev.ircclient.net._ircIllIlIIIII;
import de.liquiddev.ircclient.net._ircIllIllIIlI;
import de.liquiddev.ircclient.net._irclIIIIlIIII;
import de.liquiddev.ircclient.net._irclIIIlIlllI;
import de.liquiddev.ircclient.net._irclIIlIIIIIl;
import de.liquiddev.ircclient.net._irclIIlIllIII;
import de.liquiddev.ircclient.net._irclllIllIIII;
import de.liquiddev.ircclient.net.packet.Irc00Login;
import de.liquiddev.ircclient.net.packet.Irc00Login$LoginState;
import java.io.IOException;
import javax.net.ssl.SSLSocket;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class _ircIIIIlIlIlI
extends _ircIllIllIIlI {
    private static int _ircIllIllIIlI = 10000;
    private String _ircIllIllIIlI;
    private boolean _ircIllIllIIlI;
    private Thread _ircIllIllIIlI;

    public _ircIIIIlIlIlI(SkidIrc skidIrc, SSLSocket sSLSocket, String string) {
        super(skidIrc, sSLSocket);
        this._ircIllIllIIlI = string;
        this._ircIllIllIIlI = false;
    }

    @Override
    public final void _ircIllIllIIlI() {
        this._ircIllIllIIlI().getApiManager().onChatMessage(new _ircIllIlIIIII(this).toString());
        IrcClient ircClient = this._ircIllIllIIlI();
        Irc00Login irc00Login = new Irc00Login();
        new Irc00Login().protocolVersion = ircClient.getProtocolVersion();
        irc00Login.state = Irc00Login$LoginState.LOGIN;
        irc00Login.message = ircClient.getUuid();
        irc00Login.clientType = ircClient.getType();
        irc00Login.token = this._ircIllIllIIlI;
        irc00Login.minecraftName = ircClient.getIngameName();
        irc00Login.clientVersion = ircClient.getClientVersion();
        irc00Login.ircVersion = ircClient.getIrcVersion();
        irc00Login.supportsExtensions = false;
        this._ircIllIllIIlI(irc00Login);
    }

    @Override
    public final void _ircIllIlIlIII(String string) {
        this._ircIllIllIIlI = false;
        if (this._ircIllIllIIlI().isForcedDisconnect()) {
            return;
        }
        if (string.equalsIgnoreCase(new _ircIlIIIIlIlI(this).toString())) {
            return;
        }
        this._ircIllIllIIlI().getApiManager().onChatMessage(String.valueOf(new _ircIllIIlIlll(this).toString()) + string + new _irclIIIlIlllI(this).toString());
        try {
            Thread.sleep(10000L);
        }
        catch (InterruptedException interruptedException) {}
        this._irclllllIIlII();
    }

    private void _irclllllIIlII() {
        while (!this._ircIllIllIIlI() && !this._ircIllIllIIlI().isForcedDisconnect()) {
            try {
                Object object = (SkidIrc)this._ircIllIllIIlI();
                object = (SSLSocket)((SkidIrc)object).getSSLContext().getSocketFactory().createSocket(((SkidIrc)object).getHost(), ((SkidIrc)object).getServerPort());
                this._ircIllIllIIlI((SSLSocket)object);
            }
            catch (IOException iOException) {
                this._ircIllIllIIlI().getApiManager().onChatMessage(String.valueOf(new _ircIIIlIIIIll(this).toString()) + 10 + new _irclIIlIllIII(this).toString());
                iOException.printStackTrace();
                try {
                    Thread.sleep(10000L);
                }
                catch (InterruptedException interruptedException) {}
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void _irclllllIIlII(IrcPacket ircPacket) {
        block6: {
            this._ircIllIllIIlI().getApiManager().onPacketIn(ircPacket);
            try {
                ircPacket.handle(this);
                if (!(ircPacket instanceof Irc00Login)) break block6;
                this._ircIllIllIIlI = true;
                _ircIIIIlIlIlI _ircIIIIlIlIlI2 = this;
                if (_ircIIIIlIlIlI2._ircIllIllIIlI != null) {
                    Thread thread = _ircIIIIlIlIlI2._ircIllIllIIlI;
                    synchronized (thread) {
                        _ircIIIIlIlIlI2._ircIllIllIIlI.notify();
                        _ircIIIIlIlIlI2._ircIllIllIIlI = null;
                        break block6;
                    }
                }
                return;
            }
            catch (Exception exception) {
                System.out.println(String.valueOf(new _irclllIllIIII(this).toString()) + ircPacket.getClass().getSimpleName() + new _irclIIIIlIIII(this).toString());
                exception.printStackTrace();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final boolean _ircIllIlIlIII() {
        if (this._ircIllIllIIlI != null) {
            throw new IllegalStateException(new _irclIIlIIIIIl(this).toString());
        }
        if (this._ircIllIllIIlI) {
            return false;
        }
        Thread thread = this._ircIllIllIIlI = Thread.currentThread();
        synchronized (thread) {
            try {
                this._ircIllIllIIlI.wait();
            }
            catch (InterruptedException interruptedException) {
                InterruptedException interruptedException2 = interruptedException;
                interruptedException.printStackTrace();
            }
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void _ircIllIlIlIII() {
        if (this._ircIllIllIIlI == null) {
            return;
        }
        Thread thread = this._ircIllIllIIlI;
        synchronized (thread) {
            this._ircIllIllIIlI.notify();
            this._ircIllIllIIlI = null;
            return;
        }
    }
}

