/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  ave
 *  axu
 *  bcx
 *  ek
 *  el
 *  ep
 *  eu
 *  fb
 *  ff
 *  jc
 *  jl
 */
package net.minecraft.realms;

import java.net.InetAddress;
import java.net.UnknownHostException;
import net.minecraft.realms.DisconnectedRealmsScreen;
import net.minecraft.realms.Realms;
import net.minecraft.realms.RealmsScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsConnect {
    private static final Logger LOGGER = LogManager.getLogger();
    private final RealmsScreen onlineScreen;
    private volatile boolean aborted = false;
    private ek connection;

    public RealmsConnect(RealmsScreen realmsScreen) {
        this.onlineScreen = realmsScreen;
    }

    public void connect(final String string, final int n) {
        Realms.setConnectedToRealms(true);
        new Thread("Realms-connect-task"){

            @Override
            public void run() {
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getByName(string);
                    if (RealmsConnect.this.aborted) {
                        return;
                    }
                    RealmsConnect.this.connection = ek.a((InetAddress)inetAddress, (int)n, (boolean)ave.A().t.f());
                    if (RealmsConnect.this.aborted) {
                        return;
                    }
                    RealmsConnect.this.connection.a((ep)new bcx(RealmsConnect.this.connection, ave.A(), (axu)RealmsConnect.this.onlineScreen.getProxy()));
                    if (RealmsConnect.this.aborted) {
                        return;
                    }
                    RealmsConnect.this.connection.a((ff)new jc(47, string, n, el.d));
                    if (RealmsConnect.this.aborted) {
                        return;
                    }
                    RealmsConnect.this.connection.a((ff)new jl(ave.A().L().e()));
                }
                catch (UnknownHostException \u26032) {
                    Realms.clearResourcePack();
                    if (RealmsConnect.this.aborted) {
                        return;
                    }
                    LOGGER.error("Couldn't connect to world", (Throwable)\u26032);
                    ave.A().R().f();
                    Realms.setScreen(new DisconnectedRealmsScreen(RealmsConnect.this.onlineScreen, "connect.failed", (eu)new fb("disconnect.genericReason", new Object[]{"Unknown host '" + string + "'"})));
                }
                catch (Exception \u26033) {
                    Realms.clearResourcePack();
                    if (RealmsConnect.this.aborted) {
                        return;
                    }
                    LOGGER.error("Couldn't connect to world", (Throwable)\u26033);
                    String string2 = \u26033.toString();
                    if (inetAddress != null) {
                        \u2603 = inetAddress.toString() + ":" + n;
                        string2 = string2.replaceAll(\u2603, "");
                    }
                    Realms.setScreen(new DisconnectedRealmsScreen(RealmsConnect.this.onlineScreen, "connect.failed", (eu)new fb("disconnect.genericReason", new Object[]{string2})));
                }
            }
        }.start();
    }

    public void abort() {
        this.aborted = true;
    }

    public void tick() {
        if (this.connection != null) {
            if (this.connection.g()) {
                this.connection.a();
            } else {
                this.connection.l();
            }
        }
    }
}

