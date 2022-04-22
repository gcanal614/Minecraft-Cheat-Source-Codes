/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.net;

import de.liquiddev.ircclient.api.IrcClient;
import de.liquiddev.ircclient.client.SkidIrc;
import de.liquiddev.ircclient.net.IrcPacket;
import de.liquiddev.ircclient.net._ircIIIIIlIIll;
import de.liquiddev.ircclient.net._ircIIIIllIlll;
import de.liquiddev.ircclient.net._ircIIIlIlllII;
import de.liquiddev.ircclient.net._ircIIlIIIlIlI;
import de.liquiddev.ircclient.net._ircIllIIIIllI;
import de.liquiddev.ircclient.net._ircIllIlIlIII;
import de.liquiddev.ircclient.net._irclIIlIlllll;
import de.liquiddev.ircclient.net._irclllIIlIlIl;
import de.liquiddev.ircclient.net._irclllllIIlII;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import javax.net.ssl.SSLSocket;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class _ircIllIllIIlI
implements Runnable {
    private volatile SSLSocket _ircIllIllIIlI;
    private volatile DataInputStream _ircIllIllIIlI;
    private volatile DataOutputStream _ircIllIllIIlI;
    private volatile boolean _ircIllIllIIlI;
    private SkidIrc _ircIllIllIIlI;

    public _ircIllIllIIlI(SkidIrc skidIrc, SSLSocket sSLSocket) {
        this._ircIllIllIIlI = skidIrc;
        this._ircIllIllIIlI = sSLSocket;
        this._ircIllIllIIlI = new DataInputStream(sSLSocket.getInputStream());
        this._ircIllIllIIlI = new DataOutputStream(sSLSocket.getOutputStream());
        this._ircIllIllIIlI = true;
    }

    private InetAddress _ircIllIllIIlI() {
        return this._ircIllIllIIlI.getInetAddress();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void _ircIllIllIIlI(String string) {
        _ircIllIllIIlI _ircIllIllIIlI2 = this;
        synchronized (_ircIllIllIIlI2) {
            Object object;
            if (this._ircIllIllIIlI != null) {
                try {
                    this._ircIllIllIIlI.close();
                }
                catch (Exception exception) {
                    object = exception;
                    exception.printStackTrace();
                }
            }
            object = this;
            if (!Thread.currentThread().equals(((_ircIllIllIIlI)object)._ircIllIllIIlI.getNetworkThread())) {
                System.out.println(new _irclllllIIlII(this).toString());
                return;
            }
            if (!this._ircIllIllIIlI) {
                return;
            }
            this._ircIllIllIIlI = false;
        }
        this._ircIllIlIlIII(string);
    }

    public final boolean _ircIllIllIIlI() {
        return this._ircIllIllIIlI;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void _ircIllIllIIlI(SSLSocket sSLSocket) {
        _ircIllIllIIlI _ircIllIllIIlI2 = this;
        synchronized (_ircIllIllIIlI2) {
            this._ircIllIllIIlI = sSLSocket;
            this._ircIllIllIIlI = new DataInputStream(this._ircIllIllIIlI.getInputStream());
            this._ircIllIllIIlI = new DataOutputStream(this._ircIllIllIIlI.getOutputStream());
            this._ircIllIllIIlI = true;
        }
        this._ircIllIllIIlI();
    }

    private void _ircIllIllIIlI(Throwable throwable) {
        _ircIllIllIIlI _ircIllIllIIlI2 = this;
        if (_ircIllIllIIlI2._ircIllIllIIlI) {
            throwable.printStackTrace();
        }
        this._ircIllIllIIlI(String.valueOf(throwable.getClass().getSimpleName()) + new _ircIllIlIlIII(this).toString() + throwable.getLocalizedMessage());
    }

    public final byte[] _ircIllIllIIlI() {
        try {
            int n = this._ircIllIllIIlI.readInt();
            if (n > 65535) {
                throw new IOException(String.valueOf(new _irclIIlIlllll(this).toString()) + n);
            }
            byte[] byArray = new byte[n];
            this._ircIllIllIIlI.read(byArray);
            return byArray;
        }
        catch (IOException iOException) {
            this._ircIllIllIIlI(iOException);
            return new byte[0];
        }
    }

    public final void _ircIllIllIIlI(byte[] byArray) {
        try {
            if (byArray.length > 65535) {
                throw new IOException(String.valueOf(new _irclllIIlIlIl(this).toString()) + byArray.length);
            }
            this._ircIllIllIIlI.writeInt(byArray.length);
            this._ircIllIllIIlI.write(byArray);
            return;
        }
        catch (IOException iOException) {
            this._ircIllIllIIlI(iOException);
            return;
        }
    }

    public final int _ircIllIllIIlI() {
        try {
            return this._ircIllIllIIlI.readInt();
        }
        catch (IOException iOException) {
            this._ircIllIllIIlI(iOException);
            return 0;
        }
    }

    public final long _ircIllIllIIlI() {
        try {
            return this._ircIllIllIIlI.readLong();
        }
        catch (IOException iOException) {
            this._ircIllIllIIlI(iOException);
            return 0L;
        }
    }

    public final void _ircIllIllIIlI(int n) {
        try {
            this._ircIllIllIIlI.writeInt(n);
            return;
        }
        catch (IOException iOException) {
            this._ircIllIllIIlI(iOException);
            return;
        }
    }

    public final void _ircIllIllIIlI(long l) {
        try {
            this._ircIllIllIIlI.writeLong(l);
            return;
        }
        catch (IOException iOException) {
            this._ircIllIllIIlI(iOException);
            return;
        }
    }

    public final void _ircIllIllIIlI(boolean bl) {
        try {
            this._ircIllIllIIlI.writeBoolean(bl);
            return;
        }
        catch (IOException iOException) {
            this._ircIllIllIIlI(iOException);
            return;
        }
    }

    public final boolean _irclllllIIlII() {
        try {
            return this._ircIllIllIIlI.readBoolean();
        }
        catch (IOException iOException) {
            this._ircIllIllIIlI(iOException);
            return false;
        }
    }

    public final String _ircIllIllIIlI() {
        try {
            String string = this._ircIllIllIIlI.readUTF();
            return string;
        }
        catch (IOException iOException) {
            this._ircIllIllIIlI(iOException);
            return new _ircIIlIIIlIlI(this).toString();
        }
    }

    public final String _ircIllIllIIlI(int n) {
        String string = this._ircIllIllIIlI();
        if (string.length() > 60) {
            string = string.substring(0, 60);
        }
        return string;
    }

    public final void _irclllllIIlII(String string) {
        try {
            if (string == null) {
                string = new _ircIIIlIlllII(this).toString();
            }
            this._ircIllIllIIlI.writeUTF(string);
            return;
        }
        catch (IOException iOException) {
            this._ircIllIllIIlI(iOException);
            return;
        }
    }

    @Override
    public void run() {
        while (this._ircIllIllIIlI) {
            try {
                IrcPacket ircPacket = this._ircIllIllIIlI();
                if (ircPacket == null) continue;
                this._irclllllIIlII(ircPacket);
            }
            catch (Throwable throwable) {
                this._ircIllIllIIlI(throwable);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void _ircIllIllIIlI(IrcPacket ircPacket) {
        DataOutputStream dataOutputStream = this._ircIllIllIIlI;
        synchronized (dataOutputStream) {
            this._ircIllIllIIlI.getApiManager().onPacketOut(ircPacket);
            try {
                this._ircIllIllIIlI.write(this._ircIllIllIIlI.getPacketManager()._ircIllIllIIlI(ircPacket));
                ircPacket.write(this);
            }
            catch (Throwable throwable) {
                this._ircIllIllIIlI(throwable);
            }
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private IrcPacket _ircIllIllIIlI() {
        DataInputStream dataInputStream = this._ircIllIllIIlI;
        synchronized (dataInputStream) {
            int n;
            block5: {
                n = this._ircIllIllIIlI.read();
                if (n > this._ircIllIllIIlI.getPacketManager()._ircIllIllIIlI() && n != 255) {
                    throw new IOException(String.valueOf(new _ircIllIIIIllI(this).toString()) + n);
                }
                if (n != -1) break block5;
                System.out.println(new _ircIIIIIlIIll(this).toString());
                this._ircIllIllIIlI(new _ircIIIIllIlll(this).toString());
                return null;
            }
            Object object = this._ircIllIllIIlI.getPacketManager()._ircIllIllIIlI(n);
            object = (IrcPacket)((Class)object).newInstance();
            object.read(this);
            return object;
        }
    }

    public final IrcClient _ircIllIllIIlI() {
        return this._ircIllIllIIlI;
    }

    public abstract void _ircIllIllIIlI();

    public abstract void _ircIllIlIlIII(String var1);

    public abstract void _irclllllIIlII(IrcPacket var1);
}

