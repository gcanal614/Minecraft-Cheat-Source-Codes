/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient.util;

import de.liquiddev.ircclient.util._ircIIIlIlllII;
import de.liquiddev.ircclient.util._ircIIlIIIlIlI;
import de.liquiddev.ircclient.util._ircIllIIIIllI;
import de.liquiddev.ircclient.util._irclIIlIlllll;
import de.liquiddev.ircclient.util._irclllIIlIlIl;
import java.io.DataInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class IrcServers {
    private static final List _ircIllIllIIlI = Collections.unmodifiableList(Arrays.asList(new _irclIIlIlllll().toString(), new _irclllIIlIlIl().toString(), new _ircIIlIIIlIlI().toString(), new _ircIIIlIlllII().toString()));
    private static final int _ircIllIllIIlI = 25000;

    public static List getDefaultServers() {
        return _ircIllIllIIlI;
    }

    public static int getDefaultPort() {
        return 25000;
    }

    public static String getOnlineServer(List servers, int port) {
        List list;
        for (String string : servers) {
            if (!IrcServers._ircIllIllIIlI(string, port)) continue;
            return string;
        }
        return (String)list.get(0);
    }

    private static boolean _ircIllIllIIlI(String object, int n) {
        try {
            Throwable throwable = null;
            try (Socket socket = new Socket();){
                socket.connect(new InetSocketAddress((String)object, n + 1), (int)TimeUnit.SECONDS.toMillis(5L));
                object = new DataInputStream(socket.getInputStream());
                object = ((DataInputStream)object).readUTF();
                System.out.println((String)object);
                return ((String)object).equalsIgnoreCase(new _ircIllIIIIllI().toString());
            }
            catch (Throwable throwable2) {
                if (throwable == null) {
                    throwable = throwable2;
                } else if (throwable != throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
        }
        catch (Exception exception) {
            return false;
        }
    }
}

