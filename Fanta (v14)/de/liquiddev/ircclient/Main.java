/*
 * Decompiled with CFR 0.152.
 */
package de.liquiddev.ircclient;

import de.liquiddev.ircclient._ircIIIIIlIIll;
import de.liquiddev.ircclient._ircIIIIlIlIlI;
import de.liquiddev.ircclient._ircIIIIllIlll;
import de.liquiddev.ircclient._ircIIIlIlllII;
import de.liquiddev.ircclient._ircIIlIIIlIlI;
import de.liquiddev.ircclient._ircIlIIIIlIlI;
import de.liquiddev.ircclient._ircIllIIIIllI;
import de.liquiddev.ircclient._ircIllIIIlIlI;
import de.liquiddev.ircclient._ircIllIlIIIII;
import de.liquiddev.ircclient._ircIllIlIlIII;
import de.liquiddev.ircclient._irclIIIIlIIII;
import de.liquiddev.ircclient._irclIIlIllIII;
import de.liquiddev.ircclient._irclIIlIlllll;
import de.liquiddev.ircclient._irclllIIlIlIl;
import de.liquiddev.ircclient._irclllIllIIII;
import de.liquiddev.ircclient.api.IrcClient;
import de.liquiddev.ircclient.client.ClientType;
import de.liquiddev.ircclient.client.IrcClientFactory;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        IrcClient ircClient;
        Object object;
        if (args.length != 2) {
            System.out.println(new _ircIllIlIlIII().toString());
            return;
        }
        try {
            object = ClientType.valueOf(args[0].toUpperCase());
        }
        catch (IllegalArgumentException illegalArgumentException) {
            System.out.println(new _ircIIIIllIlll().toString());
            return;
        }
        System.out.println(String.valueOf(new _ircIllIIIlIlI().toString()) + ((ClientType)((Object)object)).getName() + new _ircIIIIlIlIlI().toString());
        Object object2 = IrcClientFactory.getDefault();
        ircClient = ((IrcClientFactory)object2).createIrcClient((ClientType)((Object)object), ircClient[1], null, new _ircIllIlIIIII().toString());
        System.out.println(ircClient.getNickname());
        ircClient.getApiManager().registerApi(new _ircIlIIIIlIlI().setPrefix(new _irclIIlIllIII().toString()));
        ircClient.getApiManager().registerCustomDataListener((ircPlayer, string, byArray) -> System.out.println(String.valueOf(ircPlayer.getIrcNick()) + new _irclllIllIIII().toString() + string));
        object = new Scanner(System.in);
        while (!ircClient.isForcedDisconnect()) {
            object2 = ((Scanner)object).nextLine();
            if (((String)object2).equalsIgnoreCase(new _irclIIIIlIIII().toString())) {
                ircClient.disconnect();
                continue;
            }
            if (((String)object2).startsWith(new _irclIIlIlllll().toString())) {
                ircClient.executeCommand((String)object2);
                continue;
            }
            if (((String)object2).startsWith(new _irclllIIlIlIl().toString())) {
                ircClient.sendCustomData(new _ircIIlIIIlIlI().toString(), new byte[0]);
                continue;
            }
            ircClient.sendChatMessage((String)object2);
        }
        ((Scanner)object).close();
    }

    private static void _ircIllIllIIlI(IrcClient ircClient) {
        ircClient.getApiManager().registerCustomDataListener((ircPlayer, object, byArray) -> {
            if (!((String)object).equalsIgnoreCase(new _ircIIIlIlllII().toString())) {
                return;
            }
            object = ByteBuffer.wrap(byArray);
            int n = ((ByteBuffer)((Object)object)).getInt();
            System.out.println(String.valueOf(ircPlayer.getIrcNick()) + new _ircIllIIIIllI().toString() + n);
        });
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        byteBuffer.putInt(4);
        byteBuffer.flip();
        byte[] byArray2 = new byte[byteBuffer.remaining()];
        byteBuffer.get(byArray2);
        ircClient.sendCustomData(new _ircIIIIIlIIll().toString(), byArray2);
    }
}

